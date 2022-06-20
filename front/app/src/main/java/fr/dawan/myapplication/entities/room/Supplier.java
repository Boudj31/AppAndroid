package fr.dawan.myapplication.entities.room;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "t_suppliers")
public class Supplier implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long supplierId;
    private String name;

    @Ignore
    public Supplier(long supplierId, String name) {
        this.supplierId = supplierId;
        this.name = name;
    }

    public Supplier() {
    }

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
