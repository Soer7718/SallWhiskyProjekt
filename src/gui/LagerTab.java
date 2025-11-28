package gui;

import application.controller.Controller;
import application.model.Lager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class LagerTab {
    private TableView<Lager> lagerTable;

    public Node getContent() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // === Formular til oprettelse ===
        GridPane formPane = new GridPane();
        formPane.setHgap(10);
        formPane.setVgap(10);
        formPane.setPadding(new Insets(10));

        Label lblNavn = new Label("Lager navn:");
        TextField txtNavn = new TextField();

        Label lblAdresse = new Label("Adresse:");
        TextField txtAdresse = new TextField();

        Label lblPladser = new Label("Antal pladser:");
        TextField txtPladser = new TextField();

        Button btnOpret = new Button("Opret Lager");

        formPane.add(lblNavn, 0, 0);
        formPane.add(txtNavn, 1, 0);
        formPane.add(lblAdresse, 0, 1);
        formPane.add(txtAdresse, 1, 1);
        formPane.add(lblPladser, 0, 2);
        formPane.add(txtPladser, 1, 2);
        formPane.add(btnOpret, 1, 3);

        root.setTop(formPane);

        // === TableView til liste ===
        lagerTable = new TableView<>();
        lagerTable.setPrefHeight(300);

        TableColumn<Lager, String> colNavn = new TableColumn<>("Navn");
        colNavn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNavn()));

        TableColumn<Lager, String> colAdresse = new TableColumn<>("Adresse");
        colAdresse.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAdresse()));

        TableColumn<Lager, String> colPladser = new TableColumn<>("Pladser");
        colPladser.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getPladser())));

        lagerTable.getColumns().addAll(colNavn, colAdresse, colPladser);
        lagerTable.setItems(FXCollections.observableArrayList(Controller.getInstance().getLagerList()));

        root.setCenter(lagerTable);

        // === Eventhandler til knappen ===
        btnOpret.setOnAction(e -> {
            String navn = txtNavn.getText().trim();
            String adresse = txtAdresse.getText().trim();
            String pladserStr = txtPladser.getText().trim();

            StringBuilder fejl = new StringBuilder();
            int pladser = 0;
            try {
                pladser = Integer.parseInt(pladserStr);
                if (pladser <= 0) fejl.append("- Antal pladser skal være større end 0.\n");
            } catch (NumberFormatException ex) {
                fejl.append("- Antal pladser skal være et helt tal.\n");
            }

            if (navn.isEmpty()) fejl.append("- Navn skal udfyldes.\n");
            if (adresse.isEmpty()) fejl.append("- Adresse skal udfyldes.\n");

            if (fejl.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Valideringsfejl");
                alert.setHeaderText("Ret venligst følgende:");
                alert.setContentText(fejl.toString());
                alert.showAndWait();
                return;
            }

            // Opret lager via Controller
            Lager lager = Controller.getInstance().createLager(navn, adresse);
            lager.setPladser(pladser);

            // Opdater tabel
            lagerTable.setItems(FXCollections.observableArrayList(Controller.getInstance().getLagerList()));

            // Ryd felter
            txtNavn.clear();
            txtAdresse.clear();
            txtPladser.clear();
        });

        return root;
    }
}