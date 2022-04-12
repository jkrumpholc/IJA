package ija.uml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class File_manager {

    public StringBuilder open (String filepath) throws FileNotFoundException {
        StringBuilder file_data = new StringBuilder();
        File filename = new File(filepath);
        Scanner reader = new Scanner(filename);
        while (reader.hasNextLine()){
            file_data.append(reader.nextLine());
        }
        return file_data;
    }

    public boolean write (String filepath, StringBuilder data) throws IOException {
        FileWriter writer = new FileWriter(filepath);
        writer.write(String.valueOf(data));
        writer.close();
        return true;
    }
}
