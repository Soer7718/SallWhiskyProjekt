package application.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Destilat {
    private double alkoholProcent;
    private double mængde;
    private String mark;
    private String kornSort;
    private int maltBatch;
    private LocalDate startDato;
    private LocalDate slutDato;
    private String rygeMateriale;
    private String kommentar;
    private List<Påfyldning> påfyldningList = new ArrayList<Påfyldning>();

    public Destilat(double alkoholProcent, double mængde, String mark, String kornSort, int maltBatch, LocalDate slutDato, String rygeMateriale, String kommentar) {
        this.alkoholProcent = alkoholProcent;
        this.mængde = mængde;
        this.mark = mark;
        this.kornSort = kornSort;
        this.maltBatch = maltBatch;
        this.startDato = LocalDate.now();
        this.slutDato = slutDato;
        this.rygeMateriale = rygeMateriale;
        this.kommentar = kommentar;
    }

    public double getAlkoholProcent() {
        return alkoholProcent;
    }

    public void setAlkoholProcent(double alkoholProcent) {
        this.alkoholProcent = alkoholProcent;
    }

    public double getMængde() {
        return mængde;
    }

    public void setMængde(double mængde) {
        this.mængde = mængde;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getKornSort() {
        return kornSort;
    }

    public void setKornSort(String kornSort) {
        this.kornSort = kornSort;
    }

    public int getMaltBatch() {
        return maltBatch;
    }

    public void setMaltBatch(int maltBatch) {
        this.maltBatch = maltBatch;
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

    public String getRygeMateriale() {
        return rygeMateriale;
    }

    public void setRygeMateriale(String rygeMateriale) {
        this.rygeMateriale = rygeMateriale;
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }

    public List<Påfyldning> getPåfyldningList() {
        return new ArrayList<>(påfyldningList);
    }
    
    public void addPåfyldning(Påfyldning påfyldning) {
        if (!påfyldningList.contains(påfyldning)) {
            påfyldningList.add(påfyldning);
        }
    }
    
    public void removePåfyldning(Påfyldning påfyldning) {
        påfyldningList.remove(påfyldning);
    }


    public Påfyldning opretPåfyldning(LocalDate localDate) {
        Påfyldning påfyldning = new Påfyldning(localDate,this);
        return påfyldning;
    }

    @Override
    public String toString() {
        return "Destilat{" +
                "kommentar='" + kommentar + '\'' +
                ", rygeMateriale='" + rygeMateriale + '\'' +
                ", slutDato=" + slutDato +
                ", startDato=" + startDato +
                ", maltBatch=" + maltBatch +
                ", kornSort='" + kornSort + '\'' +
                ", mark='" + mark + '\'' +
                ", mængde=" + mængde +
                ", alkoholProcent=" + alkoholProcent +
                '}';
    }
}
