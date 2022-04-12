package ija.uml.items;

public class Element {
    Element element;
    String name;
    public Element(String name){
        element = this;
        element.name = name;
    }

    public String getName(){
        return element.name;
    }

    public void rename(String newName){
        element.name = newName;
    }
}
