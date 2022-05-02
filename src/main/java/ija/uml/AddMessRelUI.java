// autor: Tereza Buchníčková        //
// login: xbuchn00                  //
//      //

package ija.uml;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeListener;

import ija.uml.items.ClassDiagram;
import ija.uml.items.SequenceDiagram;
import ija.uml.items.UMLClass;
import ija.uml.items.UMLMessage;
import ija.uml.items.UMLObject;
import ija.uml.items.UMLOperation;
import ija.uml.items.UMLRelation;
import ija.uml.items.UMLMessage.MesType;
import ija.uml.items.UMLRelation.RelType;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
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
    private ChoiceBox<String> type, class_from, class_to, method;
    @FXML
    private ListView<String> list;
    @FXML
    private Label label_from, label_to;

    
    public void init(SequenceDiagram sDiagram, ClassDiagram cDiagram, boolean isRelMode) {
        this.isRelMode = isRelMode;
        sequenceDiagram = sDiagram;
        classDiagram = cDiagram;

        if(isRelMode){
            for (UMLClass umlClass : classDiagram.getClasses()) { //ziskani jmen trid do vyberu
                String name = umlClass.getName();
                
                class_from.getItems().add(name);
                class_to.getItems().add(name);
            }
            for (UMLRelation rel : classDiagram.getRelations()) {
                rels.add(rel);
                list.getItems().add(rel.toString());
            }
            type.setItems(FXCollections.observableArrayList(
                "Asociace", "Agregace", "Kompozice", "Generalizace"));
            type.getSelectionModel().selectFirst();
            //TODO schovat metodu
        }
        else {
            class_to.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    String obt = class_to.getItems().get(new_val.intValue());
                    List<UMLOperation> meth = sequenceDiagram.findObject(obt).getUMLClass().getOperation();
                    method.getItems().clear();
                    for(UMLOperation m : meth) {
                        method.getItems().add(m.getName());
                    }
             });
            for (UMLObject umlObj : sequenceDiagram.getObjects()) { //ziskani jmen trid do vyberu
                String name = umlObj.getName();
                // if(umlObj.getActive()) {
                     class_from.getItems().add(name);
                // }
                class_to.getItems().add(name);
            }
            for (UMLMessage mess : sequenceDiagram.getMessages()) { 
                messages.add(mess);
                list.getItems().add(mess.toString()); //vlozeni zprav do listView
            }
            type.setItems(FXCollections.observableArrayList(
                "Synchronní", "Asynchronní", "Návrat", "Vytvoření objektu", "Zánik objektu")); 
            type.getSelectionModel().selectFirst();
            label_from.setText("Objekt 1");
            label_to.setText("Objekt 2");
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
            String obf = class_from.getValue();
            String obt = class_to.getValue();
            String meth = method.getValue();
            if (obf == null || obt == null || obf.isEmpty() || obt.isEmpty()) {
                Controller.errorMessage("Vyplňte zdrojový a cílový objekt");
                return;
            }
            if ((type == MesType.SYNC || type == MesType.ASYN) && (meth == null || meth.isEmpty())) {
                Controller.errorMessage("Vyplňte metodu");
                return;
            }
            //TODO nevyplňovat metodu pro jiný než sync a async
            UMLMessage mess = new UMLMessage(type, meth, sequenceDiagram.findObject(obf), sequenceDiagram.findObject(obt)); 
            messages.add(mess);
            if (!checkMessages()) {
                messages.remove(mess);
                return;
            }
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

    boolean checkMessages() {
        for (UMLObject object : sequenceDiagram.getObjects()) {
            if(object.getAutCreate()) {
                object.setActive(true);
            }
            else {
                object.setActive(false);
            }
        }
        for (UMLMessage mess : messages) {
            // TODO doplnit další kontroly
            if (!mess.getObjFrom().getActive()) {
                Controller.errorMessage("Objekt není vytvořený");
                return false;
            }
            switch (mess.getType()) {
                case CREATE:
                    if (mess.getObjTo().getActive()) {
                        Controller.errorMessage("Objekt je již vytvořený");
                        return false;
                    }
                    mess.getObjTo().setActive(true);
                    break;
                case ASYN:
                    break;
                case DELETE:
                    mess.getObjTo().setActive(false);
                    break;
                case SYNC:
                    mess.getObjTo().addObjMess(mess.getObjFrom());
                    break;
                default: // REPLY
                    mess.getObjFrom().delObjMess(mess.getObjTo());
                    break;
            }
        }
        return true;
    }

}

