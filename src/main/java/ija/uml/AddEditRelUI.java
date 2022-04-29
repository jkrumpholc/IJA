// autor: Tereza Buchníčková        //
// login: xbuchn00                  //
//      //

package ija.uml;

import java.util.ArrayList;

import ija.uml.items.ClassDiagram;
import ija.uml.items.UMLRelation;
import ija.uml.items.UMLRelation.RelType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddEditRelUI {

    ClassDiagram classDiagram;
    ArrayList<UMLRelation> rels = new ArrayList<UMLRelation>();

    @FXML
    private Button save;
    @FXML
    private TextField class_from, class_to;
    @FXML 
    private ChoiceBox<String> rel_type;
    @FXML
    private ListView<String> rel_list;

    
    public void init(ClassDiagram diagram) {
        classDiagram = diagram;
        for (UMLRelation rel : classDiagram.getRelations()) {
            rels.add(rel);
            rel_list.getItems().add(rel.toString());
        }
        rel_type.setItems(FXCollections.observableArrayList(
            "Asociace", "Agregace", "Kompozice", "Generalizace"));
        rel_type.getSelectionModel().selectFirst();
    }  

    @FXML
    void close() {
        Stage stage = (Stage) save.getScene().getWindow();
        stage.close();
    }

    @FXML
    void save_rel() {
        Stage stage = (Stage) save.getScene().getWindow();
        stage.close();
        classDiagram.clearRelations();
        for (UMLRelation rel : rels) {
            classDiagram.addRelation(rel);
        }
    }
  
    @FXML
    void rel_add() {
        int index = rel_type.getSelectionModel().getSelectedIndex();
        UMLRelation.RelType type;
        switch (index) {
            case 0:
                type = RelType.ASSOC;
                break;
            case 1:
                type = RelType.AGGR;
                break;
            case 2:
                type = RelType.COMPOS;
                break;
            default:
                type = RelType.GENER;
                break;

        }
        UMLRelation rel = new UMLRelation(type, class_from.getText(), class_to.getText());
        rels.add(rel);
        rel_list.getItems().add(rel.toString()); //pridani relace do seznamu
    }    

    @FXML
    void rel_del() {
        int index = rel_list.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            rel_list.getItems().remove(index);
            rels.remove(index);
        }
    }

}

