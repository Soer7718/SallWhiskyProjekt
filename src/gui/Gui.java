package gui;

import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import javafx.application.Application;

public class Gui extends Application{

    @Override
    public void start(Stage stage) {
        TabPane tabPane = new TabPane();

        // Lav tabs fra dine egne klasser
        Tab fadTab = new Tab("Fad");
        //fadTab.setContent(new FadTab().getContent());

        Tab destilleriTab = new Tab("Destilleri");
        destilleriTab.setContent(new DestilatTab().getContent());

        tabPane.getTabs().addAll(fadTab, destilleriTab);

        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE); //gør så fanerne ikke kan lukkes

        Scene scene = new Scene(tabPane, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Multi-tab App");
        stage.show();
    }
}
