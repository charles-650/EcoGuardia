package com.devst.ecoguardia;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetalleReporte extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_reporte);

        TextView txtTitulo = findViewById(R.id.txtTituloDetalle);
        TextView txtDescripcion = findViewById(R.id.txtDescripcionDetalle);
        TextView txtUbicacion = findViewById(R.id.txtUbicacionDetalle);
        Button btnVolver = findViewById(R.id.btnVolver);

        // Recepción y validación de datos enviados por Intent Explícito
        if (getIntent() != null && getIntent().getExtras() != null) {
            String titulo = getIntent().getStringExtra("TITULO");
            String descripcion = getIntent().getStringExtra("DESCRIPCION");
            String ubicacion = getIntent().getStringExtra("UBICACION");

            txtTitulo.setText(titulo != null ? titulo : "Sin título");
            txtDescripcion.setText(descripcion != null ? descripcion : "Sin descripción");
            txtUbicacion.setText(ubicacion != null ? ubicacion : "Sin ubicación");
        }

        btnVolver.setOnClickListener(v -> finish());
    }
}