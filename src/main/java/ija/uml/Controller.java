// autor: Tereza Buchníčková        //
// login: xbuchn00                  //
// hlavní konstruktor aplikace      //

package ija.uml;

import java.io.File;
import java.io.IOException;

import ija.uml.items.ClassDiagram;
import ija.uml.items.SequenceDiagram;
import ija.uml.items.UMLAttribute;
import ija.uml.items.UMLClass;
import ija.uml.items.UMLClassifier;
import ija.uml.items.UMLMessage;
import ija.uml.items.UMLObject;
import ija.uml.items.UMLOperation;
import ija.uml.items.UMLRelation;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Controller implements EventHandler<ActionEvent> {

    ClassDiagram classDiagram;
    ArrayList<SequenceDiagramUI> s_diagrams_array_ui; 
    ArrayList<SequenceDiagram> s_diagrams_array; 
    int idSeqDiag = 1;
    int activeDiag = 0;
    ClassDiagramUI c_diagram_UI;
    ToggleGroup buttonGroup;
    
    @FXML
    private ToggleButton c_diagram_button;
    @FXML
    private AnchorPane center;
    @FXML
    private VBox left_menu;
    @FXML
    private MenuItem addSeqDiag, addClassWindow, addRelWindow, addObjWindow, addMessWindow;

    
    @FXML
    public void initialize() {
        classDiagram = new ClassDiagram("New");
        s_diagrams_array_ui = new ArrayList<SequenceDiagramUI>();
        s_diagrams_array = new ArrayList<SequenceDiagram>();
        c_diagram_UI = new ClassDiagramUI(classDiagram); 
        center.getChildren().add(c_diagram_UI); 
        buttonGroup = new ToggleGroup();
        c_diagram_button.setToggleGroup(buttonGroup);
        c_diagram_button.setSelected(true);
        activeDiag = 0;
        addObjWindow.setDisable(true);
        addMessWindow.setDisable(true);
    }

    @FXML
    public void addClassWindow() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("add_edit_ui.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Přidat třídu");
                stage.setScene(new Scene(loader.load(), 400, 400));
                stage.initModality(Modality.APPLICATION_MODAL);
                AddEditUI controller = loader.getController();
                controller.init(classDiagram, false, null);
                stage.showAndWait();
                c_diagram_UI.draw(); //vykresledni diagramu trid
            }
            catch (IOException e) {
                e.printStackTrace();
            }
    }

    @FXML
    public void addRelWindow() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("add_mess_rel_ui.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Vztahy");
                stage.setScene(new Scene(loader.load(), 400, 400));
                stage.initModality(Modality.APPLICATION_MODAL);
                AddMessRelUI controller = loader.getController();
                var tempUndoData = new UndoData(classDiagram.getRelations()); 
                controller.init(null, classDiagram, true);
                stage.showAndWait();
                if (controller.getDataSaved()) {
                    c_diagram_UI.draw(); //vykresledni diagramu trid
                    UndoData.setUndoBuffer(tempUndoData); 
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
    }

    @FXML
    public void addObjWindow() {
            try {
                if (activeDiag == 0) {
                    return;
                }
                int diagIndex = activeDiag - 1;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("add_obj_ui.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Přidání objektu");
                stage.setScene(new Scene(loader.load(), 400, 400));
                stage.initModality(Modality.APPLICATION_MODAL);
                AddObjUI controller = loader.getController();
                var seqDiag = s_diagrams_array.get(diagIndex);
                var tempUndoData = new UndoData(seqDiag, seqDiag.getObjects(), null);
                controller.init(seqDiag, classDiagram);
                stage.showAndWait();
                if (controller.getDataSaved()) {
                    s_diagrams_array_ui.get(diagIndex).draw(); //vykresledni sekvencniho diagramu
                    UndoData.setUndoBuffer(tempUndoData);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
    }

    @FXML
    public void addMessWindow() {
            try {
                if (activeDiag == 0) {
                    return;
                }
                int diagIndex = activeDiag - 1;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("add_mess_rel_ui.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Přidání zprávu");
                stage.setScene(new Scene(loader.load(), 400, 400));
                stage.initModality(Modality.APPLICATION_MODAL);
                AddMessRelUI controller = loader.getController();
                var seqDiag = s_diagrams_array.get(diagIndex);
                var tempUndoData = new UndoData(seqDiag, null, seqDiag.getMessages());
                controller.init(seqDiag, classDiagram, false);
                stage.showAndWait();
                if (controller.getDataSaved()) {
                    s_diagrams_array_ui.get(diagIndex).draw(); 
                    UndoData.setUndoBuffer(tempUndoData);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
    }

    @FXML
    public void open() {
        ArrayList data;
        FileChooser file_chooser = new FileChooser();
        File selected_file = file_chooser.showOpenDialog(null);
        if (selected_file != null) {
            data = File_manager.open(selected_file);
            JSONObject diagram = (JSONObject) data.get(0);
            String diagram_name = diagram.get("name").toString();
            int class_num = ((JSONArray) diagram.get("classes")).size();
            int classifiers_num = ((JSONArray) diagram.get("classifiers")).size();
            JSONObject classes = (JSONObject) data.get(1);
            JSONObject classifiers = (JSONObject) data.get(2);
            JSONObject attributes = (JSONObject) data.get(3);
            JSONObject operations = (JSONObject) data.get(4);
            ArrayList<UMLClass> classes_list = new ArrayList<UMLClass>();
            ArrayList<UMLClassifier> classifiers_list = new ArrayList<UMLClassifier>();
            ArrayList<UMLAttribute> attribute_list = new ArrayList<UMLAttribute>();
            ArrayList<UMLOperation> operation_list = new ArrayList<UMLOperation>();
            ArrayList<UMLRelation> relation_list = new ArrayList<UMLRelation>();

            if (class_num != classes.size()){
                System.out.println("Inconsistency");}
            classifiers.keySet().forEach(classifier_name ->{
                JSONObject classifier_object = (JSONObject) classifiers.get(classifier_name);
                UMLClassifier classifier = new UMLClassifier((String) classifier_name);
                classifiers_list.add(classifier);
            });
            attributes.keySet().forEach(attribute_name ->{
                JSONObject attribute_object = (JSONObject) attributes.get(attribute_name);
                //UMLAttribute attribute = new UMLAttribute((String) attribute_name);

            });
            classes.keySet().forEach(class_name ->{
                JSONObject class_object = (JSONObject) classes.get(class_name);
                String UML_class_diagram = class_object.get("diagram").toString();
                JSONArray UML_class_attributes = (JSONArray) class_object.get("attributes");
                if (!Objects.equals(diagram_name, UML_class_diagram)){
                    System.out.println("Inconsistency");
                }

            });
            //for (JSONObject Class: classes.values()){

            //}
        //TODO změnit cestu
        UMLClass cl = classDiagram.createClass("Cl1");
        cl.addAttribute(new UMLAttribute("attr1", new UMLClassifier("int"), UMLClass.AccessMod.PUBLIC));
        cl.addAttribute(new UMLAttribute("attr2", new UMLClassifier("int"), UMLClass.AccessMod.PUBLIC));
        cl.addOperation(new UMLOperation("meth1", new UMLClassifier("int"), UMLClass.AccessMod.PUBLIC));
        cl.addOperation(new UMLOperation("meth2", new UMLClassifier("String"), UMLClass.AccessMod.PUBLIC));
        UMLClass c2 = classDiagram.createClass("Class2");
        c2.addAttribute(new UMLAttribute("attr3", new UMLClassifier("int"), UMLClass.AccessMod.PUBLIC));
        c2.addAttribute(new UMLAttribute("attr4", new UMLClassifier("int"), UMLClass.AccessMod.PUBLIC));
        c2.addOperation(new UMLOperation("meth3", new UMLClassifier("int"), UMLClass.AccessMod.PUBLIC));
        c2.addOperation(new UMLOperation("meth4", new UMLClassifier("String"), UMLClass.AccessMod.PUBLIC));
        classDiagram.addRelation(new UMLRelation(UMLRelation.RelType.AGGR, cl, cl));
        classDiagram.addRelation(new UMLRelation(UMLRelation.RelType.GENER, cl, c2));
        c_diagram_UI.draw();
        } else {
            System.out.println("Chyba při otevření souboru");
        }
    }

    @FXML
    public void addSeqDiag() {
        SequenceDiagram s_diagram = new SequenceDiagram("Sequence Diagram", idSeqDiag);
        s_diagrams_array.add(s_diagram);
        SequenceDiagramUI s_diagram_ui = new SequenceDiagramUI(s_diagram);
        String id = Integer.toString(idSeqDiag);
        s_diagram_ui.setId(id);
        s_diagrams_array_ui.add(s_diagram_ui);
        center.getChildren().add(s_diagram_ui);
        ToggleButton sd_button = new ToggleButton();
        sd_button.setText("Sekvenční diagram " + id);
        sd_button.setPrefWidth(200);
        sd_button.setOnAction(this); 
        sd_button.setId(id);
        sd_button.setToggleGroup(buttonGroup);
        sd_button.setSelected(true);
        left_menu.getChildren().add(sd_button);
        activeDiag = idSeqDiag;
        idSeqDiag++; 
        //znepristupnenii tlacitek pro diagram trid
        addClassWindow.setDisable(true);
        addRelWindow.setDisable(true);
        addObjWindow.setDisable(false);
        addMessWindow.setDisable(false);

    }

    @FXML
    public void undo() {

        var undoData = UndoData.getUndoBuffer();

        if (undoData == null) {
            return;
        }
        if (undoData.prevRels != null) {
            classDiagram.clearRelations();
            for (var rel : undoData.prevRels) {
                classDiagram.addRelation(rel);
            }
            c_diagram_UI.draw();
        }
        if (undoData.prevClass != null) {
            var cl = classDiagram.getClassAt(undoData.classPos);
            if (cl != null) {
                cl.rename(undoData.prevClass.getName());
                for (UMLAttribute attr : undoData.prevClass.getAttributes()) {
                    cl.addAttribute(attr);
                }
                for (UMLOperation meth : undoData.prevClass.getOperation()) {
                    cl.addOperation(meth);
                }
            }
            c_diagram_UI.draw();
        }
        if (undoData.prevMess != null) {
            undoData.lastDiag.clearMessages();
            for (var mess : undoData.prevMess) {
                undoData.lastDiag.addMessage(mess);
            }
        }
        if (undoData.prevObjs != null) {
            undoData.lastDiag.clearObjects();
            for (var obj : undoData.prevObjs) {
                undoData.lastDiag.addObject(obj);
            }
        }

        UndoData.clearUndoBuffer();
    }
    
    @Override
    public void handle(ActionEvent event) {
        if(event.getSource() == c_diagram_button) {
            c_diagram_UI.toFront();
            c_diagram_UI.draw();
            activeDiag = 0;
            addObjWindow.setDisable(true);
            addMessWindow.setDisable(true);
            addClassWindow.setDisable(false);
            addRelWindow.setDisable(false);
        }
        else {
            ToggleButton sd_button = (ToggleButton) event.getTarget();
            for (SequenceDiagramUI sd: s_diagrams_array_ui) {
                if (sd.getId() == sd_button.getId()) {
                    sd.toFront();
                    sd.draw();
                    activeDiag = Integer.parseInt(sd.getId());
                }
                addClassWindow.setDisable(true);
                addRelWindow.setDisable(true);
                addObjWindow.setDisable(false);
                addMessWindow.setDisable(false);
            }

        }
    }

    public static void errorMessage(String text) {
        Alert errorAlert = new Alert(AlertType.ERROR);
        errorAlert.setHeaderText(text);
        errorAlert.showAndWait();
    }
}
