package application.model;

import java.util.Arrays;

public class Palle {
    private int palleNummer;
    private Fad[] pallePlads = new Fad[2];
    private Hylde hylde;
    private static int seq = 1;

    //constructor
    public Palle(Hylde hylde) {
        this.hylde = hylde;
        this.palleNummer = seq++;
    }
    //Getters og Setters
    public int getPalleNummer() { return palleNummer; }
    public Hylde getHylde() {
        return hylde;
    }
    public Fad[] getPallePlads() {return pallePlads; }
    public void setPallePlads(Fad[] pallePlads) {
        this.pallePlads = pallePlads;
    }

    //Hjælpe-Metode
    public int getLedigePladser() {
        int count = 0;
        for (Fad f : pallePlads) {
            if (f == null) count++;
        }
        return count;
    }
}
