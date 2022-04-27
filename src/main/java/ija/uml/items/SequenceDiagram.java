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

    public SequenceDiagram(String name) {
        super(name);
    }

    public UMLObject createObject(String name) {
        //UMLObject umlObject = new UMLObject(name);
        this.listObject.add(umlObject);
        return umlObject;
    }

    /* public void addRelation(UMLRelation rel) {
        diagram.relations.add(rel);
    }
 */

    public java.util.List<UMLMessage> getMessages() {
        return Collections.unmodifiableList(listMessages);
    }

    public List<UMLObject> getObjects() {
        return Collections.unmodifiableList(listObject);
    }
}
