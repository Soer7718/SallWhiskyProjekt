package application.model;

import application.controller.Controller;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

        // Forvent at der er 5 ledige paller tilbage 1 plads brugt på en palle ud af 6 pladser
        assertEquals(5, ledigeEfterPlacering, "Der skal være 5 ledige paller tilbage efter at have placeret 1 fad");
    }

    @Test
    void testAfFad() {
        Fad f = new Fad(42, 1, "TestType", "TestLeverandør", "testBrugt", 225.0);

        assertEquals(42, f.getId());
        assertEquals(1, f.getAntalGangeBrugt());
        assertEquals("TestType", f.getType());
        assertEquals("TestLeverandør", f.getLeverandør());
        assertEquals("testBrugt", f.getHistorik());
        assertEquals(225.0, f.getStørrelseLiter());

        // tester nyoprettet fad er tomt
        assertTrue(f.erTomt(), "Fad er ikke tomt");

        // putter noget i vores fad så ikke tomt mere
        f.setFyldt(50.0);
        assertFalse(f.erTomt(), "fad skal ikke være tomt");

        // historik kan opdateres
        f.setHistorik("Historik");
        assertEquals("Historik", f.getHistorik());

        // størrelse kan opdateres
        f.setStørrelseLiter(200.0);
        assertEquals(200.0, f.getStørrelseLiter());

        // tjekker påfyldning
        List<Påfyldning> påfyldninger = new ArrayList<>();
        f.setPåfyldning(påfyldninger);
        assertTrue(f.getPåfyldninger().isEmpty());
    }

    @Test
    void TestAfUC5FyldDestilatPåFad() {
        // Arrange
        Fad fad = Controller.createFad(200, "Sherry", "Bourbon", "Jim Beam",  200.0);
        Destilat destillat = Controller.createDestilat(300, 70.0, "Larsens", "Byg", 1,
                LocalDate.of(2030, 6, 1), "", "");

        // Act
        Påfyldning p = Controller.påfyldDestillatPåFad(fad.getId(), destillat.getId(), LocalDate.now());

        // Assert
        assertNotNull(p, "opretter en påfyldning");
        assertEquals(1, destillat.getPåfyldningList().size(), "Destillat opretter en påfyldning");
    }

    @Test
    void TestGetLedigePladser() {
        //Arrange
        Lager lager = Controller.createLager("TestLager", "TestAdresse");
        lager.createGang(1,1);


        // initialt: begge slots null => 2 ledige
        assertEquals(6, lager.getLedigePladser());

        // placer et fad i slot[0] (vi bruger en simpel Fad-objekt)
        Fad f0 = new Fad(1, 0, "Type", "Lev", "Hist", 50.0);
        lager.placerFad(f0);

        assertEquals(5, lager.getLedigePladser(), "5 ledige pladser en optaget");

        // placer også i slot[1]
        Fad f1 = new Fad(2, 0, "Type", "Lev", "Hist", 50.0);
        lager.placerFad(f1);

        assertEquals(4, lager.getLedigePladser(), "4 ledige pladser");
    }

    @Test
    void TestOpretterPåfyldning() {
        // arrange
        Fad fad = Controller.createFad(100, "Bourbon", "Bourbon", "Jim Beam",  200.0);
        Destilat dest = Controller.createDestilat(80, 70.0, "Larsens", "Byg", 1, LocalDate.of(2030, 1, 1), "", "");

        // act:
        Påfyldning p = dest.opretPåfyldning(LocalDate.now());
        p.addFad(fad);

        // assert:
        assertNotNull(p, "Påfyldning må ikke være null");

        assertEquals(70.0, fad.getFyldt(), "Fadets fyldte mængde skal matche destillatets mængde");
    }

}