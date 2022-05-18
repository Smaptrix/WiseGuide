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

import java.util.Objects;
import java.util.concurrent.TimeoutException;

public class AccountDetailsPageIntegrationTest extends ApplicationTest {

    private Stage stage;
    private LoginController controller;
    public Client client;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(LoginApplication.class.getResource("login-page.fxml")));
        Parent mainNode = loader.load();
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
        this.stage = stage;
        this.controller = loader.getController();
    }

    @Before
    public void setUpClass() throws Exception {
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
    //Tests to make sure that if a user logs in, the user details page works correctly
    public void accountDetailsIntegrationTest(){

        //Log in to the application
        sleep(1000);
        clickOn("#usernameTextField");
        write("test");
        sleep(1000);
        clickOn("#userPassField");
        write("12345");
        sleep(1000);
        clickOn("#loginButton");
        sleep(1000);

        //Open the user details page
        clickOn("#menuBarAccount").clickOn("#accDetailsButton");

        //Verifies that the account details page is open
        FxAssert.verifyThat(window("Account Details"), WindowMatchers.isShowing());



    }







}
