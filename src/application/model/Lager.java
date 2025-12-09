
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

    //Constructor
    public Lager(String navn, String adresse) {
        this.navn = navn;
        this.adresse = adresse;
    }

    //---Destillater---
    public List<Destilat> getDestillatListe() {
        return new ArrayList<>(destillatListe);
    }
    public void addDestillat(Destilat d) {
        destillatListe.add(d);
    }



    public List<Fad> getFadList() {
        return new ArrayList<>(fadList);
    }

    // Getters og Setters
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


    //Create Metode
    public void createGang(int antalReoler, int antalHylder) {
            Gang gang = new Gang(this,gangList.size() + 1);
            gang.createReol(antalReoler, antalHylder);

            gangList.add(gang);
    }

    //Hjælpe-Metoder
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

    private Object[] findLedigPlads(Fad fad, Lager lager) {
        int pladserBehov = fad.getStørrelseLiter() <= 50 ? 1 : 2;

        for (Gang gang : lager.getGangList()) {
            for (Reol reol : gang.getReolList()) {
                for (Hylde hylde : reol.getHyldeList()) {
                    for (Palle palle : hylde.getPalleList()) {
                        Fad[] pladser = palle.getPallePlads();

                        for (int i = 0; i < pladser.length; i++) {
                            if (pladser[i] == null) {
                                if (pladserBehov == 2 && (i + 1 >= pladser.length || pladser[i + 1] != null)) {
                                    continue;
                                }
                                return new Object[]{palle, i};
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public boolean placerFad(Fad fad) {
        int pladserBehov = fad.getStørrelseLiter() <= 50 ? 1 : 2;

        for (Gang gang : gangList) {
            for (Reol reol : gang.getReolList()) {
                for (Hylde hylde : reol.getHyldeList()) {
                    for (Palle palle : hylde.getPalleList()) {
                        Fad[] pladser = palle.getPallePlads();

                        for (int i = 0; i <= pladser.length - pladserBehov; i++) {
                            boolean kanPlaceres = true;
                            for (int j = 0; j < pladserBehov; j++) {
                                if (pladser[i + j] != null) {
                                    kanPlaceres = false;
                                    break;
                                }
                            }

                            if (kanPlaceres) {
                                for (int j = 0; j < pladserBehov; j++) {
                                    pladser[i + j] = fad;
                                }

                                // Sæt korrekt placering
                                String placering = String.format(
                                        "%s,%02d,%02d,%02d,%02d,%02d",
                                        palle.getHylde().getReol().getGang().getLager().getNavn(),
                                        palle.getHylde().getReol().getGang().getGangNummer(),
                                        palle.getHylde().getReol().getReolNummer(),
                                        palle.getHylde().getHyldeNummer(),
                                        palle.getPalleNummer(),
                                        i + 1
                                );

                                fad.setPlacering(placering);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }





}
