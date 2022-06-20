package fr.dawan.myapplication.repositories.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import fr.dawan.myapplication.entities.room.Supplier;
import fr.dawan.myapplication.entities.room.SupplierWithProdutcs;

@Dao
public interface SupplierDao {

    @Insert
    long insert(Supplier s);

    @Query("SELECT * FROM t_suppliers")
    List<Supplier> suppliers();

    //May-to-Many

    @Transaction
    @Query("SELECT * FROM t_suppliers")
    List<SupplierWithProdutcs> supplierWithProducts();

}
