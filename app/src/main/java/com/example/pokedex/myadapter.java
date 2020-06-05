package com.example.pokedex;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.ContactsContract;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder> implements Filterable {

    private ArrayList<String> names;
    private ArrayList<String> images;
    private ArrayList<String> regions;
    private ArrayList<String> pokedexes;
    private ArrayList<String> names1;
    private ArrayList<String> images1;
    private ArrayList<String> regions1;
    private ArrayList<String> pokedexes1;

    int type;
    Context context;

    myadapter(Context context, ArrayList<String> nam, ArrayList<String> im, ArrayList<String> reg, ArrayList<String> poke, int ty) {
        this.context = context;
        type = ty;
        if (type == 1) {
            names = nam;
            images = im;
            names1 = new ArrayList<>(names);
            images1 = new ArrayList<>(images);
        } else if (type == 2) {
            regions = reg;
            pokedexes = poke;
            regions1 = new ArrayList<>(regions);
            pokedexes1 = new ArrayList<>(pokedexes);
        } else if (type == 3) {
            names = nam;
            names1 = new ArrayList<>(names);
        }
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item, parent, false);
        if (type == 2) {
            view = inflater.inflate(R.layout.itemregion, parent, false);
        }
        return new myviewholder(view, type);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final myviewholder holder, final int position) {
        if (type == 1) {
            final String name = names.get(position);
            final Context context1 = holder.img.getContext();
            String imageurl = images.get(position);
            Picasso.with(context1).load(imageurl).into(holder.img);
            holder.tvname.setText(name.toUpperCase());
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
                    Intent intent = new Intent(context, Main4Activity.class);
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, holder.img, "poke_transition");
                    intent.putExtra("name", names.get(position));
                    context.startActivity(intent, options.toBundle());
                }
            });
        } else if (type == 2) {
            final String name = regions.get(position);
            holder.tvregion.setText(name.toUpperCase());
            holder.lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!name.equals("alola")) {
                        Intent intent = new Intent(context, Main3Activity.class);
                        intent.putExtra("region", regions.get(position));
                        intent.putExtra("pokedex", pokedexes.get(position));
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context, "There are no pokemons in Alola!!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else if (type == 3) {

            String name = names.get(position);
            holder.tvname.setText(name.toUpperCase());
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
                    holder.img.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(context, Main4Activity.class);
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, holder.img, "poke_transition");
                    intent.putExtra("name", names.get(position));
                    context.startActivity(intent, options.toBundle());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        int a = 0;
        if (type == 1 || type == 3) {
            a = names.size();
        } else if (type == 2) {
            a = regions.size();
        }
        return a;
    }

    public static class myviewholder extends RecyclerView.ViewHolder {
        TextView tvname, tvregion;
        ImageView img;
        ConstraintLayout lay;

        public myviewholder(@NonNull View itemView, int type) {
            super(itemView);
            if (type == 1 || type == 3) {
                tvname = itemView.findViewById(R.id.name);
                img = itemView.findViewById(R.id.pokepic);
                lay = (ConstraintLayout) itemView.findViewById(R.id.item);
            } else if (type == 2) {
                tvregion = itemView.findViewById(R.id.region_name);
                lay = (ConstraintLayout) itemView.findViewById(R.id.regionitem);
            }
        }
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<String> filterednames = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                if (type == 1 || type == 3) {
                    filterednames.addAll(names1);
                } else {
                    filterednames.addAll(regions1);
                }
            } else {
                String filterpattern = constraint.toString().toLowerCase().trim();
                if (type == 1 || type == 3) {
                    for (int i = 0; i < names1.size(); i++) {
                        if (names1.get(i).toLowerCase().contains(filterpattern)) {
                            filterednames.add(names1.get(i));
                        }
                    }
                } else {
                    for (int i = 0; i < regions1.size(); i++) {
                        if (regions1.get(i).toLowerCase().contains(filterpattern)) {
                            filterednames.add(regions1.get(i));
                        }
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterednames;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (type == 1 || type == 3) {
                names.clear();
                names.addAll((List) results.values);
                if (type == 1) {
                    images.clear();
                    for (int i = 0; i < names1.size(); i++) {
                        for (int j = 0; j < names.size(); j++) {
                            if (names1.get(i).equals(names.get(j))) {
                                images.add(images1.get(i));
                                break;
                            }
                        }
                    }
                }
            } else {
                regions.clear();
                regions.addAll((List) results.values);
                pokedexes.clear();
                for (int i = 0; i < regions1.size(); i++) {
                    for (int j = 0; j < regions.size(); j++) {
                        if (regions1.get(i).equals(regions.get(j))) {
                            pokedexes.add(pokedexes1.get(i));
                            break;
                        }
                    }
                }
            }
            notifyDataSetChanged();
        }
    };

    public void adder(ArrayList<String> nameadder , ArrayList<String> imageadder) {
        names1.clear();
        notifyDataSetChanged();
        names1.addAll(nameadder);
        notifyItemRangeInserted(0, names1.size() - 1);
        images1.clear();
        notifyDataSetChanged();
        images1.addAll(imageadder);
        notifyItemRangeInserted(0, images1.size() - 1);
    }
}
