package application.controller;

import application.model.Destilat;
import storage.Storage;
import application.model.WhiskyType;

import java.time.LocalDate;

public class Controller {
    public static Destilat createDestilat(double alkoholProcent, double mængde, String mark, String kornSort, int maltBatch, LocalDate slutDato, String rygeMateriale, String kommentar) {
        Destilat objektDestilat = new Destilat(alkoholProcent,mængde,mark,kornSort,maltBatch,slutDato,rygeMateriale,kommentar);
        Storage.addDestilat(objektDestilat);
        return objektDestilat;
    }

}
