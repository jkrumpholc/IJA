// autor: Tereza Buchníčková        //
// login: xbuchn00                  //
// hlavní konstruktor aplikace      //

package ija.uml;

import java.io.File;
import java.io.IOException;

import com.google.gson.*;
import com.google.gson.stream.MalformedJsonException;
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
import netscape.javascript.JSObject;

import java.util.ArrayList;

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
    public void open() throws IOException, MalformedJsonException {
        String data;
        FileChooser file_chooser = new FileChooser();
        file_chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files", "*.json"));
        file_chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All files", "*.*"));
        File selected_file = file_chooser.showOpenDialog(null);
        if (selected_file != null) {
            data = File_manager.open(selected_file);
            JsonObject jsonObject = new JsonParser().parse(data).getAsJsonObject();
            JsonArray classes_array = (JsonArray) jsonObject.getAsJsonObject("class diagram").get("classes");
            JsonArray relations_array = (JsonArray) jsonObject.getAsJsonObject("class diagram").get("relations");
            JsonObject sequence_diagrams = jsonObject.getAsJsonObject("sequence diagram");
            Gson gson = new Gson();
            for (int i = 0; i < classes_array.size(); i++) {
                UMLClass umlClass = new UMLClass(gson.fromJson((classes_array).get(i), UMLClass.class));
                for (int j = 0;j < umlClass.getAttributes().size(); j++){
                    umlClass.fix_attribute(umlClass.getAttributes().get(j), j);
                }
                for (int j = 0;j < umlClass.getOperations().size(); j++){
                    umlClass.fix_operations(umlClass.getOperation().get(j), j);
                }
                classDiagram.addClass(umlClass);
            }
            for (int i = 0;i < relations_array.size(); i++){
                JsonElement item = (relations_array).get(i);
                UMLRelation umlRelation = new UMLRelation(gson.fromJson(item, UMLRelation.class));
                umlRelation.setClassFrom(ClassDiagram.findClass(((JsonObject) item).getAsJsonObject("classFrom").get("name").getAsString()));
                umlRelation.setClassTo(ClassDiagram.findClass(((JsonObject) item).getAsJsonObject("classTo").get("name").getAsString()));
                classDiagram.addRelation(umlRelation);

            }
            for (int i = 0; i < sequence_diagrams.size(); i++) {
                JsonElement item = (sequence_diagrams).getAsJsonObject(String.valueOf(i));
                SequenceDiagram sequenceDiagram = new SequenceDiagram(gson.fromJson(item,SequenceDiagram.class));
                for (int j = 0; j < sequenceDiagram.getObjects().size(); j++) {
                    sequenceDiagram.fixObjects(sequenceDiagram.getObjects().get(j),j);
                }
                addSeqDiag(sequenceDiagram);
            }
            c_diagram_UI.draw();
        } else {
            System.out.println("Chyba při otevření souboru");
        }
    }

    /**
     * @throws IOException
     */
    @FXML
    public void save_file() throws IOException {
        FileChooser file_chooser = new FileChooser();
        file_chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files", "*.json"));
        file_chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All files", "*.*"));
        File selected_file = file_chooser.showSaveDialog(null);
        if (selected_file != null) {
            Gson gson = new Gson();
            String json_classes = gson.toJson(classDiagram.getClasses());
            String json_relations = gson.toJson(classDiagram.getRelations());
            StringBuilder seq_diagram_string = new StringBuilder(",\"sequence diagram\":{");
            for (int i = 0; i < s_diagrams_array.size(); i++) {
                String id = "\""+i+"\",";
                seq_diagram_string.append(id).append(gson.toJson(s_diagrams_array.get(i))).append(",");
            }
            if (s_diagrams_array.size() > 0){
                seq_diagram_string.deleteCharAt(seq_diagram_string.length()-1);
            }
            seq_diagram_string.append("}");
            String json = "{\"class diagram\":{\"name\": \""+classDiagram.getName()+"\", \"classes\":"+json_classes+",\"relations\":"+json_relations+"}"+seq_diagram_string+"}";
            File_manager.write(selected_file.toString(),json);
        }
    }

    @FXML
    public void addNewSeqDiag() {
        SequenceDiagram s_diagram = new SequenceDiagram("Sequence Diagram", idSeqDiag);
        addSeqDiag(s_diagram);

    }

    @FXML
    public void addSeqDiag(SequenceDiagram sequenceDiagram) {
        s_diagrams_array.add(sequenceDiagram);
        SequenceDiagramUI s_diagram_ui = new SequenceDiagramUI(sequenceDiagram);
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
