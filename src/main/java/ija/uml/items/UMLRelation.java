package ija.uml.items;

public class UMLRelation {
    public enum RelType {
        ASSOC,
        AGGR,
        COMPOS,
        GENER
    }
    RelType type;
    String classFrom;
    String classTo;
    public UMLRelation(RelType type, String classFrom, String classTo) {
        this.type = type;
        this.classFrom = classFrom;
        this.classTo = classTo;
    }

    public String toString() {
        String strType;
        switch (type) {
            case ASSOC:
                strType = "Asociace";
                break;
            case AGGR:
                strType = "Agregace";
                break;
            case COMPOS:
                strType = "Kompozice";
                break;
            default:
                strType = "Generalizace";
                break;

        }
        return strType + ": " + classFrom + " -> " + classTo;
    }
}
