package gui;

import application.controller.Controller;
import application.model.*;
import javafx.beans.binding.Bindings;
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


import java.util.stream.Collectors;

public class FadTab {

    private final ObservableList<Fad> fadListe = Controller.getFadListe();
    private final TableView<Fad> fadTable = new TableView<>(fadListe);

    public Node getContent() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Formular til oprettelse
        GridPane formPane = new GridPane();
        formPane.setHgap(10);
        formPane.setVgap(10);
        formPane.setPadding(new Insets(10));

        //Labels og TextFields
        Label lblAntal = new Label("Antal gange brugt:");
        TextField txtAntal = new TextField();
        Label lblType = new Label("Type:");
        TextField txtType = new TextField();
        Label lblLeverandør = new Label("Leverandør:");
        TextField txtLeverandør = new TextField();
        Label lblHistorik = new Label("Historik:");
        TextField txtHistorik = new TextField();
        Label lblStørrelse = new Label("Størrelse (L):");
        Label lblValgteFad = new Label("Omhæld til andet Fad");
        TextField txtLiter = new TextField();
        txtLiter.setPromptText("Liter");

        //Combobox
        ComboBox<Integer> cbStørrelse = new ComboBox<>();
        cbStørrelse.getItems().addAll(50, 100, 200, 500);
        cbStørrelse.setValue(200);

        ComboBox<Fad> cbFade = new ComboBox<>();
        cbFade.getItems().addAll(Controller.getFadList());
        //ændrer listens tekst i comboboxen
        cbFade.setCellFactory(lv -> new ListCell<Fad>() {
            @Override
            protected void updateItem(Fad fad, boolean empty) {
                super.updateItem(fad, empty);
                if (empty || fad == null) {
                    setText(null);
                } else {
                    setText("Fad #" + fad.getId() + " (" + fad.getType() + ")");
                }
            }
        });
        //ændrer knappens tekst
        cbFade.setButtonCell(new ListCell<Fad>() {
            @Override
            protected void updateItem(Fad fad, boolean empty) {
                super.updateItem(fad, empty);
                if (empty || fad == null) {
                    setText(null);
                } else {
                    setText("Fad #" + fad.getId() + " (" + fad.getType() + ")");
                }
            }
        });
        cbFade.setValue(Controller.getFadList().getFirst());

        //Buttons
        Button btnOpret = new Button("Opret Fad");
        Button btnTapFad = new Button("Tap Fad");
        Button btnFlytTilLager = new Button("Flyt til Lager");
        Button btnOmhældTilFad = new Button("Omhæld til Fad");

        btnTapFad.setDisable(true);
        btnFlytTilLager.setDisable(true);
        //gør btnOmhældning disabled indtil tal er udfyldt
        btnOmhældTilFad.disableProperty().bind(
                fadTable.getSelectionModel().selectedItemProperty().isNull()
                        .or(cbFade.valueProperty().isNull())
                        .or(txtLiter.textProperty().isEmpty())
                        .or(Bindings.createBooleanBinding(() -> {
            try {
                double liter = Double.parseDouble(txtLiter.getText().trim());
                Fad valgtFad = fadTable.getSelectionModel().getSelectedItem();
                return valgtFad == null || liter <= 0 || valgtFad.getFyldt() < liter;
            } catch (NumberFormatException e) {
                return true;
            }
        }, txtLiter.textProperty(), fadTable.getSelectionModel().selectedItemProperty()))
        );

        // Tilføj felter til GridPane
        formPane.add(lblAntal, 0, 0);
        formPane.add(txtAntal, 1, 0);
        formPane.add(lblType, 0, 1);
        formPane.add(txtType, 1, 1);
        formPane.add(lblLeverandør, 0, 2);
        formPane.add(txtLeverandør, 1, 2);
        formPane.add(lblHistorik, 0, 3);
        formPane.add(txtHistorik, 1, 3);
        formPane.add(btnOpret, 1, 7);
        formPane.add(btnTapFad, 1, 5);
        formPane.add(lblStørrelse, 0, 4);
        formPane.add(cbStørrelse, 1, 4);
        formPane.add(btnFlytTilLager, 1, 6);

        //Laver box "2"
        formPane.add(lblValgteFad,3,0);
        formPane.add(txtLiter,3,1);
        formPane.add(cbFade,3,2);
        formPane.add(btnOmhældTilFad,3,3);


        root.setTop(formPane);

