package com.example.pokedex;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface specint {

    @GET("pokemon/{name}")
    Call<specific_pokemon> getpokemon(@Path("name") String name);
}
