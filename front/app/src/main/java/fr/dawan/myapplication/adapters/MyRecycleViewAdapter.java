package fr.dawan.myapplication.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.dawan.myapplication.R;
import fr.dawan.myapplication.RecycleViewActivity;
import fr.dawan.myapplication.entities.Country;

public class MyRecycleViewAdapter extends RecyclerView.Adapter<CountryViewHolder> {

    List<Country> countries;
    Context context;

    public MyRecycleViewAdapter(List<Country> countries) {
        this.countries = countries;
    }

    //Pour chaque element de la liste -- cette méthode va s'exécuter en premier
    //Son rôle désérialiser (LayoutInflater) le layout xml et le relier à un ViewHolder

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Désérialiser le laypout xml
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_item, parent, false);
        return new CountryViewHolder(v);
    }


    //Pour chque élément de la liste cette méthode va s'exécuter en deuxième juste après le génération du Holder pour le remplir
    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        Country country = countries.get(position);
        holder.tvCountry.setText(country.getCountry());
        holder.tvCapital.setText(country.getCapital());

        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);
        holder.itemView.setAnimation(animation);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                builder.setTitle("Suppression country")
                        .setMessage("Are you sure?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                RecycleViewActivity activity = (RecycleViewActivity) holder.itemView.getContext();
                                activity.position = position;
                                activity.delete();
                            }
                        })
                        .setNegativeButton("NO", null).create().show();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                RecycleViewActivity activity = (RecycleViewActivity) holder.itemView.getContext();
                activity.position = position;
                activity.Remplir(holder.tvCountry.getText().toString(), holder.tvCapital.getText().toString());
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return countries.size();
    }
}
