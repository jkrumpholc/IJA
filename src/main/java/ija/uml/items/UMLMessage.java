// autor: Tereza Buchníčková        //
// login: xbuchn00                  //
//      //

package ija.uml.items;


public class UMLMessage {
    //TODO přidat název

    public enum MesType {
        SYNC,
        ASYN,
        REPLY,
        CREATE,
        DELETE
    }
    MesType type;
    UMLObject objFrom;
    UMLObject objTo;

    public UMLMessage(MesType type, UMLObject umlObject, UMLObject umlObject2) {
        this.type = type;
        this.objFrom = umlObject;
        this.objTo = umlObject2;
    }

    public String toString() {
        String strType;
        switch (type) {
            case SYNC:
                strType = "Synchronní";
                break;
            case ASYN:
                strType = "Asynchronní";
                break;
            case REPLY:
                strType = "Návrat";
                break;
            case CREATE:
                strType = "Vytvoření objektu";
                break;
        default:
                strType = "Zánik objektu";
                break;
        }
        return strType + ": " + objFrom + " -> " + objTo;
    }

    public UMLObject getObjFrom() {
        return objFrom;
    }

    public UMLObject getObjTo() {
        return objTo;
    }

    public MesType getType() {
        return type;
    }
}

