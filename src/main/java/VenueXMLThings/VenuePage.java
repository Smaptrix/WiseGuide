package VenueXMLThings;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

//Stores all relevant media data in a tree for one specific venue.
public class VenuePage {

    private Node node;
    public Dictionary<String, String> attributes;
    public List<MediaElement> children;
    public int numberOfElements;

    //Constructor takes in a node (venue) and creates a tree with all the child nodes of class
    //MediaElements which extends VenuePage.
    public VenuePage(Node item) {

        this.node = item;

        this.attributes = new Hashtable<>();
        this.children = new ArrayList<>();

        if(this.node.getAttributes() != null) {

            int i = 0;
            while (this.node.getAttributes().item(i) != null) {
                String[] tempSplit = String.valueOf(this.node.getAttributes().item(i)).split("=");
                this.attributes.put(tempSplit[0], tempSplit[1].replace("\"", ""));
                i++;
            }

        }

        if(this.node.getChildNodes().item(0) != null) {

            int j = 0;

            while (j < (this.node.getChildNodes().getLength() - 1)) {
                MediaElement temp = new MediaElement(node.getChildNodes().item(j+1));
                if (!temp.mediaType.equals("#text")) {
                    this.children.add(temp);
                    this.numberOfElements++;
                }
                j++;
            }
        }

    }

    //Returns a list of children node media types (mainly for testing)
    public List<String> getChildrenType() {

        List<String> returnString = new ArrayList<>();

        for(int i = 0; i < this.numberOfElements; i++) {
            returnString.add(this.children.get(i).mediaType);
        }

        return returnString;

    }

    //Gather the child nodes of media type
    public List<Integer> getMediaByType(String mediaType) {

        List<Integer> returnString = new ArrayList<>();

        for(int i = 0; i < numberOfElements; i++) {

            if(this.children.get(i).mediaType.equals(mediaType)) {
                returnString.add(i);
            }

        }

        if(!returnString.isEmpty()) {

            return returnString;

        } else {

            System.out.println("No such media: " + mediaType + " in page ID: " + this.attributes.get("ID"));
            return null;

        }
    }

    public String getMediaSourceByID(String IDSearch) {

        System.out.println(this.attributes);
            for(int i = 0; i < this.children.size(); i++) {
                MediaElement element = null;
                try {
                    if (IDSearch.contains("text") && this.children.get(i).attributes.get("type").equals("textbox")) {
                        for(int j = 0; j <= this.children.get(i).children.size() - 1; ++j) {
                            if (children.get(i).children.get(j).ID.contains("text")) {
                                element = this.children.get(i).children.get(j);
                                j = this.children.get(i).children.size();
                            }
                        }
                    } else {
                        element = this.children.get(i);
                    }
                } catch (Exception e) {}

                try {
                    if (element != null) {
                        if (element.ID != null) {
                            if (element.ID.equals(IDSearch)) {
                                return element.include_source;
                            }
                        }
                    }
                } catch (Exception e) {}
            }

        return null;
    }
}
