package com.example.pokedex;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface pokeinter {

    @GET("pokemon/{id}")
    Call<pokemon> getpokemon(@Path("id") int id);
}
