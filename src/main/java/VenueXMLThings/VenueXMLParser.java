package VenueXMLThings;

import java.io.File;
import java.util.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;


/**
 * XML parsing for venue data, constructs a tree with the ability to get pages through id or title.
 */
public class VenueXMLParser {

    /**
     * file is the xml file
     */
    private File file;

    /**
     * document is created by the document builder
     */
    private Document document;

    /**
     * root contains the top node of the xml tree
     */
    public Element root = null;

    /**
     * number of pages is the number of venue elements (excludes #comment and #text)
     */
    public int numberOfPages;


    /**
     * <p>
     *     Constructor, takes xml file and returns VenueXMLParser class
     * </p>
     * @param inputFile the name of the xml file
     */
    public VenueXMLParser(File inputFile) {

        try {
            this.file = inputFile;

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

    /**
     * <p>
     *     Returns a list of the page names under the root (mainly used for testing)
     * </p>
     * @return List of strings with the page names
     */
    public List<String> getPageNames() {

        List<String> returnString = new ArrayList<>();

        for (int i = 0; i < numberOfPages; i++) {
            returnString.add(((this.root.getElementsByTagName("base:page").item(i).getAttributes().getNamedItem("title"))).toString());
            }

        return returnString;
    }

    /**
     * <p>
     *     Searches for a requested page based on title/ID
     * </p>
     * @param indexType can be "title" or "id"
     * @param index the title/id value
     * @return Returns a new page based on the title or ID, where there is no page with title or ID, returns null
     */
    public VenuePage getPage(String indexType, String index) {

        int searchedIndex = searchForPage(indexType, index);

        if (searchedIndex == -1) {
            return null;
        }

        return new VenuePage(this.root.getElementsByTagName("base:page").item(searchedIndex));

    }

    /**
     * <p>
     *     Adds a page to the xml and creates a backup before altering the xml file.
     * </p>
     * @param title of new venue
     * @param ID of new venue
     * @param lat of new venue
     * @param lon of new venue
     * @param category of new venue
     * @param price of new venue
     * @throws TransformerException when the document builder is unable to build the document correctly
     */
    public void addPage(String title, String ID, String lat, String lon, String category, String price, String rating) throws TransformerException {

        Element newPage = document.createElement("base:page");

        createXMLFile(true);

        newPage.setAttribute("title", title);
        newPage.setAttribute("ID", ID);
        newPage.setAttribute("lat", lat);
        newPage.setAttribute("lon", lon);
        newPage.setAttribute("category", category);
        newPage.setAttribute("price", price);
        newPage.setAttribute("raiting", rating);

        root.appendChild(newPage);
        numberOfPages++;

        createXMLFile(false);
    }

    /**
     * <p>
     *     Removes page based on searched title/ID
     * </p>
     * @param indexType can be "title" or "id"
     * @param index the title/id value
     * @return int 1 if successful, -1 if unsuccessful
     * @throws TransformerException
     */
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

    /**
     * <p>
     *     Changes the attribute of a searched venue
     * </p>
     * @param indexType can be "title" or "id"
     * @param index the title/id value
     * @param attributeType type of attribute
     * @param attribute value of attribute
     * @return int -1 if unsuccessful and 1 if successful
     * @throws TransformerException
     */
    public int changeAttribute(String indexType, String index, String attributeType, String attribute) throws TransformerException {
        int searchedIndex = searchForPage(indexType, index);

        if (searchedIndex == -1) {
            return -1;
        }

        createXMLFile(true);

        boolean changedAttribute = false;

        for (int i = 0; i < root.getElementsByTagName("base:page").item(searchedIndex).getAttributes().getLength(); i++) {
            if (root.getElementsByTagName("base:page").item(searchedIndex).getAttributes().item(i).getNodeName().equals(attributeType)) {
                root.getElementsByTagName("base:page").item(searchedIndex).getAttributes().item(i).setNodeValue(attribute);
                changedAttribute = true;
            }
        }

        if(changedAttribute) {
            createXMLFile(false);
            return 1;

        } else {
            return -1;
        }
    }

    /**
     * <p>
     *     Adds a textbox and text-type media with attributes specified by the user
     * </p>
     * @param indexType can be "title" or "id"
     * @param index the title/id value
     * @param attributesDict a dictionary with the specified attributes for the text media
     * @return
     * @throws TransformerException
     */
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

    /**
     * <p>
     *     Adds media element with attributes specified by the user
     * </p>
     * @param indexType can be "title" or "id"
     * @param index the title/id value
     * @param mediaType the "image"/"playable"
     * @param attributesDict a dictionary with the specified attributes for the media
     * @return int -1 if unsuccessful, 1 if successful
     * @throws TransformerException
     */
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

    /**
     * <p>
     *     Removes a media element of a specific venue from the input of the include_source of the media
     * </p>
     * @param indexType can be "title" or "id"
     * @param index the title/id value
     * @param source is the source for the include_source value
     * @return int -1 if unsuccessful, 1 if successful
     * @throws TransformerException
     */
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

    /**
     * <p>
     *     Creates a backup XML file and stores with the current date
     * </p>
     * @param isBackup true if it is a backup (will make a file with date), if false it will overwrite the XML file
     * @throws TransformerException
     */
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

    /**
     * <p>
     *     Searches for page given an title/index
     * </p>
     * @param indexType can be "title" or "id"
     * @param index the title/id value
     * @return int -1 if unsuccessful, 1 if successful
     */
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
}


