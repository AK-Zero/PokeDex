package com.example.pokedex;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class specific_pokemon {

    @SerializedName("name")
    private String name;

    @SerializedName("height")
    private int height;

    @SerializedName("weight")
    private int weight;

    @SerializedName("stats")
    private List<superstats> stats;

    @SerializedName("types")
    private List<supertype> types;

    @SerializedName("sprites")
    private pokemon.sprite sprites;


    public class supertype{
        @SerializedName("type")
        private type type1;

        public type getType1() {
            return type1;
        }
    }


    public class type{
        @SerializedName("name")
        private String name;

        public String getName() {
            return name;
        }
    }
    public class superstats{
        @SerializedName("base_stat")
        private int base_stat;

        @SerializedName("stat")
        private stat stat1;

        public int getBase_stat() {
            return base_stat;
        }

        public stat getStat1() {
            return stat1;
        }
    }

    public class stat{
        @SerializedName("name")
        private String name;

        public String getName() {
            return name;
        }
    }

    public pokemon.sprite getSprites() {
        return sprites;
    }


    public class sprite{

        @SerializedName("front_default")
        private String front_default;

        @SerializedName("front_shiny")
        private String front_shiny;

        public String getFront_default() {
            return front_default;
        }

        public String getFront_shiny() {
            return front_shiny;
        }
    }
    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public List<superstats> getStats() {
        return stats;
    }

    public List<supertype> getTypes() {
        return types;
    }
}
