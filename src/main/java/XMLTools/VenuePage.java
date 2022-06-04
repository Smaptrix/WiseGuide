/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Ben Alexander
    Date Created:   13/02/2022
    Last Updated:   04/06/2022
 */
package XMLTools;

import org.w3c.dom.Node;

import java.util.*;

/**
 * <p>Stores all relevant media data in a tree for one specific venue.</p>
 */
public class VenuePage {

    /**
     * <p>node is the top node of the VenuePage</p>
     */
    private Node node;

    /**
     * <p>attributes is a dictionary that contains all the attributes for the page</p>
     */
    public Dictionary<String, String> attributes;

    /**
     * <p>children are a list of the MediaElements attached to the VenuePage</p>
     */
    public List<MediaElement> children;

    /**
     * <p>numberOfElements is the number of MediaElements attached to the given page</p>
     */
    public int numberOfElements;


    /**
     * <p>
     *     Constructor takes in a node (venue) and creates a tree with all the child nodes of class
     * </p>
     * @param item is the page node
     */
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

    /**
     * <p>
     *     Returns a list of children node media types (mainly for debugging/testing)
     * </p>
     * @return A List of Strings containing the media children's mediaType
     */
    public List<String> getChildrenType() {

        List<String> returnString = new ArrayList<>();

        for(int i = 0; i < this.numberOfElements; i++) {
            returnString.add(this.children.get(i).mediaType);
        }

        return returnString;

    }

    /**
     * <p>
     *     Gather the child nodes of media type (mainly for debugging/testing)
     * </p>
     * @param mediaType is the type of media "text"/"playable" ect.
     * @return the index of the media with a given mediaType, returns null if none found
     */
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

    /**
     * <p>
     *     Gets the media's source location from a given media ID
     * </p>
     * @param IDSearch the unique media ID
     * @return String of source from the ID inputted
     */
    public String getMediaSourceByID(String IDSearch) {



            for(int i = 0; i < this.children.size(); i++) {
                MediaElement element = null;
                try {
                    if ((IDSearch.contains("text") || IDSearch.contains("altText")) && this.children.get(i).attributes.get("type").equals("textbox")) {
                        for(int j = 0; j <= this.children.get(i).children.size() - 1; ++j) {
                            if (children.get(i).children.get(j).ID.contains("text") || children.get(i).children.get(j).ID.contains("altText")) {
                                element = this.children.get(i).children.get(j);
                                j = this.children.get(i).children.size();
                            }
                        }
                    } else {
                        element = this.children.get(i);
                    }
                } catch (Exception e) {
                    System.out.println("Exception in media search");
                }

                try {
                    if (element != null) {
                        if (element.ID != null) {
                            if (element.ID.equals(IDSearch)) {
                                return element.include_source;
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Exception in media search");
                }
            }

        return null;
    }
}
