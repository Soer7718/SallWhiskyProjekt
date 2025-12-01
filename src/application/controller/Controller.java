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

    public static Destilat createDestilat(double alkoholProcent, double mængde, String mark, String kornSort, int maltBatch, LocalDate slutDato, String rygeMateriale, String kommentar) {
        return Storage.addDestilat(alkoholProcent, mængde, mark, kornSort, maltBatch, slutDato, rygeMateriale,kommentar);
    }

    public static List<Fad> getFadList() {
        return new ArrayList<Fad>(Storage.getFadList());
    }

    public static Fad createFad(int antal, String type, String leverandør, String historik) {
        return Storage.addFad(antal, type, leverandør, historik);
    }

    public static Lager createLager(String navn, String adresse) {
        Lager lager = new Lager(navn,adresse);
        Storage.addLager(lager);
        return lager;
    }


    public static Tapning createTapning(double alkoholProcent, String historik, double mængdeVand, String vandOprindelse, double mængde){
        Tapning tapning = new Tapning(alkoholProcent, historik, mængdeVand, vandOprindelse, mængde);
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

        Påfyldning påfyldning = fad.opretPåfyldning(dato, destilat);
        destilat.addPåfyldning(påfyldning);

        fad.setLiter(destilat.getMængde());

        return påfyldning;
    }
}
