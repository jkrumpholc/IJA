package ija.uml.items;

public class UMLAttribute extends Element{
    UMLClassifier type;
    Integer position;
    UMLClass.AccessMod mode;

    public UMLAttribute(String name, UMLClassifier type, UMLClass.AccessMod mode) {
        super(name);
        this.type = type;
        this.position = 0;
        this.mode = mode;
    }

    public UMLAttribute(UMLAttribute source){
        super(source.name);
        this.position = source.position;
        this.mode = source.mode;
        this.type = new UMLClassifier(source.type.name, source.type.isUserDefined);
    }

    public UMLClassifier getType() {
        return this.type;
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
        return String.format("%s %s : %s", s, name, type);
    }

    public UMLClass.AccessMod getMode() {
        return mode;
    }
}
