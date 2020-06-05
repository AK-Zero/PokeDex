package com.example.pokedex;

import retrofit2.Call;
import retrofit2.http.GET;

public interface pokedexint {
    @GET(".")
    Call<poke_entries> getentries();
}
