package fr.dawan.myapplication.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fr.dawan.myapplication.R;

public class CountryViewHolder extends RecyclerView.ViewHolder {

    //Objet intermédiaire entre la liste d'objet à afficher et le layout utilisé pour chaque objet de la liste


    public TextView tvCountry, tvCapital;

    public CountryViewHolder(@NonNull View itemView) {
        super(itemView);
        tvCountry = itemView.findViewById(R.id.tvCoutry);
        tvCapital = itemView.findViewById(R.id.tvCapital);
    }
}
