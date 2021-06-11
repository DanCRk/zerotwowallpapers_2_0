package com.futurefix.zerotwowallpapers20.adaptadores;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.futurefix.zerotwowallpapers20.fragments.CategoriasFragment;
import com.futurefix.zerotwowallpapers20.fragments.ZeroFragment;

import org.jetbrains.annotations.NotNull;

public class PagerAdapter extends FragmentPagerAdapter {
    int numTabs;

    public PagerAdapter(@NonNull @NotNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.numTabs = behavior;
    }


    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new CategoriasFragment();
            case 1:
                return new ZeroFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
