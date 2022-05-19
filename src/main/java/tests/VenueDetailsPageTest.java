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

import java.io.IOException;
import java.util.concurrent.TimeoutException;

//These are the tests which test that the venue detials page works correctly
public class VenueDetailsPageTest extends ApplicationTest {


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
    //Checks the venue details page can actually be opened
    public void venueDetailsPageOpenTest(){

        //Log into the application
        clickOn("#usernameTextField");
        write("test");
        clickOn("#userPassField");
        write("12345");
        clickOn("#loginButton");

        sleep(1000);

        //Open the venue page using the side list
        doubleClickOn("#venueList");
        sleep(1000);




        //Verify that the correct window is open
        //Needs to be programmatic but cannot get the current controller of the current page?
        FxAssert.verifyThat(window("Dusk"), WindowMatchers.isShowing());





    }




}
