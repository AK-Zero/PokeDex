package com.example.pokedex;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class pokemon {

        @SerializedName("name")
        private String name;

        @SerializedName("sprites")
        private sprite sprites;

        public String getName() {
            return name;
        }
        public sprite getSprites() {
            return sprites;
        }


    public class sprite{

            @SerializedName("front_default")
            private String front_default;

            @SerializedName("front_shiny")
            private String front_shiny;

            @SerializedName("front_female")
            private String front_female ;

            @SerializedName("front_shiny_female")
            private String front_shiny_female;

            @SerializedName("back_default")
            private String back_default;

            @SerializedName("back_shiny")
            private String back_shiny;

            @SerializedName("back_female")
            private String back_female;

            @SerializedName("back_shiny_female")
            private String back_shiny_female;

            public String getFront_default() {
                return front_default;
            }

            public String getFront_shiny() {
                return front_shiny;
            }
        }
}


