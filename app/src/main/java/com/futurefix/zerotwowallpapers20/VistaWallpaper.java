package com.futurefix.zerotwowallpapers20;

import android.Manifest;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.futurefix.zerotwowallpapers20.adaptadores.WallpaperService;
import com.futurefix.zerotwowallpapers20.modelos.Wallpaper;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.IOException;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class VistaWallpaper extends AppCompatActivity {

    ImageView img_fondo, img_ampliada,img,imgSincortes;
    ImageButton cerrar;
    Uri url;
    ImageButton setwpp, descarga, favoritos;
    boolean ampliado=false;
    AdView banner;
    LottieAnimationView animCarga;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_vista_wallpaper);

        cerrar = findViewById(R.id.boton_cerrar_vista);
        img = findViewById(R.id.imagen_vista);
        imgSincortes = findViewById(R.id.imagen_sinCortes);
        img_ampliada = findViewById(R.id.imagen_vista_ampliada);
        img_fondo = findViewById(R.id.imagen_vista_fondo);
        setwpp = findViewById(R.id.buttonsetwpp);
        descarga = findViewById(R.id.buttondescarga);
        animCarga = findViewById(R.id.animacion_view);
        banner = findViewById(R.id.adViewBannerVista);
        favoritos = findViewById(R.id.buttonfavorito);

        //Anuncios
        AdRequest adRequest = new AdRequest.Builder().build();
        banner.loadAd(adRequest);

        final Intent intent = getIntent();
        Wallpaper wallpa = (Wallpaper) intent.getSerializableExtra("wpp");
        url = Uri.parse(wallpa.getUrl());

        if (WallpaperService.favoritos.contains(wallpa)){
            favoritos.setImageResource(R.drawable.ic_favorito);
        }else {
            favoritos.setImageResource(R.drawable.ic_nofav);
        }

        Glide.with(this).load(url).apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 3))).into(img_fondo);
        Glide.with(this).load(url).into(img);
        Glide.with(this).load(url).into(imgSincortes);
        Glide.with(this).load(url).into(img_ampliada);

        animCarga.loop(false);

        descarga.setOnClickListener(v -> {
            solicitarpermisoguardar();
            solicitarpermisosleer();
            BitmapDrawable drawable = (BitmapDrawable) imgSincortes.getDrawable();
            Bitmap bitmapShido = drawable.getBitmap();
            Save savefile = new Save();
            savefile.SaveImage(this, bitmapShido);
        });

        img_ampliada.setOnClickListener(v -> {
            if (ampliado){
                img_ampliada.setVisibility(View.GONE);
                banner.setVisibility(View.VISIBLE);
                ampliado = !ampliado;
            }
        });

        img.setOnClickListener(v -> {
            if (!ampliado){
                img_ampliada.setVisibility(View.VISIBLE);
                banner.setVisibility(View.GONE);
                ampliado = !ampliado;
            }
        });

        setwpp.setOnClickListener(v -> cortarWallpaper());

        favoritos.setOnClickListener(v -> {
            if (!WallpaperService.favoritos.contains(wallpa)){
                WallpaperService.addWallpaperFavoritos(wallpa);
                favoritos.setImageResource(R.drawable.ic_favorito);
                Auxiliar.identi.remove(wallpa.getId());
                Toast.makeText(VistaWallpaper.this, "Añadido a favoritos", Toast.LENGTH_SHORT).show();
            }else {
                WallpaperService.removeWallpaperFavoritos(wallpa);
                favoritos.setImageResource(R.drawable.ic_nofav);
                if (!Auxiliar.identi.contains(wallpa.getId())){
                    Auxiliar.identi.add(wallpa.getId());
                }
                Toast.makeText(VistaWallpaper.this, "Eliminado de favoritos", Toast.LENGTH_SHORT).show();
            }
        });

        cerrar.setOnClickListener(v -> {
            Auxiliar.iteradorAnuncios ++;
            finish();
        });
    }

    public void cortarWallpaper (){
        if (!Auxiliar.estadoactualCheckBox){
            Toast.makeText(VistaWallpaper.this, "Estableciendo...", Toast.LENGTH_SHORT).show();
            try {
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
                BitmapDrawable drawable = (BitmapDrawable) imgSincortes.getDrawable();
                Bitmap bit = drawable.getBitmap();
                wallpaperManager.setBitmap(bit);
                Toast.makeText(VistaWallpaper.this, "Listo!", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(VistaWallpaper.this, "Error, No se pudo establecer el wallpaper", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }else {
            Toast.makeText(VistaWallpaper.this, "Estableciendo...", Toast.LENGTH_SHORT).show();
            try {
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
                BitmapDrawable drawable = (BitmapDrawable) img.getDrawable();
                Bitmap bit = drawable.getBitmap();
                wallpaperManager.setBitmap(bit);
                Toast.makeText(VistaWallpaper.this, "Listo!", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(VistaWallpaper.this, "Error, No se pudo establecer el wallpaper", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (ampliado){
            img_ampliada.setVisibility(View.GONE);
            banner.setVisibility(View.VISIBLE);
            ampliado = !ampliado;
        }else{
            Auxiliar.iteradorAnuncios ++;
            finish();
        }
    }

    private  void  solicitarpermisoguardar() {
        int permisoguardar = ActivityCompat.checkSelfPermission(VistaWallpaper.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permisoguardar != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 111);
            }
        }
    }

    private void solicitarpermisosleer(){
        int permisoleer = ActivityCompat.checkSelfPermission(VistaWallpaper.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permisoleer !=PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
            }
        }
    }
}