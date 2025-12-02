
package application.model;
import java.util.ArrayList;
import java.util.List;

public class Lager {
    private String navn;
    private int pladser;
    private String adresse;
    private List<Gang> gangList = new ArrayList<>();
    private List<Fad> fadList = new ArrayList<>();
    private List<Destilat> destillatListe = new ArrayList<>();

    public Lager(String navn, String adresse) {
        this.navn = navn;
        this.adresse = adresse;
    }

    // === Destillat ===
    public List<Destilat> getDestillatListe() {
        return new ArrayList<>(destillatListe);
    }

    public void addDestillat(Destilat d) {
        destillatListe.add(d);
    }

    // === Fad ===
    public Fad registrerFad(int id, int antalGangeBrugt, String type, String leverandør, String historik, double størrelseLiter) {
        Fad fad = new Fad(id, antalGangeBrugt, type, leverandør, historik, størrelseLiter);
        fadList.add(fad);
        return fad;
    }

    public List<Fad> getFadList() {
        return new ArrayList<>(fadList);
    }

    // === Getters og Setters ===
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

    private List<Tapning> tapningList = new ArrayList<>();

    public void addTapning(Tapning tapning) {
        tapningList.add(tapning);
    }

    public List<Tapning> getTapningList() {
        return new ArrayList<>(tapningList);
    }

    public void createGang(int antalReoler, int antalHylder) {
            Gang gang = new Gang(this,gangList.size() + 1);
            gang.createReol(antalReoler, antalHylder);

            gangList.add(gang);
    }

    public int udregnAntalPladser() {
        //TODO
        return -1;
    }

    public int udregnLedigePladser() {
        //TODO
        return -1;
    }

}
