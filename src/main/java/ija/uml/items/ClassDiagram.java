package ija.uml.items;

import java.util.ArrayList;
import java.util.List;

public class ClassDiagram extends Element{
    ClassDiagram diagram;
    String name;
    List<UMLClass> classes;
    List<UMLClassifier> classifiers;
    public ClassDiagram(String name) {
        super(name);
        diagram = this;
        diagram.name = name;
        diagram.classes = new ArrayList<>();
        diagram.classifiers = new ArrayList<>();
    }

    public UMLClass createClass(String name) {
        UMLClass umlClass = new UMLClass(name, true);
        diagram.classes.add(umlClass);
        diagram.classifiers.add(umlClass.umlClassifier);
        return umlClass;
    }

    public UMLClassifier findClassifier(String name) {
        for (UMLClassifier classifier: diagram.classifiers){
            if (classifier.name.equals(name)){
                return classifier;}
        }
        return null;
    }

    public UMLClassifier classifierForName(String name) {
        UMLClassifier classifier = findClassifier(name);
        if (classifier == null) {
            UMLClassifier umlClassifier = UMLClassifier.forName(name);
            diagram.classifiers.add(umlClassifier);
            return umlClassifier;
        }
        else return classifier;
    }
}
