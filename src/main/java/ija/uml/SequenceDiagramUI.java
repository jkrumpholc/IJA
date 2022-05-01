// autor: Tereza Buchníčková        //
// login: xbuchn00                  //
//      //

package ija.uml;

import java.io.IOException;

import ija.uml.items.SequenceDiagram;
import ija.uml.items.UMLMessage;
import ija.uml.items.UMLObject;
import ija.uml.items.UMLMessage.MesType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class SequenceDiagramUI extends ScrollPane {

    SequenceDiagram seqDiagram;

    double x_pos_obj = 10; 
    double end_position = 5; 
    double x_space = 120;
    int line_start_position = 50;
    int line_length = 20;
    double obj_end = 45;


    @FXML
    private Pane center_pane;
    
    public SequenceDiagramUI(SequenceDiagram seqDiagram) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("center_pane_ui.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.seqDiagram = seqDiagram; 

    }

    public void draw() {
        center_pane.getChildren().clear();
        x_pos_obj = 10; 
        end_position = 5; 
        obj_end = 45;

        for (UMLObject object : seqDiagram.getObjects()) {
            
            object.setRectangle(false);
            if(object.getAutCreate()) {
                object.setActive(true);
                drawObj(object);
            }
            else {
                object.setActive(false);
            }
        } 
        end_position = obj_end + line_length; 
        obj_end = end_position + 40;

        for (UMLMessage mess : seqDiagram.getMessages()) {
            drawMess(mess);
            for (UMLObject object : seqDiagram.getObjects()) {
                if(object != mess.getObjFrom() && object != mess.getObjTo()) {
                    double timeline = object.getXPosition() + line_start_position;
                    if(object.getActive()){
                        drawTimeline(timeline, end_position - 60, timeline, end_position);
                        if(object.getRectangle()){
                            Rectangle rectangle = new Rectangle(timeline - 3, end_position - 60, 6, 60);
                            center_pane.getChildren().add(rectangle);
                        }
                    }
                }
            }
        } 
    }

    private void drawObj(UMLObject object) {
        TextArea umlObject = new TextArea(object.toString());
        umlObject.setEditable(false);
        umlObject.setPrefSize(100, 20);
        object.setPosition(x_pos_obj, end_position);
        umlObject.setLayoutX(x_pos_obj);
        umlObject.setLayoutY(end_position);
        center_pane.getChildren().add(umlObject);

        drawTimeline(x_pos_obj + line_start_position, obj_end, x_pos_obj + line_start_position, obj_end + line_length);
        x_pos_obj+= x_space;
    }

    private void drawTimeline(double x1, double y1, double x2, double y2){
        Line line = new Line(x1, y1, x2, y2);
        line.getStrokeDashArray().addAll(2d);
        center_pane.getChildren().add(line);
    }

    private void drawMess(UMLMessage message) {
        MesType type = message.getType();
        double timeline_from = message.getObjFrom().getXPosition() + line_start_position;
        double timeline_to = message.getObjTo().getXPosition() + line_start_position;
        if (type == MesType.CREATE) {
            UMLObject objTo = message.getObjTo();
            drawObj(objTo);
            drawLine(message); 
            Rectangle rectangle = new Rectangle(timeline_from - 3, end_position, 6, 40);
            center_pane.getChildren().add(rectangle);
            drawTimeline(timeline_from, end_position + 40, timeline_from, end_position + 60);
            message.getObjTo().setActive(true);
        }
        if (type == MesType.ASYN) {
            drawLine(message);
            Rectangle rectangle = new Rectangle(timeline_to - 3, end_position, 6, 40);
            center_pane.getChildren().add(rectangle);
            drawTimeline(timeline_to, end_position + 40, timeline_to, end_position + 60);
            drawTimeline(timeline_from, end_position, timeline_from, end_position + 60);
        }
        if (type == MesType.DELETE) {
            drawLine(message);
            Rectangle rectangle = new Rectangle(timeline_to - 3, end_position, 6, 40);
            center_pane.getChildren().add(rectangle);
            drawTimeline(timeline_to, end_position + 40, timeline_to, end_position + 50);
            drawCross(timeline_to, end_position + 50);
            drawTimeline(timeline_from, end_position, timeline_from, end_position + 60);
            message.getObjTo().setActive(false);
        }
        if (type == MesType.SYNC) {
            drawLine(message);
            Rectangle rectangle1 = new Rectangle(timeline_to - 3, end_position, 6, 60);
            center_pane.getChildren().add(rectangle1);
            Rectangle rectangle2 = new Rectangle(timeline_from - 3, end_position, 6, 60);
            center_pane.getChildren().add(rectangle2);
            message.getObjFrom().setRectangle(true);
            message.getObjTo().setRectangle(true);
            message.getObjTo().addObjMess(message.getObjFrom());
        }
        if (type == MesType.REPLY) {
            drawLine(message);
            drawTimeline(timeline_to, end_position, timeline_to, end_position + 60);
            drawTimeline(timeline_from, end_position, timeline_from, end_position + 60);
            message.getObjFrom().setRectangle(false);
            message.getObjTo().setRectangle(false);
            message.getObjFrom().delObjMess(message.getObjTo());
        }
        end_position += 60;
    }

    private void drawCross(double x, double y){
        Line line1 = new Line(x - 10, y + 10, x + 10, y - 10);
        center_pane.getChildren().add(line1);
        Line line2 = new Line(x + 10, y + 10, x - 10, y - 10);
        center_pane.getChildren().add(line2);
    }

    private void drawLine(UMLMessage message) {
        MesType type = message.getType();
        UMLObject objFrom = message.getObjFrom();
        double x1 = objFrom.getXPosition();
        UMLObject objTo = message.getObjTo();
        double x2 = objTo.getXPosition();

        double line_end_x = x2 + line_start_position;
        Line mess = new Line(x1 + line_start_position, end_position, line_end_x, end_position);
        Label name = new Label("text");
        name.setLayoutX(x1 + line_start_position + 40);
        name.setLayoutY(end_position);
        if (type == MesType.CREATE || type == MesType.REPLY) {
            mess.getStrokeDashArray().addAll(2d);
        } 
        center_pane.getChildren().add(mess);   
        center_pane.getChildren().add(name);
        if (x1 < x2) {
            Line arrowLine1 = new Line(x2 + line_start_position - 7, end_position - 5, x2 + line_start_position, end_position);
            center_pane.getChildren().add(arrowLine1);
            Line arrowLine2 = new Line(x2 + line_start_position - 7, end_position + 5, x2 + line_start_position, end_position);
            center_pane.getChildren().add(arrowLine2);
        } else {
            Line arrowLine1 = new Line(x2 + line_start_position + 7, end_position - 5, x2 + line_start_position, end_position);
            center_pane.getChildren().add(arrowLine1);
            Line arrowLine2 = new Line(x2 + line_start_position + 7, end_position + 5, x2 + line_start_position, end_position);
            center_pane.getChildren().add(arrowLine2);
        }
        
    }

}

//TODO zkontrolovat, že má na co odpovědět
