package application.model;

import java.util.Arrays;

public class Palle {
    private int palleNummer;
    private Fad[] pallePlads = new Fad[2];
    private Hylde hylde;

    public Palle(Hylde hylde) {
        this.hylde = hylde;
        this.palleNummer = palleNummer;
    }

    public Hylde getHylde() {
        return hylde;
    }

    public Fad[] getPallePlads() {
        return Arrays.copyOf(pallePlads, pallePlads.length);
    }

    public void setPallePlads(Fad[] pallePlads) {
        this.pallePlads = pallePlads;
    }

    public int getLedigePladser() {
        int count = 0;
        for (Fad f : pallePlads) {
            if (f == null) count++;
        }
        return count;
    }

    public boolean placerFad(Fad fad) {
        for (int i = 0; i < pallePlads.length; i++) {
            if (pallePlads[i] == null) {
                pallePlads[i] = fad;
                return true;
            }
        }
        return false;
    }
}
