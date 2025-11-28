
package gui;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import application.model.Tapning;
import application.controller.Controller;

public class TapningTab {
    private TableView<Tapning> tapningTable;

    public Node getContent() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        GridPane formPane = new GridPane();
        formPane.setHgap(10);
        formPane.setVgap(10);
        formPane.setPadding(new Insets(10));

        Label lblAlkohol = new Label("Alkohol %:");
        TextField txtAlkohol = new TextField();

        Label lblHistorik = new Label("Historik:");
        TextField txtHistorik = new TextField();

        Label lblVand = new Label("Mængde vand:");
        TextField txtVand = new TextField();

        Label lblVandOprindelse = new Label("Vand oprindelse:");
        TextField txtVandOprindelse = new TextField();

        Label lblMængde = new Label("Mængde:");
        TextField txtMængde = new TextField();

        Button btnOpret = new Button("Opret Tapning");

        formPane.add(lblAlkohol, 0, 0);
        formPane.add(txtAlkohol, 1, 0);
        formPane.add(lblHistorik, 0, 1);
        formPane.add(txtHistorik, 1, 1);
        formPane.add(lblVand, 0, 2);
        formPane.add(txtVand, 1, 2);
        formPane.add(lblVandOprindelse, 0, 3);
        formPane.add(txtVandOprindelse, 1, 3);
        formPane.add(lblMængde, 0, 4);
        formPane.add(txtMængde, 1, 4);
        formPane.add(btnOpret, 1, 5);

        root.setTop(formPane);

        tapningTable = new TableView<>();
        TableColumn<Tapning, String> colAlkohol = new TableColumn<>("Alkohol %");
        colAlkohol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(String.valueOf(data.getValue().getAlkoholProcent())));

        TableColumn<Tapning, String> colHistorik = new TableColumn<>("Historik");
        colHistorik.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getHistorik()));

        TableColumn<Tapning, String> colMængde = new TableColumn<>("Mængde");
        colMængde.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(String.valueOf(data.getValue().getMængde())));

        tapningTable.getColumns().addAll(colAlkohol, colHistorik, colMængde);
        tapningTable.setItems(FXCollections.observableArrayList(Controller.getInstance().getTapningList()));
        tapningTable.setPrefHeight(300);

        root.setCenter(tapningTable);

        btnOpret.setOnAction(e -> {
            try {
                double alkohol = Double.parseDouble(txtAlkohol.getText());
                String historik = txtHistorik.getText();
                double vand = Double.parseDouble(txtVand.getText());
                String vandOprindelse = txtVandOprindelse.getText();
                double mængde = Double.parseDouble(txtMængde.getText());

                Controller.getInstance().registrerTapning(alkohol, historik, vand, vandOprindelse, mængde);
                tapningTable.setItems(FXCollections.observableArrayList(Controller.getInstance().getTapningList()));

                txtAlkohol.clear();
                txtHistorik.clear();
                txtVand.clear();
                txtVandOprindelse.clear();
                txtMængde.clear();
            } catch (NumberFormatException ex) {
                new Alert(Alert.AlertType.ERROR, "Alle talfelter skal være gyldige tal!").showAndWait();
            }
        });

        return root;
    }
}
