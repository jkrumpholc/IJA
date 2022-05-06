// autor: Tereza Buchníčková        //
// login: xbuchn00                  //
// objekty sekvenčního diagramu     //

package ija.uml.items;

import java.util.ArrayList;

public class UMLObject extends Element {
    UMLClass umlClass;
    boolean autCreate;
    double x_pos;
    double y_pos;
    boolean visible = false;
    boolean rectangle = false;
    ArrayList<UMLObject> listObjMess = new ArrayList<UMLObject>();
    //seznam objektu, ktere poslaly tomuto objektu zpravu

    public UMLObject(String name, UMLClass umlClass, boolean autCreate) {
        super(name);
        this.umlClass = umlClass;
        this.autCreate = autCreate;
    }
    
    public java.lang.String toString() {
        return String.format("%s : %s",this.name,this.umlClass.name);
    }

    public boolean getAutCreate() {
        return this.autCreate;
    }

    public void setPosition(double x_pos, double y_pos) {
        this.x_pos = x_pos;
        this.y_pos = y_pos;
    }

    public double getXPosition() {
        return x_pos;
    }

    public double getYPosition() {
        return y_pos;
    }

    public void setVisibleObj(boolean visible) {
        this.visible = visible;
    }

    public boolean getVisible() {
        return this.visible;
    }

    public void setRectangle(boolean rectangle) {
        this.rectangle = rectangle;
    }

    public boolean getRectangle() {
        return this.rectangle;
    }

    public boolean isObjMess(UMLObject object) {
        return listObjMess.contains(object);
    }

    public void addObjMess(UMLObject obj){
        this.listObjMess.add(obj);
    }

    public void delObjMess(UMLObject obj){
        this.listObjMess.remove(obj);
    }

    public UMLClass getUMLClass() {
        return this.umlClass;
    }
}
