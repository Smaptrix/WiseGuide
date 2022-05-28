package tests;

import VenueXMLThings.VenuePage;
import VenueXMLThings.VenueXMLParser;
import client.Client;
import org.junit.Assert;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class XMLParserTests extends ApplicationTest {

    Client client;

    @Test
    //Unit Test | Confirm the page names are stored correctly.
    //This is very inefficient, but works for now.
    public void pageNamesTest(){
        client = new Client();
        File venueLocations = new File("venuesLocation_forTesting.xml");
        VenueXMLParser xml = new VenueXMLParser(venueLocations);
        List<String> venueList = xml.getPageNames();
        List<String> expectedList = Arrays.asList("title=\"Flares_York\"", "title=\"Club_Salvation\"", "title=\"Kuda\"", "title=\"Popworld_York\"", "title=\"Revolution_York\"", "title=\"The_Stone_Roses_Bar\"", "title=\"Dusk\"", "title=\"Evil_Eye\"", "title=\"All_Bar_One_York\"", "title=\"The_Cat's_Whiskers\"", "title=\"Drift-In_York\"", "title=\"Brew_&_Brownie\"", "title=\"Lucky_Days\"", "title=\"Il_Paradiso_Del_Cibo\"", "title=\"Spark_York_C.I.C\"", "title=\"Cosy_Club\"", "title=\"Cresci_Pizzeria\"", "title=\"Deniz_Best_Kebab\"", "title=\"NaNa_Noodle_Bar\"", "title=\"Cecil's_Pizza_-_Grill\"", "title=\"Efes_Pizza\"", "title=\"Charles_XII\"", "title=\"The_Waggon_&_Horses\"", "title=\"The_Black_Bull\"", "title=\"The_Rook_&_Gaskill\"", "title=\"Museum_Gardens\"", "title=\"Rowntree_Park\"", "title=\"Dean's_Park\"", "title=\"Millennium_Fields\"", "title=\"University_of_York_JB_Morrell_Library\"", "title=\"Piazza_Building\"", "title=\"The_Ron_Cooke_Hub\"", "title=\"Roger_Kirk_Centre\"", "title=\"York_City_Walls\"", "title=\"York_Minster\"", "title=\"National_Railway_Museum_York\"", "title=\"The_Hole_In_Wand\"", "title=\"York's_Chocolate_Story\"", "title=\"Study_Day_Route\"", "title=\"Night_Out_Route\"", "title=\"Tourist_Day_Route\"");
        System.out.println(venueList);
        System.out.println(expectedList);
        Assert.assertEquals(expectedList,venueList);
    }

    @Test
    //Unit Test | Confirm venue pages can be retrieved by title.
    public void getVenueByTitleTest(){
        client = new Client();
        File venueLocations = new File("venuesLocation_forTesting.xml");
        VenueXMLParser xml = new VenueXMLParser(venueLocations);
        VenuePage testVenue = xml.getPage("title","Flares_York");
        Assert.assertNotNull(testVenue);
    }

    @Test
    //Unit Test | Confirm venue pages can be retrieved by ID.
    public void getVenueByIDTest(){
        client = new Client();
        File venueLocations = new File("venuesLocation_forTesting.xml");
        VenueXMLParser xml = new VenueXMLParser(venueLocations);
        VenuePage testVenue = xml.getPage("ID","001");
        Assert.assertNotNull(testVenue);
    }

    @Test
    //Unit Test | Confirm new pages can be added to the XML File.
    //<base:page title="Flares_York" ID="001" price="2" lat="53.9587775" lon="-1.0867884" category="nightclub" rating="4">
    public void addNewPageTest() throws TransformerException {
        client = new Client();
        File venueLocations = new File("venuesLocation_forTesting.xml");
        VenueXMLParser xml = new VenueXMLParser(venueLocations);
        xml.addPage("testPage","999","0.000","0.000","nightclub","2","4");
    }

    @Test
    //Unit Test | Confirm pages can be removed from the XML File.
    public void removePageTest(){
        client = new Client();
        File venueLocations = new File("venuesLocation_forTesting.xml");
        VenueXMLParser xml = new VenueXMLParser(venueLocations);
        //TODO
    }

    @Test
    //Unit Test | Confirm page attributes can be modified.
    public void changeAttributeTest(){
        client = new Client();
        File venueLocations = new File("venuesLocation_forTesting.xml");
        VenueXMLParser xml = new VenueXMLParser(venueLocations);
        //TODO
    }

    @Test
    //Unit Test | Confirm child text can be added.
    public void addChildTextTest(){
        //TODO
    }

    @Test
    //Unit Test | Confirm child media can be added.
    public void addChildMediaTest(){
        //TODO
    }

    @Test
    //Unit Test | Confirm child media can be removed.
    public void removeChildMediaTest(){
        //TODO
    }

    @Test
    //Unit Test | Confirm XML files can be created.
    public void createXMLTest(){
        //TODO
    }

}
