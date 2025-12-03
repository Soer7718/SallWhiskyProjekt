package gui;

import application.controller.Controller;
import application.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import application.model.Fad;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FadTab{

    private ObservableList<Fad> fadListe = FXCollections.observableArrayList(Controller.getFadListe());
    private TableView<Fad> fadTable = new TableView<>(fadListe);


    public Node getContent() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // === Formular til oprettelse ===
        GridPane formPane = new GridPane();
        formPane.setHgap(10);
        formPane.setVgap(10);
        formPane.setPadding(new Insets(10));

        Label lblAntal = new Label("Antal gange brugt:");
        TextField txtAntal = new TextField();

        Label lblType = new Label("Type:");
        TextField txtType = new TextField();

        Label lblLeverandør = new Label("Leverandør:");
        TextField txtLeverandør = new TextField();

        Label lblHistorik = new Label("Historik:");
        TextField txtHistorik = new TextField();

        Button btnOpret = new Button("Opret Fad");

        Button btnTapFad = new Button("Tap Fad");
        btnTapFad.setDisable(true);

        Button btnFlytTilLager = new Button("Flyt til Lager");
        btnFlytTilLager.setDisable(true); // deaktiveret indtil et fad er valgt

        // Tilføj felter til GridPane
        formPane.add(lblAntal, 0, 0);
        formPane.add(txtAntal, 1, 0);
        formPane.add(lblType, 0, 1);
        formPane.add(txtType, 1, 1);
        formPane.add(lblLeverandør, 0, 2);
        formPane.add(txtLeverandør, 1, 2);
        formPane.add(lblHistorik, 0, 3);
        formPane.add(txtHistorik, 1, 3);
        formPane.add(btnOpret, 1, 4);
        formPane.add(btnTapFad, 1, 5);

        formPane.add(btnFlytTilLager, 1, 6);

        root.setTop(formPane);

        // === TableView til liste ===
        fadTable.setPrefHeight(300);
        fadTable.setItems(fadListe);

        TableColumn<Fad, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getId())));

        TableColumn<Fad, String> colType = new TableColumn<>("Type");
        colType.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getType()));

        TableColumn<Fad, String> colLeverandør = new TableColumn<>("Leverandør");
        colLeverandør.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLeverandør()));

        TableColumn<Fad, String> colHistorik = new TableColumn<>("Historik");
        colHistorik.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getHistorik()));

        TableColumn<Fad, String> colAntal = new TableColumn<>("Antal gange brugt");
        colAntal.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getAntalGangeBrugt())));

        TableColumn<Fad, String> colStørrelse = new TableColumn<>("Størrelse (L)");
        colStørrelse.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getStørrelseLiter())));

        TableColumn<Fad, String> colFyldt = new TableColumn<>("Fyldt (L)");
        colFyldt.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getFyldt())));

//        TableColumn<Fad, String> colAar = new TableColumn<>("Lagringsår");
//        colAar.setCellValueFactory(data ->
//                new SimpleStringProperty("" + data.getValue().udregnLagringsÅr()));

        fadTable.getColumns().addAll(colType, colLeverandør, colHistorik, colAntal, colStørrelse, colFyldt);
        fadTable.setItems(Controller.getFadListe());

        root.setCenter(fadTable);

        fadTable.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> {
            if (selected == null) {
                btnTapFad.setDisable(true);
            } else {
                btnTapFad.setDisable(selected.udregnLagringsÅr() < 3); // <--- KRAV: minimum 3 år
            }
        });

        fadTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, selected) -> {
            btnFlytTilLager.setDisable(selected == null);
        });

        btnTapFad.setOnAction(e -> openTapningsVindue());

        // === Eventhandler til knappen ===
        btnOpret.setOnAction(e -> {

            String type = txtType.getText().trim();
            String leverandør = txtLeverandør.getText().trim();
            String historik = txtHistorik.getText().trim();
            String antalStr = txtAntal.getText().trim();

            StringBuilder fejl = new StringBuilder();

            int antal = 0;
            try {
                antal = Integer.parseInt(antalStr);
                if (antal <= 0) {
                    fejl.append("- Antal gange brugt skal være større end 0.\n");
                }
            } catch (NumberFormatException ex) {
                fejl.append("- Antal gange brugt skal være et helt tal.\n");
            }

            if (type.isEmpty()) fejl.append("- Type skal udfyldes.\n");
            if (leverandør.isEmpty()) fejl.append("- Leverandør skal udfyldes.\n");
            if (historik.isEmpty()) fejl.append("- Historik skal udfyldes.\n");

            if (fejl.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Valideringsfejl");
                alert.setHeaderText("Ret venligst følgende:");
                alert.setContentText(fejl.toString());
                alert.showAndWait();
                return;
            }

            // Opret fad
            int id = nextFadId();
            double liter = 200.0;
            Fad nytFad = Controller.createFad(id, antal, type, leverandør, historik, liter);

            // Opdater oversigt
            fadListe.add(nytFad);

            // Ryd felter
            txtAntal.clear();
            txtType.clear();
            txtLeverandør.clear();
            txtHistorik.clear();
        });

        btnFlytTilLager.setOnAction(e -> {
            Fad valgtFad = fadTable.getSelectionModel().getSelectedItem();
            if (placerFad(valgtFad)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Fad placeret på lager!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Ingen ledige paller til fadet!");
                alert.showAndWait();
            }
        });


        return root;
    }

    private boolean placerFad(Fad fad) {
        Palle ledigPalle = findLedigPalle(fad);
        if (ledigPalle != null) {
            for (int i = 0; i < ledigPalle.getPallePlads().length; i++) {
                if (ledigPalle.getPallePlads()[i] == null) {
                    ledigPalle.getPallePlads()[i] = fad;
                    return true; // fad placeret
                }
            }
        }
        return false; // ingen plads
    }

    private int nextFadId() {
        return Controller.getFadList()
                .stream()
                .map(Fad::getId)
                .max(java.util.Comparator.naturalOrder())
                .orElse(0) + 1;
    }


    private Palle findLedigPalle(Fad fad) {
        int pladserBehov = fad.getStørrelseLiter() <= 50 ? 1 : 2;

        for (Lager lager : Controller.getLagerList()) {
            for (Gang gang : lager.getGangList()) {
                for (Reol reol : gang.getReolList()) {
                    for (Hylde hylde : reol.getHyldeList()) {
                        for (Palle palle : hylde.getPalleList()) {
                            int ledigePladser = palle.getLedigePladser();
                            if (ledigePladser >= pladserBehov) {
                                return palle;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public void refreshTable() {
        fadListe.setAll(Controller.getFadListe());
        fadTable.refresh();
    }

    private void openTapningsVindue() {
        Stage stage = new Stage();
        stage.setTitle("Tapning");

        TapningTab tapningTab = new TapningTab();

        Scene scene = new Scene((Parent) tapningTab.getContent(), 600, 500);
        stage.setScene(scene);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }


}
