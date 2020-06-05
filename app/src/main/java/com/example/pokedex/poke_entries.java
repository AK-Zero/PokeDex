package com.example.pokedex;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class poke_entries {

    @SerializedName("pokemon_entries")
    private List<species> pokemon_entries;

    public List<species> getPokemon_entries() {
        return pokemon_entries;
    }

    public class species{
        @SerializedName("pokemon_species")
        private deets pokemon_species;

        public deets getPokemon_species() {
            return pokemon_species;
        }

        public class deets{
            @SerializedName("name")
            private String name;

            @SerializedName("url")
            private String url;

            public String getName() {
                return name;
            }

            public String getUrl() {
                return url;
            }
        }
    }
}
