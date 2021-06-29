package com.futurefix.zerotwowallpapers20;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    boolean reintentar = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_splash);

        // Agregar animaciones

        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba);
        Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo);

        // Referenciar los id ahora si

        TextView PorTextView =findViewById(R.id.PorTextView);
        TextView FutureFixTextView =findViewById(R.id.FutureFixTextView);
        ImageView LogoImageView = findViewById(R.id.LogoImageView);

        // Asignar animaciones

        PorTextView.setAnimation(animation2);
        FutureFixTextView.setAnimation(animation2);
        LogoImageView.setAnimation(animation1);

        //Validar si tiene conexion para dejarlo entrar en la aplicacion
        if (!EsCon(SplashActivity.this)) builderDialog(SplashActivity.this).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!EsCon(SplashActivity.this)) builderDialog(SplashActivity.this).show();
    }

    public Boolean EsCon(Context context){

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnectedOrConnecting()){

            NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            new Handler().postDelayed(() -> {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }, 1300);

            return mobile != null && mobile.isConnectedOrConnecting() ||
                    (wifi != null && wifi.isConnectedOrConnecting());
        }
        else return false;

    }

    //Alerta si no tiene conexion
    public AlertDialog.Builder builderDialog(Context c){

        AlertDialog.Builder builder = new AlertDialog.Builder(c);

        builder.setTitle("Sin Conexion a Internet");
        builder.setMessage("Esta aplicacion requiere de conexion a internet estable para cargar el contenido");
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.cerrar, (dialog, which) -> finish());
        builder.setNegativeButton(R.string.ajustes, (dialog, which) -> startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)));
        // builder.setNeutralButton(R.string.reintentar, (dialog, which) -> reintentar=true);
        return builder;
    }
}