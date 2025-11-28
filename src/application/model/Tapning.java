package application.model;

import java.util.ArrayList;
import java.util.List;

public class Tapning {
    private double alkoholProcent;
    private String historik;
    private double mængdeVand;
    private String vandOprindelse;
    private double mængde;
    private List<Whisky> whiskyList;

    public double getAlkoholProcent() {
        return alkoholProcent;
    }

    public void setAlkoholProcent(double alkoholProcent) {
        this.alkoholProcent = alkoholProcent;
    }

    public String getHistorik() {
        return historik;
    }

    public void setHistorik(String historik) {
        this.historik = historik;
    }

    public double getMængdeVand() {
        return mængdeVand;
    }

    public void setMængdeVand(double mængdeVand) {
        this.mængdeVand = mængdeVand;
    }

    public String getVandOprindelse() {
        return vandOprindelse;
    }

    public void setVandOprindelse(String vandOprindelse) {
        this.vandOprindelse = vandOprindelse;
    }

    public double getMængde() {
        return mængde;
    }

    public void setMængde(double mængde) {
        this.mængde = mængde;
    }

    public void setWhiskyList(List<Whisky> flasker) {
        this.whiskyList = whiskyList;
    }

    public List<Whisky> getWhiskyList() {
        return new ArrayList<>(whiskyList);
    }

    public void tilføjWhisky(Whisky whisky) {
        if (!whiskyList.contains(whisky)) {
            whiskyList.add(whisky);
        }
    }


    public void fjernWhisky(Whisky whisky) {
        whiskyList.remove(whisky);
    }


    public Tapning(double alkoholProcent, String historik, double mængdeVand, String vandOprindelse, double mængde){
        this.alkoholProcent = alkoholProcent;
        this.historik = historik;
        this.mængdeVand = mængdeVand;
        this.vandOprindelse = vandOprindelse;
        this.mængde = mængde;
        this.whiskyList = new ArrayList<>();
    }

/*    public Whisky opretWhisky(double mængde, String navn, int nummer, double alkoholProcent, String label, WhiskyType type){
        Whisky whisky = new Whisky(mængde, navn, nummer, alkoholProcent, label, type);
        whiskyList.add(whisky);
        return whisky;
    }*/


    public int udregnWhiskyBeholdere(double væskemængde, double flaskeStørrelse){
        return (int) Math.floor(væskemængde / flaskeStørrelse);
    }

    public int tælProdukt(){
        return 0;
    }
}
