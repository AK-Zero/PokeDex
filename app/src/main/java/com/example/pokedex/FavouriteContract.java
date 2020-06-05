package com.example.pokedex;

import android.provider.BaseColumns;

public class FavouriteContract {

    private FavouriteContract(){}

    public static final class FavouriteEntry implements BaseColumns {
        public static final String TABLE_NAME = "favouritesList";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_HEIGHT = "height";
        public static final String COLUMN_WEIGHT = "weight";
        public static final String COLUMN_EVOLUTION_CHAIN = "evolution_chain";
        public static final String COLUMN_STATS = "stats";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
