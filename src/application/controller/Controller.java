
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

    public static Destilat createDestilat(double alkoholProcent, double mængde, String mark, String kornSort,
                                          int maltBatch, LocalDate slutDato, String rygeMateriale, String kommentar) {
        Destilat objektDestilat = new Destilat(alkoholProcent, mængde, mark, kornSort, maltBatch, slutDato, rygeMateriale, kommentar);
        Storage.addDestilat(objektDestilat);
        return objektDestilat;
    }

    public static List<Fad> getFadList() {
        return new ArrayList<Fad>(Storage.getFadList());
    }

    public static Fad createFad(int antal, String type, String leverandør, String historik) {
        Fad fad = new Fad(antal,type,leverandør,historik);
        Storage.addFad(fad);
        return fad;
    }

    public static Lager createLager(String navn, String adresse) {
        Lager lager = new Lager(navn,adresse);
        Storage.addLager(lager);
        return lager;
    }



    public static Tapning createTapning(Fad fad, double alkoholProcent, String historik, double mængdeVand, String vandOprindelse, double mængde, List<WhiskyType> typer){
        Tapning tapning = fad.opretTapning(alkoholProcent, historik, mængdeVand, vandOprindelse, mængde, typer);
        Storage.addTapning(tapning);
        return tapning;
    }

    public static Tapning createTapning(Fad fad, double alkoholProcent, String historik, double mængde, List<WhiskyType> typer){
        Tapning tapning = fad.opretTapning(alkoholProcent, historik, mængde, typer);
        Storage.addTapning(tapning);
        return tapning;
    }

    public static List<Tapning> getTapningList(){
        return new ArrayList<>( Storage.getTapningList());
    }

    public static List<Lager> getLagerList() {
        return new ArrayList<>(Storage.getLagerList());
    }
}
