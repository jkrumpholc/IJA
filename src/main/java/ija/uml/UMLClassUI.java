// autor: Tereza Buchníčková        //
// login: xbuchn00                  //
// konstruktor UML třídy            //

package ija.uml;

import java.io.IOException;

import ija.uml.items.ClassDiagram;
import ija.uml.items.UMLAttribute;
import ija.uml.items.UMLClass;
import ija.uml.items.UMLOperation;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class UMLClassUI extends VBox {

    @FXML
    private Button class_button;
    @FXML
    private ListView<String> attributes, operation;
    UMLClass umlClass;
    ClassDiagram classDiagram;
    ClassDiagramUI classDiagramUI;

    public UMLClassUI(UMLClass umlClass, ClassDiagram classDiagram, ClassDiagramUI classDiagramUI) {
        this.umlClass = umlClass;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("uml_class_ui.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.classDiagram = classDiagram;
        this.classDiagramUI = classDiagramUI;
        initClass();
    }

    @FXML
    public void editClass() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("add_edit_ui.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Upravit třídu");
                stage.setScene(new Scene(loader.load(), 400, 400));
                stage.initModality(Modality.APPLICATION_MODAL);
                AddEditUI controller = loader.getController();
                var tempUndoData = new UndoData(umlClass, classDiagram.findClassPos(umlClass.getName()));
                controller.init(classDiagram, true, umlClass);
                stage.showAndWait();
                if (controller.getDataSaved()) {
                    UndoData.setUndoBuffer(tempUndoData);
                    initClass();
                }
                if (controller.getDataDeleted()) {
                    UndoData.setUndoBuffer(tempUndoData); //TODO
                    initClass();
                    classDiagramUI.draw();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
    }

    private void initClass() {
        class_button.setText(umlClass.getName());
        attributes.getItems().clear();
        operation.getItems().clear();
        for (UMLAttribute attr : umlClass.getAttributes()) {
            attributes.getItems().add(attr.toString());
        }
        for (UMLOperation meth : umlClass.getOperation()) {
            operation.getItems().add(meth.toString());
        }
    }
}