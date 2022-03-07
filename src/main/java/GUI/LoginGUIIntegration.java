package GUI;

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

    //TODO: Account Creation | ANY LIMITS ON WHAT CHARACTERS ALLOWED IN USER/PASS??

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

        //Creates a testing user if one does not already exist.
        //TODO: Ideally this should be hard-coded into the user database and not created here.
        User testingUser = new User("testUser","testPassword");
        testingUser.hashUserInfo();
        ServerUserHandler desiredUser = new ServerUserHandler(testingUser, true);
        desiredUser.verifyUser();
        if(desiredUser.userExistState){
            System.out.println("WARNING: Testing User Already Exists!");
        }
        if(loginController.getClient().createUser(testingUser).equals("USERCREATED")){
            System.out.println("SUCCESS: Testing User Created");
        } else {
            System.out.println("ERROR: Testing User could not be created!");
        }

    }

    @After
    public void afterEachTest() throws TimeoutException {
        release(new KeyCode[]{});
        release(new MouseButton[]{});
        FxToolkit.hideStage();

        //TODO: IntTestUser should be deleted from database after the test, otherwise test will only work once!
    }

    //Login real user, real password
    @Test
    public void realLoginTest(){
        sleep(1000);
        clickOn("#usernameTextField");
        write("testUser");
        clickOn("#userPassField");
        write("testPassword");
        clickOn("#loginButton");
        sleep(1000);
        FxAssert.verifyThat(window("WiseGuide by Maptrix - V1.0.0"), WindowMatchers.isShowing());
    }

    //Login real user, incorrect password
    @Test
    public void realUserWrongPassTest(){
        sleep(1000);
        clickOn("#usernameTextField");
        write("testUser");
        clickOn("#userPassField");
        write("incorrectPassword");
        clickOn("#loginButton");
        FxAssert.verifyThat("#errorLabel", LabeledMatchers.hasText("Incorrect Password!"));
    }

    //Login user that does not exist
    //TODO: Account creation should forbid the creation of an account with this username so that this test never breaks.
    @Test
    public void fakeUserWrongPassTest(){
        sleep(1000);
        clickOn("#usernameTextField");
        write("userDoesNotExist");
        clickOn("#userPassField");
        write("password");
        clickOn("#loginButton");
        FxAssert.verifyThat("#errorLabel", LabeledMatchers.hasText("User does not exist!"));
    }

    //Create New User Test
    //TODO: Account creation should forbid the username IntTestUser to prevent this test from breaking.
    //TODO: Currently only works for AC's screen size/resolution...
    //In theory this test should be enough to confirm a user exists, as if the closePopupButton
    //exists then that implies the account creation process was successful.
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
        //clickOn("#ageCheckBox"); //This clicks on the centre of the text!
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        clickOn((screensize.getWidth()/2 - 93),(screensize.getHeight()/2-103));
        clickOn("#createAccountButton");
        clickOn("#closePopupButton");
        //TODO: Verify user exists in database...? Maybe not necessary?
    }

    //Create Existing User Attempt
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
        //clickOn("#ageCheckBox");
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        clickOn((screensize.getWidth()/2 - 93),(screensize.getHeight()/2-103));
        clickOn("#createAccountButton");
        FxAssert.verifyThat("#errField", LabeledMatchers.hasText("This username is taken"));
    }


    //Create User Attempt - Don't check 13 box
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

    //Create Blank User Attempt
    @Test
    public void createBlankUserTest(){
        sleep(1000);
        clickOn("#createAccButton");
        clickOn("#createAccountButton");
        FxAssert.verifyThat("#errField", LabeledMatchers.hasText("You have not entered a username!"));
    }

    //Create User Attempt - Username Only
    @Test
    public void createUserUsernameOnlyTest(){
        sleep(1000);
        clickOn("#createAccButton");
        clickOn("#usernameField");
        write("testUser");
        clickOn("#createAccountButton");
        FxAssert.verifyThat("#errField", LabeledMatchers.hasText("You have not entered a password!"));
    }

    //Create User Attempt - Password Only
    @Test
    public void createUserPasswordOnlyTest(){
        sleep(1000);
        clickOn("#createAccButton");
        clickOn("#passField");
        write("password");
        clickOn("#createAccountButton");
        FxAssert.verifyThat("#errField", LabeledMatchers.hasText("The passwords do not match!"));
    }

    //Create User Attempt - Confirm Password Only
    @Test
    public void createUserConfirmPasswordOnlyTest(){
        sleep(1000);
        clickOn("#createAccButton");
        clickOn("#passConfirmField");
        write("password");
        clickOn("#createAccountButton");
        FxAssert.verifyThat("#errField", LabeledMatchers.hasText("The passwords do not match!"));
    }

    //Create User Attempt - Username & Pass Only
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

    //Create User Attempt - Username & Confirm Only
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

    //Create User Attempt - Pass & Confirm Only
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

    //Create User Attempt - Mismatched Passwords
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
