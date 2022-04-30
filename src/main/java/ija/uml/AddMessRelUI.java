// autor: Tereza Buchníčková        //
// login: xbuchn00                  //
//      //

package ija.uml;

import java.util.ArrayList;

import ija.uml.items.ClassDiagram;
import ija.uml.items.SequenceDiagram;
import ija.uml.items.UMLClass;
import ija.uml.items.UMLMessage;
import ija.uml.items.UMLRelation;
import ija.uml.items.UMLMessage.MesType;
import ija.uml.items.UMLRelation.RelType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class AddMessRelUI {

    ClassDiagram classDiagram;
    SequenceDiagram sequenceDiagram;
    ArrayList<UMLRelation> rels = new ArrayList<UMLRelation>();
    ArrayList<UMLMessage> messages = new ArrayList<UMLMessage>();
    boolean isRelMode;

    @FXML
    private Button save;
    @FXML 
    private ChoiceBox<String> type, class_from, class_to;
    @FXML
    private ListView<String> list;

    
    public void init(SequenceDiagram sDiagram, ClassDiagram cDiagram, boolean isRelMode) {
        this.isRelMode = isRelMode;
        sequenceDiagram = sDiagram;
        classDiagram = cDiagram;

        for (UMLClass umlClass : classDiagram.getClasses()) { //ziskani jmen trid do vyberu
            String name = umlClass.getName();
            
            class_from.getItems().add(name);
            class_to.getItems().add(name);
        }

        if(isRelMode){
            for (UMLRelation rel : classDiagram.getRelations()) {
                rels.add(rel);
                list.getItems().add(rel.toString());
            }
            type.setItems(FXCollections.observableArrayList(
                "Asociace", "Agregace", "Kompozice", "Generalizace"));
            type.getSelectionModel().selectFirst();
        }
        else {
            for (UMLMessage mess : sequenceDiagram.getMessages()) { 
                messages.add(mess);
                list.getItems().add(mess.toString()); //vlozeni zprav do listView
            }
            type.setItems(FXCollections.observableArrayList(
                "Synchronní", "Asynchronní", "Návrat", "Vytvoření objektu", "Zánik objektu")); 
            type.getSelectionModel().selectFirst();
        }
    
    }  

    @FXML
    void close() {
        Stage stage = (Stage) save.getScene().getWindow();
        stage.close();
    }

    @FXML
    void save() {
        Stage stage = (Stage) save.getScene().getWindow();
        stage.close();
        if(isRelMode){
            classDiagram.clearRelations();
            for (UMLRelation rel : rels) {
                classDiagram.addRelation(rel);
            }
        } else {
            sequenceDiagram.clearMessages();
            for (UMLMessage mess : messages) {
                sequenceDiagram.addMessage(mess);
            } 
        }
        
    }
  
    @FXML
    void add() {
        int index = type.getSelectionModel().getSelectedIndex();
        if(isRelMode){
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
            String cf = class_from.getValue();
            String ct = class_to.getValue();
            UMLRelation rel = new UMLRelation(type, classDiagram.findClass(cf), classDiagram.findClass(ct));
            rels.add(rel);
            list.getItems().add(rel.toString()); //pridani relace do seznamu
        } else {
            UMLMessage.MesType type;
            switch (index) {
                case 0:
                    type = MesType.SYNC;
                    break;
                case 1:
                    type = MesType.ASYN;
                    break;
                case 2:
                    type = MesType.REPLY;
                    break;
                case 3:
                    type = MesType.CREATE;
                    break;
                default:
                    type = MesType.DELETE;
                    break;
            }
            String cf = class_from.getValue();
            String ct = class_to.getValue();
            UMLMessage mess = new UMLMessage(type, classDiagram.findClass(cf), classDiagram.findClass(ct)); 
            messages.add(mess);
            list.getItems().add(mess.toString()); //pridani zprav do seznamu
        } 
        
    }    

    @FXML
    void del() {
        int index = list.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            list.getItems().remove(index);
            if(isRelMode){
                rels.remove(index);
            } else {
                messages.remove(index);
            }
            
        }
    }

}

