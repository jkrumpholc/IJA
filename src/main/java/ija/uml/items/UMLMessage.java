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
    UMLClass classFrom;
    UMLClass classTo;

    public UMLMessage(MesType type, UMLClass classFrom, UMLClass classTo) {
        this.classFrom = classFrom;
        this.classTo = classTo;
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
        return strType + ": " + classFrom + " -> " + classTo;
    }
}

