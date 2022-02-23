package VenueXMLThings;

import org.w3c.dom.Node;

public class MediaElement extends VenuePage {

    public String mediaType;

    public MediaElement(Node item) {
        super(item);

        this.mediaType = item.getNodeName();

    }

}
