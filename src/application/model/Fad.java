package application.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Fad {
    private int antalGangeBrugt;
    private String type;
    private String leverandør;
    private String historik;
    private List<Påfyldning> påfyldninger;

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

    public void setPåfyldninger(List<Påfyldning> påfyldninger) {
        this.påfyldninger = påfyldninger;
    }

    public Fad(int antalGangeBrugt, String type, String leverandør, String historik){
        this.antalGangeBrugt = antalGangeBrugt;
        this.type = type;
        this.leverandør = leverandør;
        this.historik = historik;
        this.påfyldninger = new ArrayList<>();
    }

    public Påfyldning opretPåfyldning(LocalDate dato, Destilat destilat){
        Påfyldning p = new Påfyldning(dato, destilat);
        påfyldninger.add(p);
        return p;
    }

    public double beregnAlkoholProcent(){
        return 0.0;
    }

    public double beregnMængde(){
        return 0.0;
    }

    public List<Påfyldning> getPåfyldninger(){
        return new ArrayList<>(påfyldninger);
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
