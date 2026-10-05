package com.devst.ecoguardia;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class FormularioReporteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Para simplificar, generamos el botón directamente
        Button btnConfirmar = new Button(this);
        btnConfirmar.setText("Confirmar y Guardar Reporte");
        setContentView(btnConfirmar);

        btnConfirmar.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("mensaje_resultado", "Reporte registrado con éxito.");
            setResult(RESULT_OK, resultIntent);
            finish(); // Cierra la Activity y retorna el resultado
        });
    }
}