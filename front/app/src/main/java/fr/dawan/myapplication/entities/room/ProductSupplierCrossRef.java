package fr.dawan.myapplication.entities.room;

import androidx.room.Entity;
import androidx.room.Ignore;

import java.io.Serializable;

//Table qui gère le Many-To-Many entre product et supplier

@Entity(tableName = "t_products_suppliers", primaryKeys = {"productId", "supplierId"})
public class ProductSupplierCrossRef implements Serializable {

    private long productId;
    private long supplierId;

    @Ignore
    public ProductSupplierCrossRef(long productId, long supplierId) {
        this.productId = productId;
        this.supplierId = supplierId;
    }

    public ProductSupplierCrossRef() {
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }
}
