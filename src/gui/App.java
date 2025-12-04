package gui;

import application.controller.Controller;
import application.model.Destilat;
import application.model.Fad;
import application.model.Lager;
import storage.Storage;

import java.time.LocalDate;

public class App {

    public static void main(String[] args) {
        initStorage();
        Gui.launch(Gui.class);
    }

    public static void initStorage() {
        Lager lager = Controller.createLager("Sall Whisky Lager", "Sallvej 123");
        lager.createGang(2, 3);


        Fad f1 = Controller.createFad(1, "Bourbon", "Jim Beam", "Nyt fad oprettet", 200);
        Fad f2 = Controller.createFad(1, "Sherry", "Andalusien", "Brugt én gang", 200);


        Destilat d1 = Controller.createDestilat(75, 200, "Larsens", "Byg", 1, LocalDate.of(2024, 10, 10), "", "");
        Destilat d2 = Controller.createDestilat(72, 150, "Larsens", "Byg", 2, LocalDate.of(2024, 7, 20), "", "");

        Controller.påfyldDestillatPåFad(f1.getId(), d1.getId(), LocalDate.now().minusYears(5));


        Controller.createTapning(f1, 46.0, "Første tapning", 0.0, null, 50.0,
                java.util.List.of(application.model.WhiskyType.singleMalt));
    }
}