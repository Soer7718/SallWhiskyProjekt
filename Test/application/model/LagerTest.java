package application.model;

import application.controller.Controller;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LagerTest {

    @Test
    void udregnAntalPladser() {
        Lager lager = Controller.createLager("test","Test");
        //arange
        lager.createGang(10,10);
        lager.createGang(10,10);
        //act
        int actual = lager.udregnAntalPladser();

        //assert
        int expected = 600;

        assertEquals(expected,actual);
    }

    @Test
    void testLedigePallerEfterEnPlacering() {
        // Opret lager
        Lager lagerTest = new Lager("Test Lager", "Test Adresse");

        // Opret 1 gang, 1 reol med 1 hylde → hver hylde har 3 paller med 2 pladser hver
        lagerTest.createGang(1, 1);
        System.out.println(lagerTest.getLedigePladser());
        // Placer et fad på første ledige palle
        Fad fad = new Fad(1, 1, "TestFad", "Leverandør", "Historik", 1);
        boolean succes = lagerTest.placerFad(fad);
        if (!succes) throw new RuntimeException("Kunne ikke placere fad!");

        // Hent antal ledige paller efter placering
        int ledigeEfterPlacering = lagerTest.getLedigePladser();
        System.out.println(ledigeEfterPlacering);

        // Forvent at der er 5 ledige paller tilbage (1 plads brugt på en palle ud af 6 pladser)
        assertEquals(5, ledigeEfterPlacering, "Der skal være 5 ledige paller tilbage efter at have placeret 1 fad");
    }

}