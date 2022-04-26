package VenueXMLThings;

import java.io.File;
import java.util.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

/*

//this is an example of how to use the updating of the XML File,
//it also creates a backup of the xml at the time in milliseconds
//(higher number after the file is newer :) ) this probably won't
//be used much in the final product as it would take up a lot of
//space. *also note I use venue and page interchangeably



package VenueXMLThings;


import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

public class mainXMLtester {

    public static void main(String[] args) throws IOException, TransformerException {
            File file = new File("venuesLocation.xml");
            VenueXMLParser xml = new VenueXMLParser(file);
            System.out.println(xml.numberOfPages);

            //adds a venue with the attributes 1,2,3...
            xml.addPage("1","2","3","4","5","6");
            System.out.println(xml.numberOfPages);
            System.out.println(xml.getPage("title", "1").attributes);

            //changes the category attribute of the venue of title "1"
            System.out.println(xml.changeAttribute("title", "1", "category", "nightclub"));

            //adding media requires a hashtable where you can add all the attributes for the media

            //adding some of the attributes for a text media object
            Dictionary<String, String> newText = new Hashtable<>();
            newText.put("include_source", "text.txt");
            newText.put("x_end", "0");

            System.out.println(xml.addChildText("title", "1", newText));

            //adding some of the attributes for a video media object
            Dictionary<String, String> newMedia = new Hashtable<>();
            newMedia.put("include_source", "video.mp4");
            newMedia.put("autoplay", "true");
            newMedia.put("type", "video");

            System.out.println(xml.addChildMedia("title", "1", "playable", newMedia));

            //Removing the child media files
            System.out.println(xml.removeChildMedia("title", "1", "text.txt"));
            System.out.println(xml.removeChildMedia("title", "1", "video.mp4"));

            //just for now, remove the page when testing as you can still see the changes in the backups
            System.out.println(xml.removePage("title", "1"));

    }
}



 */

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

        for (int i = 0; i < numberOfPages; i++) {
            returnString.add(((this.root.getElementsByTagName("base:page").item(i).getAttributes().getNamedItem("title"))).toString());
        }

        return returnString;

    }

    //Returns a new page based on the title or ID, where there is no page with title or ID, returns null
    public VenuePage getPage(String indexType, String index) {

        int searchedIndex = searchForPage(indexType, index);

        if (searchedIndex == -1) {
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
        newPage.setAttribute("price", price);

        root.appendChild(newPage);
        numberOfPages++;


        createXMLFile(false);
    }

    public int removePage(String indexType, String index) throws TransformerException {

        int searchedIndex = searchForPage(indexType, index);

        if (searchedIndex == -1) {
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

        if (searchedIndex == -1) {
            return -1;
        }

        createXMLFile(true);

        for (int i = 0; i < root.getElementsByTagName("base:page").item(searchedIndex).getAttributes().getLength(); i++) {
            if (root.getElementsByTagName("base:page").item(searchedIndex).getAttributes().item(i).getNodeName().equals(attributeType)) {
                root.getElementsByTagName("base:page").item(searchedIndex).getAttributes().item(i).setNodeValue(attribute);
            }
        }

        createXMLFile(false);

        return 1;
    }

    public int addChildText(String indexType, String index, Dictionary<String, String> attributesDict) throws TransformerException {
        int searchedIndex = searchForPage(indexType, index);

        if (searchedIndex == -1) {
            return -1;
        }

        createXMLFile(true);

        Element newMedia = document.createElement("base:shape");

        Enumeration<String> keys = attributesDict.keys();

        while (keys.hasMoreElements()) {

            String key = keys.nextElement();
            if (!key.equals("include_source")) {
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

        if (searchedIndex == -1) {
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

    public int removeChildMedia(String indexType, String index, String source) throws TransformerException {
        int searchedIndex = searchForPage(indexType, index);

        if (searchedIndex == -1) {
            return -1;
        }

        createXMLFile(true);

        boolean sourceFound = false;
        int childIndex = -1;

        int i = 0;

        while (i < root.getElementsByTagName("base:page").item(searchedIndex).getChildNodes().getLength() && !sourceFound) {
            if (root.getElementsByTagName("base:page").item(searchedIndex).getChildNodes().item(i).getNodeName().equals("base:shape")) {
                for (int j = 0; j < root.getElementsByTagName("base:page").item(searchedIndex).getChildNodes().item(i).getChildNodes().getLength(); j++) {
                    if (!root.getElementsByTagName("base:page").item(searchedIndex).getChildNodes().item(i).getChildNodes().item(j).getNodeName().contains("#text")) {
                        System.out.println(root.getElementsByTagName("base:page").item(searchedIndex).getChildNodes().item(i).getChildNodes().item(j).getAttributes().getNamedItem("include_source").getNodeValue());
                        if (root.getElementsByTagName("base:page").item(searchedIndex).getChildNodes().item(i).getChildNodes().item(j).getAttributes().getNamedItem("include_source").getNodeValue().equals(source)) {
                            System.out.println("FOUND IT");
                            sourceFound = true;
                            childIndex = i;
                            break;
                        }
                    }
                }
            } else {
                System.out.println(root.getElementsByTagName("base:page").item(searchedIndex).getChildNodes().item(i).getNodeName().contains("#text"));
                if (!root.getElementsByTagName("base:page").item(searchedIndex).getChildNodes().item(i).getNodeName().contains("#text")) {
                    if (root.getElementsByTagName("base:page").item(searchedIndex).getChildNodes().item(i).getAttributes().getNamedItem("include_source").getNodeValue().equals(source)) {
                        sourceFound = true;
                        childIndex = i;
                        break;
                    }
                }
            }
            i++;
        }

        if (sourceFound) {
            Node tempNode = root.getElementsByTagName("base:page").item(searchedIndex).getChildNodes().item(childIndex);
            root.getElementsByTagName("base:page").item(searchedIndex).removeChild(tempNode);

            createXMLFile(false);
            return 1;
        } else {
            System.out.println("ERROR: NO SUCH SOURCE IN XML");
            return -1;
        }

    }

    private void createXMLFile(boolean isBackup) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult;
        if (isBackup) {
            streamResult = new StreamResult(new File(String.valueOf(file.toPath()).replace("venuesLocation", "venuesLocation" + System.currentTimeMillis())));
        } else {
            streamResult = new StreamResult(new File(String.valueOf(file.toPath())));
        }
        transformer.transform(domSource, streamResult);
    }

    private int searchForPage(String indexType, String index) {
        if (!(indexType.equals("title") || indexType.equals("ID"))) {
            System.out.println("Error: no such index.");
            return -1;
        }

        index = index.replaceAll(" ", "_");
        index = indexType + "=\"" + index + "\"";

        for (int i = 0; i < numberOfPages; i++) {


            if (this.root.getElementsByTagName("base:page").item(i).getAttributes().getNamedItem(indexType).toString().equals(index)) {
                return i;
            }

        }

        if (indexType.equals("title")) {
            System.out.println("Error: no such title.");
        } else {
            System.out.println("Error: no such ID.");
        }

        return -1;
    }

    public String getMediaSourceByID(String IDsearch) {
        System.out.println(this.attributes);

        try {
            for (int i = 0; i < this.children.size() - 1; ++i) {
                MediaElement element = (MediaElement) this.children.get(i);
                System.out.println(i + ":, ID: " + element.ID + ", " + element.include_source);
                if (element.ID.equals(IDsearch)) {
                    System.out.println("MATCH! for " + element.include_source);
                    return element.include_source;
                }
            }
        } catch (Exception var4) {
            System.out.println("Error: Unable to get source by ID: " + IDsearch + " for venue " + (String) this.attributes.get("title"));
        }

        return null;
    }
}


