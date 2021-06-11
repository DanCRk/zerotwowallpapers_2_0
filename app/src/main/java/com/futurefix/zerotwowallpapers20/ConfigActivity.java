package com.futurefix.zerotwowallpapers20;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class ConfigActivity extends AppCompatActivity{

    CheckBox mCheckBox;
    boolean estadoAviso = false;
    TextView aviso, notis, app, toolbar;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        String url = "https://play.google.com/store/apps/details?id=com.futurefix.wallsnakano";

        TextView califica = findViewById(R.id.Calificar);
        mCheckBox = findViewById(R.id.checkBox);
        app = findViewById(R.id.AppConfig);
        notis = findViewById(R.id.Notification);
        toolbar = findViewById(R.id.texto_toolbar);

        toolbar.setText(R.string.configuraciones);

        califica.setOnClickListener(v -> {
             {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        aviso = findViewById(R.id.Aviso);

        aviso.setOnClickListener(v -> {
            setContentView(R.layout.aviso_activity);
            estadoAviso = true;
        });

        notis.setOnClickListener(v -> {
            Intent intent = new Intent();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
            } else {
                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                intent.putExtra("app_package", getPackageName());
                intent.putExtra("app_uid", getApplicationInfo().uid);
            }
            startActivity(intent);
        });

        app.setOnClickListener(v -> {
            Intent intente = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intente.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intente);
        });
    }

    @Override
    public void onBackPressed() {
        if (estadoAviso){
            setContentView(R.layout.activity_config);
            estadoAviso = !estadoAviso;
        }else {
            Estado.guardarEstadoCheckBox(mCheckBox.isChecked());
            finish();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        save(mCheckBox.isChecked());
    }

    @Override
    public void onResume() {
        super.onResume();
        mCheckBox.setChecked(load());
        Estado.guardarEstadoCheckBox(mCheckBox.isChecked());
    }

    private void save(final boolean isChecked) {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("check", isChecked); editor.apply();
    }

    private boolean load() {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("check", false);
    }
}