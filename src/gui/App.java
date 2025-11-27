package gui;


import application.controller.Controller;
import application.model.Fad;
import application.model.Lager;

import java.time.LocalDate;

public class App {
    private static Lager lager;

    public static void main(String[] args) {
        initStorage();
        Gui.launch(Gui.class);
    }


    public static void initStorage() {
        lager = new Lager("Sall Whisky Lager", "Sallvej 123");

        // Fad-eksempler
        lager.registrerFad(0, "Bourbon", "Jim Beam", "Nyt fad oprettet");
        lager.registrerFad(1, "Sherry", "Andalusien", "Brugt én gang");

        // Destillat-eksempler
        Controller.createDestilat(63.5, 500.0, "Mark A", "Byg", 101,
                LocalDate.now().plusYears(3), "Tørv", "Første batch");

        Controller.createDestilat(60.0, 450.0, "Mark B", "Hvede", 102,
                LocalDate.now().plusYears(4), "Ingen", "Eksperimentel destillat");
    }


    public static Lager getLager() {
        return lager;
    }
}
