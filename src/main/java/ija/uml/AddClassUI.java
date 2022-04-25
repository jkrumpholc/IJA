package ija.uml;

import ija.uml.items.ClassDiagram;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddClassUI {

    ClassDiagram classDiagram;

    @FXML
    private Button close;
    @FXML
    private TextField title;
    @FXML
    private VBox methods_array, attributes_array;

    @FXML
    public void add_method() {
        TextField text_field = new TextField();
        methods_array.getChildren().add(text_field);
    }

    @FXML
    public void add_attributes() {
        TextField text_field = new TextField();
        attributes_array.getChildren().add(text_field);
    }

    @FXML
    public void close() {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void add_class() {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
        classDiagram.createClass(title.getText());
        //predat si class diagram
        //vytvorit classu
        //vykresleni celeho diagramu
    }

    public void setClassDiagram(ClassDiagram diagram) {
        classDiagram = diagram;
    }

}

