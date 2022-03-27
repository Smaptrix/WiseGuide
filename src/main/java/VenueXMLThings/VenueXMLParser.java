package VenueXMLThings;

import java.io.File;
import java.util.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;


//XML parsing for venue data, constructs a tree with the ability to get pages through id or title.
public class VenueXMLParser {

    private File file;
    private Document document;
    public Element root = null;
    public int numberOfPages;

    //constructor, takes xml file and returns VenueXMLParser class
    public VenueXMLParser(File inputFile) {

        try {
            this.file = inputFile;

            //Parsing XML File
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            this.document = documentBuilder.parse(this.file);
            this.document.getDocumentElement().normalize();
            root = this.document.getDocumentElement();

            numberOfPages = root.getElementsByTagName("base:page").getLength();

        } catch (Exception e) {

            System.out.println("Unable to make tree of XML file at path: '" + this.file + "'.");

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
    public VenuePage getPage(String indexType, String index) {

        int searchedIndex = searchForPage(indexType, index);

        if(searchedIndex == -1) {
            return null;
        }

        return new VenuePage(this.root.getElementsByTagName("base:page").item(searchedIndex));

    }

    public void addPage(String title, String ID, String lat, String lon, String category, String price) throws TransformerException {

        Element newPage = document.createElement("base:page");

        createXMLFile(true);

        newPage.setAttribute("title", title);
        newPage.setAttribute("ID", ID);
        newPage.setAttribute("lat", lat);
        newPage.setAttribute("lon", lon);
        newPage.setAttribute("category", category);
        newPage.setAttribute("price", category);

        root.appendChild(newPage);
        numberOfPages++;


        createXMLFile(false);
    }

    public int removePage(String indexType, String index) throws TransformerException {

        int searchedIndex = searchForPage(indexType, index);

        if(searchedIndex == -1) {
            return -1;
        }

        createXMLFile(true);

        root.removeChild(root.getElementsByTagName("base:page").item(searchedIndex));
        numberOfPages--;

        createXMLFile(false);

        return 1;
    }

    public int changeAttribute(String indexType, String index, String attributeType, String attribute) throws TransformerException {
        int searchedIndex = searchForPage(indexType, index);

        if(searchedIndex == -1) {
            return -1;
        }

        createXMLFile(true);

        for(int i = 0; i<root.getElementsByTagName("base:page").item(searchedIndex).getAttributes().getLength(); i++) {
            if(root.getElementsByTagName("base:page").item(searchedIndex).getAttributes().item(i).getNodeName() == attributeType) {
                root.getElementsByTagName("base:page").item(searchedIndex).getAttributes().item(i).setNodeValue(attribute);
            }
        }

        createXMLFile(false);

        return 1;
    }

    public int addChildText(String indexType, String index, Dictionary<String, String> attributesDict) throws TransformerException {
        int searchedIndex = searchForPage(indexType, index);

        if(searchedIndex == -1) {
            return -1;
        }

        createXMLFile(true);

        Element newMedia = document.createElement("base:shape");

        Enumeration<String> keys = attributesDict.keys();

        while (keys.hasMoreElements()) {

            String key = keys.nextElement();
            if(key != "include_source") {
                newMedia.setAttribute(key, attributesDict.get(key));
            }
        }

        Element newTextbox = document.createElement("base:text");
        newTextbox.setAttribute("include_source", attributesDict.get("include_source"));
        newMedia.appendChild(newTextbox);

        root.getElementsByTagName("base:page").item(searchedIndex).appendChild(newMedia);

        createXMLFile(false);


        return 1;
    }

    public int addChildMedia(String indexType, String index, String mediaType, Dictionary<String, String> attributesDict) throws TransformerException {
        int searchedIndex = searchForPage(indexType, index);

        if(searchedIndex == -1) {
            return -1;
        }

        createXMLFile(true);

        Element newMedia = document.createElement("base:" + mediaType);

        Enumeration<String> keys = attributesDict.keys();

        while (keys.hasMoreElements()) {

            String key = keys.nextElement();
            newMedia.setAttribute(key, attributesDict.get(key));

        }

        root.getElementsByTagName("base:page").item(searchedIndex).appendChild(newMedia);

        createXMLFile(false);

        return 1;
    }

    private void createXMLFile(boolean isBackup) throws TransformerException {
        document.getDocumentElement().equals(root);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = null;
        if(isBackup) {
            streamResult = new StreamResult(new File(String.valueOf(file.toPath()).replace("venuesLocation", "venuesLocation" + System.currentTimeMillis())));
        } else {
            streamResult = new StreamResult(new File(String.valueOf(file.toPath())));
        }
        transformer.transform(domSource, streamResult);
    }

    private int searchForPage(String indexType, String index) {
        if(!(indexType.equals("title") || indexType.equals("ID"))) {
            System.out.println("Error: no such index.");
            return -1;
        }

        index.replaceAll(" ", "_");
        index = indexType + "=\"" + index + "\"";

        for(int i = 0; i < numberOfPages; i++) {

            if(this.root.getElementsByTagName("base:page").item(i).getAttributes().getNamedItem(indexType).toString().equals(index)) {
                return i;
            }

        }

        if(indexType.equals("title")){
            System.out.println("Error: no such title.");
        } else {
            System.out.println("Error: no such ID.");
        }

        return -1;
    }

}

