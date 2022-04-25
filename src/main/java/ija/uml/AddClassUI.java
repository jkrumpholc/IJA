package ija.uml;

import java.util.ArrayList;

import ija.uml.items.ClassDiagram;
import ija.uml.items.UMLAttribute;
import ija.uml.items.UMLClass;
import ija.uml.items.UMLClassifier;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddClassUI {

    ClassDiagram classDiagram;
    ArrayList<UMLAttribute> attrs = new ArrayList<UMLAttribute>();
    ArrayList<UMLAttribute> meths = new ArrayList<UMLAttribute>();

    @FXML
    private Button close;
    @FXML
    private TextField title;
    @FXML
    private TextField attr_name, meth_name;
    @FXML
    private TextField attr_type, meth_type;
    @FXML
    private ListView<String> attr_list, meth_list;

    @FXML
    void close() {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    @FXML
    void add_class() {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
        UMLClass umlClass = classDiagram.createClass(title.getText());
        for (UMLAttribute attr : attrs) {
            umlClass.addAttribute(attr);
        }
    }
  
    @FXML
    void attr_add() {
        UMLAttribute attr = new UMLAttribute(attr_name.getText(), new UMLClassifier(attr_type.getText()));
        attrs.add(attr);
        attr_list.getItems().add(attr.toString());
    }    

    @FXML
    void attr_del() {

    }

    @FXML
    void meth_add() {
        UMLAttribute meth = new UMLAttribute(meth_name.getText(), new UMLClassifier(meth_type.getText()));
        meths.add(meth);
        meth_list.getItems().add(meth.toString()); 
    }    

    @FXML
    void meth_del() {

    }

    public void setClassDiagram(ClassDiagram diagram) {
        classDiagram = diagram;
    }   
}

