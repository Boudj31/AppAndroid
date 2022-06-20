package fr.dawan.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;

import fr.dawan.myapplication.entities.retrofit.User;
import fr.dawan.myapplication.repositories.retrofit.RetrofitClientInstance;
import fr.dawan.myapplication.repositories.retrofit.UserApi;
import fr.dawan.myapplication.tools.RealPathUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAccountActivity extends AppCompatActivity {

    EditText edFirstName, edLastName, edEmail, edPassword;
    ImageView imageView;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        edFirstName = findViewById(R.id.edFirstName);
        edLastName = findViewById(R.id.edLaststName);
        edEmail = findViewById(R.id.edEmailCreate);
        edPassword = findViewById(R.id.edPasswordCreate);
        imageView = findViewById(R.id.imageViewUser);
    }

    //Choisir une image à partir de Galerie
    //Permission stockage externe
    public void btnSelectImageClick(View view) {

    //Depuis la version 29, pour les permissions, ajouter dans le manifest balise <Application android:requestLegacyExternalStorage="true"

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 150);
        }
        else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    //Save User
    public void btnSaveUserClick(View view) {
        User user = new User(edFirstName.getText().toString(), edLastName.getText().toString(), edEmail.getText().toString(), edPassword.getText().toString(), "");
        //Fournir au back le user sous format JSON
        Gson gson = new Gson();
        String userJson = gson.toJson(user);
        File file = new File(path);
        UserApi service = RetrofitClientInstance.getRetrofitInstance(RetrofitClientInstance.LOCALHOST_BASE_URL).create(UserApi.class);

        //Construire les 2 objets de la méthode addUser de UserAPi: MultipartBody.Part - RequestBody

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        RequestBody userBody = RequestBody.create(MediaType.parse("multipart/form-data"), userJson);

        Call<User> call = service.addUser(body, userBody);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    Toast.makeText(CreateAccountActivity.this, "Compte crée avec succés", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(CreateAccountActivity.this, RetrofitActivity.class));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(CreateAccountActivity.this, "Erreur: "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    //Lien classe utilitaire pour le path
    //https://gist.github.com/tatocaster/32aad15f6e0c50311626

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 150 && resultCode == RESULT_OK){
            Uri uri = data.getData();
            path = RealPathUtil.getRealPath(this,uri);
            Bitmap bmp = BitmapFactory.decodeFile(path);
            imageView.setImageBitmap(bmp);
        }
    }
}