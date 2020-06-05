package com.example.pokedex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        Intent intent = getIntent();
        final int type = intent.getIntExtra("type",1);
        if(type==1){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container , new PokemonFragment()).commit();
            setTitle("Pokemon List");
            navigationView.setCheckedItem(R.id.nav_pokemon);
        }
        else if(type==2){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container , new RegionFragment()).commit();
            setTitle("Region List");
            navigationView.setCheckedItem(R.id.nav_region);
        }
        else if(type==3){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container , new FavouriteFragment()).commit();
            setTitle("Favourites List");
            navigationView.setCheckedItem(R.id.nav_favourites);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_pokemon:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container , new PokemonFragment()).commit();
                setTitle("Pokemon List");
                break;
            case R.id.nav_region:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container , new RegionFragment()).commit();
                setTitle("Region List");
                break;
            case R.id.nav_favourites:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container , new FavouriteFragment()).commit();
                setTitle("Favourites List");
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
