package com.example.pokedex;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main5Activity extends AppCompatActivity {
    TextView height ,weight , evolve , type ,stats ,name ,share;
    ImageView img;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        evolve = findViewById(R.id.evoltuion_details);
        type = findViewById(R.id.type);
        stats = findViewById(R.id.stats);
        img = findViewById(R.id.imageView);
        name = findViewById(R.id.name);
        share = findViewById(R.id.share);

        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);
        Intent intent = getIntent();
        final String name1 = intent.getStringExtra("name");
        final String image1 = intent.getStringExtra("image");
        final String height1 = intent.getStringExtra("height");
        final String weight1 = intent.getStringExtra("weight");
        final String evolution_chain1 = intent.getStringExtra("evolution_chain");
        final String stats1 = intent.getStringExtra("stats");
        final String type1 = intent.getStringExtra("type");

        name.setText(name1);
        height.setText(height1);
        weight.setText(weight1);
        evolve.setText(evolution_chain1);
        stats.setText(stats1);
        type.setText(type1);
        img.setVisibility(View.VISIBLE);
        final Context context1 = img.getContext();
        Picasso.with(context1).load(image1).into(img);

        evolve.setMovementMethod(new ScrollingMovementMethod());
        type.setMovementMethod(new ScrollingMovementMethod());
        stats.setMovementMethod(new ScrollingMovementMethod());

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sendable = "..................................................\n\n"+"CHECK OUT THIS COOL POKEMON...\n\n" + "Name : " + name1 +"\n\nImageUrl : " + image1 + "\n\n" + height1 + "\n\n" + weight1 + "\n\n" +
                        evolution_chain1 + "\n\n" + type1 + "\n\n" + stats1 + "\n.....................................................";
                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_SEND);
                intent1.setType("text/plain");
                intent1.putExtra(Intent.EXTRA_TEXT , sendable);
                startActivity(intent1);
            }
        });
    }
}
