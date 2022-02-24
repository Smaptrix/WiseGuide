package VenueXMLThings;

import org.w3c.dom.Node;

//Node of media which extends the page class.
public class MediaElement extends VenuePage {

    public String mediaType;

    public MediaElement(Node item) {
        super(item);

        this.mediaType = item.getNodeName();

    }

}
