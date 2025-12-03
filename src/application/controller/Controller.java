
package application.controller;

import application.model.*;
import storage.Storage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private static Controller instance;

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public static List<Destilat> getDestillatListe() {
        return new ArrayList<>(Storage.getDestilatList()) ;
    }

    public static Destilat createDestilat(int id, double alkoholProcent, double mængde, String mark, String kornSort,
                                          int maltBatch, LocalDate slutDato, String rygeMateriale, String kommentar) {
        Destilat objektDestilat = new Destilat(id, alkoholProcent, mængde, mark, kornSort, maltBatch, slutDato, rygeMateriale, kommentar);
        Storage.addDestilat(objektDestilat);
        return objektDestilat;
    }

    public static List<Fad> getFadList() {
        return new ArrayList<Fad>(Storage.getFadList());
    }

    public static Fad createFad(int id, int antal, String type, String leverandør, String historik, double størrelseLiter) {
        Fad fad = new Fad(id, antal,type,leverandør,historik, størrelseLiter);
        Storage.addFad(fad);
        return fad;
    }

    public static Lager createLager(String navn, String adresse) {
        Lager lager = new Lager(navn,adresse);
        Storage.addLager(lager);
        return lager;
    }



    public static Tapning createTapning(double alkoholProcent, String historik, double mængdeVand, String vandOprindelse, double mængde, List<WhiskyType> typer){
        Tapning tapning = new Tapning(alkoholProcent, historik, mængdeVand, vandOprindelse, mængde, typer);
        Storage.addTapning(tapning);
        return tapning;
    }

    public static Tapning createTapning(double alkoholProcent, String historik, double mængde, List<WhiskyType> typer){
        Tapning tapning = new Tapning(alkoholProcent, historik, mængde, typer);
        Storage.addTapning(tapning);
        return tapning;
    }

    public static List<Tapning> getTapningList(){
        return new ArrayList<>( Storage.getTapningList());
    }

    public static List<Lager> getLagerList() {
        return new ArrayList<>(Storage.getLagerList());
    }

    public static Påfyldning påfyldDestillatPåFad(int fadID, int destillatID, LocalDate dato){
        Fad fad = Storage.findFadById(fadID);
        Destilat destilat = Storage.findDestilatById(destillatID);

        if (!fad.erTomt()){
            throw new IllegalStateException("Fadet er allerede fyldt og kan ikke påfyldes igen.");
        }

        Påfyldning påfyldning = destilat.opretPåfyldning(dato);
        destilat.addPåfyldning(påfyldning);

        fad.setStørrelseLiter(destilat.getMængde());

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
                                if (f == null) {
                                    ledige++;
                                }
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
