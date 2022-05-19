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
    //     - Login with incorrect password
    //     - Login correctly
    //     - Open the about page
    //     - Close the about page
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

        //Close the popup page
        clickOn("#closePopupButton");

        //Verify that the login page is open



    }




}
