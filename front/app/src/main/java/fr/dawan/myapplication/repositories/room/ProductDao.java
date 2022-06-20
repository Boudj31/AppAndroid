package fr.dawan.myapplication.repositories.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import fr.dawan.myapplication.entities.room.Product;
import fr.dawan.myapplication.entities.room.ProductWithCodeBarre;
import fr.dawan.myapplication.entities.room.ProductWithSuppliers;

//Equivalent des JPA Repository

@Dao
public interface ProductDao {

    @Insert
    void insert(Product p);

    @Insert
    void insertAllProducts(Product... products);

    @Update
    void update(Product p);

    @Delete
    void delete(Product p);

    @Query("DELETE FROM t_products WHERE productId =:id")
    void deleteById(long id);

    @Query("SELECT * FROM t_products")
    List<Product> getAllProducts();

    //one-to-many
    @Query("SELECT * FROM t_products WHERE categoryId =:categoryId")
    List<Product> findByCategoryId(long categoryId);

    @Query("SELECt * FROM t_products WHERE productId =:id")
    Product getById(long id);

    //one-to-one
    @Transaction //ROOM lance 2 requêtes
    @Query("SELECT * FROM t_products")
    List<ProductWithCodeBarre> productAndCodeBarre();

    //many-to-many
    @Transaction
    @Query("SELECT * FROM t_products")
    public List<ProductWithSuppliers>  productWithSuppliers();

}
