package gui;

import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import javafx.application.Application;

public class Gui extends Application{

    private FadTab fadTab;
    private DestilatTab destilleriTab;
    private LagerTab lagerTab;
    private PåfyldningTab påfyldningTab;
    private FadOversigtTab fadOversigtTab;

    @Override
    public void start(Stage stage) {
        TabPane tabPane = new TabPane();

        //FadTap
        fadTab = new FadTab();
        Tab tab1 = new Tab("Fad");
        tab1.setContent(fadTab.getContent());

       //DestilleriTap
        destilleriTab = new DestilatTab();
        Tab tab2 = new Tab("Destilleri");
        tab2.setContent(destilleriTab.getContent());

        //LagerTap
        lagerTab = new LagerTab();
        Tab tab3 = new Tab("Lager");
        tab3.setContent(lagerTab.getContent());

        //PåfyldningsTap
        påfyldningTab = new PåfyldningTab();
        Tab tab4 = new Tab("Påfyldning");
        tab4.setContent(påfyldningTab.getContent());

        //FadOversigtTap
        fadOversigtTab = new FadOversigtTab();
        Tab tab5 = new Tab("Oversigt");
        tab5.setContent(fadOversigtTab.getContent());

        //Tilføjer alle Taps
        tabPane.getTabs().addAll(tab1, tab2,tab3, tab4, tab5);
        //Fjerner Kryds på Taps
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
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
