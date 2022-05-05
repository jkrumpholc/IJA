// autor: Tereza Buchníčková                        //
// login: xbuchn00                                  //
// kontroler okna pro přidání nebo editaci třídy    //

package ija.uml;

import java.util.ArrayList;

import ija.uml.items.ClassDiagram;
import ija.uml.items.UMLAttribute;
import ija.uml.items.UMLClass;
import ija.uml.items.UMLClassifier;
import ija.uml.items.UMLOperation;
import ija.uml.items.UMLClass.AccessMod;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
    private boolean dataSaved = false;
    private boolean dataDeleted = false;

    @FXML
    private Button save, class_del;
    @FXML
    private TextField title;
    @FXML
    private TextField attr_name, meth_name;
    @FXML
    private TextField attr_type, meth_type;
    @FXML
    private ListView<String> attr_list, meth_list;
    @FXML
    private ChoiceBox<String> attr_access_mod, meth_access_mod;

    
    public void init(ClassDiagram diagram, boolean isEditMode, UMLClass editClass) {
        classDiagram = diagram;
        this.isEditMode = isEditMode;
        this.editClass = editClass;
        attr_access_mod.setItems(FXCollections.observableArrayList(
            "+", "-", "#", "~"));
        attr_access_mod.getSelectionModel().selectFirst();
        meth_access_mod.setItems(FXCollections.observableArrayList(
            "+", "-", "#", "~"));
        meth_access_mod.getSelectionModel().selectFirst();
        if (isEditMode) {
            class_del.setVisible(true);
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
        //kontrola
        if (title.getText().isEmpty()) {
            Controller.errorMessage("Musíte vyplnit jméno třídy"); 
            return;
        }
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
        dataSaved = true;
    }

    private AccessMod mode(int index){
        UMLClass.AccessMod mode;
        switch (index) {
            case 0:
                mode = AccessMod.PUBLIC;
                break;
            case 1:
                mode = AccessMod.PRIVATE;
                break;
            case 2:
                mode = AccessMod.PROTECTED;
                break;
            default:
                mode = AccessMod.INTERNAL;
                break;
        }
        return mode;
    }
  
    @FXML
    void attr_add() {
        if (attr_name.getText().isEmpty() || attr_type.getText().isEmpty()) {
            Controller.errorMessage("Musíte vyplnit jméno a typ atributu"); 
            return;
        }
        int i = attr_access_mod.getSelectionModel().getSelectedIndex();
        UMLAttribute attr = new UMLAttribute(attr_name.getText(), new UMLClassifier(attr_type.getText()), mode(i));
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
        if (meth_name.getText().isEmpty() || meth_type.getText().isEmpty()) {
            Controller.errorMessage("Musíte vyplnit jméno a typ metody"); 
            return;
        }
        int i = meth_access_mod.getSelectionModel().getSelectedIndex();
        UMLOperation meth = new UMLOperation(meth_name.getText(), new UMLClassifier(meth_type.getText()), mode(i)); 
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

    @FXML
    void class_del() {
        String name = editClass.getName();
        classDiagram.deleteClass(name);
        dataDeleted = true;
        Stage stage = (Stage) save.getScene().getWindow();
        stage.close();
    } 

    boolean getDataSaved() {
        return dataSaved;
    }

    boolean getDataDeleted() {
        return dataDeleted;
    }

}

