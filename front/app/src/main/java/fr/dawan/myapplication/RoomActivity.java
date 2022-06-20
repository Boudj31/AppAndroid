package fr.dawan.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.dawan.myapplication.entities.room.Category;
import fr.dawan.myapplication.entities.room.Product;
import fr.dawan.myapplication.repositories.room.MyDatabase;

public class RoomActivity extends AppCompatActivity {

    Button btnSearch;
    EditText edSearchCategory;
    TextView tvNbreCategories;
    ListView lvCategories;
    ArrayAdapter<Category>  adapter;
    MyDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        edSearchCategory = findViewById(R.id.edSearchCategory);
        tvNbreCategories = findViewById(R.id.tvNbreCategories);
        lvCategories = findViewById(R.id.lvCategories);
        btnSearch = findViewById(R.id.btnSearchCategory);


        //Click sur les items de la ListView
        lvCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                //Récupérer la catégorie selectionnée
                Category selectedCategory = adapter.getItem(position);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                       List<Product> products = db.productDao().findByCategoryId(selectedCategory.getCategoryId());

                       //Transmettre cette liste de produits à la prochaine Activity
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RoomActivity.this, "nbProds = "+products.size(), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(RoomActivity.this, RoomProductsActivity.class);
                                intent.putExtra("products", new ArrayList<>(products));
                                intent.putExtra("selectedCategoryId", selectedCategory.getCategoryId());
                                startActivity(intent);
                            }
                        });
                    }
                }).start();
            }
        });


    }

    public void btnSearchCategoriesClick(View view) {
        //Masquer le clavier
         InputMethodManager inputManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
         inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

        String search = edSearchCategory.getText().toString();

        new Thread(new Runnable() {
            @Override
            public void run() {
                db = MyDatabase.getInstance(RoomActivity.this);

                List<Category> categories = db.categoryDao().findByName("%"+search+"%");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvNbreCategories.setText(categories.size()+" catégorie trouvées.");
                        adapter = new ArrayAdapter<>(RoomActivity.this, android.R.layout.simple_list_item_1, categories);
                        lvCategories.setAdapter(adapter);
                    }
                });
            }
        }).start();
    }
}