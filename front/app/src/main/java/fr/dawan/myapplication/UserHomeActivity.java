package fr.dawan.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import fr.dawan.myapplication.entities.retrofit.LoginResponseDto;
import fr.dawan.myapplication.repositories.retrofit.RetrofitClientInstance;
import fr.dawan.myapplication.repositories.retrofit.UserApi;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserHomeActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navView;
    ImageView imageViewHeader;
    LoginResponseDto loginResponseDto;
    TextView tvHeader;

    Bitmap image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        drawerLayout = findViewById(R.id.drawer);
        navView = findViewById(R.id.navigationView);

        loginResponseDto = (LoginResponseDto) getIntent().getSerializableExtra("loginResponseDto");

        //Remplir le header de navigation view
        View header = navView.getHeaderView(0);
        tvHeader = header.findViewById(R.id.textView_header);
        imageViewHeader = header.findViewById(R.id.imageView_header);

        //getSupportActionBar() permet de manipuler la barre d'action
        //Le bouton home
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_button);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Récupérer l'image du user
        getUserImage(loginResponseDto.getId());

        //Afficher home_fragment
        Fragment fragment = new home_fragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout_container, fragment);
        transaction.commit();


        setNavigationView();

    }



    //Affichage de la navigation barre via le bouton home


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(Gravity.LEFT);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Gestion de la navigation View
    private void setNavigationView() {

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;

                switch (item.getItemId()){
                    case R.id.all_users:
                        fragment = new user_list_fragment();
                        break;
                    case R.id.settings:
                        fragment = new settings_fragment();
                        break;
                    case R.id.logout:
                        //Pour désactiver le bouton précedent (supprimer l'historique), ajouter dans le manifet android:noHistory="true"
                        startActivity(new Intent(UserHomeActivity.this, MainActivity.class));
                        break;
                }

                if(fragment != null){
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameLayout_container, fragment);
                    transaction.commit();
                    drawerLayout.closeDrawers();
                    return false;
                }

                return false;
            }
        });

    }

    private void getUserImage(long id) {

        UserApi service = RetrofitClientInstance.getRetrofitInstance(RetrofitClientInstance.LOCALHOST_BASE_URL).create(UserApi.class);
        Call<ResponseBody> call = service.getUserImage("Bearer "+MyApplicationClasse.token,id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    if (response.body().byteStream() != null){
                        image = BitmapFactory.decodeStream(response.body().byteStream());

                        if(image == null)
                            //Affecter une image par defaut aux users sans photo de profil
                            image = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);

                        tvHeader.setText(loginResponseDto.getEmail());
                        imageViewHeader.setImageBitmap(image);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(UserHomeActivity.this, "Erreur: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}