package gui;

import application.controller.Controller;
import application.model.Destilat;
import application.model.Fad;
import application.model.Lager;
import storage.Storage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        Fad f3 = Controller.createFad(1, "Bourbon", "Bent", "Brugt to gang", 200);
        Fad f4 = Controller.createFad(1, "Sherry", "Lars", "Brugt tre gang", 50);
        Fad f5 = Controller.createFad(1, "Bourbon", "Champagne", "Nyt fad", 50);


        Destilat d1 = Controller.createDestilat(75, 200, "Larsens", "Byg", 1, LocalDate.of(2014, 10, 10), "", "");
        Destilat d2 = Controller.createDestilat(72, 150, "Larsens", "Byg", 2, LocalDate.of(2024, 7, 20), "", "");
        Destilat d3 = Controller.createDestilat(62, 150, "Larsens", "Byg", 3, LocalDate.of(208, 7, 20), "", "");

        Controller.påfyldDestillatPåFad(f1.getId(), d1.getId(), LocalDate.now().minusYears(5));
        Controller.påfyldDestillatPåFad(f3.getId(), d3.getId(), LocalDate.now().minusYears(5));

        List<Fad> fadList = new ArrayList<>();
        fadList.add(f1);

        Controller.createTapning(fadList, 46.0, "Første tapning", 0.0, null,
                0.7, java.util.List.of(application.model.WhiskyType.singleMalt));
    }
}