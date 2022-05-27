package tests;


import GUI.LoginApplication;
import GUI.LoginController;
import client.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Labeled;
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
import org.testfx.service.finder.WindowFinder;

import java.awt.*;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

//This is a test to ensure that the main application works correctly
//Needs the map sections adding
//For now it is a test that the software works if the user wants to click through the buttons one by one
public class MainApplicationIntegrationTest extends ApplicationTest {

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

    //TODO
    //     - Open a venue page (using the list)
    //     - Close the venue page
    //     -Open the account information page
    //     - Delete there account (then test that the user can no longer log in)
    //Probably more to do including the map tests - it has to end with the account being deleted



    @Test
    //Opens the test and does some random things that an actual user might actually do
    //RUN THE SERVER FIRST
    public void mainApplicationTest(){

        //First thing a user may do is create an account
        sleep(1000);
        clickOn("#createAccButton");

        //Verify that the account creation page is open
        FxAssert.verifyThat(window("Account Creation"), WindowMatchers.isShowing());

        sleep(1000);
        //Create an account with an already taken name
        clickOn("#usernameField");
        write("test");
        clickOn("#passField");
        write("12345");
        //Enter the wrong password into the password confirmation box
        clickOn("#passConfirmField");
        write("123");

        clickOn("#createAccountButton");

        //Verify that the correct error label appears
        FxAssert.verifyThat("#errLabel", LabeledMatchers.hasText("The passwords do not match!"));

        sleep(1000);

        clickOn("#passConfirmField");
        write("45");

        clickOn("#createAccountButton");
        sleep(1000);
        //Verify that the correct error label appears
        FxAssert.verifyThat("#errLabel", LabeledMatchers.hasText("You are not over the age of 13!"));

        //Click the checkbox and create an account
        clickOn("#ageCheckBox");
        clickOn("#createAccountButton");

        //Verify that the correct error label is displayed (name already in use)
        FxAssert.verifyThat("#errLabel", LabeledMatchers.hasText("This username is taken."));

        //Change the username to a non-taken one
        clickOn("#usernameField");
        write("test");
        //Create the account
        clickOn("#createAccountButton");

        //Verify that the account created page has been opened
        sleep(1000);
        FxAssert.verifyThat(window("Account Created"), WindowMatchers.isShowing());

        sleep(1000);
        //Close the popup page
        clickOn("#closePopupButton");

        //Attempt to login with the incorrect password

        sleep(1000);
        clickOn("#usernameTextField");
        write("testtest");
        clickOn("#userPassField");
        write("1234");
        sleep(1000);

        clickOn("#loginButton");

        sleep(1000);

        //Verify that the correct error message is displayed
        FxAssert.verifyThat("#errorLabel", LabeledMatchers.hasText("Unrecognised user details"));

        //Enter correct login details
        clickOn("#userPassField");
        write("5");
        clickOn("#loginButton");

        //Verify that the main page is opened
        FxAssert.verifyThat(window("WiseGuide by Maptrix - " + client.getCurrVersion()), WindowMatchers.isShowing());



        //Open the about page
        clickOn("#menuBarHelp").clickOn("#aboutButton");


        //Verify that the about page is open
        FxAssert.verifyThat(window("About Page"), WindowMatchers.isShowing());

        //Verify that the version number label is correct
        FxAssert.verifyThat("#verNumLabel", LabeledMatchers.hasText(client.getCurrVersion()));

        sleep(1000);

        //Close the about page - MAybe not necessary
        //interact(()->((Stage)((lookup(".error").query())).getScene().getWindow()).close());



        //Verify that the main page is still open
        FxAssert.verifyThat(window("WiseGuide by Maptrix - " + client.getCurrVersion()), WindowMatchers.isShowing());

        doubleClickOn("#venueList");

        sleep(1000);

        String defaultVenue = "Dusk";

        //Verify that the dusk page is opened
        FxAssert.verifyThat(window(defaultVenue), WindowMatchers.isShowing());


        clickOn("#faveVenueButton");
        //Verify that the button has changed its text
        sleep(1000);
        FxAssert.verifyThat("#faveVenueButton", LabeledMatchers.hasText("UnFavourite"));

        //Go back to the main page
        sleep(1000);
        clickOn("#goBack");

        //Verify that the main page is open
        FxAssert.verifyThat(window("WiseGuide by Maptrix - " + client.getCurrVersion()), WindowMatchers.isShowing());

        //Open the account details page
        clickOn("#menuBarAccount").clickOn("#accDetailsButton");
        //Verify that the correct page is open
        FxAssert.verifyThat(window("Account Details"), WindowMatchers.isShowing());
        sleep(1000);

        //Open the delete account page
        clickOn("#deleteAccountButton");

        //Verify that the correct page is open
        FxAssert.verifyThat(window("Account Deletion"), WindowMatchers.isShowing());

        //Enter passwords and delete account
        clickOn("#passField");
        write("12345");
        clickOn("#passConfirmField");
        write("12345");
        clickOn("#delCheckBox");
        sleep(1000);
        clickOn("#deleteAccountButton");

        sleep(1000);
        //Verify that the account deleted page opens
        FxAssert.verifyThat(window("Account Deleted"), WindowMatchers.isShowing());

        sleep(1000);
        //Close the popup
        clickOn("#closePopUpButton");
        sleep(1000);

        //Verify that the user can no longer login with the deleted account
        sleep(1000);
        clickOn("#usernameTextField");
        write("testtest");
        clickOn("#userPassField");
        write("12345");
        sleep(1000);
        clickOn("#loginButton");
        sleep(1000);

        //Verify that the correct error message is displayed
        FxAssert.verifyThat("#errorLabel", LabeledMatchers.hasText("Unrecognised user details"));



    }




}
