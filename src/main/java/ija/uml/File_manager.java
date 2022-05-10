// autor: Jan Krumpholc             //
// login: xkrump02                  //
// zpracovani souboru               //

package ija.uml;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class File_manager {

    /**
     * @param filepath Path to file to open
     * @return JSON string
     * @throws IOException
     */
    public static String open (File filepath) throws IOException{
        return new String(Files.readAllBytes(Paths.get(String.valueOf(filepath))));
    }

    /**
     * @param filepath Path to file to save
     * @param data Datastring to save
     * @throws IOException
     */
    public static void write (String filepath, String data) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
        writer.write(data);
        writer.close();
    }
}