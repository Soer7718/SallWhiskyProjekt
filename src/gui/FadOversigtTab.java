package gui;


import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;
import application.model.Fad;
import application.controller.Controller;


public class FadOversigtTab {
    private ObservableList<Fad> fadList = FXCollections.observableArrayList(Controller.getFadList());
    TableView<Fad> table = new TableView<>(fadList);

    public Node getContent() {
        table.setItems(FXCollections.observableArrayList(Controller.getFadList()));

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

        table.getColumns().addAll(colId, colType, colLiter, colLeverandør, colHistorik);

        VBox layout = new VBox(table);
        layout.setSpacing(10);
        layout.setPadding(new javafx.geometry.Insets(10));
        return layout;
    }

    public void refresh() {
    }

    public void updateControls() {
        fadList.setAll(Controller.getFadList());
        table.refresh();
    }
}
