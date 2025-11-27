    package gui;

    import javafx.scene.control.Button;
    import javafx.scene.control.Label;
    import javafx.scene.control.TextField;
    import javafx.scene.layout.HBox;
    import javafx.scene.layout.Priority;
    import javafx.scene.layout.VBox;



    public class DestilatTab{

        Label kornSortLabel = new Label("Korn Sort:");
        Label slutDatoLabel = new Label("Slut Dato:");
        Label alkoholProcentLabel = new Label("Alkohol Procent:");
        Label mængdeLabel = new Label("Mængde:");
        Label markLabel = new Label("Mark:");
        Label maltBatchLabel = new Label("Malt Batch:");
        Label rygeMaterialeLabel = new Label("Ryge materiale:");
        Label kommentarLabel = new Label("Kommentar:");
        TextField kornSortInput = new TextField();
        TextField slutDatoInput = new TextField();
        TextField alkoholProcentInput = new TextField();
        TextField mængdeInput = new TextField();
        TextField markInput = new TextField();
        TextField maltBatchInput = new TextField();
        TextField rygeMaterialeInput = new TextField();
        TextField kommentarInput = new TextField();
        Button opretDestilatKnap = new Button("Opret Destilat");

        public VBox getContent() {
            VBox layout = new VBox();
            layout.setSpacing(10); //afstand imellem kasserne
            VBox.setVgrow(layout, Priority.ALWAYS);

            HBox row = new HBox();
            row.setSpacing(10); // afstand mellem kasserne
            HBox.setHgrow(row, Priority.ALWAYS); // row fylder vandret
            VBox.setVgrow(row, Priority.ALWAYS); // row fylder lodret

            // --- Kasse 1:
            VBox box1 = new VBox();
            box1.setSpacing(5);
            box1.setStyle("-fx-border-color: black; -fx-padding: 5;");
            Label label1 = new Label("Opret Destilat");
            box1.getChildren().add(label1);
            HBox.setHgrow(box1, Priority.ALWAYS);
            VBox.setVgrow(box1, Priority.ALWAYS);
            box1.getChildren().addAll(
                    kornSortLabel,
                    kornSortInput,
                    slutDatoLabel,
                    slutDatoInput,
                    alkoholProcentLabel,
                    alkoholProcentInput,
                    mængdeLabel,
                    mængdeInput,
                    markLabel,
                    markInput,
                    maltBatchLabel,
                    maltBatchInput,
                    rygeMaterialeLabel,
                    rygeMaterialeInput,
                    kommentarLabel,
                    kommentarInput,
                    opretDestilatKnap);

            // --- Kasse 2:
            VBox box2 = new VBox();
            box2.setSpacing(5);
            box2.setStyle("-fx-border-color: black; -fx-padding: 5;");
            Label label2 = new Label("Destilat liste");
            box2.getChildren().add(label2);
            HBox.setHgrow(box2, Priority.ALWAYS);
            VBox.setVgrow(box2, Priority.ALWAYS);

            // --- Kasse 3:
            VBox box3 = new VBox();
            box3.setSpacing(5);
            box3.setStyle("-fx-border-color: black; -fx-padding: 5;");
            Label label3 = new Label("Destilat info");
            box3.getChildren().add(label3);
            HBox.setHgrow(box3, Priority.ALWAYS);
            VBox.setVgrow(box3, Priority.ALWAYS);


            row.getChildren().addAll(box1,box2,box3);

            layout.getChildren().add(row);


            return layout;
        }
    }
