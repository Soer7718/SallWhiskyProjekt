
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
    public Fad registrerFad(int id, int antalGangeBrugt, String type, String leverandør, String historik, double liter) {
        Fad fad = new Fad(id, antalGangeBrugt, type, leverandør, historik,liter);
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
        int totalPladser = 0;
        for (Gang gang : gangList) {
            for (Reol reol : gang.getReolList()) {
                for (Hylde hylde : reol.getHyldeList()) {
                    totalPladser += hylde.getPalleList().length;
                }
            }
        }
        return totalPladser;
    }

    public int getLedigePladser() {
        int ledigePaller = 0;

        for (Gang gang : gangList) {
            for (Reol reol : gang.getReolList()) {
                for (Hylde hylde : reol.getHyldeList()) {
                    for (Palle palle : hylde.getPalleList()) {
                        for (Fad fad : palle.getPallePlads()) {
                            if (fad == null) ledigePaller++;
                        }
                    }
                }
            }
        }
        return ledigePaller;
    }

    public Palle findLedigPalle(Fad fad) {
        int pladserBehov = fad.getStørrelseLiter() <= 50 ? 1 : 2;

        for (Gang gang : gangList) {
            for (Reol reol : gang.getReolList()) {
                for (Hylde hylde : reol.getHyldeList()) {
                    for (Palle palle : hylde.getPalleList()) {
                        int ledigePladser = palle.getLedigePladser();
                        if (ledigePladser >= pladserBehov) {
                            return palle;
                        }
                    }
                }
            }
        }
        return null; // ingen ledig palle
    }

    public boolean placerFad(Fad fad) {
        Palle ledigPalle = findLedigPalle(fad);

        if (ledigPalle != null) {

            for (int i = 0; i < ledigPalle.getPallePlads().length; i++) {

                if (ledigPalle.getPallePlads()[i] == null) {
                    ledigPalle.getPallePlads()[i] = fad;

                    // FORMATERET PLACERING MED 0-FYLD
                    Lager lager = ledigPalle.getHylde().getReol().getGang().getLager();
                    Gang gang = ledigPalle.getHylde().getReol().getGang();
                    Reol reol = ledigPalle.getHylde().getReol();
                    Hylde hylde = ledigPalle.getHylde();

                    String placering = String.format(
                            "Lager %02d, Gang %02d, Reol %02d, Hylde %02d, Palle %02d, Plads %d",
                            lager.getNavn(),
                            gang.getGangNummer(),
                            reol.getReolNummer(),
                            hylde.getHyldeNummer(),
                            ledigPalle.getPalleNummer(),
                            i + 1
                    );

                    fad.setPlacering(placering);

                    return true;
                }
            }
        }
        return false;
    }





}
