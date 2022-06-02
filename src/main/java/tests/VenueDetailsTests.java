package tests;

import GUI.LoginApplication;
import GUI.VenueDetailsController;
import VenueXMLThings.VenueXMLParser;
import client.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.WindowMatchers;
import org.testfx.matcher.control.LabeledMatchers;
import serverclientstuff.User;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class VenueDetailsTests extends ApplicationTest {

    VenueDetailsController controller;
    Client client;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("VenueDetailsPage.fxml"));
        Parent mainNode = fxmlLoader.load();
        stage.setScene(new Scene(mainNode));

        this.controller = fxmlLoader.getController();
        stage.show();
        stage.toFront();
    }

    //Allows for Manual Testing
    //Manual Tests Required | Confirm "Close WiseGuide" actually closes WiseGuide.
    @Test
    public void manualTest(){
        sleep(10000);
    }

    //Unit Test | Confirm File Menu is functional
    @Test
    public void fileMenuTest(){
        sleep(1000);
        FxAssert.verifyThat("#fileMenu", Node::isVisible);
        FxAssert.verifyThat("#fileMenu", LabeledMatchers.hasText("File"));
        clickOn("#fileMenu");
        FxAssert.verifyThat("#menuClose", Node::isVisible);
        Assert.assertEquals("Close WiseGuide",controller.menuClose.getText());
    }

    //Unit Test | Confirm Help Menu is functional
    @Test
    public void helpMenuTest() throws IOException {
        sleep(1000);

        client = new Client();
        client.startConnection("127.0.0.1", 5555);
        controller.setClient(client);


        FxAssert.verifyThat("#helpMenu", Node::isVisible);
        FxAssert.verifyThat("#helpMenu", LabeledMatchers.hasText("Help"));
        clickOn("#helpMenu");
        FxAssert.verifyThat("#aboutButton", Node::isVisible);
        Assert.assertEquals("About",controller.aboutButton.getText());
        clickOn("#aboutButton");
        FxAssert.verifyThat(window(("WiseGuide by Maptrix - " + client.getCurrVersion())), WindowMatchers.isShowing());
    }

    //Unit Test | Confirm Back Button is functional
    @Test
    public void backButtonTest() throws IOException {
        sleep(1000);

        client = new Client();
        client.startConnection("127.0.0.1", 5555);
        controller.setClient(client);

        Stage currStage = (Stage) controller.goBack.getScene().getWindow();

        FxAssert.verifyThat("#goBack",Node::isVisible);
        FxAssert.verifyThat("#goBack",LabeledMatchers.hasText("Back"));
        clickOn("#goBack");
        Assert.assertFalse(currStage.isShowing());
    }

    //Unit Test | Confirm Venue Title (Default Text)
    @Test
    public void titleTest(){
        FxAssert.verifyThat("#venueName",Node::isVisible);
        FxAssert.verifyThat("#venueName", LabeledMatchers.hasText("VENUE NAME"));
    }


    //Unit Test | Confirm favourite Button displays correctly
    @Test
    public void favouriteButtonTextTest(){
        FxAssert.verifyThat("#faveVenueButton",Node::isVisible);
        FxAssert.verifyThat("#faveVenueButton",LabeledMatchers.hasText("Favourite"));
    }

    //Integration Test | Confirm favourite button works
    @Test
    public void favouriteButtonTest() throws IOException {

        //Set up Client
        client = new Client();
        client.startConnection("127.0.0.1", 5555);
        controller.setClient(client);

        //Load venue info
        User testUser = new User("test","12345");
        String venueName = "York_Minster";
        File xmlFile = new File("venuesLocation.xml");
        VenueXMLParser xml = new VenueXMLParser(xmlFile);

        //Get user faves list
        Assert.assertEquals("GOODLOGIN",client.requestLogin(testUser));
        testUser.setFaveVenues(client.requestFaveVenueList());
        controller.setCurrVenue(venueName.replaceAll("_"," "), xml.getPage("title", venueName), testUser);


        //Check venue isn't favourited already
        FxAssert.verifyThat("#faveVenueButton",LabeledMatchers.hasText("Favourite"));
        Assert.assertFalse(Arrays.asList(testUser.getFaveVenues()).contains(venueName));

        //Click to favourite
        clickOn("#faveVenueButton");
        sleep(500);
        FxAssert.verifyThat("#faveVenueButton",LabeledMatchers.hasText("UnFavourite"));
        sleep(500);
        testUser.setFaveVenues(client.requestFaveVenueList());
        Assert.assertTrue(Arrays.asList(testUser.getFaveVenues()).contains(venueName.replaceAll("_"," ")));

        //Click to unfavourite
        clickOn("#faveVenueButton");
        sleep(500);
        FxAssert.verifyThat("#faveVenueButton",LabeledMatchers.hasText("Favourite"));
        testUser.setFaveVenues(client.requestFaveVenueList());
        Assert.assertFalse(Arrays.asList(testUser.getFaveVenues()).contains(venueName));
    }

    //TODO: Media Handlers


}
