package VenueXMLThings;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

public class mainXMLtester {

    public static void main(String[] args) throws IOException, TransformerException {
        File file = new File("venuesLocation.xml");
        VenueXMLParser xml = new VenueXMLParser(file);
        System.out.println(xml.numberOfPages);
        xml.addPage("1","2","3","4","5", "6");
        System.out.println(xml.numberOfPages);

        System.out.println(xml.getPage("title", "1").attributes);

        System.out.println(xml.changeAttribute("title", "1", "category", "river"));

        Dictionary<String, String> newText = new Hashtable<>();
        newText.put("include_source", "text.txt");
        newText.put("x_end", "0");

        Dictionary<String, String> newMedia = new Hashtable<>();
        newMedia.put("include_source", "video.mp4");
        newMedia.put("autoplay", "true");

        System.out.println(xml.addChildText("title", "1", newText));
        System.out.println(xml.addChildMedia("title", "1", "playable", newMedia));

        System.out.println(xml.removePage("title", "1"));

    }
}
