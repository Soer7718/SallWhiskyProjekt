package gui;

import application.controller.Controller;
import application.model.Fad;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class LagerTab {
    private TableView<Fad> fadTable;

    public Node getContent() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // === Formular til oprettelse ===
        GridPane formPane = new GridPane();
        formPane.setHgap(10);
        formPane.setVgap(10);
        formPane.setPadding(new Insets(10));

        Label lblAntal = new Label("Lager NavnPenis");
        TextField txtAntal = new TextField();

        Label lblType = new Label("Adresse:");
        TextField txtType = new TextField();

/*
        Label lblLeverandør = new Label("Leverandør:");
        TextField txtLeverandør = new TextField();

        Label lblHistorik = new Label("Historik:");
        TextField txtHistorik = new TextField();
*/

        Button btnOpret = new Button("Opret Lager");

        // Tilføj felter til GridPane
        formPane.add(lblAntal, 0, 0);
        formPane.add(txtAntal, 1, 0);
        formPane.add(lblType, 0, 1);
        formPane.add(txtType, 1, 1);
/*        formPane.add(lblLeverandør, 0, 2);
        formPane.add(txtLeverandør, 1, 2);
        formPane.add(lblHistorik, 0, 3);
        formPane.add(txtHistorik, 1, 3);*/
        formPane.add(btnOpret, 1, 4);

        root.setTop(formPane);

        // === TableView til liste ===
        fadTable = new TableView<>();
        fadTable.setPrefHeight(300);

        TableColumn<Fad, String> colType = new TableColumn<>("Type");
        colType.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getType()));

        TableColumn<Fad, String> colLeverandør = new TableColumn<>("Leverandør");
        colLeverandør.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLeverandør()));

        TableColumn<Fad, String> colHistorik = new TableColumn<>("Historik");
        colHistorik.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getHistorik()));

        TableColumn<Fad, String> colAntal = new TableColumn<>("Antal gange brugt");
        colAntal.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getAntalGangeBrugt())));

        fadTable.getColumns().addAll(colType, colLeverandør, colHistorik, colAntal);
        fadTable.setItems(FXCollections.observableArrayList(Controller.getFadList()));

        root.setCenter(fadTable);

        // === Eventhandler til knappen ===
        btnOpret.setOnAction(e -> {
            String type = txtType.getText().trim();
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

            if (fejl.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Valideringsfejl");
                alert.setHeaderText("Ret venligst følgende:");
                alert.setContentText(fejl.toString());
                alert.showAndWait();
                return;
            }

            // Opret fad

            // Opdater tabel
            fadTable.setItems(FXCollections.observableArrayList(Controller.getFadList()));

            // Ryd felter
            txtAntal.clear();
            txtType.clear();

        });

        return root;
    }
}
