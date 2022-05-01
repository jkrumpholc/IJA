// autor: Tereza Buchníčková        //
// login: xbuchn00                  //
//      //

package ija.uml.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UMLObject extends Element {
    UMLClass umlClass;
    boolean autCreate;
    double x_pos;
    double y_pos;
    boolean active = false;
    boolean rectangle = false;
    ArrayList<UMLObject> listObjMess = new ArrayList<UMLObject>();
    //seznam objektu, ktere poslaly tomuto objektu zpravu

    public UMLObject(String name, UMLClass umlClass, boolean autCreate) {
        super(name);
        this.umlClass = umlClass;
        this.autCreate = autCreate;
    }
    
    public java.lang.String toString() {
        if (autCreate) {
            return String.format("%s:%s (auto)",this.name,this.umlClass.name);
        }
        else {
            return String.format("%s:%s",this.name,this.umlClass.name);
        }
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

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getActive() {
        return this.active;
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
}
