
package application.controller;

    import application.model.*;
    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import storage.Storage;
    import java.time.LocalDate;
    import java.util.ArrayList;
    import java.util.List;

    public class Controller {
        private static final ObservableList<Fad> fadListe = FXCollections.observableArrayList(Storage.getFadList());
        private static final ObservableList<Destilat> destillatListe = FXCollections.observableArrayList(Storage.getDestilatList());
        private static Controller instance;

        //Getters og Setters
        public static ObservableList<Fad> getFadListe() {
            return fadListe;
        }
        public static Controller getInstance() {
            if (instance == null) {
                instance = new Controller();
            }
            return instance;
        }
        public static List<Destilat> getDestillatListe() {
            return new ArrayList<>(Storage.getDestilatList()) ;
        }
        public static ObservableList<Destilat> getDestillatListeObs() {
            return destillatListe;
        }
        public static List<Whisky> getWhiskyList() {return new ArrayList<>(Storage.getWhiskyList()); }
        public static List<Fad> getFadList() {
            return new ArrayList<Fad>(Storage.getFadList());
        }
        public static List<Tapning> getTapningList(){
            return Storage.getTapningList();
        }
        public static List<Lager> getLagerList() {
            return Storage.getLagerList();
        }

        //Hjælpe-Metoder
        public static Destilat createDestilat(double alkoholProcent, double mængde, String mark, String kornSort, int maltBatch, LocalDate slutDato, String rygeMateriale, String kommentar) {
            int id = Storage.nextDestilatId();
            Destilat objektDestilat = new Destilat(id, alkoholProcent, mængde, mark, kornSort, maltBatch, slutDato, rygeMateriale, kommentar);
            Storage.addDestilat(objektDestilat);
            destillatListe.add(objektDestilat);
            return objektDestilat;
        }

        public static Fad createFad( int antal, String type, String leverandør, String historik, double størrelseLiter) {
            int id = Storage.nextFadId();
            Fad fad = new Fad(id, antal,type,leverandør,historik, størrelseLiter);
            Storage.addFad(fad);
            fadListe.add(fad);
            return fad;
        }

        public static Lager createLager(String navn, String adresse) {
            Lager lager = new Lager(navn,adresse);
            Storage.addLager(lager);
            return lager;
        }

        public static Tapning createTapning(List<Fad> fade, double alkoholProcent, String historik, double mængdeVand, String vandOprindelse, double flaskeStørrelse, List<WhiskyType> typer, String navn) {
            // Opret tapningen
            Tapning tapning = new Tapning(alkoholProcent, historik, mængdeVand, vandOprindelse, typer, navn);

            for (Fad fad : fade){
                tapning.tilføjFad(fad);
            }

            tapning.setFuldMængde(tapning.udregnFuldMængde());

            // Gem i Storage
            Storage.addTapning(tapning);

            for (Fad fad : fade){
                double rest = Math.max(0.0, fad.getFyldt() - tapning.getFuldMængde());
                fad.setFyldt(rest);

                // Sæt slutdato på påfyldningen
                if (fad.getPåfyldninger() != null) {
                    fad.setPåfyldning(null);
                }

                fad.addTapning(tapning);
            }

            List<Whisky> whiskyList = tapning.opretWhisky(flaskeStørrelse);
            for (Whisky w : whiskyList) {
                Storage.addWhisky(w);
            }

            return tapning;
        }

        public static Påfyldning påfyldDestillatPåFad(int fadID, int destillatID, LocalDate dato){
            Fad fad = Storage.findFadById(fadID);
            Destilat destilat = Storage.findDestilatById(destillatID);

            if (!fad.erTomt()){
                throw new IllegalStateException("Fadet er allerede fyldt og kan ikke påfyldes igen.");
            }

            Påfyldning påfyldning = destilat.opretPåfyldning(dato);
            destilat.addPåfyldning(påfyldning);
            Storage.addPåfyldning(påfyldning);
            fad.setFyldt(destilat.getMængde());
            fad.addPåfyldning(påfyldning);

            return påfyldning;
        }

        public static boolean flytFadTilLager(Fad fad) {
            if (fad.getPlacering() != null) return false;

            for (Lager lager : getLagerList()) {
                if (lager.placerFad(fad)) {
                    return true;
                }
            }
            return false;
        }
}
