package com.devst.app;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class HomeActivity extends AppCompatActivity {

    private String emailUsuario = "";
    private TextView tvBienvenida;

    // Activity Result (for ProfileActivity - from menu)
    private final ActivityResultLauncher<Intent> editarPerfilLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String nombre = result.getData().getStringExtra("nombre_editado");
                    if (nombre != null) {
                        tvBienvenida.setText("Hola, " + nombre);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // UI references
        tvBienvenida = findViewById(R.id.tvBienvenida);
        Button btnAbrirWeb = findViewById(R.id.btnAbrirWeb);
        Button btnEnviarCorreo = findViewById(R.id.btnEnviarCorreo);
        Button btnAbrirMapa = findViewById(R.id.btnAbrirMapa);
        Button btnLlamarTaxi = findViewById(R.id.btnLlamarTaxi);
        Button btnConfigurarWifi = findViewById(R.id.btnConfigurarWifi);
        Button btnItineraryStep1 = findViewById(R.id.btnItineraryStep1);  // Explicit Intent

        // Welcome text
        emailUsuario = getIntent().getStringExtra("email_usuario");
        if (emailUsuario == null) emailUsuario = "";
        tvBienvenida.setText("Bienvenido: " + emailUsuario);

        // 1. Implicit Intent → Open travel review site
        btnAbrirWeb.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://www.tripadvisor.com");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

        // 2. Implicit Intent → Send email feedback
        btnEnviarCorreo.setOnClickListener(v -> {
            Intent email = new Intent(Intent.ACTION_SENDTO);
            email.setData(Uri.parse("mailto:"));
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{"reviews@travelapp.com"});
            email.putExtra(Intent.EXTRA_SUBJECT, "Opinión sobre mi experiencia de viaje");

            String cuerpo = "Hola,\n\n" +
                    "Quisiera compartir mi opinión sobre el lugar que visité recientemente. " +
                    "Aquí está mi reseña:\n\n[Escribe tu reseña aquí]\n\n" +
                    "¡Gracias por ayudarnos a mejorar la experiencia de viaje!\n\nSaludos.";
            email.putExtra(Intent.EXTRA_TEXT, cuerpo);

            startActivity(Intent.createChooser(email, "Enviar correo con:"));
        });

        // 3. Implicit Intent → Open map to specific location
        btnAbrirMapa.setOnClickListener(v -> {
            Uri mapUri = Uri.parse("geo:-33.447487,-70.673676?q=Universidad Santo Tomás");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
            mapIntent.setPackage("com.google.android.apps.maps");

            PackageManager pm = getPackageManager();
            if (mapIntent.resolveActivity(pm) != null) {
                startActivity(mapIntent);
            } else {
                // Fallback: open in browser
                String url = "https://www.google.com/maps/search/?api=1&query=-33.447487,-70.673676";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });

        // 4. Implicit Intent → Dial taxi number
        btnLlamarTaxi.setOnClickListener(v -> {
            String telefonoTaxi = "tel:+56912345678";
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse(telefonoTaxi));
            startActivity(intent);
        });

        // 5. Implicit Intent → Open Wi-Fi settings
        btnConfigurarWifi.setOnClickListener(v -> {
            Intent intent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "No se pudo abrir la configuración de Wi-Fi.", Toast.LENGTH_SHORT).show();
            }
        });

        // ✅ Explicit Intent → Open itinerary detail activity
        btnItineraryStep1.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, DetalleActivity.class);
            intent.putExtra("step_title", "City Tour");
            intent.putExtra("step_time", "9:00 AM");
            intent.putExtra("step_location", "Plaza de Armas");
            intent.putExtra("step_instructions", "Reúnete en el punto de encuentro con tu guía.");
            startActivity(intent);
        });
    }

    // ===== Top-right menu =====
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_perfil) {
            Intent i = new Intent(this, PerfilActivity.class);
            i.putExtra("email_usuario", emailUsuario);
            editarPerfilLauncher.launch(i);
            return true;
        } else if (id == R.id.action_web) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://developer.android.com")));
            return true;
        } else if (id == R.id.action_salir) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
