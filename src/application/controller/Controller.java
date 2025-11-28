
package application.controller;

import application.model.Destilat;
import application.model.Lager;
import application.model.Tapning;
import storage.Storage;
import gui.App;
import java.time.LocalDate;
import java.util.List;

public class Controller {

    private static Controller instance;
    private Lager lager;

    private Controller() {
        lager = new Lager("Sall Whisky Lager", "Sallvej 123");
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public static List<Destilat> getDestillatListe() {
        return App.getLager().getDestillatListe();
    }

    public static Destilat createDestilat(double alkoholProcent, double mængde, String mark, String kornSort,
                                          int maltBatch, LocalDate slutDato, String rygeMateriale, String kommentar) {
        Destilat objektDestilat = new Destilat(alkoholProcent, mængde, mark, kornSort, maltBatch, slutDato, rygeMateriale, kommentar);
        App.getLager().addDestillat(objektDestilat);
        return objektDestilat;
    }

    public Tapning registrerTapning( double alkoholProcent, String historik, double mængdeVand, String vandOprindelse, double mængde){
        Tapning tapning = new Tapning(alkoholProcent, historik, mængdeVand, vandOprindelse, mængde);
        lager.addTapning(tapning);
        return tapning;
    }
    public List<Tapning> getTapningList(){
        return lager.getTapningList();
    }
}
