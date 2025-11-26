package application.model;

import java.util.Arrays;

public class Palle {
    private Fad[] pallePlads = new Fad[2];
    private Hylde hylde;

    public Palle(Hylde hylde) {
        this.hylde = hylde;
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


}
