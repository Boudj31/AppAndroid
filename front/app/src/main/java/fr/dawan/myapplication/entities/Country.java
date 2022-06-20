package fr.dawan.myapplication.entities;

public class Country {

    private String country;
    private String capital;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public Country(String country, String capital) {
        this.country = country;
        this.capital = capital;
    }
}
