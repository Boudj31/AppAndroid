package fr.dawan.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import fr.dawan.myapplication.entities.room.Category;
import fr.dawan.myapplication.entities.room.Product;
import fr.dawan.myapplication.repositories.room.MyDatabase;

public class RoomProductsActivity extends AppCompatActivity {

    ArrayList<Product> products;
    long selectedRoomCategoryId;

    ArrayAdapter<Category> categoriesAdapter;
    ArrayAdapter<Product> productsAdapter;

    ListView lvProducts;
    Spinner spinnerCategories;

    EditText edDescription, edPrix;
    Button btnAjouter;

    List<Category> categories;

    long selectedCategoryIdSpinner;

    Product selectedProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_products);

        lvProducts = findViewById(R.id.lvProducts);
        edDescription = findViewById(R.id.edDescription);
        edPrix = findViewById(R.id.edPrix);
        btnAjouter = findViewById(R.id.btnAjouter);
        spinnerCategories = findViewById(R.id.spinnerCategories);

        //Récupérer la liste des produits fournie par RoomActivity
        products = (ArrayList<Product>) getIntent().getSerializableExtra("products");
        selectedRoomCategoryId = getIntent().getLongExtra("selectedCategoryId", 0);

        //Remplir la listView
        productsAdapter = new ArrayAdapter<Product>(this, android.R.layout.simple_list_item_1, products);
        lvProducts.setAdapter(productsAdapter);

        //Remplir le Spinner
        fillSpinner();

        //Récupéerer depuis le Spinner l'id de la catégorie selectionnée

        spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedCategoryIdSpinner =  categoriesAdapter.getItem(position).getCategoryId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Simple click sur un produit de la listView - Mode Modif
        lvProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectedProduct = products.get(position);
                edDescription.setText(selectedProduct.getDescription());
                edPrix.setText(String.valueOf(selectedProduct.getPrice()));
                spinnerCategories.setSelection(categories.indexOf(categories.stream().filter(c -> c.getCategoryId() == selectedProduct.getCategoryId()).findAny().get()));
                btnAjouter.setText("Modifier");

            }
        });


        //Long click sur un produit de la listView - Suppression avec confirmation
        lvProducts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {

                AlertDialog.Builder builder = new AlertDialog.Builder(RoomProductsActivity.this);
                builder.setTitle("Suppression d'un produit")
                        .setMessage("Supprimer le produit?")
                        .setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Product p = productsAdapter.getItem(position);
                                        MyDatabase db = MyDatabase.getInstance(RoomProductsActivity.this);
                                        db.productDao().delete(p);
                                        products.clear();
                                        products.addAll(db.productDao().findByCategoryId(selectedRoomCategoryId));

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                productsAdapter.notifyDataSetChanged();
                                            }
                                        });
                                    }
                                }).start();
                            }
                        })
                        .setNegativeButton("NON", null);

                builder.create().show();

                return true;
            }
        });


    }

    private void fillSpinner() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MyDatabase db = MyDatabase.getInstance(RoomProductsActivity.this);
                categories = db.categoryDao().getAllCategories();
                runOnUiThread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void run() {
                        categoriesAdapter = new ArrayAdapter<>(RoomProductsActivity.this, android.R.layout.simple_list_item_1, categories);
                        spinnerCategories.setAdapter(categoriesAdapter);
                        spinnerCategories.setSelection(categories.indexOf( categories.stream().filter(c -> c.getCategoryId() == selectedRoomCategoryId).findAny().get()));
                    }
                });
            }
        }).start();
    }


    public void btnAjouterClick(View view) {
        if (edDescription.getText().toString().equals("")){
            edDescription.setError("Not Empty");
        }else if(edPrix.getText().equals("")){
            edPrix.setError("Not Empty");
        }else{
            Product p = new Product(0, selectedCategoryIdSpinner, edDescription.getText().toString(), Double.parseDouble(edPrix.getText().toString()) );

            //Vérifier le texte du bouton ajouter
            boolean ajouter = btnAjouter.getText().toString().equals("Ajouter");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    MyDatabase db = MyDatabase.getInstance(RoomProductsActivity.this);

                    if (ajouter){
                        db.productDao().insert(p);
                    }else{
                        p.setProductId(selectedProduct.getProductId());
                        db.productDao().update(p);
                    }


                    //Si le produit inséré fait partie de la catégorie en cours, actualiser la ListView
                    if(selectedRoomCategoryId == selectedCategoryIdSpinner){
                        products.clear();
                        products.addAll(db.productDao().findByCategoryId(selectedRoomCategoryId));
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(selectedRoomCategoryId == selectedCategoryIdSpinner){
                                productsAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }).start();
        }

        modeAjout();
    }

    private void modeAjout() {
        edDescription.setText("");
        edPrix.setText("");
        btnAjouter.setText("Ajouter");
    }
}