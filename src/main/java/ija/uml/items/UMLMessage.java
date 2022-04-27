package ija.uml.items;


public class UMLMessage extends Element {

    public enum MesType {
        SAYN,
        ASYN,
        REPLY,
        CREATE,
        DELETE
    }
    MesType type;
    UMLClass classFrom;
    UMLClass classTo;

    public UMLMessage(String name, UMLClass classFrom, UMLClass classTo) {
        super(name);
        this.classFrom = classFrom;
        this.classTo = classTo;
    }
}

