package fr.dawan.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.dawan.myapplication.adapters.UserRecyclerViewAdapter;
import fr.dawan.myapplication.entities.retrofit.User;
import fr.dawan.myapplication.repositories.retrofit.RetrofitClientInstance;
import fr.dawan.myapplication.repositories.retrofit.UserApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class user_list_fragment extends Fragment {

   RecyclerView recyclerView;
   UserRecyclerViewAdapter adapter;
   List<User> users = new ArrayList<>();

    public user_list_fragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.fragment_user_list_fragment, container, false);
       recyclerView = v.findViewById(R.id.recycler_users);

        UserApi service = RetrofitClientInstance.getRetrofitInstance(RetrofitClientInstance.LOCALHOST_BASE_URL).create(UserApi.class);

        Call<List<User>> call = service.getAllUsers("Bearer "+MyApplicationClasse.token);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                if (response.isSuccessful()){
                    users = response.body();
                    adapter =new UserRecyclerViewAdapter(users);

                    //Choisir un Layout pour la recycler View
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                    //Affecter l'adapter à la recyclerView
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getContext(), "Erreur: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}