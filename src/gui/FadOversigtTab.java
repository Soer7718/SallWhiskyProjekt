package gui;


import application.model.Tapning;
import application.model.Whisky;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;
import application.model.Fad;
import application.controller.Controller;

import java.util.stream.Collectors;

public class FadOversigtTab {
    private TableView<Fad> fadTable;
    private TableView<Tapning> whiskyTable;

    //observable Lists
    private ObservableList<Fad> fadList = FXCollections.observableArrayList(Controller.getFadList());
    private ObservableList<Tapning> whiskyList = FXCollections.observableArrayList(Controller.getTapningList()); // antag at du har en metode til at hente whiskyer


    public Node getContent() {
        // --- ComboBox til at vælge visning ---
        ComboBox<String> cbVisning = new ComboBox<>();
        cbVisning.setItems(FXCollections.observableArrayList("Fade", "Whiskyprodukter"));
        cbVisning.getSelectionModel().select(0); // standard: Fade

        // --- TableView til fade ---
        fadTable = new TableView<>(fadList);
        setupFadTable();

        // --- TableView til whiskyer ---
        // WhiskyTable er skult fra starten
        whiskyTable = new TableView<>(whiskyList);
        setupWhiskyTable();
        whiskyTable.setVisible(false);


        StackPane tableStack = new StackPane(fadTable, whiskyTable);
        tableStack.setPrefHeight(400);

        // --- eventListener ---
        cbVisning.setOnAction(e -> {
            String valg = cbVisning.getSelectionModel().getSelectedItem();
            if (valg.equals("Fade")) {
                fadTable.setVisible(true);
                whiskyTable.setVisible(false);
            } else {
                fadTable.setVisible(false);
                whiskyTable.setVisible(true);
            }
        });

        VBox layout = new VBox(10, cbVisning, tableStack);
        layout.setPadding(new Insets(10));
        return layout;
    }

    private void setupFadTable() {
        TableColumn<Fad, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(String.valueOf(data.getValue().getId())));

        TableColumn<Fad, String> colType = new TableColumn<>("Type");
        colType.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getType()));

        TableColumn<Fad, String> colLiter = new TableColumn<>("Liter");
        colLiter.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(String.valueOf(data.getValue().getStørrelseLiter())));

        TableColumn<Fad, String> colLeverandør = new TableColumn<>("Leverandør");
        colLeverandør.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getLeverandør()));

        TableColumn<Fad, String> colHistorik = new TableColumn<>("Historik");
        colHistorik.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getHistorik()));

        TableColumn<Fad, String> colTapninger = new TableColumn<>("Tapninger");
        colTapninger.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(String.valueOf(data.getValue().getTapninger().size())));

        TableColumn<Fad, String> colTapningstyper = new TableColumn<>("Tapningstyper");
        colTapningstyper.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getTapninger().stream()
                                .flatMap(t -> t.getTyper().stream())
                                .map(Enum::name)
                                .distinct()
                                .collect(Collectors.joining(", "))));

        TableColumn<Fad, String> colKlarTilTapning = new TableColumn<>("Klar til tapning?");
        colKlarTilTapning.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().udregnLagringsÅr() >= 3 ? "Ja" : "Nej"
                )
        );

        fadTable.getColumns().setAll(colId, colType, colLiter, colLeverandør, colHistorik, colTapninger, colTapningstyper, colKlarTilTapning);
    }

    private void setupWhiskyTable() {
        TableColumn<Tapning, String> colNavn = new TableColumn<>("Tapning");
        colNavn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getWhiskyNavn())
        );

        TableColumn<Tapning, String> colAntalFlasker = new TableColumn<>("Antal flasker");
        colAntalFlasker.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(String.valueOf(data.getValue().getWhiskyList().size()))
        );

        TableColumn<Tapning, String> colAlkohol = new TableColumn<>("Samlet alkohol %");
        colAlkohol.setCellValueFactory(data -> {
            double gennemsnitAlkohol = data.getValue().getWhiskyList().stream()
                    .mapToDouble(Whisky::getAlkoholProcent)
                    .average()
                    .orElse(0);
            return new javafx.beans.property.SimpleStringProperty(String.format("%.2f", gennemsnitAlkohol));
        });

        TableColumn<Tapning, String> colTapningstyper = new TableColumn<>("Tapningstyper");
        colTapningstyper.setCellValueFactory(data -> {
            if (data.getValue().getTyper().isEmpty()) {
                return new javafx.beans.property.SimpleStringProperty("");
            } else {
                return new javafx.beans.property.SimpleStringProperty(data.getValue().getTyper().get(0).name());
            }
        });

        whiskyTable.getColumns().setAll(colNavn, colAntalFlasker, colAlkohol, colTapningstyper);
    }

    //Update metode
    public void updateControls() {
        fadList.setAll(Controller.getFadList());
        whiskyList.setAll(Controller.getTapningList());
        fadTable.refresh();
        whiskyTable.refresh();
    }
}
