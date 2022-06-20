package fr.dawan.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import fr.dawan.myapplication.databinding.ActivityDemoBindingBinding;
import fr.dawan.myapplication.entities.Contact;
import fr.dawan.myapplication.entities.Student;

public class DemoBinding extends AppCompatActivity {

    //Classe générée par la bibliothèque Android Data Binding
    ActivityDemoBindingBinding binding;

    /*TextView tvFirstName, textViewLast;
    Button btnValider;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_demo_binding);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_demo_binding);
        binding.setStudent(new Student("dawan", "jehann"));

        binding.btnBinding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DemoBinding.this, "Click sur le bouton", Toast.LENGTH_SHORT).show();
            }
        });

        binding.setContact(new Contact("contactName"));


    }
}