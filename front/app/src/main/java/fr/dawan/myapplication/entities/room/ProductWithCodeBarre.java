package fr.dawan.myapplication.entities.room;

import androidx.room.Embedded;
import androidx.room.Relation;

public class ProductWithCodeBarre {

    @Embedded
    private Product product;

    @Relation(parentColumn = "productId", entityColumn = "productId")
    private CodeBarre codeBarre;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public CodeBarre getCodeBarre() {
        return codeBarre;
    }

    public void setCodeBarre(CodeBarre codeBarre) {
        this.codeBarre = codeBarre;
    }
}
