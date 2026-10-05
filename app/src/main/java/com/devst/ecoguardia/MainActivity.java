package com.devst.ecoguardia;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    // Componentes de la interfaz
    private ImageView imgPreview;
    private Button btnMap, btnWeb, btnCall, btnEmail, btnCamera;
    private Button btnDetalle, btnFormulario, btnStartService;

    // Launcher para solicitar permiso de cámara
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    abrirCamara();
                } else {
                    Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show();
                }
            });

    // Launcher para capturar foto (MediaStore.ACTION_IMAGE_CAPTURE)
    private final ActivityResultLauncher<Intent> cameraLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bundle extras = result.getData().getExtras();
                    if (extras != null && extras.get("data") != null) {
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        imgPreview.setImageBitmap(imageBitmap);
                    }
                }
            });

    // Launcher para intent explícito con retorno de resultado
    private final ActivityResultLauncher<Intent> formularioLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String mensaje = result.getData().getStringExtra("mensaje_resultado");
                    Toast.makeText(this, "Resultado: " + mensaje, Toast.LENGTH_LONG).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar vistas
        imgPreview = findViewById(R.id.imgPreview);
        btnMap = findViewById(R.id.btnMap);
        btnWeb = findViewById(R.id.btnWeb);
        btnCall = findViewById(R.id.btnCall);
        btnEmail = findViewById(R.id.btnEmail);
        btnCamera = findViewById(R.id.btnCamera);

        btnDetalle = findViewById(R.id.btnDetalle);
        btnFormulario = findViewById(R.id.btnFormulario);
        btnStartService = findViewById(R.id.btnStartService);

        // ==========================================
        // 5 INTENTS IMPLÍCITOS
        // ==========================================

        // 1. Abrir Mapa (geo:lat,lng?q=texto)
        btnMap.setOnClickListener(v -> {
            Uri gmmIntentUri = Uri.parse("geo:-33.4489,-70.6693?q=Punto+Verde+Reciclaje");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            ejecutarIntentSeguro(mapIntent, "No se encontró una aplicación de mapas.");
        });

        // 2. Abrir Página Web (ACTION_VIEW)
        btnWeb.setOnClickListener(v -> {
            Uri webpage = Uri.parse("https://www.gob.cl/noticias/cuidado-del-medio-ambiente/");
            Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
            ejecutarIntentSeguro(webIntent, "No hay navegador web instalado.");
        });

        // 3. Marcador telefónico (ACTION_DIAL)
        btnCall.setOnClickListener(v -> {
            Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:800123456"));
            ejecutarIntentSeguro(dialIntent, "No se puede abrir el marcador telefónico.");
        });

        // 4. Enviar correo electrónico (ACTION_SENDTO con mailto:)
        btnEmail.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:contacto@ecoguardia.cl"));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Reporte Ambiental Ciudadano");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Estimados, quisiera reportar el siguiente problema...");
            ejecutarIntentSeguro(emailIntent, "No se encontró una aplicación de correo.");
        });

        // 5. Tomar fotografía con la cámara
        btnCamera.setOnClickListener(v -> verificarYTomarFoto());

        // ==========================================
        // 3 INTENTS EXPLÍCITOS
        // ==========================================

        // 1. Abrir DetalleActivity pasando datos extras (putExtra)
        btnDetalle.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DetalleReporte.class);
            intent.putExtra("TITULO", "Microbasural en Av. Central");
            intent.putExtra("DESCRIPCION", "Acumulación de residuos sólidos en la vía pública.");
            intent.putExtra("UBICACION", "Lat: -33.4489, Lng: -70.6693");
            startActivity(intent);
        });

        // 2. Abrir FormularioReporteActivity esperando un resultado
        btnFormulario.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FormularioReporteActivity.class);
            formularioLauncher.launch(intent);
        });

        // 3. Iniciar Servicio en Segundo Plano (SyncService)
        btnStartService.setOnClickListener(v -> {
            Intent serviceIntent = new Intent(MainActivity.this, SyncService.class);
            startService(serviceIntent);
            Toast.makeText(this, "Servicio de sincronización iniciado...", Toast.LENGTH_SHORT).show();
        });
    }

    // Método de validación para evitar cierres inesperados (ActivityNotFoundException)
    private void ejecutarIntentSeguro(Intent intent, String mensajeError) {
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, mensajeError, Toast.LENGTH_SHORT).show();
        }
    }

    // Validación de permisos de cámara en tiempo de ejecución
    private void verificarYTomarFoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            abrirCamara();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }

    private void abrirCamara() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            cameraLauncher.launch(takePictureIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No se puede acceder a la cámara", Toast.LENGTH_SHORT).show();
        }
    }
}