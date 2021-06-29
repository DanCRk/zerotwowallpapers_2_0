package com.futurefix.zerotwowallpapers20.modelos;

import java.io.Serializable;

public class Wallpaper implements Serializable {
    private String id;
    private String url;

    public Wallpaper (){

    }

    public Wallpaper(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getUrl() {
        return url;
    }


    @Override
    public boolean equals(Object o) {
        return id.equals(((Wallpaper)o).id);
    }

}
