// autor: Tereza Buchníčková         //
// login: xbuchn00                   //
// zprávy mezi objekty sek. diagramu //

package ija.uml.items;


public class UMLMessage {

    public enum MesType {
        SYNC,
        ASYN,
        REPLY,
        CREATE,
        DELETE
    }
    MesType type;
    String method;
    UMLObject objFrom;
    UMLObject objTo;
    boolean deactive = false;

    public UMLMessage(MesType type, String method, UMLObject umlObject, UMLObject umlObject2) {
        this.type = type;
        this.method = method;
        this.objFrom = umlObject;
        this.objTo = umlObject2;
    }

    public String toString() {
        String strType;
        switch (type) {
            case SYNC:
                strType = method + "(sync)";
                break;
            case ASYN:
                strType = method + "(async)";
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

    public String getMethod() {
        return method;
    }

    public void setDeactive(boolean deactive) {
        this.deactive = deactive;
    }

    public boolean getDeactive() {
        return this.deactive;
    }
}

