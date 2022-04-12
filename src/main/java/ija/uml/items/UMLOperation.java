package ija.uml.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UMLOperation extends UMLAttribute{
    UMLOperation umlOperation;
    String name;
    UMLClassifier type;
    List<UMLAttribute> arguments;
    public UMLOperation(String name, UMLClassifier type){
        super(name, type);
        umlOperation = this;
        umlOperation.name = name;
        umlOperation.type = type;
        umlOperation.arguments = new ArrayList<>();
    }
    public static UMLOperation create(String name, UMLClassifier type, UMLAttribute... umlAttributes) {
        UMLOperation tmp = new UMLOperation(name, type);
        tmp.arguments.addAll(Arrays.asList(umlAttributes));
        return tmp;
    }

    public List<UMLAttribute> getArguments() {
        return umlOperation.arguments;
    }
}
