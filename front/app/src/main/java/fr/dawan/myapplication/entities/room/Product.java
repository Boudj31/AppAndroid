package fr.dawan.myapplication.entities.room;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "t_products")
public class Product implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long productId;

    private long categoryId; //foreignKey

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    private String description;
    private double price;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    //ROOM exige d'ignorer le constructeur avec paramétres

@Ignore
    public Product(long productId, long categoryId, String description, double price) {
        this.productId = productId;
        this.categoryId = categoryId;
        this.description = description;
        this.price = price;
    }

    public Product() {
    }

    @Override
    public String toString() {
        return description + '\t' + price;
    }
}
