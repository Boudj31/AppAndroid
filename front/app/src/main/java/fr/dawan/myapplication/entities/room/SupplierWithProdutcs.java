package fr.dawan.myapplication.entities.room;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class SupplierWithProdutcs {

    @Embedded
    private Supplier supplier;

    @Relation(parentColumn = "supplierId", entityColumn = "productId", associateBy = @Junction(ProductSupplierCrossRef.class))
    private List<Product> products;

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
