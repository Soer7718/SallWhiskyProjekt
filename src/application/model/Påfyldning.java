package application.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Påfyldning {
    private LocalDate startDato;
    private LocalDate slutDato;
    private Destilat destilat;
    private List<Fad> fadList = new ArrayList<Fad>();


    public Påfyldning(LocalDate slutDato, Destilat destilat) {
        this.startDato = destilat.getSlutDato();
        this.slutDato = slutDato;
        this.destilat = destilat;
    }

    public LocalDate getStartDato() {
        return startDato;
    }

    public void setStartDato(LocalDate startDato) {
        this.startDato = startDato;
    }

    public LocalDate getSlutDato() {
        return slutDato;
    }

    public void setSlutDato(LocalDate slutDato) {
        this.slutDato = slutDato;
    }

    public Destilat getDestilat() {
        return destilat;
    }

    public void setDestilat(Destilat destilat) {
        this.destilat = destilat;
    }

    public List<Fad> getFadList() {
        return new ArrayList<>(fadList);
    }

    public void addFad(Fad fad) {
        if (!fadList.contains(fad)) {
            fadList.add(fad);
        }
    }

    public void removeFad(Fad fad) {
        fadList.remove(fad);
    }



}
