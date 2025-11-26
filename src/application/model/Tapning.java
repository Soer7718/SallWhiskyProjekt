package application.model;

import java.util.ArrayList;
import java.util.List;

public class Tapning {
    private double alkoholProcent;
    private String historik;
    private double mængdeVand;
    private String vandOprindelse;
    private double mængde;
    private List<Flaske> flasker;


    public List<Flaske> getFlasker() {
        return new ArrayList<>(flasker);
    }

    public void addFlaske(Flaske flaske) {
        if (!flasker.contains(flaske)) {
            flasker.add(flaske);
        }
    }


    public void removeFlaske(Flaske flaske) {
        flasker.remove(flaske);
    }


    public Tapning(double alkoholProcent, String historik, double mængdeVand, String vandOprindelse, double mængde){
        this.alkoholProcent = alkoholProcent;
        this.historik = historik;
        this.mængdeVand = mængdeVand;
        this.vandOprindelse = vandOprindelse;
        this.mængde = mængde;
        this.flasker = new ArrayList<>();
    }

/*    public Flaske opretFlaske(double mængde, String navn, int nummer, double alkoholProcent, String label, WhiskyType type){
        Flaske flaske = new Flaske(mængde, navn, nummer, alkoholProcent, label, type);
        flasker.add(flaske);
        return flaske;
    }*/

    public void addVand(double liter){
        this.mængdeVand += liter;
    }

    public int udregnFlasker(double væskemængde, double flaskeStørrelse){
        return (int) Math.floor(væskemængde / flaskeStørrelse);
    }

    public double udregnMængde( double fadIndhold, double mængdeVand){
        return fadIndhold + mængdeVand;
    }

    public int tælFlaske(){
        return flasker.size();
    }
}
