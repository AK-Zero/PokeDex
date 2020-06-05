package com.example.pokedex;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {
    private Context context;
    private Cursor cursor;
    public FavouriteAdapter(Context context , Cursor cursor){
        this.context = context;
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item , parent , false);
        return new FavouriteViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final FavouriteViewHolder holder, int position) {
        if(!cursor.moveToPosition(position)){
            return;
        }

        final String name = cursor.getString(cursor.getColumnIndex(FavouriteContract.FavouriteEntry.COLUMN_NAME));
        final String image = cursor.getString(cursor.getColumnIndex(FavouriteContract.FavouriteEntry.COLUMN_IMAGE));
        final String height = cursor.getString(cursor.getColumnIndex(FavouriteContract.FavouriteEntry.COLUMN_HEIGHT));
        final String weight = cursor.getString(cursor.getColumnIndex(FavouriteContract.FavouriteEntry.COLUMN_WEIGHT));
        final String evolution_chain = cursor.getString(cursor.getColumnIndex(FavouriteContract.FavouriteEntry.COLUMN_EVOLUTION_CHAIN));
        final String stats = cursor.getString(cursor.getColumnIndex(FavouriteContract.FavouriteEntry.COLUMN_STATS));
        final String type = cursor.getString(cursor.getColumnIndex(FavouriteContract.FavouriteEntry.COLUMN_TYPE));
        final long id = cursor.getLong(cursor.getColumnIndex(FavouriteContract.FavouriteEntry._ID));
        holder.tvname.setText(name.toUpperCase());
        final  Context context1 = holder.img.getContext();
        Picasso.with(context1).load(image).into(holder.img);
       holder.itemView.setTag(id);
        Fade fade = new Fade();
        View decor = ((Activity) context).getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        ((Activity) context).getWindow().setEnterTransition(fade);
        ((Activity) context).getWindow().setExitTransition(fade);
        holder.lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Main5Activity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)context , holder.img , "poke_transition");
                intent.putExtra("name" , name);
                intent.putExtra("image" , image);
                intent.putExtra("height" , height);
                intent.putExtra("weight" , weight);
                intent.putExtra("evolution_chain" , evolution_chain);
                intent.putExtra("stats" , stats);
                intent.putExtra("type" , type);
                context.startActivity(intent,options.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }


    public class FavouriteViewHolder extends RecyclerView.ViewHolder{
        TextView tvname;
        ImageView img;
        ConstraintLayout lay;
        public FavouriteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvname = itemView.findViewById(R.id.name);
            img = itemView.findViewById(R.id.pokepic);
            lay = (ConstraintLayout) itemView.findViewById(R.id.item);
        }
    }

    public void swapCursor(Cursor newCursor){
        if(cursor!=null){
            cursor.close();
        }
        cursor = newCursor;
        if(newCursor!= null){
            notifyDataSetChanged();
        }

    }

}
