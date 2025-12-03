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


    private FadTab fadTabContent;
    private DestilatTab destilleriTabContent;
    private LagerTab lagerTabContent;
    private PåfyldningTab påfyldningTabContent;
    private FadOversigtTab fadOversigtTabContent;


    @Override
    public void start(Stage stage) {
        TabPane tabPane = new TabPane();


        // Gem referencer til tab-indhold, så vi kan opdatere dem
        fadTabContent = new FadTab();
        destilleriTabContent = new DestilatTab();
        lagerTabContent = new LagerTab();
        påfyldningTabContent = new PåfyldningTab();
        fadOversigtTabContent = new FadOversigtTab();

        // Lav tabs fra dine egne klasser
        fadTabContent = new FadTab();
        Tab fadTab = new Tab("Fad");
        fadTab.setContent(fadTabContent.getContent());

        destilleriTabContent = new DestilatTab();
        Tab destilleriTab = new Tab("Destilleri");
        destilleriTab.setContent(destilleriTabContent.getContent());

        lagerTabContent = new LagerTab();
        Tab lagerTap = new Tab("Lager");
        lagerTap.setContent(lagerTabContent.getContent());

        påfyldningTabContent = new PåfyldningTab();
        Tab PåfyldningTab = new Tab("Påfyldning");
        PåfyldningTab.setContent(påfyldningTabContent.getContent());

        fadOversigtTabContent = new FadOversigtTab();
        Tab FadOversigtTab = new Tab("Oversigt");
        FadOversigtTab.setContent(fadOversigtTabContent.getContent());

        tabPane.getTabs().addAll(fadTab, destilleriTab,lagerTap, PåfyldningTab, FadOversigtTab);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE); //gør så fanerne ikke kan lukkes

        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab != null) {
                updateViews(newTab);
            }
        });

        Scene scene = new Scene(tabPane, 1200, 800);
        stage.setScene(scene);
        stage.setTitle("Multi-tab App");
        stage.show();


    }

    /**
     * Opdaterer viewet for den valgte tab.
     */
    private void updateViews(Tab selectedTab) {
        if (selectedTab.getText().equals("Fad")) {
            fadTabContent.refresh(); // Antag at du laver en refresh-metode i FadTab
        } else if (selectedTab.getText().equals("Destilleri")) {
            destilleriTabContent.refresh();
        } else if (selectedTab.getText().equals("Lager")) {
            lagerTabContent.refresh();
        } else if (selectedTab.getText().equals("Påfyldning")) {
            påfyldningTabContent.refresh();
        } else if (selectedTab.getText().equals("Oversigt")) {
            fadOversigtTabContent.refresh();
        }
    }
}
