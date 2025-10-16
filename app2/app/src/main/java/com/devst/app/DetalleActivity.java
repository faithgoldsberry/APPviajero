package com.devst.app;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DetalleActivity extends AppCompatActivity {

    private TextView tvTitulo, tvHora, tvLugar, tvInstrucciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle);

        // Set up toolbar with back button
        Toolbar toolbar = findViewById(R.id.toolbarDetalle);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Detalle del Itinerario");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Referencias a los TextViews
        tvTitulo = findViewById(R.id.tvDetalleTitulo);
        tvHora = findViewById(R.id.tvDetalleHora);
        tvLugar = findViewById(R.id.tvDetalleLugar);
        tvInstrucciones = findViewById(R.id.tvDetalleInstrucciones);

        // Obtener datos del intent
        String titulo = getIntent().getStringExtra("step_title");
        String hora = getIntent().getStringExtra("step_time");
        String lugar = getIntent().getStringExtra("step_location");
        String instrucciones = getIntent().getStringExtra("step_instructions");

        // Mostrar los datos
        tvTitulo.setText(titulo != null ? titulo : "Sin título");
        tvHora.setText("Hora: " + (hora != null ? hora : "No definida"));
        tvLugar.setText("Lugar: " + (lugar != null ? lugar : "No definido"));
        tvInstrucciones.setText("Instrucciones:\n" + (instrucciones != null ? instrucciones : "No hay instrucciones"));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // volver atrás
        return true;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
