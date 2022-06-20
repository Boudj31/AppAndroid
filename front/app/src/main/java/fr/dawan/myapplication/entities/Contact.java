package fr.dawan.myapplication.entities;


import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import fr.dawan.myapplication.BR;

public class Contact extends BaseObservable {
    private String name;

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

        notifyPropertyChanged(BR.name); //BR equivalent de la classe R qu'utilise Android pour sauvegarder les ID des éléments
    }

    public Contact(String name) {
        this.name = name;
    }

    public Contact() {
    }
}
