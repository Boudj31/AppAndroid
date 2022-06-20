package fr.dawan.myapplication.entities.room;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "t_codebarre")
public class CodeBarre implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long codebarreId;
    private String code;
    private long productId; //foreignKey

    @Ignore
    public CodeBarre(long codebarreId, String code, long productId) {
        this.codebarreId = codebarreId;
        this.code = code;
        this.productId = productId;
    }

    public CodeBarre() {
    }

    public long getCodebarreId() {
        return codebarreId;
    }

    public void setCodebarreId(long codebarreId) {
        this.codebarreId = codebarreId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }
}
