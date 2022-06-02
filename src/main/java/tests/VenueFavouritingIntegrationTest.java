package tests;

import GUI.LoginApplication;
import GUI.LoginController;
import client.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.WindowMatchers;
import org.testfx.matcher.control.LabeledMatchers;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class VenueFavouritingIntegrationTest extends ApplicationTest {

    public Client client;
    private LoginController controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("login-page.fxml"));
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
    }

    @After
    public void afterEachTest() throws TimeoutException {
        release(new KeyCode[]{});
        release(new MouseButton[]{});
        FxToolkit.hideStage();
    }



    @Test
    //Integration test - verifies that the user can favourite and un-favourite venues using the GUI (which implicitly uses the server)
    //RUN serverMain before this
    public void venueFavouriteGUIandServerIntegrationTest(){

        //Log the user into the program
        sleep(1000);
        clickOn("#usernameTextField");
        write("test");
        sleep(1000);
        clickOn("#userPassField");
        write("12345");
        sleep(1000);
        clickOn("#loginButton");
        sleep(1000);
        doubleClickOn("#venueList");

        //Check to see that the venue isn't favourited
        FxAssert.verifyThat("#faveVenueButton", LabeledMatchers.hasText("Favourite"));

        sleep(1000);

        clickOn("#faveVenueButton");

        sleep(1000);

        //Check to see that the venue button text has changed
        FxAssert.verifyThat("#faveVenueButton", LabeledMatchers.hasText("UnFavourite"));

        sleep(1000);

        //Go back onto the main page
        clickOn("#goBack");

        sleep(1000);

        doubleClickOn("#venueList");

        //Check to make sure that the venue is still favourited
        FxAssert.verifyThat("#faveVenueButton", LabeledMatchers.hasText("UnFavourite"));

        sleep(1000);

        clickOn("#faveVenueButton");

        sleep(1000);

        //Check to see if the venue has been un-favourited
        FxAssert.verifyThat("#faveVenueButton", LabeledMatchers.hasText("Favourite"));





    }


}
