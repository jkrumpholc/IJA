package ija.uml.items;

public class UMLAttribute extends Element{
    UMLAttribute umlAttribute;
    String name;
    UMLClassifier type;
    Integer position;
    UMLClass.AccessMod mode;

    public UMLAttribute(String name, UMLClassifier type, UMLClass.AccessMod mode) {
        super(name);
        umlAttribute = this;
        umlAttribute.name = name;
        umlAttribute.type = type;
        umlAttribute.position = 0;
        this.mode = mode;
    }

    public UMLClassifier getType() {
        return umlAttribute.type;
    }

    public java.lang.String toString(){
        String s;
        switch (mode) {
            case PUBLIC:
                s = "+";
                break;
            case PROTECTED:
                s = "#";
                break;
            case INTERNAL:
                s = "~";
                break;
            default:
                s = "-";
                break;
        }
        return String.format("%s %s  : %s", s, umlAttribute.name, umlAttribute.type);
    }

    public UMLClass.AccessMod getMode() {
        return mode;
    }
}
