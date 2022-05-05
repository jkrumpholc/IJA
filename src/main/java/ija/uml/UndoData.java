// autor: Tereza Buchníčková        //
// login: xbuchn00                  //
//            //

package ija.uml;

import java.util.ArrayList;
import java.util.List;

import ija.uml.items.SequenceDiagram;
import ija.uml.items.UMLClass;
import ija.uml.items.UMLMessage;
import ija.uml.items.UMLObject;
import ija.uml.items.UMLRelation;

public class UndoData {

    static UndoData undoBuffer = null; 

    ArrayList<UMLRelation> prevRels = null;
    ArrayList<UMLMessage> prevMess = null;
    ArrayList<UMLObject> prevObjs = null;
    UMLClass prevClass = null;
    int classPos = 0;
    boolean classDeleted = false;
    SequenceDiagram lastDiag = null;    
    UndoData(List<UMLRelation> prevRels) {
        this.prevRels = new ArrayList<>(prevRels);
    }
    UndoData(SequenceDiagram diagram, List<UMLObject> prevObjs, List<UMLMessage> prevMess) {
        this.lastDiag = diagram;
        if (prevObjs != null) {
            this.prevObjs = new ArrayList<>(prevObjs);
        }
        if (prevMess != null) {
            this.prevMess = new ArrayList<>(prevMess);
        }
    }
    UndoData(UMLClass prevClass, int pos) {
        classPos = pos;
        this.prevClass = new UMLClass(prevClass);
    }

    static void setUndoBuffer(UndoData data) {
        undoBuffer = data;
    } 

    static void clearUndoBuffer() {
        undoBuffer = null;
    } 

    static UndoData getUndoBuffer() {
        return undoBuffer;
    } 
}
