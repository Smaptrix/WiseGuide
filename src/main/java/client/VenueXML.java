package client;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class VenueXML {

    public Element root = null;
    public int numberOfPages;


    public VenueXML(Path filePath) {

        try {

            File inputFile = new File(filePath.toString());

            //Parsing XML File
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputFile);
            document.getDocumentElement().normalize();
            root = document.getDocumentElement();

            numberOfPages = root.getElementsByTagName("base:page").getLength();

        } catch (Exception e) {

            System.out.println("Unable to make tree of XML file at path: '" + filePath + "'.");

        }
    }


    public List<String> getPageNames() {

        List<String> returnString = new ArrayList<>();

        for(int i = 0; i < numberOfPages; i++) {
           returnString.add(((this.root.getElementsByTagName("base:page").item(i).getAttributes().getNamedItem("title"))).toString());
        }

        return returnString;

    }


    public Page getPage(String index, String titleOrID) {

        if(!(index.equals("title") || index.equals("ID"))) {
            System.out.println("Error: no such index.");
            return null;
        }

        titleOrID = index + "=\"" + titleOrID + "\"";

        for(int i = 0; i < numberOfPages; i++) {

            String titleToCheck = this.root.getElementsByTagName("base:page").item(i).getAttributes().getNamedItem(index).toString();

            System.out.println(titleToCheck);

            if(titleToCheck.equals(titleOrID)) {
                return new Page(this.root.getElementsByTagName("base:page").item(i));
            }
        }
        if(index.equals("title")){
            System.out.println("Error: no such title.");
        } else {
            System.out.println("Error: no such index.");
        }
        return null;

    }


}
