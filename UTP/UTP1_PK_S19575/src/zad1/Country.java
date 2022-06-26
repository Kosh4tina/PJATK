package zad1;

import javax.swing.*;

public class Country {

    private ImageIcon imageIcon;
    private String capital;
    private String population;
    private int parseInt;

    public Country(ImageIcon imageIcon, String capital, String population, int parseInt) {
        this.imageIcon=imageIcon;
        this.capital=capital;
        this.population=population;
        this.parseInt=parseInt;
    }

    public Object getFlag() {
        return imageIcon;
    }

    public Object getCountryName() {
        return capital;
    }

    public Object getCapital() {
        return population;
    }

    public Object getPopulation() {
        return parseInt;
    }
}
