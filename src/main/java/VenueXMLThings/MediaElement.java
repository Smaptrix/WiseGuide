package VenueXMLThings;

import org.w3c.dom.Node;

//Node of media which extends the page class.
public class MediaElement extends VenuePage {

    public String mediaType;
    public String ID;
    public String include_source;

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
