package VenueXMLThings;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


//XML parsing for venue data, constructs a tree with the ability to get pages through id or title.
public class VenueXMLParser {

    public Element root = null;
    public int numberOfPages;

    //constructor, takes filepath of xml file and returns VenueXMLParser class
    public VenueXMLParser(Path filePath) {

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

    //Returns a list of the page names under the root (mainly used for testing)
    public List<String> getPageNames() {

        List<String> returnString = new ArrayList<>();

        for(int i = 0; i < numberOfPages; i++) {
           returnString.add(((this.root.getElementsByTagName("base:page").item(i).getAttributes().getNamedItem("title"))).toString());
        }

        return returnString;

    }

    //Returns a new page based on the title or ID, where there is no page with title or ID, returns null
    public VenuePage getPage(String index, String titleOrID) {

        if(!(index.equals("title") || index.equals("ID"))) {
            System.out.println("Error: no such index.");
            return null;
        }

        titleOrID = index + "=\"" + titleOrID + "\"";

        for(int i = 0; i < numberOfPages; i++) {

            if(this.root.getElementsByTagName("base:page").item(i).getAttributes().getNamedItem(index).toString().equals(titleOrID)) {
                return new VenuePage(this.root.getElementsByTagName("base:page").item(i));
            }

        }

        if(index.equals("title")){
            System.out.println("Error: no such title.");
        } else {
            System.out.println("Error: no such ID.");
        }

        return null;

    }


}
