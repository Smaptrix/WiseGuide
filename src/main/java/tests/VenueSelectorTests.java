package tests;

import GUI.LoginApplication;
import GUI.LoginController;
import GUI.VenueSelectPageController;
import VenueXMLThings.VenueXMLParser;
import client.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.WindowMatchers;
import org.testfx.matcher.control.LabeledMatchers;
import serverclientstuff.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class VenueSelectorTests extends ApplicationTest {

    public Client client;
    private VenueSelectPageController controller;
    private User user = new User("test","12345");
    private VenueXMLParser xml;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("venue-selector-page.fxml"));
        Parent mainNode = fxmlLoader.load();
        stage.setScene(new Scene(mainNode));

        this.controller = fxmlLoader.getController();
        stage.show();
        stage.toFront();

    }


    @Before
    public void setUpClass() throws IOException {
        client = new Client(); // Creates new instance of client object
        client.startConnection("127.0.0.1", 5555);
        controller.setClient(client);
        controller.setCurrUser(user);

        //Copied from MainController.
        File xmlFile = new File("venuesLocation.xml");
        Assert.assertTrue(xmlFile.exists());
        xml = new VenueXMLParser(xmlFile);
        controller.setXml(xml);

        List<String> venueNameList = xml.getPageNames();
        List<String> strippedListOfVenues =  new ArrayList();

        for(String s: venueNameList){
            s =  s.substring(7, s.length() -1);
            strippedListOfVenues.add(s);
        }
        controller.setListOfVenues(strippedListOfVenues);

    }

    @After
    public void afterEachTest() throws TimeoutException {
        release(new KeyCode[]{});
        release(new MouseButton[]{});
        FxToolkit.hideStage();
    }

    //Unit Test | Confirm title displays correctly.
    @Test
    public void titleTest(){
        FxAssert.verifyThat("#title", Node::isVisible);
        FxAssert.verifyThat("#title", LabeledMatchers.hasText("Venue Selector!"));
    }

    //Unit Test | Confirm options label displays correctly.
    @Test
    public void optionsLabelTest(){
        FxAssert.verifyThat("#optionsLabel", Node::isVisible);
        FxAssert.verifyThat("#optionsLabel", LabeledMatchers.hasText("Options"));
    }

    //Unit Test | Confirm type label displays correctly.
    @Test
    public void typeLabelTest(){
        FxAssert.verifyThat("#typeLabel", Node::isVisible);
        FxAssert.verifyThat("#typeLabel", LabeledMatchers.hasText("Venue Type"));
    }

    //Unit Test | Confirm Favourite Checkbox displays correctly and can be clicked.
    @Test
    public void faveCheckboxTest(){
        FxAssert.verifyThat("#faveCheckBox",Node::isVisible);
        FxAssert.verifyThat("#faveCheckBox",LabeledMatchers.hasText("Favourited "));
        clickOn("#faveCheckBox");
        Assert.assertTrue(controller.faveCheckBox.isSelected());
    }

    //Unit Test | Confirm Food Checkbox displays correctly and can be clicked.
    @Test
    public void foodCheckboxTest(){
        FxAssert.verifyThat("#foodCheckBox",Node::isVisible);
        FxAssert.verifyThat("#foodCheckBox",LabeledMatchers.hasText("Food"));
        clickOn("#foodCheckBox");
        Assert.assertTrue(controller.foodCheckBox.isSelected());
    }

    //Unit Test | Confirm Sightseeing Checkbox displays correctly and can be clicked.
    @Test
    public void sightseeingCheckboxTest(){
        FxAssert.verifyThat("#sightseeingCheckBox",Node::isVisible);
        FxAssert.verifyThat("#sightseeingCheckBox",LabeledMatchers.hasText("Sightseeing"));
        clickOn("#sightseeingCheckBox");
        Assert.assertTrue(controller.sightseeingCheckBox.isSelected());
    }

    //Unit Test | Confirm Drinks Checkbox displays correctly and can be clicked.
    @Test
    public void drinksCheckboxTest(){
        FxAssert.verifyThat("#drinksCheckBox",Node::isVisible);
        FxAssert.verifyThat("#drinksCheckBox",LabeledMatchers.hasText("Drinks"));
        clickOn("#drinksCheckBox");
        Assert.assertTrue(controller.drinksCheckBox.isSelected());
    }

    //Unit Test | Confirm Study Checkbox displays correctly and can be clicked.
    @Test
    public void studyCheckboxTest(){
        FxAssert.verifyThat("#studySpacesCheckBox",Node::isVisible);
        FxAssert.verifyThat("#studySpacesCheckBox",LabeledMatchers.hasText("Study Spaces"));
        clickOn("#studySpacesCheckBox");
        Assert.assertTrue(controller.studySpacesCheckBox.isSelected());
    }

    //Unit Test | Confirm pick venue button displays correctly and can be clicked.
    @Test
    public void pickVenueButtonTest(){
        FxAssert.verifyThat("#venuePickButton",Node::isVisible);
        FxAssert.verifyThat("#venuePickButton",LabeledMatchers.hasText("Pick a Venue!"));
        clickOn("#venuePickButton");
        FxAssert.verifyThat("#errLabel",LabeledMatchers.hasText("You have not selected any venue types!"));
    }

    //Unit Test | Confirm error label is invisible by default.
    @Test
    public void errLabelTest(){
        FxAssert.verifyThat("#errLabel",Node::isVisible);
        FxAssert.verifyThat("#errLabel",LabeledMatchers.hasText(""));
    }

    //Unit Test | Confirm random venue button displays correctly and can be clicked.
    @Test
    public void randomVenueButtonTest(){
        sleep(1000);
        FxAssert.verifyThat("#randomButton",Node::isVisible);
        FxAssert.verifyThat("#randomButton",LabeledMatchers.hasText("Random Venue!"));
        clickOn("#randomButton");
        sleep(100);
        String randomVenueName = controller.randomVenue;
        FxAssert.verifyThat(window(randomVenueName.replaceAll("_"," ")), WindowMatchers.isShowing());
    }



    //Manual Tests | Provides an opportunity for manual tests to be performed
    //Tests to be carried out:
    // - Confirm GUI layout is correct.
    @Test
    public void manualTests(){
        sleep(1000000);
    }

}