        // TableView til liste
        fadTable.setItems(fadListe);
        fadTable.setPrefHeight(300);

        //---TableColumns---
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
        TableColumn<Fad, String> colTapninger   = new TableColumn<>("Tapninger");
        colTapninger.setCellValueFactory(data ->
                new SimpleStringProperty(String.valueOf(data.getValue().getTapninger().size()))
        );
        TableColumn<Fad, String> colTapningstyper = new TableColumn<>("Tapningstyper");
        colTapningstyper.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getTapninger().stream()
                                .flatMap(t -> t.getTyper().stream())
                                .map(Enum::name)
                                .distinct()
                                .collect(Collectors.joining(", "))));
        TableColumn<Fad, String> colKlarTilTapning = new TableColumn<>("Klar til tapning?");
        colKlarTilTapning.setCellValueFactory(data -> {
            Fad f = data.getValue();
            String svar = (f.udregnLagringsÅr() >= 3) ? "Ja" : "Nej";
            return new SimpleStringProperty(svar);
        });
        TableColumn<Fad, String> colLager = new TableColumn<>("placering");
        colLager.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getPlacering())));
        fadTable.getColumns().clear();
        fadTable.getColumns().addAll(colId ,colType, colLeverandør, colHistorik, colAntal, colStørrelse, colFyldt,colTapninger, colTapningstyper,colKlarTilTapning, colLager);
        root.setCenter(fadTable);

        fadTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, selected) -> {
            btnFlytTilLager.setDisable(selected == null);
            if (selected == null) {
                btnTapFad.setDisable(true);
            } else {
                btnTapFad.setDisable(selected.udregnLagringsÅr() < 3);
            }
        });

        btnTapFad.setOnAction(e -> openTapningsVindue());

        //Eventhandler
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
                new Alert(Alert.AlertType.ERROR, fejl.toString()).showAndWait();
                return;
            }

            // Opret fad
            int id = nextFadId();
            int liter = cbStørrelse.getValue();
            Controller.createFad(antal, type, leverandør, historik, liter);

            // Ryd felter
            txtAntal.clear();
            txtType.clear();
            txtLeverandør.clear();
            txtHistorik.clear();
        });

        btnFlytTilLager.setOnAction(e -> {
            Fad valgtFad = fadTable.getSelectionModel().getSelectedItem();

            if (valgtFad == null) return;

            boolean succes = Controller.flytFadTilLager(valgtFad);

            if (succes) {
                new Alert(Alert.AlertType.INFORMATION,
                        "Fadet er placeret på lager!").showAndWait();
                fadTable.refresh();
            } else {
                new Alert(Alert.AlertType.WARNING,
                        "Kunne ikke placere fadet — enten mangler plads eller fadet står allerede på en placering.")
                        .showAndWait();
            }
        });

        btnOmhældTilFad.setOnAction(e -> {
            // Hent det valgte fad fra tabellen
            Fad valgtFad = fadTable.getSelectionModel().getSelectedItem();
            double liter = 0.0;

            // Hent det fad, man vil omhælde til
            Fad tilFad = cbFade.getValue();
            StringBuilder fejl = new StringBuilder();

            // Valider
            try {
                liter = Double.parseDouble(txtLiter.getText().trim());
                if (liter <= 0) {
                    fejl.append("- Antal liter skal være større end 0.\n");
                }
            } catch (NumberFormatException ex) {
                fejl.append("- Liter skal være et tal.\n");
            }

            // viser hvis der er fejl
            if (fejl.length() > 0) {
                new Alert(Alert.AlertType.ERROR, fejl.toString()).showAndWait();
                return;
            }

            // Kør omhældning
            valgtFad.omhældTilAndetFad(tilFad, liter);

            txtLiter.clear();
            updateControls();
        });

        return root;
    }

    private int nextFadId() {
        return Controller.getFadList()
                .stream()
                .map(Fad::getId)
                .max(java.util.Comparator.naturalOrder())
                .orElse(0) + 1;
    }


    private void openTapningsVindue() {
        Fad valgtFad = fadTable.getSelectionModel().getSelectedItem();
        if (valgtFad == null) return;

        Stage stage = new Stage();
        stage.setTitle("Tapning");

        TapningTab tapningTab = new TapningTab(valgtFad);

        Scene scene = new Scene((Parent) tapningTab.getContent(), 600, 500);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        fadTable.refresh();
    }

    public void updateControls() {
        fadListe.setAll(Controller.getFadList());
        fadTable.refresh();
    }
}
