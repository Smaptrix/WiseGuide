package VenueXMLThings;

import org.w3c.dom.Node;

import java.util.Dictionary;
import java.util.Hashtable;

public class Page {

    public Node node;
    public Dictionary<String, String> attributes;


    public Page(Node item) {

        node = item;

        attributes = new Hashtable<>();

        int i = 0;
        while(node.getAttributes().item(i) != null) {
            String[] tempSplit = String.valueOf(node.getAttributes().item(i)).split("=");
            attributes.put(tempSplit[0], tempSplit[1].replace("\"",""));
            i++;
        }
    }


}
