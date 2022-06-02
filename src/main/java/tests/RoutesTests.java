package tests;

import GUI.MainApplication;
import GUI.RouteDetailsController;
import VenueXMLThings.VenueXMLParser;
import client.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.WindowMatchers;
import org.testfx.matcher.control.LabeledMatchers;
import serverclientstuff.User;

import java.io.File;
import java.io.IOException;

/**
 * <p>
 *     Tests for the route details page.
 * </p>
 */
public class RoutesTests extends ApplicationTest {

    RouteDetailsController controller;
    Client client;
    VenueXMLParser xml;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("RouteDetailsPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        controller = fxmlLoader.getController();
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    //Manual Testing | Opens a blank routes page for GUI verification. Actual route pages should be tested via the
    //normal application as the route details cannot be loaded on the main thread.
    @Test
    public void manualTest() {
        sleep(1000000);
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
        FxAssert.verifyThat(window("About Page"), WindowMatchers.isShowing());
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
        FxAssert.verifyThat("#routeName",Node::isVisible);
        FxAssert.verifyThat("#routeName", LabeledMatchers.hasText("ROUTE NAME"));
    }

    //Unit Test | Check Route Text
    @Test
    public void routeTextTest(){
        sleep(1000);
        FxAssert.verifyThat("#routeTextPane",Node::isVisible);
        FxAssert.verifyThat("#routeTextPane",LabeledMatchers.hasText("Text Description"));
    }

    //Unit Test | Check Venues Text
    @Test
    public void venuesTextTest(){
        sleep(1000);
        FxAssert.verifyThat("#venueTextPane",Node::isVisible);
        FxAssert.verifyThat("#venueTextPane",LabeledMatchers.hasText("Venues In Route"));
    }

}
