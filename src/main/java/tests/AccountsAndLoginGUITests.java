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
import ServerClientUtility.User;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

/**
 * <p>
 *     Tests for the Accounts/Login GUI pages.
 * </p>
 */
public class AccountsAndLoginGUITests extends ApplicationTest {

    private LoginController loginController; //Reference to controller so testing user can be created manually.
    private Client client;

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
        loginController.createTestAccount();
    }

    @After
    public void afterEachTest() throws TimeoutException, IOException {
        release(new KeyCode[]{});
        release(new MouseButton[]{});
        FxToolkit.hideStage();
        loginController.client.deleteUser(new User("IntTestUser","password")); //Deletes IntTestUser from database, otherwise test would only work once.
        loginController.setTestingMode(false);
    }

    //Integration Test | Confirm user can log in with real username and password by clicking "Login" after entering them.
    @Test
    public void realLoginTest(){
        sleep(1000);
        clickOn("#usernameTextField");
        write("accountTestUser");
        clickOn("#userPassField");
        write("accountTest");
        clickOn("#loginButton");
        sleep(2000);
        FxAssert.verifyThat(window("WiseGuide by Maptrix - Ver 0.45"), WindowMatchers.isShowing());
    }

    //Integration Test | Confirm user cannot log in if they have entered a real username but incorrect password.
    @Test
    public void realUserWrongPassTest(){
        sleep(1000);
        clickOn("#usernameTextField");
        write("accountTestUser");
        clickOn("#userPassField");
        write("incorrectPassword");
        clickOn("#loginButton");
        FxAssert.verifyThat("#errorLabel", LabeledMatchers.hasText("Unrecognised user details"));
    }

    //Integration Test | Confirm user cannot log in if they have entered an incorrect username and password.
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
    @Test
    public void createNewUserTest(){
        sleep(1000);
        loginController.setTestingMode(true);
        clickOn("#createAccButton");
        clickOn("#usernameField");
        write("IntTestUser");
        clickOn("#passField");
        write("password");
        clickOn("#passConfirmField");
        write("password");
        clickOn("#ageCheckBox");
        clickOn("#createAccountButton");
        clickOn("#closePopupButton");
    }

    //Integration Test | Confirm a user cannot be created if the username is taken.
    @Test
    public void createExistingUser(){
        sleep(1000);
        loginController.setTestingMode(true);
        clickOn("#createAccButton");
        clickOn("#usernameField");
        write("accountTestUser");
        clickOn("#passField");
        write("password");
        clickOn("#passConfirmField");
        write("password");
        clickOn("#ageCheckBox");
        clickOn("#createAccountButton");
        FxAssert.verifyThat("#errLabel", LabeledMatchers.hasText("This username is taken"));
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
        FxAssert.verifyThat("#errLabel", LabeledMatchers.hasText("You are not over the age of 13!"));
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
        FxAssert.verifyThat("#errLabel", LabeledMatchers.hasText("You have not entered a username!"));
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
        FxAssert.verifyThat("#errLabel", LabeledMatchers.hasText("You have not entered a password!"));
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
        FxAssert.verifyThat("#errLabel", LabeledMatchers.hasText("The passwords do not match!"));
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
        FxAssert.verifyThat("#errLabel", LabeledMatchers.hasText("The passwords do not match!"));
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
        FxAssert.verifyThat("#errLabel", LabeledMatchers.hasText("The passwords do not match!"));
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
        FxAssert.verifyThat("#errLabel", LabeledMatchers.hasText("The passwords do not match!"));
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
        FxAssert.verifyThat("#errLabel", LabeledMatchers.hasText("You have not entered a username!"));
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
        FxAssert.verifyThat("#errLabel", LabeledMatchers.hasText("The passwords do not match!"));
    }

    //Integration Test | Confirm that a user cannot create an account with a forbidden name.
    @Test
    public void createReservedUserAttemptTest(){
        //TextInputControls.clearTextIn
        sleep(1000);
        loginController.setTestingMode(false);
        clickOn("#createAccButton");
        clickOn("#usernameField");
        write("userDoesNotExist");
        clickOn("#passField");
        write("password");
        clickOn("#passConfirmField");
        write("password");
        clickOn("#ageCheckBox");
        clickOn("#createAccountButton");
        FxAssert.verifyThat("#errLabel",LabeledMatchers.hasText("The selected username is unavailable."));
    }

    @Test
    //Integration Test | Confirm the username can't be > 15 characters.
    public void userOver15charsTest(){
        sleep(1000);
        loginController.setTestingMode(false);
        clickOn("#createAccButton");
        clickOn("#usernameField");
        write("0123456789ABCDEF");
        clickOn("#passField");
        write("password");
        clickOn("#passConfirmField");
        write("password");
        clickOn("#ageCheckBox");
        clickOn("#createAccountButton");
        FxAssert.verifyThat("#errLabel",LabeledMatchers.hasText("Username can't be more than 15 characters!"));
    }

    @Test
    //Integration Test | Confirm the password can't be > 15 characters.
    public void passOver15charsTest(){
        sleep(1000);
        loginController.setTestingMode(false);
        clickOn("#createAccButton");
        clickOn("#usernameField");
        write("username");
        clickOn("#passField");
        write("0123456789ABCDEF");
        clickOn("#passConfirmField");
        write("0123456789ABCDEF");
        clickOn("#ageCheckBox");
        clickOn("#createAccountButton");
        FxAssert.verifyThat("#errLabel",LabeledMatchers.hasText("Password can't be more than 15 characters!"));
    }

}
