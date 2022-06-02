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
    //YOU NEED TO RUN THE SERVER FIRST
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

        //Verifies that the correct username is showing
        FxAssert.verifyThat("#usernameLabel", LabeledMatchers.hasText(controller.getCurrUser().getUsername()));


        sleep(1000);

        clickOn("#changeNameButton");

        sleep(1000);
        //Verifies that the change username window is open
        FxAssert.verifyThat(window("Change username"), WindowMatchers.isShowing());
        //Verify that the displayed username is correct
        FxAssert.verifyThat("#usernameLabel", LabeledMatchers.hasText(controller.getCurrUser().getUsername()));

        clickOn("#desiredNameField");

        String newUsername = "testname";

        write(newUsername);

        sleep(1000);

        clickOn("#confirmButton");

        sleep(1000);
        //Verify that after pressing confirm the user is taken back to the main page
        FxAssert.verifyThat(window("WiseGuide by Maptrix - " + client.getCurrVersion()), WindowMatchers.isShowing());

        sleep(1000);

        clickOn("#menuBarAccount").clickOn("#signOutButton");

        //Verify that the user is taken back to the login page
        FxAssert.verifyThat(window("Welcome to WiseGuide"), WindowMatchers.isShowing());

        sleep(1000);

        //Log in to the application
        sleep(1000);
        clickOn("#usernameTextField");
        write(newUsername);
        sleep(1000);
        clickOn("#userPassField");
        write("12345");
        sleep(1000);
        clickOn("#loginButton");
        sleep(1000);

        //Verify that the user has logged into the main page with the new name successfully
        FxAssert.verifyThat(window("WiseGuide by Maptrix - " + client.getCurrVersion()), WindowMatchers.isShowing());


        sleep(1000);

        //Open the user details page
        clickOn("#menuBarAccount").clickOn("#accDetailsButton");

        //Verifies that the account details page is open
        FxAssert.verifyThat(window("Account Details"), WindowMatchers.isShowing());

        //Verifies that the correct username is showing
        FxAssert.verifyThat("#usernameLabel", LabeledMatchers.hasText(newUsername));

        sleep(1000);

        clickOn("#changePassButton");

        sleep(1000);

        FxAssert.verifyThat(window("Change Password"), WindowMatchers.isShowing());

        clickOn("#currPasswordField");

        write("12345");

        String newPass = "54321";

        clickOn("#newPasswordField");

        write(newPass);

        clickOn("#newPasswordConfirmField");

        write(newPass);

        clickOn("#changePassButton");

        sleep(1000);

        //Verify that the user is taken back to the main page
        FxAssert.verifyThat(window("WiseGuide by Maptrix - " + client.getCurrVersion()), WindowMatchers.isShowing());

        sleep(1000);

        clickOn("#menuBarAccount").clickOn("#signOutButton");

        //Verify that the user is taken back to the login page
        FxAssert.verifyThat(window("Welcome to WiseGuide"), WindowMatchers.isShowing());

        sleep(1000);

        //Log in to the application
        sleep(1000);
        clickOn("#usernameTextField");
        write(newUsername);
        sleep(1000);
        clickOn("#userPassField");
        write(newPass);
        sleep(1000);
        clickOn("#loginButton");
        sleep(1000);

        //Verify that the user can log in with the new password
        FxAssert.verifyThat(window("WiseGuide by Maptrix - " + client.getCurrVersion()), WindowMatchers.isShowing());

        sleep(1000);

        //Change the username nad password back to the original in the same login session

        //Open the user details page
        clickOn("#menuBarAccount").clickOn("#accDetailsButton");


        clickOn("#changeNameButton");

        sleep(1000);

        //Verifies that the change username window is open
        FxAssert.verifyThat(window("Change username"), WindowMatchers.isShowing());
        //Verify that the displayed username is correct
        FxAssert.verifyThat("#usernameLabel", LabeledMatchers.hasText(controller.getCurrUser().getUsername()));

        clickOn("#desiredNameField");

        String oldUsername = "test";

        write(oldUsername);

        sleep(1000);

        clickOn("#confirmButton");

        sleep(1000);


        //Open the user details page
        clickOn("#menuBarAccount").clickOn("#accDetailsButton");

        sleep(1000);

        clickOn("#changePassButton");

        sleep(1000);

        FxAssert.verifyThat(window("Change Password"), WindowMatchers.isShowing());

        clickOn("#currPasswordField");

        write(newPass);

        String oldPass = "12345";

        clickOn("#newPasswordField");

        write(oldPass);

        clickOn("#newPasswordConfirmField");

        write(oldPass);

        clickOn("#changePassButton");

        sleep(1000);

        //Sign out of the application
        clickOn("#menuBarAccount").clickOn("#signOutButton");

        sleep(1000);

        //Log into the application using the old details after they have been changed back
        sleep(1000);
        clickOn("#usernameTextField");
        write(oldUsername);
        sleep(1000);
        clickOn("#userPassField");
        write(oldPass);
        sleep(1000);
        clickOn("#loginButton");
        sleep(1000);

        //Verify that the user has logged into the main application

        FxAssert.verifyThat(window("WiseGuide by Maptrix - " + client.getCurrVersion()), WindowMatchers.isShowing());

        clickOn("#menuBarAccount").clickOn("#accDetailsButton");

        sleep(1000);

        //Verify that the old username is now displayed in the user details page
        FxAssert.verifyThat("#usernameLabel", LabeledMatchers.hasText(oldUsername));


        //Test to make sure that a user cannot change to a name that is already taken (in this case their own name)

        clickOn("#changeNameButton");
        sleep(1000);
        clickOn("#desiredNameField");
        write(oldUsername);
        clickOn("#confirmButton");

        //Check that the error label is displaying the correct error message
        FxAssert.verifyThat("#errLabel", LabeledMatchers.hasText("This is your current name!"));

    }







}
