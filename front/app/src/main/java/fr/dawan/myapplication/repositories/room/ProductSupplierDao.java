package fr.dawan.myapplication.repositories.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import fr.dawan.myapplication.entities.room.ProductSupplierCrossRef;

@Dao
public interface ProductSupplierDao {

    @Insert
    void insert(ProductSupplierCrossRef ref);

    @Query("SELECT * FROM t_products_suppliers")
    List<ProductSupplierCrossRef> findAll();
}
