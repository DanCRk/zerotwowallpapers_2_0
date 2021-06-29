package com.futurefix.zerotwowallpapers20.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.futurefix.zerotwowallpapers20.Auxiliar;
import com.futurefix.zerotwowallpapers20.R;
import com.futurefix.zerotwowallpapers20.adaptadores.WallpaperAdapter;
import com.futurefix.zerotwowallpapers20.adaptadores.WallpaperService;
import com.futurefix.zerotwowallpapers20.modelos.Wallpaper;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class ZeroFragment extends Fragment {

    RecyclerView rc;
    AdView banner;

    public ZeroFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wallpapers_fragment, container, false);
        banner = view.findViewById(R.id.adViewBannerTabs);

        //Anuncios
        AdRequest adRequest = new AdRequest.Builder().build();
        banner.loadAd(adRequest);

        // Referenciar
        rc = view.findViewById(R.id.recyclerViewWallpaper);
        // Cargar Lista
        cargarLista();
        // Cargar Datos
        cargarDatos();
        return view;
    }

    public void cargarLista() {
        WallpaperAdapter adapter = null;
        switch (Auxiliar.estadoSelectorColumnas){
            case 0:
                rc.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new WallpaperAdapter(WallpaperService.wallpaperZero, R.layout.item_wallpaper_1, getActivity(), getContext());
                break;
            case 1:
                rc.setLayoutManager(new GridLayoutManager(getContext(), 2));
                adapter = new WallpaperAdapter(WallpaperService.wallpaperZero, R.layout.item_wallpaper_2, getActivity(), getContext());
                break;
            case 2:
                rc.setLayoutManager(new GridLayoutManager(getContext(), 3));
                adapter = new WallpaperAdapter(WallpaperService.wallpaperZero, R.layout.item_wallpaper_3, getActivity(), getContext());
                break;
            case 3:
                rc.setLayoutManager(new GridLayoutManager(getContext(), 4));
                adapter = new WallpaperAdapter(WallpaperService.wallpaperZero, R.layout.item_wallpaper_4, getActivity(), getContext());
                break;
            default:
                break;
        }
        rc.setAdapter(adapter);
    }

    public void cargarDatos() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Wallpapers");
        reference.getRef();
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Wallpaper wallpaper = snapshot.getValue(Wallpaper.class);
                assert wallpaper != null;
                wallpaper.setId(snapshot.getKey());
                if (!WallpaperService.wallpaperZero.contains(wallpaper)){
                    WallpaperService.addWallpaperZero(wallpaper);
                }
                Objects.requireNonNull(rc.getAdapter()).notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Auxiliar.cambiaronColumnas){
            cargarLista();
            Auxiliar.cambiaronColumnas=false;
        }
    }
}