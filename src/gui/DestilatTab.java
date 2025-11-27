package gui;

import application.controller.Controller;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.time.LocalDate;


public class DestilatTab {

    Label kornSortLabel = new Label("Korn Sort:");
    Label slutDatoLabel = new Label("Slut Dato:");
    Label alkoholProcentLabel = new Label("Alkohol Procent:");
    Label mængdeLabel = new Label("Mængde:");
    Label markLabel = new Label("Mark:");
    Label maltBatchLabel = new Label("Malt Batch:");
    Label rygeMaterialeLabel = new Label("Ryge materiale:");
    Label kommentarLabel = new Label("Kommentar:");
    TextField kornSortInput = new TextField();
    DatePicker slutDatoInput = new DatePicker();
    TextField alkoholProcentInput = new TextField();
    TextField mængdeInput = new TextField();
    TextField markInput = new TextField();
    TextField maltBatchInput = new TextField();
    TextField rygeMaterialeInput = new TextField();
    TextField kommentarInput = new TextField();
    Button opretDestilatKnap = new Button("Opret Destilat");


    public VBox getContent() {
        VBox layout = new VBox();
        layout.setSpacing(10); //afstand imellem kasserne
        VBox.setVgrow(layout, Priority.ALWAYS);

        HBox row = new HBox();
        row.setSpacing(10); // afstand mellem kasserne
        HBox.setHgrow(row, Priority.ALWAYS); // row fylder vandret
        VBox.setVgrow(row, Priority.ALWAYS); // row fylder lodret

        // --- Kasse 1:
        VBox box1 = new VBox();
        box1.setSpacing(5);
        box1.setStyle("-fx-border-color: black; -fx-padding: 5;");
        Label label1 = new Label("Opret Destilat");
        box1.getChildren().add(label1);
        HBox.setHgrow(box1, Priority.ALWAYS);
        box1.getChildren().addAll(
                kornSortLabel,
                kornSortInput,
                alkoholProcentLabel,
                alkoholProcentInput,
                mængdeLabel,
                mængdeInput,
                markLabel,
                markInput,
                maltBatchLabel,
                maltBatchInput,
                rygeMaterialeLabel,
                rygeMaterialeInput,
                kommentarLabel,
                kommentarInput,
                slutDatoLabel,
                slutDatoInput,
                opretDestilatKnap
        );
        LocalDate today = LocalDate.now();
        slutDatoInput.setValue(today.plusYears(3));

        slutDatoInput.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                LocalDate minDate = today.plusYears(3);
                if (date.isBefore(minDate)) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ddd;");
                }
            }
        });

        VBox.setVgrow(box1, Priority.ALWAYS);
        opretDestilatKnap.setOnAction(e -> opretDestilat());
        // --- Kasse 2:
        VBox box2 = new VBox();
        box2.setSpacing(5);
        box2.setStyle("-fx-border-color: black; -fx-padding: 5;");
        Label label2 = new Label("Destilat liste");
        box2.getChildren().add(label2);
        HBox.setHgrow(box2, Priority.ALWAYS);
        VBox.setVgrow(box2, Priority.ALWAYS);

        // --- Kasse 3:
        VBox box3 = new VBox();
        box3.setSpacing(5);
        box3.setStyle("-fx-border-color: black; -fx-padding: 5;");
        Label label3 = new Label("Destilat info");
        box3.getChildren().add(label3);
        HBox.setHgrow(box3, Priority.ALWAYS);
        VBox.setVgrow(box3, Priority.ALWAYS);


        row.getChildren().addAll(box1, box2, box3);

        layout.getChildren().add(row);


        return layout;
    }

    private void opretDestilat() {
        String kornSort = kornSortInput.getText().toLowerCase();
        LocalDate slutDato = slutDatoInput.getValue();
        double alkoholProcent;
        double mængde;
        String mark = markInput.getText().toLowerCase();
        int maltBatch;
        String rygeMateriale = rygeMaterialeInput.getText().toLowerCase();
        String kommentar = kommentarInput.getText().toLowerCase();

        StringBuilder fejl = new StringBuilder();

        // --- Kornsort ---
        if (kornSort == null || kornSort.isBlank()) {
            fejl.append("- Kornsort må ikke være tom.\n");
        }

        // --- Slutdato ---
        LocalDate today = LocalDate.now();
        if  (slutDato.isBefore(today.plusYears(3))) {
            fejl.append("- Slutdato må ikke være før i dag.\n");
        }

        // --- Alkoholprocent ---
        alkoholProcent = 0;
        try {
            alkoholProcent = Double.parseDouble(alkoholProcentInput.getText());
            if (alkoholProcent < 0 || alkoholProcent > 100) {
                fejl.append("- Alkoholprocent skal være mellem 0 og 100.\n");
            }
        } catch (NumberFormatException e) {
            fejl.append("- Alkoholprocent skal være et tal.\n");
        }

        // --- Mængde ---
        mængde = 0;
        try {
            mængde = Double.parseDouble(mængdeInput.getText());
            if (mængde <= 0) {
                fejl.append("- Mængde skal være større end 0.\n");
            }
        } catch (NumberFormatException e) {
            fejl.append("- Mængde skal være et tal.\n");
        }

        // --- Mark ---

        if (mark == null || mark.isBlank()) {
            fejl.append("- Mark skal udfyldes.\n");
        }

        // --- Malt batch ---
        maltBatch = 0;
        try {
            maltBatch = Integer.parseInt(maltBatchInput.getText());
            if (maltBatch < 0) {
                fejl.append("- Malt batch skal være et positivt tal.\n");
            }
        } catch (NumberFormatException e) {
            fejl.append("- Malt batch skal være et helt tal.\n");
        }

        // viser fejl
        if (!fejl.toString().isEmpty()) {
            System.out.println("FEJL:\n" + fejl);
            return;
        }

        System.out.println("=== Opretter Destillat ===");
        System.out.println("Kornsort: " + kornSort);
        System.out.println("Slutdato: " + slutDato);
        System.out.println("Alkohol%: " + alkoholProcent);
        System.out.println("Mængde: " + mængde);
        System.out.println("Mark: " + mark);
        System.out.println("Malt batch: " + maltBatch);
        System.out.println("Ryge materiale: " + rygeMateriale);
        System.out.println("Kommentar: " + kommentar);


        Controller.createDestilat(alkoholProcent, mængde, mark, kornSort, maltBatch, slutDato, rygeMateriale, kommentar);

        kornSortInput.clear();
        alkoholProcentInput.clear();
        mængdeInput.clear();
        markInput.clear();
        maltBatchInput.clear();
        rygeMaterialeInput.clear();
        kommentarInput.clear();
    }
}
