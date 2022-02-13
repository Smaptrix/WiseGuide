package client;

import java.io.File;
import java.nio.file.Path;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class XMLParser {


    public NodeList OpenXMLFile (Path filePath) {

        NodeList pageList = null;

        try {
            File inputFile = new File(filePath.toString());

            //Parsing XML File
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputFile);
            document.getDocumentElement().normalize();

            System.out.println("XML of File path '" + filePath + "' normalised.");
            System.out.println("Root Element: " + document.getDocumentElement().getNodeName());

            pageList = document.getElementsByTagName("page");

        } catch (Exception e) {
            System.out.println("Unable to make tree of XML file at path: '" + filePath + "'.");
        }

        return pageList;

    }

}
