package fr.dawan.myapplication.viewModels;


import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import fr.dawan.myapplication.entities.room.Product;
import fr.dawan.myapplication.repositories.room.MyDatabase;

public class ProductViewModel extends AndroidViewModel {

    private MutableLiveData<List<Product>> mutableLstProducts; //Le type mutable possède la méthode postValue qui envoie une notif à l'activity qui observe cette liste

    private MutableLiveData<Boolean> isUpdating = new MutableLiveData<>();
    Context context;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }


    public LiveData<List<Product>> getProductsList(){

        if (mutableLstProducts == null){
            MyDatabase db = MyDatabase.getInstance(context);
            mutableLstProducts = new MutableLiveData<>(db.productDao().getAllProducts());

        }

        return mutableLstProducts;

    }

    public LiveData<Boolean> getIsUpdating(){
        return isUpdating;
    }

    public void insert(Product p) {
        isUpdating.setValue(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                MyDatabase db = MyDatabase.getInstance(context);
                db.productDao().insert(p);

                try {
                    //Simuler une tâche lente
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //La liste des produits à été modifiée. Envoyer une notifi aux activities qui observent cette list de produi
                mutableLstProducts.postValue(db.productDao().getAllProducts());
                isUpdating.postValue(false);
            }
        }).start();
    }
}
