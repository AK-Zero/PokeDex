package com.example.pokedex;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.transition.Fade;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main4Activity extends AppCompatActivity {

    TextView height, weight, evolve, type, stats, name1;
    ImageView img;
    String type1 = "";
    String stat1 = "";
    LottieAnimationView l;
    private SQLiteDatabase database;
    boolean status = false;
    boolean addedtodb = false, ka = true;
    String dbimageurl;
    String evolveurl;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        FavouriteDBHelper dbHelper = new FavouriteDBHelper(this);
        database = dbHelper.getWritableDatabase();


        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        evolve = findViewById(R.id.evoltuion_details);
        type = findViewById(R.id.type);
        stats = findViewById(R.id.stats);
        img = findViewById(R.id.imageView);
        name1 = findViewById(R.id.name);
        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(fade);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setExitTransition(fade);
        }
        evolve.setMovementMethod(new ScrollingMovementMethod());
        type.setMovementMethod(new ScrollingMovementMethod());
        stats.setMovementMethod(new ScrollingMovementMethod());
        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        assert name != null;
        name1.setText(name.toUpperCase());
        Retrofit retro = new Retrofit.Builder().
                baseUrl("https://pokeapi.co/api/v2/").addConverterFactory(GsonConverterFactory.create()).build();
        speciesint speint = retro.create(speciesint.class);
        Call<speciesobj> call3 = speint.getspecies(name);
        call3.enqueue(new Callback<speciesobj>() {
            @Override
            public void onResponse(Call<speciesobj> call, Response<speciesobj> response) {
                speciesobj obj;
                obj = response.body();
                evolveurl = obj.getEvolution_chain().getUrl();
                Toast.makeText(Main4Activity.this, " " + evolveurl, Toast.LENGTH_SHORT);
            }

            @Override
            public void onFailure(Call<speciesobj> call, Throwable t) {

            }
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final specint specin = retrofit.create(specint.class);

        Call<specific_pokemon> call = specin.getpokemon(name);

        call.enqueue(new Callback<specific_pokemon>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<specific_pokemon> call, Response<specific_pokemon> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Main4Activity.this, "FailureError", Toast.LENGTH_LONG).show();
                    return;
                }

                specific_pokemon poke = response.body();
                assert poke != null;
                height.setText("Height : " + poke.getHeight());
                weight.setText("Weight : " + poke.getWeight());
                for (int i = 0; i < poke.getStats().size(); i++) {
                    stat1 = stat1 + poke.getStats().get(i).getStat1().getName().toUpperCase() + " : " + poke.getStats().get(i).getBase_stat() + "\n";
                }
                for (int i = 0; i < poke.getTypes().size(); i++) {
                    type1 = type1 + poke.getTypes().get(i).getType1().getName().toUpperCase() + "\n";
                }
                stats.setText("Stats : \n" + stat1);
                type.setText("Types : \n" + type1);
                img.setVisibility(View.VISIBLE);
                dbimageurl = poke.getSprites().getFront_shiny();
                final Context context1 = img.getContext();
                Picasso.with(context1).load(poke.getSprites().getFront_shiny()).into(img);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (evolveurl != null) {
                            Retrofit retrofit1 = new Retrofit.Builder()
                                    .baseUrl(evolveurl)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            evolutionint evolint = retrofit1.create(evolutionint.class);
                            Call<evolution_chain> call1;
                            call1 = evolint.getevolution_chain();
                            call1.enqueue(new Callback<evolution_chain>() {
                                @Override
                                public void onResponse(Call<evolution_chain> call, Response<evolution_chain> response) {
                                    if (!response.isSuccessful()) {
                                        return;
                                    }
                                    evolution_chain evolut = response.body();
                                    assert evolut != null;
                                    String a = evolut.getChain().getSpecies().getName();
                                    String b = null, c = null;
                                    if (evolut.getChain().getEvolves_to().size() != 0) {
                                        b = evolut.getChain().getEvolves_to().get(0).getSpecies().getName();
                                        if (evolut.getChain().getEvolves_to().get(0).getEvolves_to().size() != 0) {
                                            c = evolut.getChain().getEvolves_to().get(0).getEvolves_to().get(0).getSpecies().getName();
                                        }
                                    }

                                    boolean stat = false;
                                    String evolution_list = "";
                                    if (a == null && b == null && c == null) {
                                        evolution_list = evolution_list + "allnull";
                                    } else if (b == null && c == null) {
                                        evolution_list = evolution_list + ' ' + a.toUpperCase() + ' ';
                                        if (a.equals(name)) {
                                            stat = true;
                                        }
                                    } else if (c == null) {
                                        evolution_list = evolution_list + ' ' + a.toUpperCase() + " -> " + b.toUpperCase() + ' ';
                                        if (a.equals(name) || b.equals(name)) {
                                            stat = true;
                                        }
                                    } else {
                                        evolution_list = evolution_list + ' ' + a.toUpperCase() + " -> " + b.toUpperCase() + " -> " + c.toUpperCase() + ' ';
                                        if (a.equals(name) || b.equals(name) || c.equals(name)) {
                                            stat = true;
                                        }
                                    }
                                    if (stat) {
                                        evolve.setText("Evolution Chain : \n" + evolution_list);
                                        status = true;
                                        if (ka) {
                                            Toast t = Toast.makeText(Main4Activity.this, "Swipe UP to add this Pokemon to Favourites!!", Toast.LENGTH_SHORT);
                                            t.setGravity(Gravity.CENTER | Gravity.BOTTOM, 0, 0);
                                            t.show();
                                        }
                                    }
                                    if (evolve.getText().toString().equals("Loading...")) {
                                        evolve.setText("This Pokemon does not have an evolution chain");
                                        status = true;
                                        if (ka) {
                                            Toast t = Toast.makeText(Main4Activity.this, "Swipe UP to add this Pokemon to Favourites!!", Toast.LENGTH_SHORT);
                                            t.setGravity(Gravity.CENTER | Gravity.BOTTOM, 0, 0);
                                            t.show();
                                        }
                                    }

                                }

                                @Override
                                public void onFailure(Call<evolution_chain> call, Throwable t) {
                                    Toast.makeText(Main4Activity.this, "Internet Connection Weak or Unavailable!", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                }, 1200);

            }


            @Override
            public void onFailure(Call<specific_pokemon> call, Throwable t) {
                Toast.makeText(Main4Activity.this, "Internet Connection Weak or Unavailable!", Toast.LENGTH_LONG).show();
            }
        });

    }

    float y1, y2;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value = super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                y1 = event.getY();
                return value;
            }
            case MotionEvent.ACTION_UP: {
                y2 = event.getY();
                if (y2 < y1 && y1 - y2 > 150) {
                    addItem();
                }
                return value;
            }
        }
        return value;
    }

    private void addItem() {
        if (status && !addedtodb) {
            String dbname = name1.getText().toString();
            String dbimage = dbimageurl;
            String dbheight = height.getText().toString();
            String dbweight = weight.getText().toString();
            String dbevolution_chain = evolve.getText().toString();
            String dbstats = stats.getText().toString();
            String dbtype = type.getText().toString();

            ContentValues cv = new ContentValues();
            cv.put(FavouriteContract.FavouriteEntry.COLUMN_NAME, dbname);
            cv.put(FavouriteContract.FavouriteEntry.COLUMN_IMAGE, dbimage);
            cv.put(FavouriteContract.FavouriteEntry.COLUMN_HEIGHT, dbheight);
            cv.put(FavouriteContract.FavouriteEntry.COLUMN_WEIGHT, dbweight);
            cv.put(FavouriteContract.FavouriteEntry.COLUMN_EVOLUTION_CHAIN, dbevolution_chain);
            cv.put(FavouriteContract.FavouriteEntry.COLUMN_STATS, dbstats);
            cv.put(FavouriteContract.FavouriteEntry.COLUMN_TYPE, dbtype);

            database.insert(FavouriteContract.FavouriteEntry.TABLE_NAME, null, cv);
            if (ka) {
                Toast t = Toast.makeText(Main4Activity.this, "Added to Favourites!!", Toast.LENGTH_SHORT);
                t.setGravity(Gravity.CENTER, 0, 20);
                t.show();
            }
            addedtodb = true;
        }
    }

    @Override
    public void onBackPressed() {
        ka = false;
        super.onBackPressed();
    }
}
