package fr.dawan.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EspressoActivity extends AppCompatActivity {

    EditText edText;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espresso);


        edText = findViewById(R.id.edTextEspresso);
        textView = findViewById(R.id.tvEspresso);
    }

    public void btnEspressoValider(View view) {

        if (edText.getText().toString().equals(""))
            textView.setText("Aucune saisie");
        else
            textView.setText(edText.getText().toString());
    }
}