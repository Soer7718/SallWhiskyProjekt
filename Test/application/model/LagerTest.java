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
    void testLedigePaller() {
        // Arrange
        Lager lagerTest = new Lager("Test", "Adresse");
        lagerTest.createGang(1, 1); // 1 gang, 1 reol, 1 hylde → 1 palle med 2 pladser

        // Act
        int actual = lagerTest.getLedigePaller();

        // Beregn expected: pallen har 2 pladser, begge tomme
        int expected = 0;
        for (Gang gang : lagerTest.getGangList()) {
            for (Reol reol : gang.getReolList()) {
                for (Hylde hylde : reol.getHyldeList()) {
                    for (Palle palle : hylde.getPalleList()) {
                        for (Fad fad : palle.getPallePlads()) {
                            if (fad == null) expected++;
                        }
                    }
                }
            }
        }

        System.out.println("Expected ledige pladser: " + expected);
        System.out.println("Actual ledige pladser: " + actual);

        // Assert
        assertEquals(expected, actual);
    }

}