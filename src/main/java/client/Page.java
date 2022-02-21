package client;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class Page {

    public Node node;
    public Dictionary attributes;


    public Page(Node item) {

        node = item;

        attributes = new Hashtable<String, String>();

        int i = 0;
        while(node.getAttributes().item(i) != null) {
            String[] tempSplit = String.valueOf(node.getAttributes().item(i)).split("=");
            attributes.put(tempSplit[0], tempSplit[1].replace("\"",""));
            i++;
        }
    }


}
