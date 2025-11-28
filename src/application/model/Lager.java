package application.model;

import java.util.ArrayList;
import java.util.List;

public class Lager {
    private String navn;
    private int pladser;
    private String adresse;
    private List<Gang> gangList = new ArrayList<>();
    private List<Fad> fadList;

    public Lager(String navn, String adresse) {
        this.navn = navn;
        this.adresse = adresse;
        this.fadList = new ArrayList<>();
    }



    public List<Fad> getFadList(){
        return new ArrayList<>(fadList);
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public int getPladser() {
        return pladser;
    }

    public void setPladser(int pladser) {
        this.pladser = pladser;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public List<Gang> getGangList() {
        return new ArrayList<>(gangList);
    }

    public void createGang(int antal) {
        for (int i = 0; i < antal; i++) {
            Gang gang = new Gang(this,gangList.size()+1);
            gangList.add(gang);
        }
    }

}
