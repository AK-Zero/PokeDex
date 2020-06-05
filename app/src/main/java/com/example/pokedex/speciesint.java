package com.example.pokedex;

import java.lang.reflect.GenericArrayType;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface speciesint {

    @GET("pokemon-species/{name}")
    Call<speciesobj> getspecies(@Path("name") String name);
}
