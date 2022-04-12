package ija.uml.items;

public class UMLClassifier extends Element{
    UMLClassifier umlClassifier;
    String name;
    Boolean isUserDefined;
    public UMLClassifier(String name, Boolean isUserDefined) {
        super(name);
        umlClassifier = this;
        umlClassifier.name = name;
        umlClassifier.isUserDefined = isUserDefined;
    }
    public UMLClassifier(String name){
        super(name);
        umlClassifier = this;
        umlClassifier.name = name;
        umlClassifier.isUserDefined = true;
    }


    public static UMLClassifier forName(String name) {
        return new UMLClassifier(name,false);
    }

    public boolean isUserDefined() {
        return umlClassifier.isUserDefined;
    }
    @Override
    public String toString(){
        return String.format("%s(%s)",umlClassifier.name,umlClassifier.isUserDefined);
    }
}
