package gui;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import application.model.Fad;

public class FadTab {
    private TableView<Fad> fadTable;

    public Node getContent() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Form til oprettelse
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

        formPane.add(lblAntal, 0, 0);
        formPane.add(txtAntal, 1, 0);
        formPane.add(lblType, 0, 1);
        formPane.add(txtType, 1, 1);
        formPane.add(lblLeverandør, 0, 2);
        formPane.add(txtLeverandør, 1, 2);
        formPane.add(lblHistorik, 0, 3);
        formPane.add(txtHistorik, 1, 3);
        formPane.add(btnOpret, 1, 4);

        root.setTop(formPane);

        // TableView
        fadTable = new TableView<>();
        TableColumn<Fad, String> colType = new TableColumn<>("Type");
        colType.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getType()));

        TableColumn<Fad, String> colLeverandør = new TableColumn<>("Leverandør");
        colLeverandør.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getLeverandør()));

        TableColumn<Fad, String> colHistorik = new TableColumn<>("Historik");
        colHistorik.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getHistorik()));

        TableColumn<Fad, String> colAntal = new TableColumn<>("Antal gange brugt");
        colAntal.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(String.valueOf(data.getValue().getAntalGangeBrugt())));

        fadTable.getColumns().addAll(colType, colLeverandør, colHistorik, colAntal);
        fadTable.setItems(FXCollections.observableArrayList(App.getLager().getFadList()));
        fadTable.setPrefHeight(300);

        root.setCenter(fadTable);

        // Eventhandler til knappen
        btnOpret.setOnAction(e -> {
            try {
                int antal = Integer.parseInt(txtAntal.getText());
                String type = txtType.getText();
                String leverandør = txtLeverandør.getText();
                String historik = txtHistorik.getText();

                App.getLager().registrerFad(antal, type, leverandør, historik);
                fadTable.setItems(FXCollections.observableArrayList(App.getLager().getFadList()));

                txtAntal.clear();
                txtType.clear();
                txtLeverandør.clear();
                txtHistorik.clear();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Antal gange brugt skal være et tal!");
                alert.showAndWait();
            }
        });

        return root;
    }
}
