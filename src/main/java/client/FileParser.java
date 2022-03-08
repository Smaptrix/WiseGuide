/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham, Ben Alexander
    Date Created:   27/01/2022
    Last Updated:   10/02/2022
 */

package client;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Scanner;

//After a received file is reconstructed by the client, it will be parsed by this

/**
 * Defunct class - not entirely sure why we made it now lol
 */
public class FileParser {

    private Path filePath;
    private static Charset ENCODING = StandardCharsets.UTF_8;


    //Uses a scanner to read the file line by line
    public void processFile(String FileType) throws IOException {

        try(Scanner scanner = new Scanner(filePath, ENCODING.name())){

            //If the file is a text file we want to print its contents
            if(FileType.equals("Txt")) {
                while (scanner.hasNextLine()) {
                    processTextLine(scanner.nextLine());
                }
            }
        }

    }

    //Uses a second scanner to process the line and print the text
    private void processTextLine(String line) {
        try(Scanner scanner = new Scanner(line)){
            scanner.useDelimiter(" ");
            if(scanner.hasNext()){
                log(scanner.next());
            }
        }

    }

    //Prints whatever we put in to the console (useful for debugging)
    private void log(Object object){
        System.out.println(Objects.toString(object));
    }


}
