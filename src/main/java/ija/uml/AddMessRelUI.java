// autor: Tereza Buchníčková             //
// login: xbuchn00                       //
// kontroler okna pro přidání vztahu     //
// mezi třídami nebo zprývy mezi objekty //

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
import javafx.scene.control.CheckBox;
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
    boolean dataSaved = false;

    @FXML
    private Button save;
    @FXML 
    private ChoiceBox<String> type, class_from, class_to, method;
    @FXML
    private ListView<String> list;
    @FXML
    private CheckBox deact1, deact2;
    @FXML
    private Label label_from, label_to, method_lable;

    
    public void init(SequenceDiagram sDiagram, ClassDiagram cDiagram, boolean isRelMode) {
        this.isRelMode = isRelMode;
        sequenceDiagram = sDiagram;
        classDiagram = cDiagram;

        if(isRelMode){
            method.setVisible(false);
            method_lable.setVisible(false);
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
            type.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    int index = new_val.intValue();
                    if (index == 2) {
                        deact1.setVisible(true);
                        deact2.setVisible(true);
                    } else {
                        deact1.setVisible(false);
                        deact2.setVisible(false);
                    }
                    method.setVisible(index < 2 ? true : false);
                    method_lable.setVisible(index < 2 ? true : false);
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
        dataSaved = true;
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
            String meth = method.getValue(); //vybrana metoda
            if (obf == null || obt == null || obf.isEmpty() || obt.isEmpty()) {
                Controller.errorMessage("Vyplňte zdrojový a cílový objekt");
                return;
            }
            if ((type == MesType.SYNC || type == MesType.ASYN) && (meth == null || meth.isEmpty())) {
                Controller.errorMessage("Vyplňte metodu");
                return;
            }
            if ((type != MesType.SYNC && type != MesType.ASYN)) {
                meth = "";
            }
            UMLObject objFrom = sequenceDiagram.findObject(obf);
            UMLObject objTo = sequenceDiagram.findObject(obt);
            UMLMessage mess = new UMLMessage(type, meth, objFrom, objTo); 
            messages.add(mess);
            if (!checkMessages()) {
                messages.remove(mess);
                return;
            }
            list.getItems().add(mess.toString()); //pridani zprav do seznamu
            objFrom.setDeactive(deact1.isSelected());
            objTo.setDeactive(deact2.isSelected());
            //TODO aby checkboxy už nebyly checked
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
                object.setVisibleObj(true);
            }
            else {
                object.setVisibleObj(false);
            }
        }
        for (UMLMessage mess : messages) {
            // TODO doplnit další kontroly
            if (!mess.getObjFrom().getVisible()) {
                Controller.errorMessage("Objekt není vytvořený");
                return false;
            }
            switch (mess.getType()) {
                case CREATE:
                    if (mess.getObjTo().getVisible()) {
                        Controller.errorMessage("Objekt je již vytvořený");
                        return false;
                    }
                    mess.getObjTo().setVisibleObj(true);
                    break;
                case ASYN:
                    break;
                case DELETE:
                    mess.getObjTo().setVisibleObj(false);
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

    boolean getDataSaved() {
        return dataSaved;
    }
}

