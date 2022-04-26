package ija.uml.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UMLClass extends UMLClassifier{
    UMLClass umlClass;
    Boolean isAbstract;
    List<UMLAttribute> attribute_list;
    List<UMLOperation> operation_list;
    public UMLClass(String name) {
        super(name);
        umlClass = this;
        umlClass.isAbstract = false;
        umlClass.attribute_list = new ArrayList<>();
    }

    public UMLClass(String name, boolean defined) {
        super(name, defined);
        umlClass = this;
        umlClass.isAbstract = false;
        umlClass.attribute_list = new ArrayList<>();
        umlClass.operation_list = new ArrayList<>();
    }

    public void setAbstract(boolean b) {
        umlClass.isAbstract = b;
    }

    public boolean isAbstract() {
        return umlClass.isAbstract;
    }

    public void addAttribute(UMLAttribute attr) {
        if (!umlClass.attribute_list.contains(attr)){
            attr.position = attribute_list.size();
            umlClass.attribute_list.add(attr);
        }
    }

    public int getAttrPosition(UMLAttribute attr) {
        return attr.position;
    }

    public int moveAttrAtPosition(UMLAttribute attr, int pos) {
        Integer old_pos = attribute_list.size();
        if (!umlClass.attribute_list.contains(attr)){
            return -1;}
        else{
            if (pos < -1){
                return -1;}
            for (UMLAttribute attribute: umlClass.attribute_list){
                if (attribute == attr){
                    old_pos = attribute.position;
                    umlClass.attribute_list.get(attribute.position).position = pos;
                }else if (attribute.position >= pos & attribute.position < old_pos){
                    umlClass.attribute_list.get(attribute.position).position++;
                }
            }
        }
        return 0;
    }

    public List<UMLAttribute> getAttributes() {
        return Collections.unmodifiableList(attribute_list);
    }

    public void delAttributesOperation() {
        attribute_list.clear();
        operation_list.clear();
    }

    public void addOperation(UMLOperation meth) {
        if (!umlClass.operation_list.contains(meth)){
            umlClass.operation_list.add(meth);
        }
    }

    public List<UMLOperation> getOperation() {
        return Collections.unmodifiableList(operation_list);
    }
}
