package fr.dawan.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fr.dawan.myapplication.adapters.MyRecycleViewAdapter;
import fr.dawan.myapplication.comparateurs.CountryByCapitalAsc;
import fr.dawan.myapplication.entities.Country;

public class RecycleViewActivity extends AppCompatActivity {

    RecyclerView recycleView;
    List<Country> countries = new ArrayList<>();
    MyRecycleViewAdapter adapter;
    EditText edCountry, edCapital;
    Button btnAjouter;
    public int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);

        recycleView = findViewById(R.id.recycleView);
        edCountry = findViewById(R.id.edAddCountry);
        edCapital = findViewById(R.id.edAddCapital);
        btnAjouter = findViewById(R.id.btnAjouter);

        btnAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edCountry.getText().toString().equals("")){
                    edCountry.setError("Country can not be empty");
                }else if(edCapital.getText().toString().equals("")){
                    edCapital.setError("Capital can not be empty");
                }else{
                    Country country = new Country(edCountry.getText().toString(), edCapital.getText().toString());
                    if (btnAjouter.getText().toString().equals("Ajouter")){
                        countries.add(country);
                    }else
                    {
                        countries.remove(position);
                        countries.add(position, country);
                    }


                    edCountry.setText("");
                    edCapital.setText("");
                    btnAjouter.setText("Ajouter");

                    //Trier la liste
                    Collections.sort(countries, new Comparator<Country>() {
                        @Override
                        public int compare(Country t1, Country t2) {
                            return t1.getCountry().compareToIgnoreCase(t2.getCountry());
                        }
                    });

                    adapter.notifyDataSetChanged();
                }
            }
        });

        testData();
        adapter = new MyRecycleViewAdapter(countries);

        //Définir un LayoutManager - choisir la façon d'afficher les éléments dans la RecycleView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        //RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2, RecyclerView.HORIZONTAL, false);

        //Affecter le layout
        recycleView.setLayoutManager(layoutManager);

        //Affecter l'adapter à recycleView
        recycleView.setAdapter(adapter);



    }

    private void testData() {
        countries.add(new Country("France", "Paris"));
        countries.add(new Country("Espagne", "Madrid"));
        countries.add(new Country("Japan", "Tokyo"));
        countries.add(new Country("Italie", "Rome"));
        countries.add(new Country("Argentine", "Bueno Aires"));
    }

    public void delete() {

        countries.remove(position);
        adapter.notifyDataSetChanged();

    }

    public void Remplir(String country, String capital) {
        edCountry.setText(country);
        edCapital.setText(capital);
        btnAjouter.setText("Modifier");
    }
}