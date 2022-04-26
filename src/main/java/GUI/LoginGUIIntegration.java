package GUI;

import client.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import server.ServerUserHandler;
import serverclientstuff.User;

import java.awt.*;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

public class LoginGUIIntegration extends ApplicationTest {

    /* ===== INTEGRATION TESTS for Login and GUI ===== */

    private LoginController loginController; //Reference to controller so testing user can be created manually.
    private Client client;

    //TODO: Account Creation | Any limits on what characters allowed in username/password? These will need testing.

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(LoginApplication.class.getResource("login-page.fxml")));
        Parent mainNode = loader.load();
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
        this.loginController = loader.getController();

    }

    @Before
    public void setUpClass() throws Exception {

        loginController.initialConnection();

        //TODO: Hardcode testing user into database.

    }

    @After
    public void afterEachTest() throws TimeoutException {
        release(new KeyCode[]{});
        release(new MouseButton[]{});
        FxToolkit.hideStage();
        //TODO: IntTestUser should be deleted from database after the test, otherwise test will only work once! (AC)
    }

    //Integration Test | Confirm user can login with real username and password by clicking "Login" after entering them.
    @Test
    public void realLoginTest(){
        sleep(1000);
        clickOn("#usernameTextField");
        write("testUser");
        clickOn("#userPassField");
        write("testPassword");
        clickOn("#loginButton");
        sleep(2000);
        FxAssert.verifyThat(window("WiseGuide by Maptrix - Ver 0.45"), WindowMatchers.isShowing());
    }

    //Integration Test | Confirm user cannot login if they have entered a real username but incorrect password.
    @Test
    public void realUserWrongPassTest(){
        sleep(1000);
        clickOn("#usernameTextField");
        write("testUser");
        clickOn("#userPassField");
        write("incorrectPassword");
        clickOn("#loginButton");
        FxAssert.verifyThat("#errorLabel", LabeledMatchers.hasText("Unrecognised user details"));
    }

    //Integration Test | Confirm user cannot login if they have entered an incorrect username and password.
    //TODO: Account creation should forbid the creation of an account with this username so that this test never breaks.
    @Test
    public void fakeUserWrongPassTest(){
        sleep(1000);
        clickOn("#usernameTextField");
        write("userDoesNotExist");
        clickOn("#userPassField");
        write("password");
        clickOn("#loginButton");
        FxAssert.verifyThat("#errorLabel", LabeledMatchers.hasText("Unrecognised user details"));
    }

    //Integration Test | Confirm that a new user can be created by entering unique username, acceptable password,
    //and by checking the checkbox.

    //TODO: Account creation should forbid the username IntTestUser to prevent this test from breaking.
    @Test
    public void createNewUserTest(){
        sleep(1000);
        clickOn("#createAccButton");
        clickOn("#usernameField");
        write("IntTestUser");
        clickOn("#passField");
        write("password");
        clickOn("#passConfirmField");
        write("password");
        clickOn("#ageCheckBox"); //This clicks on the centre of the text!
        clickOn("#createAccountButton");
        clickOn("#closePopupButton");
    }

    //Integration Test | Confirm a user cannot be created if the username is taken.
    @Test
    public void createExistingUser(){
        sleep(1000);
        clickOn("#createAccButton");
        clickOn("#usernameField");
        write("testUser");
        clickOn("#passField");
        write("password");
        clickOn("#passConfirmField");
        write("password");
        clickOn("#ageCheckBox");
        //Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        //clickOn((screensize.getWidth()/2 - 93),(screensize.getHeight()/2-103));
        clickOn("#createAccountButton");
        FxAssert.verifyThat("#errField", LabeledMatchers.hasText("This username is taken"));
    }


    //Integration Test | Confirm a user cannot be created if the checkbox is left unchecked.
    @Test
    public void createUserWithoutCheckboxTest(){
        sleep(1000);
        clickOn("#createAccButton");
        clickOn("#usernameField");
        write("testUser");
        clickOn("#passField");
        write("password");
        clickOn("#passConfirmField");
        write("password");
        clickOn("#createAccountButton");
        FxAssert.verifyThat("#errField", LabeledMatchers.hasText("You are not over the age of 13!"));
    }

    //Integration Test | Confirm a user cannot be created if all fields are left blank.
    /*
    USER: N
    PASS: N
    CONF: N
     */
    @Test
    public void createBlankUserTest(){
        sleep(1000);
        clickOn("#createAccButton");
        clickOn("#createAccountButton");
        FxAssert.verifyThat("#errField", LabeledMatchers.hasText("You have not entered a username!"));
    }

    //Integration Test | Confirm a user cannot be created if a username is entered without a password or password confirmation.
    /*
    USER: Y
    PASS: N
    CONF: N
     */
    @Test
    public void createUserUsernameOnlyTest(){
        sleep(1000);
        clickOn("#createAccButton");
        clickOn("#usernameField");
        write("testUser");
        clickOn("#createAccountButton");
        FxAssert.verifyThat("#errField", LabeledMatchers.hasText("You have not entered a password!"));
    }

    //Integration Test | Confirm a user cannot be created if a password is entered without a username or password confirmation.
    /*
    USER: N
    PASS: Y
    CONF: N
     */
    @Test
    public void createUserPasswordOnlyTest(){
        sleep(1000);
        clickOn("#createAccButton");
        clickOn("#passField");
        write("password");
        clickOn("#createAccountButton");
        FxAssert.verifyThat("#errField", LabeledMatchers.hasText("The passwords do not match!"));
    }

    //Integration Test | Confirm a user cannot be created if the password confirmation is entered without a username or password.
    /*
    USER: N
    PASS: N
    CONF: Y
     */
    @Test
    public void createUserConfirmPasswordOnlyTest(){
        sleep(1000);
        clickOn("#createAccButton");
        clickOn("#passConfirmField");
        write("password");
        clickOn("#createAccountButton");
        FxAssert.verifyThat("#errField", LabeledMatchers.hasText("The passwords do not match!"));
    }

    //Integration Test | Confirm a user cannot be created if a username and password are entered but the password is not confirmed.
    /*
    USER: Y
    PASS: Y
    CONF: N
     */
    @Test
    public void createUserUsernameAndPasswordOnlyTest(){
        sleep(1000);
        clickOn("#createAccButton");
        clickOn("#usernameField");
        write("testUser");
        clickOn("#passField");
        write("password");
        clickOn("#createAccountButton");
        FxAssert.verifyThat("#errField", LabeledMatchers.hasText("The passwords do not match!"));
    }

    //Integration Test | Confirm a user cannot be created if a username and password confirmation are entered without a password.
    /*
    USER: Y
    PASS: N
    CONF: Y
     */
    @Test
    public void createUserUsernameAndConfirmOnlyTest(){
        sleep(1000);
        clickOn("#createAccButton");
        clickOn("#usernameField");
        write("testUser");
        clickOn("#passConfirmField");
        write("password");
        clickOn("#createAccountButton");
        FxAssert.verifyThat("#errField", LabeledMatchers.hasText("The passwords do not match!"));
    }

    //Integration Test | Confirm a user cannot be created if a password and confirmation are entered without a username.
    /*
    USER: N
    PASS: Y
    CONF: Y
     */
    @Test
    public void createUserPasswordAndConfirmOnlyTest(){
        sleep(1000);
        clickOn("#createAccButton");
        clickOn("#passField");
        write("password");
        clickOn("#passConfirmField");
        write("password");
        clickOn("#createAccountButton");
        FxAssert.verifyThat("#errField", LabeledMatchers.hasText("You have not entered a username!"));
    }

    //Integration Test | Confirm a user cannot be created if a username is entered but the passwords do not match.
    @Test
    public void createUserMismatchedPasswordsTest(){
        sleep(1000);
        clickOn("#createAccButton");
        clickOn("#usernameField");
        write("testUser");
        clickOn("#passField");
        write("password");
        clickOn("#passConfirmField");
        write("mismatch");
        clickOn("#createAccountButton");
        FxAssert.verifyThat("#errField", LabeledMatchers.hasText("The passwords do not match!"));
    }

}
