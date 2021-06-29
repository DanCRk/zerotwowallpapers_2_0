package com.futurefix.zerotwowallpapers20;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.futurefix.zerotwowallpapers20.adaptadores.WallpaperService;
import com.futurefix.zerotwowallpapers20.fragments.ZeroFragment;
import com.futurefix.zerotwowallpapers20.modelos.Wallpaper;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.navigation.NavigationView;
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    Fragment newFragment = null;
    private String url;
    TextView textoToolbar;
    private InterstitialAd mInterstitial;
    SharedPreferences sharedPreferences, sharedPreferences3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cargarTodosDatos();

        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        sharedPreferences3 = getPreferences(Context.MODE_PRIVATE);

        // Inicializar los anuncios
        MobileAds.initialize(this, initializationStatus -> {
        });

        // Add intestical

        cargarAdd();

        url= "https://play.google.com/store/apps/details?id=com.futurefix.zerotwowallpapers20";

        // Referenciar las weas locas
        textoToolbar = findViewById(R.id.texto_toolbar);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);

        textoToolbar.setText(R.string.ZeroTwoWpp);

        // Solicitar los datos de todos los wallpapers



        // Icono para el menu lateral

        Drawable icono = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_barrasmenu, this.getTheme());

        // Establecer evento onclick al navigationView

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        actionBarDrawerToggle.setHomeAsUpIndicator(icono);
        actionBarDrawerToggle.setToolbarNavigationClickListener(v -> {
            if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        actionBarDrawerToggle.syncState();

        // Cargar fragment principal
        newFragment = new ZeroFragment();
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);

        // Para que la mmda no se cargue dos veces :)
        comprobar(currentFragment);

        try {
            borrarWallpaper();
        }catch (Exception ignored){

        }

        Auxiliar.guardarEstadoCheckBox(load());
        Auxiliar.guardarEstadoelectorColumnas(load2());
        loadWallpapers();
    }

    private void comprobar(Fragment currentFragment){
        if (currentFragment == null) {
            //carga del primer fragment justo en la carga inicial de la app
            loadFragment(newFragment);
        } else if (!currentFragment.getClass().getName().equalsIgnoreCase(newFragment.getClass().getName())) {
            //currentFragment no concide con newFragment
            loadFragment(newFragment);
        }
    }

    private void loadFragment(Fragment newFragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, newFragment, newFragment.getClass().getName())
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if (menuItem.getItemId() == R.id.favoritos_menu) {
            Intent i = new Intent(this, FavActivity.class);
            startActivity(i);
            textoToolbar.setText(R.string.favoritos);
        }
        if (menuItem.getItemId()==R.id.comparte_menu){
            Intent compartir = new Intent(Intent.ACTION_SEND);
            compartir.setType("text/plain");
            String mensaje = "Mira esta app de Wallpapers de Zero Two: "+url;
            compartir.putExtra(Intent.EXTRA_TEXT, mensaje);
            startActivity(Intent.createChooser(compartir,"Compartir via"));
        }
        if (menuItem.getItemId() == R.id.calificanos_menu) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            finish();
        }
        if (menuItem.getItemId() == R.id.configuracion_menu) {
            Intent intent = new Intent(this, ConfigActivity.class);
            startActivity(intent);
        }
        if (menuItem.getItemId()==R.id.info_menu){
            Intent intent = new Intent(this, AcercaActivity.class);
            startActivity(intent);
        }
        return false;
    }

    public void cargarTodosDatos() {

    }

    public  void cargarAdd(){
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-2030839089746380/9362041357", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitial = interstitialAd;
                Log.i("nocarga", "onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                Log.i("algo", loadAdError.getMessage());
                mInterstitial = null;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        textoToolbar.setText(R.string.ZeroTwoWpp);
        save(Auxiliar.estadoactualCheckBox);
        saveColumnas(Auxiliar.estadoSelectorColumnas);
        saveWallpaperFavoritos();
        if (Auxiliar.iteradorAnuncios>=4){
            if (mInterstitial!= null) {
                mInterstitial.show(this);
                cargarAdd();
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }
            Auxiliar.iteradorAnuncios=1;
        }
        Auxiliar.guardarEstadoCheckBox(load());
        Auxiliar.guardarEstadoelectorColumnas(load2());
        try {
            borrarWallpaper();
        }catch (Exception ignored){

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        loadWallpapers();
    }

    private void save(boolean isChecked) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("check", isChecked);
        editor.apply();
    }

    private void saveColumnas(int columnas){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("columnas", columnas);
        editor.apply();
    }

    private boolean load() {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("check", false);
    }

    private int load2() {
        SharedPreferences sharedPreferences2 = getPreferences(Context.MODE_PRIVATE);
        return sharedPreferences2.getInt("columnas", 2);
    }

    private void saveWallpaperFavoritos(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (Wallpaper wallpaper : WallpaperService.favoritos){
            if (!sharedPreferences.getString(wallpaper.getId(), "").equals(wallpaper.getId())){
                editor.putString(wallpaper.getId(), wallpaper.getId());
                editor.apply();
            }
        }
    }

    public void borrarWallpaper(){
        SharedPreferences sharedPreferences1 = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences1.edit();
        for (String s1 : Auxiliar.identi){
            edit.remove(s1);
            edit.apply();
        }
    }

    private void loadWallpapers (){
        SharedPreferences sharedPreferences2 = getPreferences(Context.MODE_PRIVATE);
        for (Wallpaper wallpaper : WallpaperService.wallpaperZero){
            if (sharedPreferences2.getString(wallpaper.getId(), "").equals(wallpaper.getId())){
                if (!WallpaperService.favoritos.contains(wallpaper)){
                    WallpaperService.addWallpaperFavoritos(wallpaper);
                }
            }
        }
    }
}
