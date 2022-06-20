package fr.dawan.myapplication.adapters;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fr.dawan.myapplication.R;

public class UserViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView textView;

    public UserViewHolder(@NonNull View view) {
        super(view);
        imageView = view.findViewById(R.id.iv_user_recycler);
        textView = view.findViewById(R.id.tv_user_recycler);
    }
}
