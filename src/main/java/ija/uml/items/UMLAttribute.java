package ija.uml.items;

public class UMLAttribute extends Element{
    UMLAttribute umlAttribute;
    String name;
    UMLClassifier type;
    Integer position;
    public UMLAttribute(String name, UMLClassifier type) {
        super(name);
        umlAttribute = this;
        umlAttribute.name = name;
        umlAttribute.type = type;
        umlAttribute.position = 0;
    }

    public UMLClassifier getType() {
        return umlAttribute.type;
    }

    public java.lang.String toString(){
        return String.format("%s:%s",umlAttribute.name,umlAttribute.type);
    }
}
