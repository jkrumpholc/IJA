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
    public void fixObjects(UMLObject umlObject,int i){
        this.listObject.get(i).umlClass = ClassDiagram.findClass(umlObject.umlClass.name);
    }
}
