package gui;

import application.controller.Controller;
import application.model.Destilat;
import application.model.Fad;
import application.model.Lager;

import java.time.LocalDate;

public class App {

    public static void main(String[] args) {
        initStorage();
        Gui.launch(Gui.class);
    }

    public static void initStorage() {
        Lager lager1 = Controller.createLager("Sall Whisky Lager", "Sallvej 123");

        Fad fad1 = Controller.createFad(0, "Bourbon", "Jim Beam", "Nyt fad oprettet");
        Fad fad2 = Controller.createFad(1, "Sherry", "Andalusien", "Brugt én gang");

        Destilat destilat1 = Controller.createDestilat(70,200, "Larsens", "byg", 1, LocalDate.of(2027,6,18),"","");
        Destilat destilat2= Controller.createDestilat(60,150, "Larsens", "byg", 2, LocalDate.of(2028,6,18),"tørv","");

    }
}
