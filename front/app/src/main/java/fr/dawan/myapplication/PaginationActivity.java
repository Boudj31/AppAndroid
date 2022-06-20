package fr.dawan.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PaginationActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> adapter;
    List<String> items = new ArrayList<>();
    Context context = this;

    Button btnNext, btnPrevious;
    TextView tvPage;

    int MAX = 3;
    int CURRENT_PAGE = 0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagination);

        listView = findViewById(R.id.lv_pagination);
        btnNext = findViewById(R.id.btnNext);
        btnPrevious = findViewById(R.id.btnPrevious);
        tvPage = findViewById(R.id.tv_page);

        testData();

        List<String> lst = generatePage(CURRENT_PAGE, MAX);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lst);
        listView.setAdapter(adapter);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CURRENT_PAGE++;
                lst.clear();
               lst.addAll(generatePage(CURRENT_PAGE, MAX));
               adapter.notifyDataSetChanged();
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                CURRENT_PAGE--;
                lst.clear();
                lst.addAll(generatePage(CURRENT_PAGE, MAX));
                adapter.notifyDataSetChanged();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<String> generatePage(int current_page, int max) {
        List<String> lst = new ArrayList<>();

        if(CURRENT_PAGE == 0)
            CURRENT_PAGE++;

        tvPage.setText(String.valueOf(CURRENT_PAGE));

        int skip = (CURRENT_PAGE - 1) * MAX;

        lst.addAll(items.stream().skip(skip).limit(MAX).collect(Collectors.toList()));

        //Gestion des boutons
        if (CURRENT_PAGE == 1){
            btnPrevious.setEnabled(false);
        }else
        {
            btnPrevious.setEnabled(true);
        }

        //ceil: soit un int - soit un double arrondi au sup quelque soit la valeur décimal
        //items.size() (int) / max (int) ---> division entière --- besoin de caster le int en double pour avoir une division classique qui permet à la méthode ceil de faire un arrondi supérieur

        int MAX_PAGES = (int) Math.ceil( (double)items.size() / MAX);
        if (MAX_PAGES == CURRENT_PAGE ){
            btnNext.setEnabled(false);
        }else{
            btnNext.setEnabled(true);
        }



        return lst;
    }

    private void testData() {
        items.add("Chaine1");
        items.add("Chaine2");
        items.add("Chaine3");
        items.add("Chaine4");
        items.add("Chaine5");
        items.add("Chaine6");
        items.add("Chaine7");
        items.add("Chaine8");
        items.add("Chaine9");
        items.add("Chaine10");
    }
}