package fr.dawan.myapplication.adapters;


import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.dawan.myapplication.MyApplicationClasse;
import fr.dawan.myapplication.R;
import fr.dawan.myapplication.entities.retrofit.User;
import fr.dawan.myapplication.repositories.retrofit.RetrofitClientInstance;
import fr.dawan.myapplication.repositories.retrofit.UserApi;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserViewHolder> {

    List<User> users = new ArrayList<>();

    public UserRecyclerViewAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_user_item, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User u = users.get(position);
        holder.textView.setText(u.getEmail());

        UserApi service = RetrofitClientInstance.getRetrofitInstance(RetrofitClientInstance.LOCALHOST_BASE_URL).create(UserApi.class);
        Call<ResponseBody> call = service.getUserImage("Bearer "+MyApplicationClasse.token, u.getId());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    if (response.body().byteStream() != null){
                        Bitmap image = BitmapFactory.decodeStream(response.body().byteStream());
                        holder.imageView.setImageBitmap(image);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(holder.itemView.getContext(), "Erreur: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //Delete User
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                builder.setTitle("Suppresuin User")
                        .setMessage("Supprimer le user?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                delete(u.getId());

                            }
                        })
                        .setNegativeButton("No", null);

                builder.create().show();

            }
        });
    }

    private void delete(long id) {
        UserApi service = RetrofitClientInstance.getRetrofitInstance(RetrofitClientInstance.LOCALHOST_BASE_URL).create(UserApi.class);

        Call<Long> call = service.deleteUser("Berear "+MyApplicationClasse.token, id);
        call.enqueue(new Callback<Long>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (response.isSuccessful()){
                    users.remove(users.stream().filter(user -> user.getId() == id).findAny().get());
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
