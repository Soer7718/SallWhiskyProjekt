package application.model;

import java.util.ArrayList;
import java.util.List;

public class Reol {
    private int reolNummer;
    private List<Hylde> hyldeList = new ArrayList<>();
    private Gang gang;

    //Constructor
    public Reol(Gang gang, int reolNummer, int antalHylder) {
        this.gang = gang;
        this.reolNummer = reolNummer;
        for (int i = 0; i < antalHylder; i++) {
            hyldeList.add(new Hylde(this,i+1));
        }
    }

    //Getters og Setters
    public int getReolNummer() {
        return reolNummer;
    }
    public List<Hylde> getHyldeList() {
        return new ArrayList<>(hyldeList);
    }
    public Gang getGang() {
        return gang;
    }

    //Create-Metoder
    public void createAntalHylder(int antal) {
        for (int i = 0; i < antal; i++) {
            Hylde hylde = new Hylde(this, hyldeList.size()+1);
            hyldeList.add(hylde);
        }
    }
}
