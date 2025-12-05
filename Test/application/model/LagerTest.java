package application.model;

import application.controller.Controller;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

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

    @Test
    void testAfFad() {
        Fad f = new Fad(42, 1, "Sherry", "Andalusien", "Brugt én gang", 225.0);

        assertEquals(42, f.getId());
        assertEquals(1, f.getAntalGangeBrugt());
        assertEquals("Sherry", f.getType());
        assertEquals("Andalusien", f.getLeverandør());
        assertEquals("Brugt én gang", f.getHistorik());
        assertEquals(225.0, f.getStørrelseLiter());

        // tester nyoprettet fad er tomt
        assertTrue(f.erTomt(), "Fad bør være tomt når fyldt==0.0");

        // putter noget i vores fad så ikke tomt mere
        f.setFyldt(50.0);
        assertFalse(f.erTomt(), "Fad bør ikke være tomt når fyldt>0.0");

        // historik kan opdateres
        f.setHistorik("Efter reparation");
        assertEquals("Efter reparation", f.getHistorik());

        // størrelse kan opdateres
        f.setStørrelseLiter(200.0);
        assertEquals(200.0, f.getStørrelseLiter());

        // påfyldning felt kan sættes/læses (kun strukturel test)
        Påfyldning p = null; // vi tester ikke selve Påfyldning her
        f.setPåfyldning(p);
        assertNull(f.getPåfyldning());
    }

    @Test
    void TestAfUC5FyldDestilatPåFad() {
        // Arrange: Opret fad og destillat
        Fad fad = Controller.createFad(200, 1, "Bourbon", "Jim Beam", "Nyt", 200.0);
        Destilat dest = Controller.createDestilat(300, 70.0, 50.0, "Larsens", "Byg", 2,
                LocalDate.of(2030, 6, 1), "", "");

        assertTrue(fad.erTomt(), "Forudsætning: fad er tomt");
        assertEquals(0, dest.getPåfyldningList().size(), "Ingen påfyldninger før UC5");

        // Act: Udfør UC5 via Controller
        Påfyldning p = Controller.påfyldDestillatPåFad(fad.getId(), dest.getId(), LocalDate.now());

        // Assert: Effekt på objekterne
        assertNotNull(p, "UC5 skal oprette en påfyldning");
        assertEquals(1, dest.getPåfyldningList().size(), "Destillat har nu præcis én påfyldning");
        assertEquals(50.0, fad.getFyldt(), "UC5 sætter fyldt til destillatets mængde");
    }

    @Test
    void TestGetLedigePladser() {
        // Kæden er lidt tung; vi kan mocke, men her bruger vi simple konstruktører
        Lager lager = new Lager("Testlager", "Adresse");
        Gang gang = new Gang(lager, 1);
        Reol reol = new Reol(gang, 1, 1);
        Hylde hylde = reol.getHyldeList().get(0);

        Palle palle = new Palle(hylde);

        // initialt: begge slots null => 2 ledige
        assertEquals(2, palle.getLedigePladser());

        // placer et fad i slot[0] (vi bruger en simpel Fad-objekt)
        Fad f0 = new Fad(1, 0, "Type", "Lev", "Hist", 50.0);
        Fad[] pladser = palle.getPallePlads(); // defensive copy; så sæt via setter
        pladser[0] = f0;
        palle.setPallePlads(pladser);

        assertEquals(1, palle.getLedigePladser(), "Én optaget, én ledig");

        // placer også i slot[1]
        Fad f1 = new Fad(2, 0, "Type", "Lev", "Hist", 50.0);
        pladser = palle.getPallePlads();
        pladser[1] = f1;
        palle.setPallePlads(pladser);

        assertEquals(0, palle.getLedigePladser(), "Ingen ledige når begge slots er optaget");
    }

    @Test
    void TestOpretterPåfyldning() {
        // arrange
        //opretter fad og destilat
        Fad fad = Controller.createFad(100, 1, "Bourbon", "Jim Beam", "Nyt fad", 200.0);
        Destilat dest = Controller.createDestilat(80, 70.0, 50.0, "Larsens", "Byg", 2,
                LocalDate.of(2030, 1, 1), "", "");

        // tjekker om fadet er tomt
        assertTrue(fad.erTomt(), "Fad er ikke tomt");
        assertEquals(0, dest.getPåfyldningList().size(), "Destillat må ikke have påfyldninger endnu");

        // act:
        // fylder fadet med destillat
        Påfyldning p = Controller.påfyldDestillatPåFad(fad.getId(), dest.getId(), LocalDate.now());

        // assert:
        // Påfyldning blev oprettet
        assertNotNull(p, "Påfyldning må ikke være null");

        // assert:
        // fadets fyldte mængde er korrekt
        assertEquals(50.0, fad.getFyldt(), "Fadets fyldte mængde skal matche destillatets mængde");

        // assert:
        // destillat har fået påfyldningen i sin liste
        assertEquals(1, dest.getPåfyldningList().size(), "Destillat bør have præcis én påfyldning");

    }

}