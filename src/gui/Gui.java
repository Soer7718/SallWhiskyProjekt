package gui;

import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import application.model.Fad;
import application.controller.Controller;


public class Gui extends Application{

    private FadTab fadTabInstance;
    @Override
    public void start(Stage stage) {
        TabPane tabPane = new TabPane();

        // Lav tabs fra dine egne klasser
        fadTabInstance = new FadTab();
        Tab fadTab = new Tab("Fad");
        fadTab.setContent(new FadTab().getContent());

        Tab destilleriTab = new Tab("Destilleri");
        destilleriTab.setContent(new DestilatTab().getContent());

        Tab lagerTap = new Tab("Lager");
        lagerTap.setContent(new LagerTab().getContent());

        Tab PåfyldningTab = new Tab("Påfyldning");
        PåfyldningTab.setContent(new PåfyldningTab(fadTabInstance).getContent());

        Tab FadOversigtTab = new Tab("Oversigt");
        FadOversigtTab.setContent(new FadOversigtTab().getContent());

        tabPane.getTabs().addAll(fadTab, destilleriTab,lagerTap, PåfyldningTab, FadOversigtTab);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE); //gør så fanerne ikke kan lukkes

        Scene scene = new Scene(tabPane, 1200, 800);
        stage.setScene(scene);
        stage.setTitle("Multi-tab App");
        stage.show();

    }

    public void refreshFadListe() {
        if (fadTabInstance != null) {
            fadTabInstance.refreshTable();
        }


    }
}
//tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
//        if (newTab != null) {
//updateListForTab(newTab);
//        }
//                });
//private void updateListForTab(Tab tab) {
//    tab.
//
//
//            ListView<String> listView = (ListView<String>) ((VBox) tab.getContent()).getChildren().get(0);
//    ObservableList<String> items = listView.getItems();
//    items.clear();
//
//    switch (tab.getText()) {
//        case "Tab 1":
//            items.addAll("A1", "A2", "A3");
//            break;
//        case "Tab 2":
//            items.addAll("B1", "B2", "B3");
//            break;
//        case "Tab 3":
//            items.addAll("C1", "C2", "C3");
//            break;
//        case "Tab 4":
//            items.addAll("D1", "D2", "D3");
//            break;
//    }
//}