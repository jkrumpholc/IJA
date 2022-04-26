package ija.uml;

import java.util.ArrayList;

import ija.uml.items.ClassDiagram;
import ija.uml.items.UMLAttribute;
import ija.uml.items.UMLClass;
import ija.uml.items.UMLClassifier;
import ija.uml.items.UMLOperation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddEditUI {

    ClassDiagram classDiagram;
    boolean isEditMode;
    UMLClass editClass;
    String currentItem;
    ArrayList<UMLAttribute> attrs = new ArrayList<UMLAttribute>();
    ArrayList<UMLOperation> meths = new ArrayList<UMLOperation>(); 

    @FXML
    private Button save;
    @FXML
    private TextField title;
    @FXML
    private TextField attr_name, meth_name;
    @FXML
    private TextField attr_type, meth_type;
    @FXML
    private ListView<String> attr_list, meth_list;

    
    public void init(ClassDiagram diagram, boolean isEditMode, UMLClass editClass) {
        classDiagram = diagram;
        this.isEditMode = isEditMode;
        this.editClass = editClass;
        if (isEditMode) {
            title.setText(editClass.getName());
            save.setText("Uložit");
            for (UMLAttribute attr : editClass.getAttributes()) {
                attrs.add(attr);
                attr_list.getItems().add(attr.toString());
            }
            for (UMLOperation meth : editClass.getOperation()) {
                meths.add(meth);
                meth_list.getItems().add(meth.toString());
            }
        }
    }  

    @FXML
    void close() {
        Stage stage = (Stage) save.getScene().getWindow();
        stage.close();
    }

    @FXML
    void save_class() {
        Stage stage = (Stage) save.getScene().getWindow();
        stage.close();
        if (isEditMode) {
            editClass.delAttributesOperation();
            editClass.rename(title.getText());
            for (UMLAttribute attr : attrs) {
                editClass.addAttribute(attr);
            }
            for (UMLOperation meth : meths) {
                editClass.addOperation(meth);
            }
        } else {
            UMLClass umlClass = classDiagram.createClass(title.getText()); //vlozeni tridy do modelu
            for (UMLAttribute attr : attrs) {
                umlClass.addAttribute(attr);
            }
            for (UMLOperation meth : meths) {
                umlClass.addOperation(meth);
            }
        }  
    }
  
    @FXML
    void attr_add() {
        UMLAttribute attr = new UMLAttribute(attr_name.getText(), new UMLClassifier(attr_type.getText()));
        attrs.add(attr);
        attr_list.getItems().add(attr.toString()); //pridani atributu do seznamu
    }    

    @FXML
    void attr_del() {
        int index = attr_list.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            attr_list.getItems().remove(index);
            attrs.remove(index);
        }
    }

    @FXML
    void meth_add() {
        UMLOperation meth = new UMLOperation(meth_name.getText(), new UMLClassifier(meth_type.getText())); //TODO přepsat
        meths.add(meth);
        meth_list.getItems().add(meth.toString()); //pridani metody do seznamu
    }    

    @FXML
    void meth_del() {
        int index = meth_list.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            meth_list.getItems().remove(index);
            meths.remove(index);
        }
    } 
}

