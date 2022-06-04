/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Ben Alexander
    Date Created:   13/02/2022
    Last Updated:   04/06/2022
 */
package XMLTools;

import org.w3c.dom.Node;

import java.util.Objects;

/**
 * <p>
 *     Node of media which extends the page class.
 * </p>
 */
public class MediaElement extends VenuePage {

    /**
     * <p>mediaType is "playable"/"text"/"shape"/"image"</p>
     */
    public String mediaType;

    /**
     * <p>ID is the unique identifier for the media within the venuePage</p>
     */
    public String ID;

    /**
     * <p>include_source is the file where the media data is stored</p>
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
            if(Objects.equals(this.attributes.get("type"), "textbox")) {
                this.include_source = this.children.get(0).include_source;
            } else {
                this.include_source = this.attributes.get("include_source");
            }
        } catch(Exception e) {
            System.out.println("Error: Unable to construct media node from xml");
        }


    }

}
