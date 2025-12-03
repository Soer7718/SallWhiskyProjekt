
package gui;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import java.time.LocalDate;

import application.model.Fad;
import application.model.Destilat;
import application.model.Påfyldning;
import application.controller.Controller;


public class PåfyldningTab{
    private FadTab fadTab;

    public PåfyldningTab(FadTab fadTab) {
        this.fadTab = fadTab;
    }

    public Node getContent() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        ComboBox<Fad> fadCombo = new ComboBox<>();
        fadCombo.getItems().addAll(Controller.getFadListe());
        fadCombo.setPromptText("Vælg fad");

        ComboBox<Destilat> destilatCombo = new ComboBox<>();
        destilatCombo.getItems().addAll(Controller.getDestillatListe());
        destilatCombo.setPromptText("Vælg destillat");

        DatePicker datoPicker = new DatePicker(LocalDate.now());

        Button påfyldKnap = new Button("Påfyld destillat på fad");
        Label feedbackLabel = new Label();

        påfyldKnap.setOnAction(e -> {
            Fad valgtFad = fadCombo.getValue();
            Destilat valgtDestilat = destilatCombo.getValue();
            LocalDate valgtDato = datoPicker.getValue();

            if (valgtFad == null || valgtDestilat == null || valgtDato == null) {
                feedbackLabel.setText("Vælg både fad, destillat og dato.");
                return;
            }
            try {
                Påfyldning p = Controller.påfyldDestillatPåFad(valgtFad.getId(), valgtDestilat.getId(), valgtDato);
                feedbackLabel.setText(
                        "Påfyldning oprettet • Fad #" + valgtFad.getId()
                                + " • Destillat #" + valgtDestilat.getId()
                                + " • Dato: " + valgtDato
                );
                opdaterFadCombo(fadCombo);
                if (fadTab != null) {
                    fadTab.refreshTable();
                }

            } catch (IllegalStateException ex) {
                // Kommer fra Controller, når fadet ikke er tomt
                feedbackLabel.setText(ex.getMessage());
            } catch (Exception ex) {
                // Catch-all for uforudsete fejl
                feedbackLabel.setText("Der opstod en fejl ved påfyldning: " + ex.getMessage());
            }
        });

        root.getChildren().addAll(
                new Label("UC5: Påfyld destillat på fad"),
                new Label("Fad:"),
                fadCombo,
                new Label("Destillat:"),
                destilatCombo,
                new Label("Dato:"),
                datoPicker,
                påfyldKnap,
                feedbackLabel
        );

        return root;
    }
    private void opdaterFadCombo(ComboBox<Fad> fadCombo) {
        fadCombo.getItems().setAll(Controller.getFadListe());
    }
}
