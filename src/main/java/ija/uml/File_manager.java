// autor: Jan Krumpholc             //
// login: xkrump02                  //
// zpracovani souboru               //

package ija.uml;

import java.io.*;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class File_manager {

    public static ArrayList<JSONObject> open(File filepath) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(filepath))
            {Object obj = jsonParser.parse(reader);
            ArrayList<JSONObject> UML_Array = new ArrayList<JSONObject>();
            JSONObject Diagrams = (JSONObject) ((JSONObject) obj).get("diagram");
            UML_Array.add(Diagrams);
            JSONObject Classes =(JSONObject) ((JSONObject) obj).get("classes");
            UML_Array.add(Classes);
            JSONObject Classifiers = (JSONObject) ((JSONObject) obj).get("classifiers");
            UML_Array.add(Classifiers);
            JSONObject Attributes = (JSONObject) ((JSONObject) obj).get("attributes");
            UML_Array.add(Attributes);
            JSONObject Operations = (JSONObject) ((JSONObject) obj).get("operations");
            UML_Array.add(Operations);
            return UML_Array;

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
    public static String parseUMLObject(JSONObject UML_data){
        JSONObject diagram_object_list = (JSONObject) UML_data.get("diagrams");
        String data = "";

        return data;
    }

    public boolean write (String filepath, String data) throws IOException {

        return false;
    }
}