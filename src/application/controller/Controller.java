package application.controller;

import application.model.Destilat;
import application.model.Fad;
import application.model.Lager;
import storage.Storage;
import application.model.WhiskyType;

import java.time.LocalDate;
import java.util.ArrayList;

public class Controller {

    //----Destilat----
    public static Destilat createDestilat(double alkoholProcent, double mængde, String mark, String kornSort, int maltBatch, LocalDate slutDato, String rygeMateriale, String kommentar) {
        Destilat objektDestilat = new Destilat(alkoholProcent,mængde,mark,kornSort,maltBatch,slutDato,rygeMateriale,kommentar);
        Storage.addDestilat(objektDestilat);
        return objektDestilat;
    }

    public static ArrayList<Destilat> getDestilatList() {
        return new ArrayList<>(Storage.getDestilatList());
    }

    //----Fad----
    public static Fad createFad  (int antalGangeBrugt, String type, String leverandør, String historik) {
        Fad fad = new Fad(antalGangeBrugt, type, leverandør, historik);
        Storage.addFad(fad);
        return fad;
    }
    public static ArrayList<Fad> getFadList() {
        return new ArrayList<>(Storage.getFadList());
    }


    //----Lager----
    public static Lager createLager(String navn, String adresse) {
        Lager lager = new Lager(navn, adresse);
        Storage.addLager(lager);
        return lager;
    }
    public static ArrayList<Lager> getLagerList() {
        return new ArrayList<>(Storage.getLagerList());
    }


}
