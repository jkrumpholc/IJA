// autor: Tereza Buchníčková        //
// login: xbuchn00                  //
//      //
package ija.uml;

import java.util.ArrayList;

import ija.uml.items.ClassDiagram;
import ija.uml.items.SequenceDiagram;
import ija.uml.items.UMLClass;
import ija.uml.items.UMLObject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddObjUI {

    SequenceDiagram sequenceDiagram;
    ClassDiagram classDiagram;
    ArrayList<UMLObject> objects = new ArrayList<UMLObject>();

    @FXML
    private Button save;
    @FXML
    private ListView<String> obj_list;
    @FXML
    private ChoiceBox<String> uml_class;
    @FXML
    private TextField name;
    @FXML
    private CheckBox aut_create;

    public void init(SequenceDiagram sDiagram, ClassDiagram cDiagram) {
        sequenceDiagram = sDiagram;
        classDiagram = cDiagram;
        for (UMLClass umlClass : classDiagram.getClasses()) {
            String name = umlClass.getName();
            
            uml_class.getItems().add(name);
        }
        for (UMLObject obj : sequenceDiagram.getObjects()) {
            objects.add(obj);
            obj_list.getItems().add(obj.toString());
        }
    }  

    @FXML
    void close() {
        Stage stage = (Stage) save.getScene().getWindow();
        stage.close();
    }

    @FXML
    void save_obj() {
        Stage stage = (Stage) save.getScene().getWindow();
        stage.close();
        sequenceDiagram.clearObjects();
        for (UMLObject obj : objects) {
            sequenceDiagram.addObject(obj);
        }
    }
    
    @FXML
    void obj_add() {
        String objName = name.getText();
        String c = uml_class.getValue();
        UMLClass cl = classDiagram.findClass(c);
        if (objName.isEmpty()) {
            Controller.errorMessage("Musíte vyplnit jméno objektu"); 
            return;
        }
        if (cl == null) {
            Controller.errorMessage("Neplatná třída");
            return;
        }
        UMLObject obj = new UMLObject(objName, cl, aut_create.isSelected());
        objects.add(obj);
        obj_list.getItems().add(obj.toString());  //pridani objektu do seznamu
    }    

    @FXML
    void obj_del() {
        int index = obj_list.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            obj_list.getItems().remove(index);
            objects.remove(index);
        }
    } 
}
