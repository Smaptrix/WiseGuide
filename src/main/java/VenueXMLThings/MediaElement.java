package VenueXMLThings;

import org.w3c.dom.Node;

/**
 * <p>
 *     Node of media which extends the page class.
 * </p>
 */
public class MediaElement extends VenuePage {

    /**
     * mediaType is "playable"/"text"/"shape"/"image"
     */
    public String mediaType;

    /**
     * ID is the unique identifier for the media within the venuePage
     */
    public String ID;

    /**
     * include_source is the file where the media data is stored
     */
    public String include_source;

    /**
     * <p>
     *     Constructor for MediaElement
     * </p>
     * @param item is the media element
     */
    public MediaElement(Node item) {
        super(item);

        try{
            this.mediaType = item.getNodeName();
            this.ID = this.attributes.get("ID");
            if(this.attributes.get("type") == "textbox") {
                this.include_source = this.children.get(0).include_source;
            } else {
                this.include_source = this.attributes.get("include_source");
            }
        } catch(Exception e) {
            System.out.println("Error: Unable to construct media node from xml");
        }


    }

}
