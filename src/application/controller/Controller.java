
package application.controller;

import application.model.Destilat;
import application.model.Lager;
import storage.Storage;
import gui.App;
import java.time.LocalDate;
import java.util.List;

public class Controller {

    public static List<Destilat> getDestillatListe() {
        return App.getLager().getDestillatListe();
    }

    public static Destilat createDestilat(double alkoholProcent, double mængde, String mark, String kornSort,
                                          int maltBatch, LocalDate slutDato, String rygeMateriale, String kommentar) {
        Destilat objektDestilat = new Destilat(alkoholProcent, mængde, mark, kornSort, maltBatch, slutDato, rygeMateriale, kommentar);
        App.getLager().addDestillat(objektDestilat);
        return objektDestilat;
    }
}
