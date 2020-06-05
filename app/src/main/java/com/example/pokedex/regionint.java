package com.example.pokedex;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface regionint {

    @GET("region/{id}")
    Call<regions> getregions(@Path("id") int id);

}
