package VenueXMLThings;

import org.w3c.dom.Node;

import javax.print.attribute.standard.Media;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class VenuePage {

    private Node node;
    public Dictionary<String, String> attributes;
    public List<MediaElement> children;
    public int numberOfElements;


    public VenuePage(Node item) {

        this.node = item;

        this.attributes = new Hashtable<>();
        this.children = new ArrayList<MediaElement>();

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

    public List<String> getChildrenType() {

        List<String> returnString = new ArrayList<>();

        for(int i = 0; i < this.numberOfElements; i++) {
            returnString.add(this.children.get(i).mediaType);
        }

        return returnString;

    }

    public List<Integer> getMediaByType(String mediaType) {

        List<Integer> returnString = new ArrayList<Integer>();

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

}
