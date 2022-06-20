package fr.dawan.myapplication.entities.retrofit;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {

    private long id;
    //@SerializedName("first_name") à utiliser si le champ JSON est différent du nom de cet attribut
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String imagePath;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLasttName() {
        return lastName;
    }

    public void setLasttName(String lasttName) {
        this.lastName = lasttName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public User(long id, String firstName, String lasttName, String email, String password, String imagePath) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lasttName;
        this.email = email;
        this.password = password;
        this.imagePath = imagePath;
    }

    public User(String firstName, String lasttName, String email, String password, String imagePath) {
        this.firstName = firstName;
        this.lastName = lasttName;
        this.email = email;
        this.password = password;
        this.imagePath = imagePath;
    }

    public User() {
    }

    @Override
    public String toString() {
        return firstName+" "+lastName;
    }
}
