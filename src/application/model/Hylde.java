package application.model;

public class Hylde {
    private int hyldeNummer;
    private Palle[] palleList = new Palle[3];
    private Reol reol;

    public Hylde(Reol reol, int hyldeNummer) {
        this.reol = reol;
        this.hyldeNummer = hyldeNummer;
        createPaller();
    }

    public void createPaller() {
        for (int i = 0; i < 3; i++) {
            Palle palle = new Palle(this);
            palleList[i] = palle;
        }
    }

    public int getHyldeNummer() {
        return hyldeNummer;
    }

    public Palle[] getPalleList() {
        return palleList.clone();
    }

    public Reol getReol() {
        return reol;
    }
}
