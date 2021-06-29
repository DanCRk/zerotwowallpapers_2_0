package com.futurefix.zerotwowallpapers20;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.futurefix.zerotwowallpapers20.adaptadores.WallpaperService;
import com.futurefix.zerotwowallpapers20.adaptadores.WallpaperAdapterFav;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Objects;

public class FavActivity extends AppCompatActivity {

    RecyclerView rc;
    TextView tv, cuantos;
    ImageButton atras;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallpapers_fav);

        rc = findViewById(R.id.recyclerViewWallpaperCategorias);
        tv = findViewById(R.id.texto_toolbar);
        cuantos = findViewById(R.id.cuantoswpps);
        atras = findViewById(R.id.botonatras);
        adView = findViewById(R.id.adViewBannerCategorias);

        // Anuncios
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        atras.setVisibility(View.VISIBLE);

        atras.setOnClickListener(v -> finish());

        tv.setText(R.string.favoritos);

        cargarLista();

        cargarDatosPrincipales();
    }

    public void cargarLista() {
        WallpaperAdapterFav adapter = null;
        switch (Auxiliar.estadoSelectorColumnas){
            case 0:
                rc.setLayoutManager(new LinearLayoutManager(this));
                adapter = new WallpaperAdapterFav(WallpaperService.favoritos, R.layout.item_wallpaper_1, this, this);
                break;
            case 1:
                rc.setLayoutManager(new GridLayoutManager(this, 2));
                adapter = new WallpaperAdapterFav(WallpaperService.favoritos, R.layout.item_wallpaper_2, this, this);
                break;
            case 2:
                rc.setLayoutManager(new GridLayoutManager(this, 3));
                adapter = new WallpaperAdapterFav(WallpaperService.favoritos, R.layout.item_wallpaper_3, this, this);
                break;
            case 3:
                rc.setLayoutManager(new GridLayoutManager(this, 4));
                adapter = new WallpaperAdapterFav(WallpaperService.favoritos, R.layout.item_wallpaper_4, this, this);
                break;
            default:
                break;
        }
        rc.setAdapter(adapter);
    }

    public void cargarDatosPrincipales (){
        try {
            String cuantoswpps = String.valueOf(WallpaperService.favoritos.size());
            int cuanto = WallpaperService.favoritos.size();
            if (cuanto == 1){
                cuantos.setText(String.format("%s Wallpaper", cuantoswpps));
            }else {
                cuantos.setText(String.format("%s Wallpapers", cuantoswpps));
            }
            cuantos.setVisibility(View.VISIBLE);
            Objects.requireNonNull(rc.getAdapter()).notifyDataSetChanged();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error, no se pueden cargar los wallpapers", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Objects.requireNonNull(rc.getAdapter()).notifyDataSetChanged();
        try {
            String cuantoswpps = String.valueOf(WallpaperService.favoritos.size());
            int cuanto = WallpaperService.favoritos.size();
            if (cuanto == 1){
                cuantos.setText(String.format("%s Wallpaper", cuantoswpps));
            }else {
                cuantos.setText(String.format("%s Wallpapers", cuantoswpps));
            }
            cuantos.setVisibility(View.VISIBLE);
            Objects.requireNonNull(rc.getAdapter()).notifyDataSetChanged();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error, no se pueden cargar los wallpapers", Toast.LENGTH_LONG).show();
        }
    }
}