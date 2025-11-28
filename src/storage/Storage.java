package storage;

import application.model.Destilat;
import application.model.Fad;
import application.model.Lager;
import application.model.Tapning;

import java.util.ArrayList;
import java.util.List;

public class Storage {

    private static List<Destilat> destilatList = new ArrayList<>();
    private static List<Fad> fadList = new ArrayList<>();
    private static List<Lager> lagerList = new ArrayList<>();
    private static List<Tapning> tapningList = new ArrayList<>();

    //----Destilat----
    public static void addDestilat(Destilat destilat) {
        destilatList.add(destilat);
    }

    public static List<Destilat> getDestilatList() {
        return destilatList;
    }

    //----FAD----
    public static void addFad(Fad fad) {
        fadList.add(fad);
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
}
