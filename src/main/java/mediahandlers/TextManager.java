package mediahandlers;

//CREATED BY ENTROPY DESIGNS FOR MAPTRIX

import java.io.*;
import java.util.Scanner;

import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.text.Font;

public class TextManager{

    protected int mediaID;
    protected int positionX;
    protected int positionY;
    protected int width;
    protected int height;
    protected String filePath;
    private Font font = new Font("Times New Roman", 12);    //Default font
    private String content = "";
    private Color colour = Color.BLACK; //Default colour
    private Text textArea;
    private boolean underline = false;
    private boolean fromFile = true;
    private boolean loaded = false;

    public TextManager(String fileLocation, int width, int height){
        this.width = width;
        this.height = height;
        this.filePath = fileLocation;

    }

    public String loadTextFromFile(){
        //Read the data from the text file and store in the content attribute.
        try{
            //Tex file we want to read from
            File textFile = new File(filePath);
            //The scanner that reads the file
            Scanner fileReader = new Scanner(textFile);
            //while the file has a next line
            while(fileReader.hasNextLine()) {
                String nextLine = fileReader.nextLine();
                content = content + nextLine + "\n";
            }
        }
        catch (FileNotFoundException e){
            content = "*UNABLE TO IMPORT TEXT, FILE NOT FOUND*";
            System.out.println("FILE NOT FOUND");
            e.printStackTrace();
        }

        //Attach the content to the JPanel displayScene attribute.
        this.textArea = new Text();
        //Set attributes
        textArea.setFont(font);
        textArea.setText(content);
        textArea.setFill(colour);
        textArea.setUnderline(underline);
        textArea.setTextAlignment(TextAlignment.CENTER);
        textArea.setWrappingWidth(width);
    return content;
}

    public Text getTextArea(){
        return this.textArea;
    }

    //Font name as a string of format: "Times New Roman", "Arial", "Helvetica" etc
    public void setFont(String fontName, int size, boolean bold, boolean italics) {
        FontWeight fontWeight = FontWeight.NORMAL;
        FontPosture fontPosture = FontPosture.REGULAR;
        if(bold){
            fontWeight = FontWeight.BOLD;
        }
        if(italics){
            fontPosture = FontPosture.ITALIC;
        }
        this.font = Font.font(fontName, fontWeight, fontPosture, size);
    }

    //Takes a hex rgb/rgba value as a string
    public void setColour(String colourValue){
        this.colour = Color.web(colourValue);
    }

    public void setUnderline(boolean underline){
        this.underline = underline;
    }

    public void setPosition(int x, int y){
        this.positionX = x;
        this.positionY = y;
    }

    public void setSize(int width, int height){
        this.width = width;
        this.height = height;
    }

    public int getMediaID() {
        return mediaID;
    }

    public void setMediaID(int mediaID) {
        this.mediaID = mediaID;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public void setWidth(int width){
        this.width = width;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
