package com.example.pokedex;

import com.google.gson.annotations.SerializedName;

public class speciesobj {

    @SerializedName("evolution_chain")
    private sub evolution_chain;

    public sub getEvolution_chain() {
        return evolution_chain;
    }

    public class sub {
        @SerializedName("url")
        private String url;

        public String getUrl() {
            return url;
        }
    }
}
