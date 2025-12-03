package application.model;

import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Fad {
    private final int id;
    private int antalGangeBrugt;
    private String type;
    private String leverandør;
    private String historik;
    private double størrelseLiter;
    private double fyldt;
    private ObservableList<Fad> fadListe;
    private List<Tapning> tidligereIndhold;
    private Påfyldning påfyldning;

    public double getFyldt() {
        return fyldt;
    }

    public void setFyldt(double fyldt) {
        this.fyldt = fyldt;
    }

    public Påfyldning getPåfyldning() {
        return påfyldning;
    }

    public void setPåfyldning(Påfyldning påfyldning) {
        this.påfyldning = påfyldning;
    }

    public List<Tapning> getTidligereIndhold() {
        return new ArrayList<>(tidligereIndhold);
    }

    public void setTidligereIndhold(List<Tapning> tidligereIndhold) {
        this.tidligereIndhold = tidligereIndhold;
    }

    public void addTapning(Tapning tapning){
        if (!tidligereIndhold.contains(tapning)){
            tidligereIndhold.add(tapning);
        }
    }

    public int getAntalGangeBrugt() {
        return antalGangeBrugt;
    }

    public void setAntalGangeBrugt(int antalGangeBrugt) {
        this.antalGangeBrugt = antalGangeBrugt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLeverandør() {
        return leverandør;
    }

    public void setLeverandør(String leverandør) {
        this.leverandør = leverandør;
    }

    public String getHistorik() {
        return historik;
    }

    public void setHistorik(String historik) {
        this.historik = historik;
    }

    public Fad(int id, int antalGangeBrugt, String type, String leverandør, String historik, double liter) {
        this.id = id;
        this.antalGangeBrugt = antalGangeBrugt;
        this.type = type;
        this.leverandør = leverandør;
        this.historik = historik;
        this.størrelseLiter = liter;
    }

    public Fad(int id, int antalGangeBrugt, String type, String leverandør, String historik, double liter, double fyldt, List<Tapning> tidligereIndhold, Påfyldning påfyldning) {
        this.id = id;
        this.antalGangeBrugt = antalGangeBrugt;
        this.type = type;
        this.leverandør = leverandør;
        this.historik = historik;
        this.størrelseLiter = liter;
        this.fyldt = fyldt;
        this.tidligereIndhold = tidligereIndhold;
        this.påfyldning = påfyldning;
    }


    public int getId(){
        return id;
    }


    public double udregnLagringsÅr(){

        return 0;
    }

    public double beregnAlkoholProcent(){
        return 0.0;
        //TODO
    }

    public double beregnMængde(){
        return 0.0;
        //TODO
    }

    public boolean erTomt() {
        return fyldt == 0.0;
    }

    public double getStørrelseLiter() {
        return størrelseLiter;
    }

    public void setStørrelseLiter(double størrelseLiter) {
        this.størrelseLiter = størrelseLiter;
    }


    public boolean placerPåLager(Lager lager) {
        Palle ledigPalle = lager.findLedigPalle(this); // findLedigPalle skal være public i Lager
        if (ledigPalle != null) {
            for (int i = 0; i < ledigPalle.getPallePlads().length; i++) {
                if (ledigPalle.getPallePlads()[i] == null) {
                    ledigPalle.getPallePlads()[i] = this;
                    return true; // fad placeret
                }
            }
        }
        return false; // ingen plads
    }

    @Override
    public String toString(){

        return "Fad{" +
                "antalGangeBrugt=" + antalGangeBrugt +
                ", type='" + type + '\'' +
                ", leverandør='" + leverandør + '\'' +
                ", historik='" + historik + '\'' +
                '}';
    }
}
