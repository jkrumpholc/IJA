// autor: Tereza Buchníčková        //
// login: xbuchn00                  //
//      //

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
}
