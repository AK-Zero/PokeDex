package com.example.pokedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main3Activity extends AppCompatActivity {

    RecyclerView plist;
    String iregion;
    String ipokedex;
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> id = new ArrayList<String>();
    myadapter adapter;
    LottieAnimationView l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        l = findViewById(R.id.animation_view1);
        plist = findViewById(R.id.pokelist2);
        plist.setLayoutManager(new GridLayoutManager(this, 2));
        Intent intent = getIntent();
        iregion = intent.getStringExtra("region");
        ipokedex = intent.getStringExtra("pokedex");
        setTitle(iregion.toUpperCase());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ipokedex)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final pokedexint pokedexin = retrofit.create(pokedexint.class);
        Call call = pokedexin.getentries();

        call.enqueue(new Callback<poke_entries>() {
            @Override
            public void onResponse(Call<poke_entries> call, Response<poke_entries> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Main3Activity.this, "FailureError", Toast.LENGTH_LONG).show();
                    return;
                }

                poke_entries pokeEntries = (poke_entries) response.body();
                assert pokeEntries != null;
                for(int i = 0; i<pokeEntries.getPokemon_entries().size(); i++) {
                    names.add(pokeEntries.getPokemon_entries().get(i).getPokemon_species().getName());

                    if(i==pokeEntries.getPokemon_entries().size()-1){
                        l.cancelAnimation();
                        l.setVisibility(View.INVISIBLE);
                        adapter = new myadapter(Main3Activity.this , names , null , null , null , 3);
                        plist.setAdapter(adapter);
                    }
                }

            }

            @Override
            public void onFailure(Call<poke_entries> call, Throwable t) {
                Toast.makeText(Main3Activity.this, "Internet Connection Unavailable!", Toast.LENGTH_LONG).show();
                l.cancelAnimation();
                l.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu , menu);
        MenuItem searchitem = menu.findItem(R.id.action_search);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) searchitem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(adapter!=null) {
                    adapter.getFilter().filter(newText);
                }
                return false;
            }
        });
        return true;
    }
}
