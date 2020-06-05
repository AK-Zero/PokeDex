package com.example.pokedex;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegionFragment extends Fragment {
    RecyclerView plist;
    ArrayList<String > names = new ArrayList<String>();
    ArrayList<String > images = new ArrayList<String>();
    ArrayList<String > regions = new ArrayList<String>();
    ArrayList<String > pokedexes = new ArrayList<String>();
    Call<pokemon> call;
    Call<regions> call1;
    LottieAnimationView l;
    myadapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_region_list , container , false);
        setHasOptionsMenu(true);
        plist = view.findViewById(R.id.pokelist);
        l = view.findViewById(R.id.animation_view);
        plist.setLayoutManager(new LinearLayoutManager(view.getContext()));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final regionint regint = retrofit.create(regionint.class);

        for(int i =1 ; i<8 ; i++) {
            final int u = i;
            call1 = regint.getregions(i);
            call1.enqueue(new Callback<com.example.pokedex.regions>() {
                @Override
                public void onResponse(Call<com.example.pokedex.regions> call, Response<com.example.pokedex.regions> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(view.getContext(), "FailureError", Toast.LENGTH_LONG).show();
                        return;
                    }
                    regions reg = response.body();
                    assert reg != null;
                    regions.add(reg.getName());
                    if (!reg.getName().equals("alola")) {
                        if (reg.getPokedex().size() > 1) {
                            pokedexes.add(reg.getPokedex().get(1).getUrl());
                        } else {
                            pokedexes.add(reg.getPokedex().get(0).getUrl());
                        }
                    } else {
                        pokedexes.add("not");
                    }
                    if (u == 7) {
                        adapter = new myadapter(view.getContext(), names, images, regions, pokedexes, 2);
                        plist.setAdapter(adapter);
                        l.cancelAnimation();
                        l.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<com.example.pokedex.regions> call, Throwable t) {
                    Toast.makeText(view.getContext(), "Internet Connection Weak or Unavailable!" , Toast.LENGTH_SHORT).show();
                    l.cancelAnimation();
                    l.setVisibility(View.INVISIBLE);
                }
            });
        }

            return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu , MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu , menu);
        super.onCreateOptionsMenu(menu,inflater);
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
    }

}
