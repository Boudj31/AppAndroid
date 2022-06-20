package fr.dawan.myapplication.repositories.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import fr.dawan.myapplication.entities.room.Category;
import fr.dawan.myapplication.entities.room.CategoryWithProducts;

@Dao
public interface CategoryDao {

    @Query("SELECT * FROM t_categories")
    List<Category> getAllCategories();

    @Query("SELECt * FROM t_categories WHERE name LIKE :name")
    List<Category> findByName(String name);

    @Insert
    long insert(Category c);

    @Update
    void update(Category c);

    @Delete
    void delete(Category c);

    @Transaction
    @Query("SELECt * FROM T_CATEGORIES")
    List<CategoryWithProducts> getCategoryWithProducts();
}
