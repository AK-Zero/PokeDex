package com.example.pokedex;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class evolution_chain {

    @SerializedName("chain")
    private dive1 chain;

    public dive1 getChain() {
        return chain;
    }

    public class dive1{
        @SerializedName("species")
        private specie species;

        @SerializedName("evolves_to")
        private List<dive1> evolves_to;

        public specie getSpecies() {
            return species;
        }

        public List<dive1> getEvolves_to() {
            return evolves_to;
        }

        public class specie{
            @SerializedName("name")
            private String name;

            public String getName() {
                return name;
            }
        }
    }
}
