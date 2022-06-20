package fr.dawan.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import fr.dawan.myapplication.entities.retrofit.LoginDto;
import fr.dawan.myapplication.entities.retrofit.LoginResponseDto;
import fr.dawan.myapplication.repositories.retrofit.RetrofitClientInstance;
import fr.dawan.myapplication.repositories.retrofit.UserApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitActivity extends AppCompatActivity {

    TextView tvErreur;
    EditText edEmail, edPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        tvErreur = findViewById(R.id.tvErreur);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoginDto loginDto = new LoginDto(edEmail.getText().toString(), edPassword.getText().toString());

                UserApi service = RetrofitClientInstance.getRetrofitInstance(RetrofitClientInstance.LOCALHOST_BASE_URL).create(UserApi.class);
                Call<LoginResponseDto> call= service.connect(loginDto);
                call.enqueue(new Callback<LoginResponseDto>() {
                    @Override
                    public void onResponse(Call<LoginResponseDto> call, Response<LoginResponseDto> response) {
                        if(response.isSuccessful()){
                            Log.i(">>>Login ID:", ""+response.body().getId());
                            Toast.makeText(RetrofitActivity.this, "Connexion OK", Toast.LENGTH_LONG).show();

                            LoginResponseDto loginResponseDto = response.body();
                            //Sauvegarde do Token
                            MyApplicationClasse.token = loginResponseDto.getToken();

                            Intent intent = new Intent(RetrofitActivity.this, UserHomeActivity.class);
                            intent.putExtra("loginResponseDto", loginResponseDto);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponseDto> call, Throwable t) {
                        tvErreur.setText("Echec connexion");
                    }
                });
            }
        });
    }


    public void tvCreateAccountClick(View view) {
        startActivity(new Intent(this, CreateAccountActivity.class));
    }
}