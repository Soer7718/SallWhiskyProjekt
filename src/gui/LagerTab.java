package gui;

import application.controller.Controller;
import application.model.*;
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

        Button btnLagerOpret = new Button("Opret Lager");

        Label lblValgteLager = new Label("Valgt lager:");
        Label lblValgteLagerNavn = new Label("(Ingen)");
        Label lblAntalReoler = new Label("Antal Reoler pr gang");
        TextField txtAntalReoler = new TextField();
        Label lblAntalHylderPrReol = new Label("Antal Hylder Pr reol");
        TextField txtAntalHylderPrReol = new TextField();

        Button btnGangOpret = new Button("Opret Gang");



        formPane.add(lblNavn, 0, 0);
        formPane.add(txtNavn, 1, 0);
        formPane.add(lblAdresse, 0, 1);
        formPane.add(txtAdresse, 1, 1);
        formPane.add(btnLagerOpret, 1, 3);


        formPane.add(lblValgteLager,3,0);
        formPane.add(lblValgteLagerNavn,4,0);
        formPane.add(lblAntalReoler,3,1);
        formPane.add(txtAntalReoler,4,1);
        formPane.add(lblAntalHylderPrReol,3,2);
        formPane.add(txtAntalHylderPrReol,4,2);

        formPane.add(btnGangOpret,4,3);

        root.setTop(formPane);

        // === TableView til liste ===
        lagerTable = new TableView<>();
        lagerTable.setPrefHeight(300);

        TableColumn<Lager, String> colNavn = new TableColumn<>("Navn");
        colNavn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNavn()));

        TableColumn<Lager, String> colAdresse = new TableColumn<>("Adresse");
        colAdresse.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAdresse()));

        TableColumn<Lager, String> colGange = new TableColumn<>("Gange");
        colGange.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getGangList().size())));

        TableColumn<Lager, String> colPladser = new TableColumn<>("Paller");
        colPladser.setCellValueFactory(data -> {
            int totalPladser = 0;
            for (Gang gang : data.getValue().getGangList()) {
                for (Reol reol : gang.getReolList()) {
                    for (Hylde hylde : reol.getHyldeList()) {
                        for (Palle palle : hylde.getPalleList()) {
                            totalPladser ++;
                        }
                    }
                }
            }
            return new SimpleStringProperty(String.valueOf(totalPladser));
        });

        TableColumn<Lager, String> colLedigePladser = new TableColumn<>("Ledige Paller");
        colLedigePladser.setCellValueFactory(data -> {
            int ledigePaller = 0;

            for (Gang gang : data.getValue().getGangList()) {
                for (Reol reol : gang.getReolList()) {
                    for (Hylde hylde : reol.getHyldeList()) {
                        for (Palle palle : hylde.getPalleList()) {
                            boolean erLedig = true;
                            for (Fad fad : palle.getPallePlads()) {
                                erLedig = erLedig && (fad == null);
                            }
                            if (erLedig) {
                                ledigePaller++;
                            }
                        }
                    }
                }
            }

            return new SimpleStringProperty(String.valueOf(ledigePaller));
        });

        lagerTable.getColumns().addAll(colNavn, colAdresse, colGange ,colPladser, colLedigePladser);
        lagerTable.setItems(FXCollections.observableArrayList(Controller.getInstance().getLagerList()));

        root.setCenter(lagerTable);


        lagerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                lblValgteLagerNavn.setText(newVal.getNavn());
            } else {
                lblValgteLagerNavn.setText("(ingen)");
            }
        });



        // === Eventhandler til knappen ===
        btnLagerOpret.setOnAction(e -> {
            String navn = txtNavn.getText().trim();
            String adresse = txtAdresse.getText().trim();

            StringBuilder fejl = new StringBuilder();
            int pladser = 0;

            if (navn.isEmpty()) fejl.append("- Navn skal udfyldes.\n");
            if (adresse.isEmpty()) fejl.append("- Adresse skal udfyldes.\n");

            if (!fejl.isEmpty()) {
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
        });

        btnGangOpret.setOnAction(e -> {
            Lager valgt = lagerTable.getSelectionModel().getSelectedItem();

            if (valgt == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ingen lager valgt");
                alert.setHeaderText("Du skal vælge et lager i tabellen");
                alert.setContentText("Klik på et lager for at tilføje en gang.");
                alert.showAndWait();
                return;
            }

            // --- Læs og valider input ---
            int antalReoler;
            int antalHylder;

            try {
                antalReoler = Integer.parseInt(txtAntalReoler.getText().trim());
                antalHylder = Integer.parseInt(txtAntalHylderPrReol.getText().trim());
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fejl i input");
                alert.setHeaderText("Forkerte værdier");
                alert.setContentText("Antal reoler og antal hylder skal være tal.");
                alert.showAndWait();
                return;
            }

            if (antalReoler <= 0 || antalHylder <= 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fejl");
                alert.setHeaderText("Ugyldige værdier");
                alert.setContentText("Antal reoler og hylder skal være større end 0.");
                alert.showAndWait();
                return;
            }

            // --- Opret gang gennem Lager (komposition) ---
            valgt.createGang(antalReoler, antalHylder);

            // --- Opdater UI med detaljer ---
            lblValgteLagerNavn.setText(valgt.getNavn() +
                    " (Gange: " + valgt.getGangList().size() +
                    ", Reoler pr gang: " + antalReoler +
                    ", Hylder pr reol: " + antalHylder + ")");

            // --- Ryd felter ---
            txtAntalReoler.clear();
            txtAntalHylderPrReol.clear();

            // --- Opdater tabel ---
            lagerTable.refresh();
        });



        return root;
    }
}