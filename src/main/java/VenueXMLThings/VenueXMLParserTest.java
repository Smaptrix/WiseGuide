package VenueXMLThings;


import client.Client;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import server.ServerUserHandler;


import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;


import static org.junit.Assert.*;


public class VenueXMLParserTest {

    VenueXMLParser XMLTestParser = null;


    @Before
    public void readInTest() {
        File XMLTestFile = new File("venuesLocationTest.xml");
        this.XMLTestParser = new VenueXMLParser(XMLTestFile);
        assertEquals(this.XMLTestParser.numberOfPages, 38);
    }

    @Test
    public void searchEveryPage() {
        for(String name : this.XMLTestParser.getPageNames()) {
            name = name.replaceAll("title=\"", "").replaceAll("\"", "");
            assertNotEquals(this.XMLTestParser.getPage("title", name), null);
        }
    }

    @Test
    public void addAndRemoveNewPage() throws TransformerException {
        this.XMLTestParser.addPage("TestTitle", "TestID", "Testlat", "Testlon", "TestCategory", "TestPrice");
        assertEquals(this.XMLTestParser.removePage("title", "TestTitle"), 1);
    }

    @Test
    public void RemoveImaginaryPage() throws TransformerException {
        assertEquals(this.XMLTestParser.removePage("title", "ImaginaryPage"), -1);
        assertEquals(this.XMLTestParser.removePage("wrongIndexType", "Flares_York"), -1);
        assertEquals(this.XMLTestParser.removePage("ID", "WrongID"), -1);
        assertEquals(this.XMLTestParser.removePage("wrongIndexType", "001"), -1);
    }

}
