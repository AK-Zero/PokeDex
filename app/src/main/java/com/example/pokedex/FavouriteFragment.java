package com.example.pokedex;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FavouriteFragment extends Fragment {
    private SQLiteDatabase database;
    FavouriteAdapter adapter;
    RecyclerView plist;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_favourites , container , false);
        FavouriteDBHelper dbHelper = new FavouriteDBHelper(view.getContext());
        database = dbHelper.getReadableDatabase();
        plist = view.findViewById(R.id.pokelist);
        plist.setLayoutManager(new GridLayoutManager(view.getContext() , 2));
        adapter = new FavouriteAdapter(view.getContext() , getAllItems());
        plist.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0 , ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((long)viewHolder.itemView.getTag(),view);
            }
        }).attachToRecyclerView(plist);

        if(adapter.getItemCount()==0){
            Toast t= Toast.makeText(view.getContext(), "No Pokemons have been added to Favourites!!" , Toast.LENGTH_LONG);
            t.setGravity(Gravity.CENTER|Gravity.BOTTOM ,0,0);
            t.show();
        }
        else{
            Toast t= Toast.makeText(view.getContext(), "Drag a Pokemon Horizontally to Delete!!" , Toast.LENGTH_SHORT);
            t.setGravity(Gravity.CENTER|Gravity.BOTTOM ,0,0);
            t.show();
        }
        return view;
    }

    private Cursor getAllItems(){
        return database.query(
                FavouriteContract.FavouriteEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                FavouriteContract.FavouriteEntry.COLUMN_TIMESTAMP + " DESC"
        );
    }

    private void removeItem(long id,View view){
        database.delete(FavouriteContract.FavouriteEntry.TABLE_NAME,
                FavouriteContract.FavouriteEntry._ID+"=" + id , null);
        Cursor a =getAllItems();
        adapter.swapCursor(a);
        if(a.getCount()==0){
            Toast t= Toast.makeText(view.getContext(), "No Pokemons are left in Favourites!!" , Toast.LENGTH_LONG);
            t.setGravity(Gravity.CENTER|Gravity.BOTTOM ,0,0);
            t.show();
        }
    }

}
