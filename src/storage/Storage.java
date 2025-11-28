package storage;

import application.model.Destilat;
import application.model.Fad;
import application.model.Lager;

import java.util.ArrayList;
import java.util.List;

public class Storage {

    private static List<Destilat> destilatList = new ArrayList<>();
    private static List<Fad> fadList = new ArrayList<>();
    private static List<Lager> lagerList = new ArrayList<>();

    //----Destilat----
    public static void addDestilat(Destilat objektDestilat) {
        destilatList.add(objektDestilat);
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
}
