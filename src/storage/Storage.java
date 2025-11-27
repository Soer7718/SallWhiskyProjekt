package storage;

import application.model.Destilat;

import java.util.ArrayList;
import java.util.List;

public class Storage {

    private static List<Destilat> destilatList = new ArrayList<>();

    public static void addDestilat(Destilat objektDestilat) {
        destilatList.add(objektDestilat);
    }
}
