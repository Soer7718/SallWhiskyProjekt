
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


        //    public static Påfyldning createPåfyldning(LocalDate startDato, Destilat destilat){
    //        Påfyldning p = new Påfyldning(startDato, destilat);
    //        Storage.addPåfyldning(p);
    //        return p;
    //    }

        public static Destilat createDestilat(double alkoholProcent, double mængde, String mark, String kornSort,
                                              int maltBatch, LocalDate slutDato, String rygeMateriale, String kommentar) {
            int id = Storage.nextDestilatId();
            Destilat objektDestilat = new Destilat(id, alkoholProcent, mængde, mark, kornSort, maltBatch, slutDato, rygeMateriale, kommentar);
            Storage.addDestilat(objektDestilat);
            destillatListe.add(objektDestilat);
            return objektDestilat;
        }

        public static List<Fad> getFadList() {
            return new ArrayList<Fad>(Storage.getFadList());
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



        public static Tapning createTapning(List<Fad> fade, double alkoholProcent, String historik, double mængdeVand,
                                            String vandOprindelse, double flaskeStørrelse, List<WhiskyType> typer) {
            // Opret tapningen
            Tapning tapning = new Tapning(alkoholProcent, historik, mængdeVand, vandOprindelse, typer);

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
                if (fad.getPåfyldning() != null) {
                    fad.getPåfyldning().setSlutDato(LocalDate.now());
                    fad.setPåfyldning(null);
                }

                fad.addTapning(tapning);
            }

            List<Whisky> whiskyer = tapning.opretWhisky(flaskeStørrelse);
            for (Whisky w : whiskyer) {
                Storage.addWhisky(w);
            }

            return tapning;
        }

//        public static Tapning createTapning(Fad fad, double alkoholProcent, String historik, double mængdeVand,
//                                            String vandOprindelse, double mængde, List<WhiskyType> typer) {
//            // Opret tapningen
//            Tapning tapning = new Tapning(alkoholProcent, historik, mængdeVand, vandOprindelse, mængde, typer);
//
//            tapning.setFuldMængde(tapning.udregnFuldMængde());
//            // Gem i Storage
//            Storage.addTapning(tapning);
//
//            // Tilføj tapningen til fadet
//            if (fad != null) {
//                fad.addTapning(tapning);
//            }
//
//            return tapning;
//        }


//        public static Tapning createTapning(double alkoholProcent, String historik, double mængde, List<WhiskyType> typer){
//            Tapning tapning = new Tapning(alkoholProcent, historik, mængde, typer);
//            Storage.addTapning(tapning);
//            return tapning;
//        }

        public static List<Tapning> getTapningList(){
            return Storage.getTapningList();
        }

        public static List<Lager> getLagerList() {
            return Storage.getLagerList();
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
            fad.setPåfyldning(påfyldning);

            return påfyldning;
        }

        public static Palle findLedigPalle(Fad fad) {
            int krævedePladser = fad.getStørrelseLiter() < 51 ? 1 : 2;

            for (Lager lager : getInstance().getLagerList()) {
                for (Gang gang : lager.getGangList()) {
                    for (Reol reol : gang.getReolList()) {
                        for (Hylde hylde : reol.getHyldeList()) {
                            for (Palle palle : hylde.getPalleList()) {

                                int ledige = 0;
                                for (Fad f : palle.getPallePlads()) {
                                    if (f == null) ledige++;
                                }

                            if (ledige >= krævedePladser) {
                                return palle; // Den første der passer
                            }
                        }
                    }
                }
            }
        }
        return null; // Ingen paller ledige
    }
}
