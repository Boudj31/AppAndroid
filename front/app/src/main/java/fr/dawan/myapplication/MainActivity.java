package fr.dawan.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import fr.dawan.myapplication.entities.room.Category;
import fr.dawan.myapplication.entities.room.Product;
import fr.dawan.myapplication.entities.room.ProductSupplierCrossRef;
import fr.dawan.myapplication.entities.room.Supplier;
import fr.dawan.myapplication.repositories.room.MyDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Exemple de notification avec différents channel - Utilisation de Application Classe (MyApplicationClasse)
    //Permission VIBRATE dans le Manifest.xml
    public void btnChennelClick(View view) {
        startActivity(new Intent(this, NotifChannelActivity.class));
    }

    /*
    RecycleView est le successeur de ListView et GridView
    Personnalisation poussée des items à afficher
    Ajouter build.gradle la dependence : implementation 'adroidx.recycleview:1.2.0'
     */


    public void btnRecycleViewClick(View view) {
        startActivity(new Intent(this, RecycleViewActivity.class));
    }

    //Exemple de pagination
    public void btnPaginationClick(View view) {

        startActivity(new Intent(this, PaginationActivity.class));
    }

    //RoomClick
    public void btnRoomClick(View view) {

        //Insertion d'un jeu de données de test
        //Important: les opérations sur des BD peuvent durées un certain temps, ce qui aura pour conséquence le crash de l'application
        //Prévoir un Thread pour les exécuter

        new Thread(new Runnable() {
            @Override
            public void run() {

                //Instancier la BD
                MyDatabase db = MyDatabase.getInstance(MainActivity.this);

                //Vérifier si les données de test ont été insérées ou pas
               if (db.categoryDao().getAllCategories().size() == 0) {

                   //Insértions
                   db.categoryDao().insert(new Category(0, "Informatique"));
                   db.categoryDao().insert(new Category(0, "Alimentaire"));

                   db.productDao().insert(new Product(0, 1, "PC Dell", 1500));
                   db.productDao().insert(new Product(0, 1, "Ecarn HP", 115));
                   db.productDao().insert(new Product(0, 2, "Chocolat", 15));
                   db.productDao().insert(new Product(0, 2, "Pattes", 25));

                   db.supplierDao().insert(new Supplier(0, "Dell"));
                   db.supplierDao().insert(new Supplier(0, "HP"));
                   db.supplierDao().insert(new Supplier(0, "Carrefour"));

                   //Table Many-To-Many
                   db.productSupplierDao().insert(new ProductSupplierCrossRef(1, 1));
                   db.productSupplierDao().insert(new ProductSupplierCrossRef(2, 2));
                   db.productSupplierDao().insert(new ProductSupplierCrossRef(3, 3));
                   db.productSupplierDao().insert(new ProductSupplierCrossRef(4, 3));

                   Log.i(">>>ROOM", "Insertion OK");

                   //Le thread n'a pas accés aux éléments graphiques

                   //seul le thread d'affichage peut manipuler les éléments graphiques
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           Toast.makeText(MainActivity.this, "Données de test insérées", Toast.LENGTH_SHORT).show();
                       }
                   });


               }else{
                   Log.i(">>>ROOM", "Données de test déjà insérées");
               }

            }
        }).start();

        startActivity(new Intent(this, RoomActivity.class));
    }

    //btnRetrofit
    public void btnRetrofitClickClick(View view) {
        startActivity(new Intent(this, RetrofitActivity.class));
    }

    //btn DataBinding
    public void btnBindingClick(View view) {
        startActivity(new Intent(this, DemoBinding.class));
    }


//btn MVVM
    public void btnMvvmClick(View view) {
        startActivity(new Intent(this, MvvmActivity.class));
    }

    //Btn Espresso
    public void btnEspressoClick(View view) {
        startActivity(new Intent(this, EspressoActivity.class));
    }

}