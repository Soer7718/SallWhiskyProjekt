
package gui;

import application.model.Fad;
import application.model.WhiskyType;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import application.model.Tapning;
import application.controller.Controller;

import java.util.List;
import java.util.stream.Collectors;

public class TapningTab {
    private TableView<Tapning> tapningTable;
    private Fad fad;

    public TapningTab(Fad fad) {
        this.fad = fad;
    }

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

        Label lblTyper = new Label("Typer:");
        ListView<WhiskyType> lstTyper = new ListView<>();
        lstTyper.setItems(FXCollections.observableArrayList(WhiskyType.values()));
        lstTyper.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lstTyper.setPrefHeight(100);

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
        formPane.add(lblTyper, 0, 5);
        formPane.add(lstTyper, 1, 5);
        formPane.add(btnOpret, 1, 6);

        root.setTop(formPane);

        tapningTable = new TableView<>();
        TableColumn<Tapning, String> colAlkohol = new TableColumn<>("Alkohol %");
        colAlkohol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(String.valueOf(data.getValue().getAlkoholProcent())));

        TableColumn<Tapning, String> colHistorik = new TableColumn<>("Historik");
        colHistorik.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getHistorik()));

        TableColumn<Tapning, String> colMængde = new TableColumn<>("Mængde");
        colMængde.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(String.valueOf(data.getValue().getFuldMængde())));

        TableColumn<Tapning, String> colTyper = new TableColumn<>("Typer");
        colTyper.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getTyper()
                                .stream()
                                .map(Enum::name)
                                .collect(Collectors.joining(", "))
                ));

        tapningTable.getColumns().addAll(colAlkohol, colHistorik, colMængde, colTyper);
        tapningTable.setItems(FXCollections.observableArrayList(
                fad.getTapninger() != null ? fad.getTapninger() : List.of()
        ));
        tapningTable.setPrefHeight(300);

        root.setCenter(tapningTable);



        btnOpret.setOnAction(e -> {
            try {
                double alkohol = Double.parseDouble(txtAlkohol.getText());
                String historik = txtHistorik.getText();
                double vand = Double.parseDouble(txtVand.getText());
                String vandOprindelse = txtVandOprindelse.getText();
                double mængde = Double.parseDouble(txtMængde.getText());
                List<WhiskyType> typer =
                        lstTyper.getSelectionModel().getSelectedItems()
                                .stream().collect(Collectors.toList());


                Controller.createTapning(fad, alkohol, historik, vand, vandOprindelse, mængde, typer);
                tapningTable.setItems(FXCollections.observableArrayList(
                        fad.getTapninger() != null ? fad.getTapninger() : List.of()
                ));

                // Ryd felter
                txtAlkohol.clear();
                txtHistorik.clear();
                txtVand.clear();
                txtVandOprindelse.clear();
                txtMængde.clear();
                lstTyper.getSelectionModel().clearSelection();

            } catch (NumberFormatException ex) {
                new Alert(Alert.AlertType.ERROR, "Alle talfelter skal være gyldige tal!").showAndWait();
            }
        });

        return root;
    }
}
