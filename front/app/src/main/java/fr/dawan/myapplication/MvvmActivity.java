package fr.dawan.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import fr.dawan.myapplication.entities.room.Product;
import fr.dawan.myapplication.viewModels.ProductViewModel;

public class MvvmActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<Product> adapter;
    private ProductViewModel productViewModel;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvvm);

        progressBar = findViewById(R.id.progressBar);
        listView = findViewById(R.id.lv_mvvm_product);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());

        productViewModel = new ProductViewModel(getApplication());

        fillListView();

        productViewModel.getIsUpdating().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    progressBar.setVisibility(View.VISIBLE);
                }else{
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void fillListView() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Product> lp = productViewModel.getProductsList().getValue();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        productViewModel.getProductsList().observe(MvvmActivity.this, new Observer<List<Product>>() {
                            @Override
                            public void onChanged(List<Product> products) {
                                adapter.clear();
                                adapter.addAll(products);
                                adapter.notifyDataSetChanged();
                            }
                        });

                        adapter.clear();
                        adapter.addAll(lp);
                        adapter.notifyDataSetChanged();
                        listView.setAdapter(adapter);

                    }
                });
            }
        }).start();
    }

    public void btnAjouterProduit(View view) {
        productViewModel.insert(new Product(0,1,"Imprimante", 1800));
    }
}