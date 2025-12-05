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
    private PåfyldningTab påfyldningTabInstance;
    private FadOversigtTab fadOversigtTabInstance;

    private FadTab fadTab;
    private DestilatTab destilleriTab;
    private LagerTab lagerTab;
    private PåfyldningTab påfyldningTab;
    private FadOversigtTab fadOversigtTab;

    @Override
    public void start(Stage stage) {
        TabPane tabPane = new TabPane();

        // Lav tabs fra dine egne klasser
//        fadTabInstance = new FadTab();
//        Tab fadTab = new Tab("Fad", fadTabInstance.getContent());

        fadTab = new FadTab();
        Tab tab1 = new Tab("Fad");
        tab1.setContent(fadTab.getContent());

       // Tab destilleriTab = new Tab("Destilleri", new DestilatTab().getContent());
        destilleriTab = new DestilatTab();
        Tab tab2 = new Tab("Destilleri");
        tab2.setContent(destilleriTab.getContent());

        //Tab lagerTap = new Tab("Lager", new LagerTab().getContent());
        lagerTab = new LagerTab();
        Tab tab3 = new Tab("Lager");
        tab3.setContent(lagerTab.getContent());

        påfyldningTab = new PåfyldningTab();
        Tab tab4 = new Tab("Påfyldning");
        tab4.setContent(påfyldningTab.getContent());

//        påfyldningTabInstance = new PåfyldningTab(fadTabInstance);
//        Tab påfyldningTab = new Tab("Påfyldning", påfyldningTabInstance.getContent());

        fadOversigtTab = new FadOversigtTab();
        Tab tab5 = new Tab("Oversigt");
        tab5.setContent(fadOversigtTab.getContent());

//        fadOversigtTabInstance = new FadOversigtTab();
//        Tab FadOversigtTab = new Tab("Oversigt", fadOversigtTabInstance.getContent());


        //tabPane.getTabs().addAll(fadTab, destilleriTab,lagerTap, påfyldningTab, FadOversigtTab);
        tabPane.getTabs().addAll(tab1, tab2,tab3, tab4, tab5);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE); //gør så fanerne ikke kan lukkes
        tabPane.setOnMouseClicked((event) -> {this.updateListViews();});

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
