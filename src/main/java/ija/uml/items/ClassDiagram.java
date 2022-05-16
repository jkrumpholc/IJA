package ija.uml.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClassDiagram extends Element{
    static ClassDiagram diagram;
    String name;
    ArrayList<UMLClass> classes;
    List<UMLClassifier> classifiers;
    List<UMLRelation> relations;
    public ClassDiagram(String name) {
        super(name);
        diagram = this;
        diagram.name = name;
        diagram.classes = new ArrayList<>();
        diagram.relations = new ArrayList<>();
        diagram.classifiers = new ArrayList<>();
    }

    public UMLClass createClass(String name) {
        UMLClass umlClass = new UMLClass(name, true);
        diagram.classes.add(umlClass);
        diagram.classifiers.add(umlClass.umlClassifier);
        return umlClass;
    }

    public UMLClass createClassAt(int pos, String name) {
        UMLClass umlClass = new UMLClass(name, true);
        diagram.classes.add(pos, umlClass);
        diagram.classifiers.add(umlClass.umlClassifier);
        return umlClass;
    }

    public void addClass(UMLClass umlClass){
        diagram.classes.add(umlClass);
        diagram.classifiers.add(umlClass.umlClassifier);}

    public void addRelation(UMLRelation rel) {
        diagram.relations.add(rel);
    }

    public void clearRelations() {
        diagram.relations.clear();
    }

    public void deleteClass(String name) {
        UMLClass deleteClass = findClass(name);
        classes.remove(deleteClass);
    }

    public List<UMLRelation> getRelations() {
        return Collections.unmodifiableList(relations);
    }

    public List<UMLClass> getClasses() {
        return Collections.unmodifiableList(classes);
    }

    public UMLClassifier findClassifier(String name) {
        for (UMLClassifier classifier: diagram.classifiers){
            if (classifier.name.equals(name)){
                return classifier;}
        }
        return null;
    }

    public static UMLClass findClass(String name) {
        for (UMLClass umlClass: diagram.classes){
            if (umlClass.name.equals(name)){
                return umlClass;}
        }
        return null;
    }

    public UMLClass getClassAt(int pos) {
        if (pos < 0 || pos >= classes.size()) {
            return null;
        }
        return classes.get(pos);
    }

    public int findClassPos(String name) {
        int index = 0;
        for (UMLClass umlClass: diagram.classes){
            if (umlClass.name.equals(name)){
                return index;
            }
            index++;
        }
        return -1;
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
