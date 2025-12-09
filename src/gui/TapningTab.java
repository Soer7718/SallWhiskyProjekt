
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
    private List<Fad> fadList;

    public TapningTab(Fad fad) {
        this.fad = fad;
    }

    public Node getContent() {
        tapningTable = new TableView<>();

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        GridPane formPane = new GridPane();
        formPane.setHgap(10);
        formPane.setVgap(10);
        formPane.setPadding(new Insets(10));

        //Labels og TextFields
        Label lblNavn = new Label("Navn:");
        TextField txtNavn = new TextField();
        Label lblAlkohol = new Label("Alkohol %:");
        TextField txtAlkohol = new TextField();
        Label lblHistorik = new Label("Historik:");
        TextField txtHistorik = new TextField();
        Label lblVand = new Label("Mængde vand:");
        TextField txtVand = new TextField();
        Label lblVandOprindelse = new Label("Vand oprindelse:");
        TextField txtVandOprindelse = new TextField();
        Label lblMængde = new Label("Fuld mængde:");
        TextField txtMængde = new TextField();
        txtMængde.setEditable(false);
        txtMængde.setFocusTraversable(false);
        Label lblFlereFade = new Label("Tilføj fad:");
        Label lblTyper = new Label("Typer:");
        Label lblFlaskeStr = new Label("Flaskestørrelse:");

        //Buttons
        Button btnTilføjFad = new Button("Tilføj fad");
        Button btnOpret = new Button("Opret Tapning");

        //ComboBoxes
        ComboBox<Fad> cbFade = new ComboBox<>();
        cbFade.setItems(FXCollections.observableArrayList(
                Controller.getFadListe().stream()
                        .filter(f -> f.getPåfyldninger() != null)
                        .filter(f -> f.udregnLagringsÅr() >= 3)
                        .filter(f -> f.getFyldt() > 0)
                        .toList()));
        cbFade.setPrefWidth(200);

        ComboBox<Double> cbFlaskeStr = new ComboBox<>();
        cbFlaskeStr.setItems(FXCollections.observableArrayList(0.5, 0.7));
        cbFlaskeStr.getSelectionModel().select(1);


        //ListViews
        ListView<Fad> lstValgteFade = new ListView<>();
        lstValgteFade.setPrefHeight(100);

        txtVand.textProperty().addListener((obs, oldVal, newVal) -> {
            opdaterMængde(txtMængde, txtVand, lstValgteFade);
        });

        ListView<WhiskyType> lstTyper = new ListView<>();
        lstTyper.setItems(FXCollections.observableArrayList(WhiskyType.values()));
        lstTyper.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lstTyper.setPrefHeight(100);



        //Tilføjer til formpane
        formPane.add(lblAlkohol, 0, 0);
        formPane.add(txtAlkohol, 1, 0);
        formPane.add(lblHistorik, 0, 1);
        formPane.add(txtHistorik, 1, 1);
        formPane.add(lblVand, 0, 2);
        formPane.add(txtVand, 1, 2);
        formPane.add(lblVandOprindelse, 0, 3);
        formPane.add(txtVandOprindelse, 1, 3);
        formPane.add(lblFlaskeStr, 2, 0);
        formPane.add(cbFlaskeStr, 3, 0);
        formPane.add(lblNavn,2,1);
        formPane.add(txtNavn,3,1);

        formPane.add(lblFlereFade, 0, 4);
        formPane.add(cbFade, 1, 4);
        formPane.add(btnTilføjFad, 1, 5);
        formPane.add(lstValgteFade, 1, 6);

        formPane.add(lblMængde, 0, 7);
        formPane.add(txtMængde, 1, 7);
        formPane.add(lblTyper, 0, 8);
        formPane.add(lstTyper, 1, 8);
        formPane.add(btnOpret, 1, 9);

        root.setTop(formPane);

        //events
        btnTilføjFad.setOnAction(e -> {
            Fad valgt = cbFade.getValue();
            if (valgt != null && !lstValgteFade.getItems().contains(valgt)) {
                lstValgteFade.getItems().add(valgt);
                opdaterMængde(txtMængde, txtVand, lstValgteFade);
            }
        });

        if (fad != null) {
            lstValgteFade.getItems().add(fad);
            opdaterMængde(txtMængde, txtVand, lstValgteFade);

            cbFade.getSelectionModel().select(fad);
        }

        //events
        btnOpret.setOnAction(e -> {
            try {
                String navn = txtNavn.getText();
                double alkohol = Double.parseDouble(txtAlkohol.getText());
                String historik = txtHistorik.getText();
                double vand = Double.parseDouble(txtVand.getText());
                String vandOprindelse = txtVandOprindelse.getText();
                double flaskeStr = cbFlaskeStr.getSelectionModel().getSelectedItem();
                List<WhiskyType> typer =
                        lstTyper.getSelectionModel().getSelectedItems()
                                .stream().collect(Collectors.toList());

                List<Fad> valgteFade = lstValgteFade.getItems();

                Controller.createTapning(valgteFade, alkohol, historik, vand, vandOprindelse, flaskeStr, typer, navn);

                tapningTable.setItems(FXCollections.observableArrayList(
                        valgteFade.stream()
                                .flatMap(f -> f.getTapninger().stream())
                                .distinct()
                                .toList()
                ));

                txtNavn.clear();
                txtAlkohol.clear();
                txtHistorik.clear();
                txtVand.clear();
                txtVandOprindelse.clear();
                txtMængde.clear();
                lstTyper.getSelectionModel().clearSelection();
                lstValgteFade.getItems().clear();

            } catch (NumberFormatException ex) {
                new Alert(Alert.AlertType.ERROR, "Alle talfelter skal være gyldige tal!").showAndWait();
            }
        });

        return root;
    }

    //hjælpe metode
    private void opdaterMængde(TextField txtMængde, TextField txtVand, ListView<Fad> lstValgteFade) {
        try {
            double vand = Double.parseDouble(txtVand.getText());
            double fadMængde = lstValgteFade.getItems().stream()
                    .mapToDouble(Fad::getFyldt)
                    .sum();
            txtMængde.setText(String.valueOf(vand + fadMængde));
        } catch (NumberFormatException ex) {
            txtMængde.setText("");
        }
    }
}
