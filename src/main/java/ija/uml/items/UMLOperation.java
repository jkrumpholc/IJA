package ija.uml.items;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UMLOperation extends UMLAttribute{
    String arguments = "";
    private boolean overrriden;
 
    public UMLOperation(String nameWithArgs, UMLClassifier type, UMLClass.AccessMod mode) {
        super("", type, mode);
        Pattern pattern = Pattern.compile("(\\w+)\\s*\\((.*)\\)", Pattern.CASE_INSENSITIVE);
        Matcher m = pattern.matcher(nameWithArgs);
        if (m.find()) {
            this.name = m.group(1);
            this.arguments = m.group(2).trim();
        }
        overrriden = false;
    }

    public UMLOperation(UMLOperation source){
        super(source.name, new UMLClassifier(source.type.name,source.type.isUserDefined),source.mode);
        this.arguments = source.arguments;
        this.overrriden = source.overrriden;
        this.mode = source.mode;

    }
    // public static UMLOperation create(String name, UMLClassifier type, UMLClass.AccessMod mode, UMLAttribute... umlAttributes) {
    //     UMLOperation tmp = new UMLOperation(name, type, mode);
    //     tmp.arguments.addAll(Arrays.asList(umlAttributes));
    //     return tmp;
    // }

    public void setOverrirde(boolean over) {
        overrriden = over;
    }

    public boolean getOverrirde() {
        return overrriden;
    }

    public String getArguments() {
        return arguments;
    }

    public String toString() {
        String ret = super.toString();
        return ret.replace(" :", "(" + arguments + ") :");
    }
}
