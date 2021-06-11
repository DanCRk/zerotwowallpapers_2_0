package com.futurefix.zerotwowallpapers20;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AcercaActivity extends AppCompatActivity {

    TextView textView, textView1, textView2;
    ImageView imageView;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca);

        // Agregar animaciones

        Animation animation1 = AnimationUtils.loadAnimation(this,R.anim.desplazamiento_arriba);
        Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo);
        Animation animation3 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba2);

        // Crear clases y Referencias id´s

        textView1 = findViewById(R.id.version);
        textView2 = findViewById(R.id.nombre);
        textView = findViewById(R.id.compartir_acercade);
        TextView PorTextView =findViewById(R.id.PorTextView);
        TextView FutureFixTextView =findViewById(R.id.FutureFixTextView);
        imageView = findViewById(R.id.imagen_logo);

        // Asignar animaciones

        textView2.setAnimation(animation3);
        textView1.setAnimation(animation3);
        textView.setAnimation(animation2);
        PorTextView.setAnimation(animation2);
        FutureFixTextView.setAnimation(animation2);
        imageView.setAnimation(animation1);

        url= "https://play.google.com/store/apps/details?id=com.futurefix.wallsnakano";
        textView.setOnClickListener(v -> {
            Intent compartir = new Intent(Intent.ACTION_SEND);
            compartir.setType("text/plain");
            String mensaje = "Mira esta app de Wallpapers de las Quintillizas: "+url;
            compartir.putExtra(Intent.EXTRA_TEXT, mensaje);
            startActivity(Intent.createChooser(compartir,"Compartir via"));
        });
    }
}