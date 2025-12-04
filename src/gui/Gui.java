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

    private FadTab fadTab;
    private DestilatTab destilleriTab;
    private LagerTab lagerTab;
    private PåfyldningTab påfyldningTab;
    private FadOversigtTab fadOversigtTab;

    @Override
    public void start(Stage stage) {
        TabPane tabPane = new TabPane();

        // Lav tabs fra dine egne klasser
        fadTab = new FadTab();
        Tab tab1 = new Tab("Fad");
        tab1.setContent(fadTab.getContent());

        destilleriTab = new DestilatTab();
        Tab tab2 = new Tab("Destilleri");
        tab2.setContent(destilleriTab.getContent());

        lagerTab = new LagerTab();
        Tab tab3 = new Tab("Lager");
        tab3.setContent(lagerTab.getContent());

        påfyldningTab = new PåfyldningTab();
        Tab tab4 = new Tab("Påfyldning");
        tab4.setContent(påfyldningTab.getContent());

        fadOversigtTab = new FadOversigtTab();
        Tab tab5 = new Tab("Oversigt");
        tab5.setContent(fadOversigtTab.getContent());

        tabPane.getTabs().addAll(tab1, tab2,tab3, tab4, tab5);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE); //gør så fanerne ikke kan lukkes
tabPane.setOnMouseClicked((event) -> {
    this.updateListViews();
});

        Scene scene = new Scene(tabPane, 1200, 800);
        stage.setScene(scene);
        stage.setTitle("Multi-tab App");
        stage.show();

    }

    private void updateListViews() {
    fadTab.updateControls();
    destilleriTab.updateControls();
    lagerTab.updateControls();
    påfyldningTab.updateControls();
    fadOversigtTab.updateControls();
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