package zad1;

import java.nio.file.*;

public class Futil {

    public static void processDir(String input_directory_str, String output_file_str){

        Path output_file_path = Paths.get(output_file_str);
        Path starting_directory = Paths.get(input_directory_str);

        try{
            FileVisitor visitor = new FileVisitor(output_file_path);
            Files.walkFileTree(starting_directory, visitor);
        }catch(Exception ignored){}

    }

}