package fr.dawan.myapplication.entities.room;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class ProductWithSuppliers {

    @Embedded
    private Product product;

    @Relation(parentColumn = "productId", entityColumn = "supplierId", associateBy = @Junction(ProductSupplierCrossRef.class))
    private List<Supplier> suppliers;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<Supplier> suppliers) {
        this.suppliers = suppliers;
    }
}
