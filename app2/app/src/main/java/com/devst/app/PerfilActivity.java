package com.devst.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class PerfilActivity extends AppCompatActivity {

    private TextView tvEmail;
    private TextView tvNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil);

        tvEmail = findViewById(R.id.tvEmail);
        tvNombre = findViewById(R.id.tvNombre);

        // Get email from intent
        String email = getIntent().getStringExtra("email_usuario");
        if (email != null) {
            tvEmail.setText("Correo: " + email);
        } else {
            tvEmail.setText("Correo: No disponible");
        }

        // Example static name or info — replace this with real data if needed
        tvNombre.setText("Nombre: Viajero Aventurero");
    }
}
