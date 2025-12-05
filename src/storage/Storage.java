package storage;

import application.model.*;

import java.util.ArrayList;
import java.util.List;

public class Storage {

    private static List<Destilat> destilatList = new ArrayList<>();
    private static List<Fad> fadList = new ArrayList<>();
    private static List<Lager> lagerList = new ArrayList<>();
    private static List<Tapning> tapningList = new ArrayList<>();
    private static List<Påfyldning> påfyldningList = new ArrayList<>();
    private static List<Whisky> whiskyList = new ArrayList<>();
    private static int destilatSeq = 1;
    private static int fadSeq = 1;


    //----Destilat----
    // public static void addDestilat(Destilat destilat) {
       // destilatList.add(destilat);
    //}


    public static int nextDestilatId() { return destilatSeq++; }
    public static int nextFadId()      { return fadSeq++; }

    public static void addDestilat(Destilat d){
        destilatList.add(d);
    }
    public static void addPåfyldning(Påfyldning p){
        påfyldningList.add(p);
    }

    public static Destilat findDestilatById(int id){
        return destilatList.stream().filter(d -> d.getId() == id).findFirst().orElseThrow(()-> new IllegalArgumentException("Destillat ikke fundet: " + id));
    }

    public static List<Destilat> getDestilatList() {
        return destilatList;
    }

    //----FAD----

    public static void addFad(Fad f) {
        fadList.add(f);
    }

    public static Fad findFadById(int id) {
        return fadList.stream().filter(f -> f.getId() == id).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Fad ikke fundet: " + id));
    }

    public static List<Fad> getFadList() {
        return fadList;
    }

    //----Lager----
    public static void addLager(Lager lager) {
        lagerList.add(lager);
    }

    public static List<Lager> getLagerList() {
        return lagerList;
    }

    public static void addTapning(Tapning tapning) {
        tapningList.add(tapning);
    }

    public static List<Tapning> getTapningList() {
        return tapningList;
    }

    public static List<Whisky> getWhiskyList() {
        return whiskyList;
    }

    public static void addWhisky(Whisky whisky) {
        whiskyList.add(whisky);
    }

}
