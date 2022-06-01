package tests;

import GUI.LoginApplication;
import GUI.VenueDetailsController;
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

import java.awt.*;
import java.io.IOException;

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


    //Fav Button

    //TODO: Media Handlers


}
