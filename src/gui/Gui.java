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

    @Override
    public void start(Stage stage) {
        TabPane tabPane = new TabPane();

        // Lav tabs fra dine egne klasser
        Tab fadTab = new Tab("Fad");
        fadTab.setContent(new FadTab().getContent());


        tabPane.getTabs().add(fadTab);

        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Scene scene = new Scene(tabPane, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Multi-tab App");
        stage.show();
        // Tab destilleriTab = new Tab("Destilleri");
       // destilleriTab.setContent(new DestilatTab().getContent());

        // tabPane.getTabs().addAll(fadTab, destilleriTab);

        // tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE); //gør så fanerne ikke kan lukkes

        // Scene scene = new Scene(tabPane, 600, 400);
        // stage.setScene(scene);
        // stage.setTitle("Multi-tab App");
        // stage.show();


    }
}
