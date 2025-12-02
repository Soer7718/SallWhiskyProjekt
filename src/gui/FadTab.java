package gui;

import application.controller.Controller;
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

public class FadTab {
    private TableView<Fad> fadTable;
    private ObservableList<Fad> fadListe;

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

        root.setTop(formPane);

        // === TableView til liste ===
        fadListe = FXCollections.observableArrayList(Controller.getFadList());
        fadTable = new TableView<>(fadListe);
        fadTable.setPrefHeight(300);

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

        fadTable.getColumns().addAll(colId, colType, colLeverandør, colHistorik, colAntal, colStørrelse, colFyldt);
//        TableColumn<Fad, String> colAar = new TableColumn<>("Lagringsår");
//        colAar.setCellValueFactory(data ->
//                new SimpleStringProperty("" + data.getValue().udregnLagringsÅr()));

        fadTable.getColumns().addAll(colType, colLeverandør, colHistorik, colAntal);
        fadTable.setItems(FXCollections.observableArrayList(Controller.getFadList()));

        root.setCenter(fadTable);

//        fadTable.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> {
//            if (selected == null) {
//                btnTapFad.setDisable(true);
//            } else {
//                btnTapFad.setDisable(selected.getLagringsAar() < 3); // <--- KRAV: minimum 3 år
//            }
//        });


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

            fadListe = FXCollections.observableArrayList(Controller.getFadList());
            fadTable = new TableView<>(fadListe);

            // Opret fad
            Fad nytFad = Controller.createFad(antal, type, leverandør, historik);

            // Opdater oversigt
            fadListe.add(nytFad);

            // Ryd felter
            txtAntal.clear();
            txtType.clear();
            txtLeverandør.clear();
            txtHistorik.clear();
        });

        return root;
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
