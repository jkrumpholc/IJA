// autor: Tereza Buchníčková        //
// login: xbuchn00                  //
// vztahy mezi třídami              //

package ija.uml.items;

public class UMLRelation {
    public enum RelType {
        ASSOC,
        AGGR,
        COMPOS,
        GENER
    }
    RelType type;
    UMLClass classFrom;
    UMLClass classTo;
    public UMLRelation(RelType type, UMLClass classFrom, UMLClass classTo) {
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
    
    public UMLClass getClassFrom() {
            return classFrom;
    }

    public UMLClass getClassTo() {
        return classTo;
    }  
    
    public RelType getType() {
        return type;
    } 
}
