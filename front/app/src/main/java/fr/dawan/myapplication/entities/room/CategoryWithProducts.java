package fr.dawan.myapplication.entities.room;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CategoryWithProducts {

    @Embedded
    private Category category;

    @Relation(parentColumn = "categoryId", entityColumn = "categoryId")
    private List<Product> products;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
