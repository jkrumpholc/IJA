package ija.uml.items;

public class UMLObject extends Element {
    UMLClass umlClass;
    boolean autCreate;

    public UMLObject(String name, UMLClass umlClass, boolean autCreate) {
        super(name);
        this.umlClass = umlClass;
        this.autCreate = autCreate;
    }
    //TODO toString
}
