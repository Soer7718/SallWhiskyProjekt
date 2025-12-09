package application.model;

public class Hylde {
    private int hyldeNummer;
    private Palle[] palleList = new Palle[3];
    private Reol reol;

    //Constructor
    public Hylde(Reol reol, int hyldeNummer) {
        this.reol = reol;
        this.hyldeNummer = hyldeNummer;
        createPaller();
    }

    //Getters og Setters
    public int getHyldeNummer() {
        return hyldeNummer;
    }
    public Palle[] getPalleList() {
        return palleList.clone();
    }
    public Reol getReol() {
        return reol;
    }

    //---Paller---
    public void createPaller() {
        for (int i = 0; i < 3; i++) {
            Palle palle = new Palle(this);
            palleList[i] = palle;
        }
    }
}
