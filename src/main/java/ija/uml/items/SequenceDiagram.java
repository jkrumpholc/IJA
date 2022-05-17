// autor: Tereza Buchníčková        //
// login: xbuchn00                  //
// sekvenční diagram                //

package ija.uml.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SequenceDiagram extends Element {

    ArrayList<UMLObject> listObject = new ArrayList<UMLObject>();
    ArrayList<UMLMessage> listMessages = new ArrayList<UMLMessage>();
    int id;

    public SequenceDiagram(String name, int id) {
        super(name);
        this.id = id;
    }

    public SequenceDiagram(SequenceDiagram source){
        super(source.name);
        this.id = source.id;
        this.listObject = source.listObject;
        this.listMessages = source.listMessages;
    }

    public void addObject(UMLObject obj) {
        listObject.add(obj);
    } 

    public void addMessage(UMLMessage mes) {
        listMessages.add(mes);
    } 

    public java.util.List<UMLMessage> getMessages() {
        return Collections.unmodifiableList(listMessages);
    }

    public List<UMLObject> getObjects() {
        return Collections.unmodifiableList(listObject);
    }

    public void clearMessages() {
        this.listMessages.clear();
    }

    public void clearObjects() {
        this.listObject.clear();
    }

    public UMLObject findObject(String name) {
        for (UMLObject umlObj: this.listObject){
            if (umlObj.name.equals(name)){
                return umlObj;
            }
        }
        return null;
    }
    public boolean fixObjects(UMLObject umlObject,int i){
        UMLClass umlClass = ClassDiagram.findClass(umlObject.umlClass.name);
        if (umlClass == null){
            System.err.println("Sekvenční diagram obsahuje třídu, která není v třídním diagramu");
            this.listObject.remove(i);
            return false;
        }
        this.listObject.get(i).umlClass = umlClass;
        return true;
    }
    public void fixMessages(){
        ArrayList<UMLMessage> newlistMessages = new ArrayList<>(listMessages);
        for (int i = 0; i < listMessages.size(); i++) {
            UMLObject objFrom = findObject(listMessages.get(i).objFrom.name);
            UMLObject objTo = findObject(listMessages.get(i).objTo.name);
            if (objFrom == null | objTo == null)
                newlistMessages.remove(i);
        }
        this.listMessages = newlistMessages;
    }
}
