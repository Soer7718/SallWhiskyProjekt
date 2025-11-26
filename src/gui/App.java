package gui;

import application.model.Fad;
import application.model.Lager;

public class App {
    private static Lager lager;

    public static void main(String[] args) {
        initStorage();
        Gui.launch(Gui.class);
    }

    public static void initStorage() {
        lager = new Lager("Sall Whisky Lager", "Sallvej 123");

        // UC1: Registrer et par fade
        lager.registrerFad(0, "Bourbon", "Jim Beam", "Nyt fad oprettet");
        lager.registrerFad(1, "Sherry", "Andalusien", "Brugt én gang");
    }

    public static Lager getLager() {
        return lager;
    }
}
