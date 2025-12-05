package gui;


import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;
import application.model.Fad;
import application.controller.Controller;
import java.util.stream.Collectors;



public class FadOversigtTab {
    private ObservableList<Fad> fadList = FXCollections.observableArrayList(Controller.getFadList());
    private TableView<Fad> table = new TableView<>(fadList);
    //TableView<Fad> table = new TableView<>(fadList);

    public Node getContent() {

//        table = new TableView<>();
//        table.setItems(Controller.getFadListe());

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
        colTapninger.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(String.valueOf(data.getValue().getTapninger().size()))
        );

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

        table.getColumns().setAll(colId, colType, colLiter, colLeverandør, colHistorik, colTapninger, colTapningstyper, colKlarTilTapning);

        VBox layout = new VBox(table);
        layout.setSpacing(10);
        layout.setPadding(new javafx.geometry.Insets(10));
        return layout;
    }

    public void refresh() {
        if (table != null) table.refresh();
    }

    public void updateControls() {
        fadList.setAll(Controller.getFadList());
        table.refresh();
    }
}
