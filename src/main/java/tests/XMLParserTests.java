package tests;

import VenueXMLThings.VenuePage;
import VenueXMLThings.VenueXMLParser;
import client.Client;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *     Tests for the XML Parser.
 * </p>
 */
public class XMLParserTests extends ApplicationTest {

    Client client;
    VenueXMLParser xml;

    @After
    public void afterEachTest() throws TransformerException {
        System.out.println("[CLEANUP] Checking for newly created test page...");
        if(xml.getPage("title","testPage") != null){
            xml.removePage("title","testPage");
        }
    }

    @Test
    //Unit Test | Confirm the XML file is read in correctly.
    public void readInTest(){
        File venueLocations = new File("venuesLocation_forTesting.xml");
        xml = new VenueXMLParser(venueLocations);
        Assert.assertEquals(41,xml.numberOfPages);
    }

    @Test
    //Unit Test | Confirm page names are read in correctly.
    public void searchEveryPageTest() {
        File venueLocations = new File("venuesLocation_forTesting.xml");
        xml = new VenueXMLParser(venueLocations);
        for(String name : xml.getPageNames()) {
            name = name.replaceAll("title=\"", "").replaceAll("\"", "");
            Assert.assertNotEquals(xml.getPage("title", name), null);
        }
    }

    @Test
    //Unit Test | Confirm the page names are stored correctly.
    //This is very inefficient, but works for now.
    public void pageNamesTest(){
        File venueLocations = new File("venuesLocation_forTesting.xml");
        xml = new VenueXMLParser(venueLocations);
        List<String> venueList = xml.getPageNames();
        List<String> expectedList = Arrays.asList("title=\"Flares_York\"", "title=\"Club_Salvation\"", "title=\"Kuda\"", "title=\"Popworld_York\"", "title=\"Revolution_York\"", "title=\"The_Stone_Roses_Bar\"", "title=\"Dusk\"", "title=\"Evil_Eye\"", "title=\"All_Bar_One_York\"", "title=\"The_Cat's_Whiskers\"", "title=\"Drift-In_York\"", "title=\"Brew_&_Brownie\"", "title=\"Lucky_Days\"", "title=\"Il_Paradiso_Del_Cibo\"", "title=\"Spark_York_C.I.C\"", "title=\"Cosy_Club\"", "title=\"Cresci_Pizzeria\"", "title=\"Deniz_Best_Kebab\"", "title=\"NaNa_Noodle_Bar\"", "title=\"Cecil's_Pizza_-_Grill\"", "title=\"Efes_Pizza\"", "title=\"Charles_XII\"", "title=\"The_Waggon_&_Horses\"", "title=\"The_Black_Bull\"", "title=\"The_Rook_&_Gaskill\"", "title=\"Museum_Gardens\"", "title=\"Rowntree_Park\"", "title=\"Dean's_Park\"", "title=\"Millennium_Fields\"", "title=\"University_of_York_JB_Morrell_Library\"", "title=\"Piazza_Building\"", "title=\"The_Ron_Cooke_Hub\"", "title=\"Roger_Kirk_Centre\"", "title=\"York_City_Walls\"", "title=\"York_Minster\"", "title=\"National_Railway_Museum_York\"", "title=\"The_Hole_In_Wand\"", "title=\"York's_Chocolate_Story\"", "title=\"Study_Day_Route\"", "title=\"Night_Out_Route\"", "title=\"Tourist_Day_Route\"");
        System.out.println(venueList);
        System.out.println(expectedList);
        Assert.assertEquals(expectedList,venueList);
    }

    @Test
    //Unit Test | Confirm venue pages can be retrieved by title.
    public void getVenueByTitleTest(){
        File venueLocations = new File("venuesLocation_forTesting.xml");
        xml = new VenueXMLParser(venueLocations);
        VenuePage testVenue = xml.getPage("title","Flares_York");
        Assert.assertNotNull(testVenue);
    }

    @Test
    //Unit Test | Confirm venue pages can be retrieved by ID.
    public void getVenueByIDTest(){
        File venueLocations = new File("venuesLocation_forTesting.xml");
        xml = new VenueXMLParser(venueLocations);
        VenuePage testVenue = xml.getPage("ID","001");
        Assert.assertNotNull(testVenue);
    }

    @Test
    //Unit Test | Confirm new pages can be added to the XML File.
    public void addNewPageTest() throws TransformerException {
        File venueLocations = new File("venuesLocation_forTesting.xml");
        xml = new VenueXMLParser(venueLocations);
        xml.addPage("testPage","testID","testLat","testLong","testCategory","testPrice","testRating");
        Assert.assertNotNull(xml.getPage("title","testPage"));
    }

    @Test
    //Unit Test | Confirm pages can be removed from the XML File.
    public void removePageTest() throws TransformerException {
        File venueLocations = new File("venuesLocation_forTesting.xml");
        xml = new VenueXMLParser(venueLocations);
        System.out.println("Checking for test page...");
        if(xml.getPage("title","testPage") == null){
            xml.addPage("testPage","testID","testLat","testLong","testCategory","testPrice","testRating");
        }

        Assert.assertNotNull(xml.getPage("title","testPage"));
        System.out.println("Checking test page was deleted...");
        int result = xml.removePage("title","testPage");
        Assert.assertEquals(1,result);
        Assert.assertNull(xml.getPage("title","testPage"));
    }

    @Test
    //Unit Test | Confirm page attributes can be modified.
    public void changeNewAttributeTest() throws TransformerException {
        File venueLocations = new File("venuesLocation_forTesting.xml");
        xml = new VenueXMLParser(venueLocations);
        if(xml.getPage("title","testPage") == null){
            xml.addPage("testPage","testID","testLat","testLong","testCategory","testPrice","testRating");
        }
        int result = xml.changeAttribute("title","testPage","price","1");
        Assert.assertEquals(1,result);
        VenuePage testVenue = xml.getPage("title","testPage");
        String price = testVenue.attributes.get("price");
        Assert.assertEquals(price,"1");
    }

    @Test
    //Unit Test | Confirm page attributes can be modified for existing pages.
    public void changeOldAttributeTest() throws TransformerException {
        File venueLocations = new File("venuesLocation_forTesting.xml");
        xml = new VenueXMLParser(venueLocations);
        String oldLatitude = xml.getPage("title", "Flares_York").attributes.get("lat");
        Assert.assertEquals(xml.changeAttribute("title", "Flares_York", "lat", "newLat"), 1);
        Assert.assertEquals(xml.changeAttribute("title", "Flares_York", "lat", oldLatitude), 1);
    }

    //TODO
    @Test
    //Unit Test | Confirm child text can be added.
    public void addChildTextTest(){
    }

    @Test
    //Unit Test | Confirm child media can be added.
    public void addChildMediaTest(){
    }

    @Test
    //Unit Test | Confirm child media can be removed.
    public void removeChildMediaTest(){
    }

    @Test
    //Unit Test | Confirm XML files can be created.
    public void createXMLTest(){
    }

}
