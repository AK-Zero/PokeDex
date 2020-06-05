package com.example.pokedex;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class regions {
    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }
   @SerializedName("pokedexes")
    private List<dets> pokedex;



    public List<dets> getPokedex() {
        return pokedex;
    }

    public class dets{
        @SerializedName("url")
        private String url;

        public String getUrl() {
            return url;
        }
    }
}
