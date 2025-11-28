package gui;

import application.controller.Controller;
import application.model.Lager;

import java.time.LocalDate;

public class App {

    public static void main(String[] args) {
        initStorage();
        Gui.launch(Gui.class);
    }

    public static void initStorage() {
         Lager lager = Controller.createLager("Sall Whisky Lager", "Sallvej 123");

        Controller.createFad(0, "Bourbon", "Jim Beam", "Nyt fad oprettet");
        Controller.createFad(1, "Sherry", "Andalusien", "Brugt én gang");

        Controller.createDestilat(80,200,"Larsens","Byg",1, LocalDate.of(2030,10,10),"","");
        Controller.createDestilat(70,150,"Larsens","Byg",2, LocalDate.of(2029,7,20),"","");
    }

}
