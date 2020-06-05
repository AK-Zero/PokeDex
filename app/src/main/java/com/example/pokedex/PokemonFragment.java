package com.example.pokedex;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonFragment extends Fragment {

    private RecyclerView plist;
    private ArrayList<String> names = new ArrayList<String>();
    private ArrayList<String> images = new ArrayList<String>();
    private ArrayList<String> regions = new ArrayList<String>();
    private ArrayList<String> pokedexes = new ArrayList<String>();
    private Call<pokemon> call,call1;
    private LottieAnimationView l;
    private myadapter adapter;
    private int currentItems, totalItems = 0, scrollOutItems;
    private RecyclerView.LayoutManager manager;
    private boolean isScrolling = false;
    private int begin = 1;
    private pokeinter pokeint;
    private String constraint;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_pokemon_list, container, false);
        setHasOptionsMenu(true);
        plist = view.findViewById(R.id.pokelist);
        l = view.findViewById(R.id.animation_view);
        manager = new GridLayoutManager(view.getContext(), 2);
        plist.setLayoutManager(manager);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        pokeint = retrofit.create(pokeinter.class);
        int end = begin;
        for (; begin < end + 24; begin++) {
            final int u = begin;
            call = pokeint.getpokemon(begin);
            call.enqueue(new Callback<pokemon>() {
                @Override
                public void onResponse(Call<pokemon> call, Response<pokemon> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(view.getContext(), "FailureError", Toast.LENGTH_LONG).show();
                        return;
                    }
                    pokemon poke = response.body();
                    assert poke != null;
                    names.add(poke.getName());
                    images.add(poke.getSprites().getFront_shiny());
                    if (u == 24) {
                        adapter = new myadapter(view.getContext(), names, images, regions, pokedexes, 1);
                        plist.setAdapter(adapter);
                        l.cancelAnimation();
                        l.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<pokemon> call, Throwable t) {
                    Toast.makeText(view.getContext(), "Internet Connection Weak or Unavailable!", Toast.LENGTH_LONG).show();
                    l.cancelAnimation();
                    l.setVisibility(View.INVISIBLE);
                }
            });

        }
        plist.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (adapter != null && (constraint == null || constraint.equals(""))) {
                    currentItems = manager.getChildCount();
                    totalItems = manager.getItemCount();
                    scrollOutItems = ((GridLayoutManager) plist.getLayoutManager()).findFirstVisibleItemPosition();
                    if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
                        fetchmore(view);
                    }
                }
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem searchitem = menu.findItem(R.id.action_search);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) searchitem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                constraint = newText;
                if (adapter != null) {
                    adapter.getFilter().filter(newText);
                }
                return false;
            }
        });
    }

    private void fetchmore(final View view) {
        l.playAnimation();
        l.setVisibility(View.VISIBLE);
        final int end = begin;
        for (; begin < end + 24 && begin < 808 && (constraint == null || constraint.equals("")); begin++) {
            final int u = begin;
            call = pokeint.getpokemon(begin);
            call.enqueue(new Callback<pokemon>() {
                @Override
                public void onResponse(Call<pokemon> call, Response<pokemon> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(view.getContext(), "FailureError", Toast.LENGTH_LONG).show();
                        return;
                    }
                    pokemon poke = response.body();
                    assert poke != null;
                    names.add(poke.getName());
                    images.add(poke.getSprites().getFront_shiny());
                    if (u == end + 23) {
                        adapter.adder(names , images);
                        l.cancelAnimation();
                        l.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<pokemon> call, Throwable t) {
                    Toast.makeText(view.getContext(), "Internet Connection Weak or Unavailable!", Toast.LENGTH_LONG).show();
                    l.setVisibility(View.INVISIBLE);
                    l.cancelAnimation();
                }
            });

        }
    }
}
