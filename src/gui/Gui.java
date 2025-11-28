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


        Tab fadTab = new Tab("Fad");
        fadTab.setContent(new FadTab().getContent());

        Tab destilleriTab = new Tab("Destilleri");
        destilleriTab.setContent(new DestilatTab().getContent());

        Tab LagerTap = new Tab("Lager");
        LagerTap.setContent(new LagerTab().getContent());

        tabPane.getTabs().addAll(fadTab, destilleriTab, LagerTap);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE); //fjerner lukning fra tab

        Scene scene = new Scene(tabPane, 1200, 800);
        stage.setScene(scene);
        stage.setTitle("Multi-tab App");
        stage.show();
    }
}
