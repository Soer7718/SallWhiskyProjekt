package application.model;

import java.util.ArrayList;
import java.util.List;

public class Tapning {
    private double alkoholProcent;
    private String historik;
    private double mængdeVand;
    private String vandOprindelse;
    private double fuldMængde;
    private List<WhiskyType> typer;
    private List<Whisky> whiskyList;
    private List<Fad> fadList;
    private String whiskyNavn;

    public Tapning(double alkoholProcent, String historik, double mængdeVand, String vandOprindelse, List<WhiskyType> typer, String navn) {
        this.alkoholProcent = alkoholProcent;
        this.historik = historik;
        this.mængdeVand = mængdeVand;
        this.vandOprindelse = vandOprindelse;
        this.typer = typer != null ? new ArrayList<>(typer) : new ArrayList<>();
        this.whiskyList = new ArrayList<>();
        this.fadList = new ArrayList<>();
        this.whiskyNavn = navn;
    }


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

    public double getFuldMængde() {
        return fuldMængde;
    }

    public void setFuldMængde(double fuldMængde) {
        this.fuldMængde = fuldMængde;
    }

    public List<WhiskyType> getTyper() {
        return new ArrayList<>(typer);
    }

    public void setTyper(List<WhiskyType> typer) {
        this.typer = typer != null ? new ArrayList<>(typer) : new ArrayList<>();
    }

    public List<Whisky> getWhiskyList() {
        return new ArrayList<>(whiskyList);
    }

    public void setWhiskyList(List<Whisky> whiskyList) {
        this.whiskyList = whiskyList != null ? new ArrayList<>(whiskyList) : new ArrayList<>();
    }

    public String getWhiskyNavn() {
        return whiskyNavn;
    }

    public void setWhiskyNavn(String whiskyNavn) {
        this.whiskyNavn = whiskyNavn;
    }

    public void tilføjWhisky(Whisky whisky) {
        if (whisky != null && !whiskyList.contains(whisky)) {
            whiskyList.add(whisky);
        }
    }

    public void fjernWhisky(Whisky whisky) {
        if (whisky != null) {
            whiskyList.remove(whisky);
        }
    }

    public List<Fad> getFadList() {
        return new ArrayList<>(fadList);
    }

    public void tilføjFad(Fad fad) {
        if (fad != null && !fadList.contains(fad)) {
            fadList.add(fad);
            fad.addTapning(this);
        }
    }

    public double udregnFuldMængde(){
        double tempMængde = mængdeVand;
        for (Fad f : fadList){
            tempMængde += f.getFyldt();
        }
        return tempMængde;
    }

    public int udregnWhiskyBeholdere(double flaskeStørrelse) {
        return (int) Math.floor(udregnFuldMængde() / flaskeStørrelse);
    }

    public int tælProdukt() {
        return whiskyList.size();
    }

    public List<Whisky> opretWhisky(double flaskeStørrelse) {
        int antalFlasker = udregnWhiskyBeholdere(flaskeStørrelse);

        for (int i = 0; i < antalFlasker; i++) {
            int nummer = tælProdukt() + 1;

            Whisky w = new Whisky(

                    flaskeStørrelse,
                    whiskyNavn,
                    nummer,
                    alkoholProcent,
                    "Standard Label",
                    typer.isEmpty() ? null : typer.get(0),
                    this
            );

            whiskyList.add(w);
        }
        return whiskyList;
    }

    @Override
    public String toString() {
        return "Tapning{" +
                "alkoholProcent=" + alkoholProcent +
                ", historik='" + historik + '\'' +
                ", fuldMængde=" + fuldMængde +
                ", typer=" + typer +
                '}';
    }
}
