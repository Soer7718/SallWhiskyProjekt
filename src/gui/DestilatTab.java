package gui;

import application.controller.Controller;
import application.model.Destilat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class DestilatTab {

    // --- Labels og inputfelter ---
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

    // --- ListView og ObservableList som felter ---
    private ObservableList<Destilat> destilater = FXCollections.observableArrayList();
    private ListView<Destilat> destilatLW = new ListView<>(destilater);

    // --- VBox for detaljer (kasse 3) ---
    private VBox box3 = new VBox();

    public VBox getContent() {
        VBox layout = new VBox();
        layout.setSpacing(10);
        VBox.setVgrow(layout, Priority.ALWAYS);

        HBox row = new HBox();
        row.setSpacing(10);
        HBox.setHgrow(row, Priority.ALWAYS);
        VBox.setVgrow(row, Priority.ALWAYS);

        // --- Kasse 1: Opret destilat ---
        VBox box1 = new VBox();
        box1.setSpacing(5);
        box1.setStyle("-fx-border-color: black; -fx-padding: 5;");
        Label label1 = new Label("Opret Destilat");
        box1.getChildren().add(label1);
        HBox.setHgrow(box1, Priority.ALWAYS);

        box1.getChildren().addAll(
                kornSortLabel, kornSortInput,
                alkoholProcentLabel, alkoholProcentInput,
                mængdeLabel, mængdeInput,
                markLabel, markInput,
                maltBatchLabel, maltBatchInput,
                rygeMaterialeLabel, rygeMaterialeInput,
                kommentarLabel, kommentarInput,
                slutDatoLabel, slutDatoInput,
                opretDestilatKnap
        );

        // --- dato 3 år frem---
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

        // --- Kasse 2: ListView ---
        VBox box2 = new VBox();
        box2.setSpacing(5);
        box2.setStyle("-fx-border-color: black; -fx-padding: 5;");
        Label label2 = new Label("Destilat liste");
        destilatLW.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Destilat item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) setText(null);
                else setText(item.getKornSort() + " (" + item.getSlutDato() + ")");
            }
        });
        box2.getChildren().addAll(label2, destilatLW);
        HBox.setHgrow(box2, Priority.ALWAYS);
        VBox.setVgrow(box2, Priority.ALWAYS);

        // --- Kasse 3: Detaljer ---
        box3.setSpacing(5);
        box3.setStyle("-fx-border-color: black; -fx-padding: 5;");
        Label label3 = new Label("Destilat info");
        box3.getChildren().add(label3);
        HBox.setHgrow(box3, Priority.ALWAYS);
        VBox.setVgrow(box3, Priority.ALWAYS);

        // --- Listener til at vise detaljer i kasse 3 ---
        destilatLW.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                box3.getChildren().clear();
                box3.getChildren().add(new Label("Kornsort: " + newVal.getKornSort()));
                box3.getChildren().add(new Label("Slutdato: " + newVal.getSlutDato()));
                box3.getChildren().add(new Label("Alkohol%: " + newVal.getAlkoholProcent()));
                box3.getChildren().add(new Label("Mængde: " + newVal.getMængde()));
                box3.getChildren().add(new Label("Mark: " + newVal.getMark()));
                box3.getChildren().add(new Label("Malt batch: " + newVal.getMaltBatch()));
                box3.getChildren().add(new Label("Ryge materiale: " + newVal.getRygeMateriale()));
                box3.getChildren().add(new Label("Kommentar: " + newVal.getKommentar()));
            }
        });


        // --- Tilføj alle bokse til row ---
        row.getChildren().addAll(box1, box2, box3);
        layout.getChildren().add(row);

        return layout;
    }

    // --- Opret destilat metode ---
    private void opretDestilat() {
        String kornSort = kornSortInput.getText().toLowerCase();
        LocalDate slutDato = slutDatoInput.getValue();
        double alkoholProcent = 0;
        double mængde = 0;
        String mark = markInput.getText().toLowerCase();
        int maltBatch = 0;
        String rygeMateriale = rygeMaterialeInput.getText().toLowerCase();
        String kommentar = kommentarInput.getText().toLowerCase();

        StringBuilder fejl = new StringBuilder();

        // --- Validering ---
        if (kornSort.isBlank()) fejl.append("- Kornsort må ikke være tom.\n");
        if (slutDato.isBefore(LocalDate.now().plusYears(3))) fejl.append("- Slutdato må ikke være før i dag.\n");

        try {
            alkoholProcent = Double.parseDouble(alkoholProcentInput.getText());
            if (alkoholProcent < 0 || alkoholProcent > 100) fejl.append("- Alkoholprocent skal være mellem 0 og 100.\n");
        } catch (NumberFormatException e) { fejl.append("- Alkoholprocent skal være et tal.\n"); }

        try {
            mængde = Double.parseDouble(mængdeInput.getText());
            if (mængde <= 0) fejl.append("- Mængde skal være større end 0.\n");
        } catch (NumberFormatException e) { fejl.append("- Mængde skal være et tal.\n"); }

        if (mark.isBlank()) fejl.append("- Mark skal udfyldes.\n");

        try {
            maltBatch = Integer.parseInt(maltBatchInput.getText());
            if (maltBatch < 0) fejl.append("- Malt batch skal være et positivt tal.\n");
        } catch (NumberFormatException e) { fejl.append("- Malt batch skal være et helt tal.\n"); }

        // --- Vis fejl ---
        if (!fejl.toString().isEmpty()) {
            System.out.println("FEJL:\n" + fejl);
            return;
        }

        // --- Opret destilat via controller ---

        Destilat destilat = Controller.createDestilat(
                alkoholProcent, mængde, mark, kornSort, maltBatch, slutDato, rygeMateriale, kommentar
        );
        // Tilføj direkte til ObservableList
        destilater.add(destilat);

        // --- Ryd inputfelter ---
        kornSortInput.clear();
        alkoholProcentInput.clear();
        mængdeInput.clear();
        markInput.clear();
        maltBatchInput.clear();
        rygeMaterialeInput.clear();
        kommentarInput.clear();
    }
}
