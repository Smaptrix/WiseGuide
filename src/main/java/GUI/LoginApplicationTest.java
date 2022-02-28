package GUI;

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
import org.testfx.assertions.api.Assertions;
import org.testfx.assertions.api.TextAssert;
import org.testfx.assertions.api.TextInputControlAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.WindowMatchers;
import org.testfx.matcher.control.TextInputControlMatchers;
import org.testfx.matcher.control.TextMatchers;

import java.util.Objects;
import java.util.concurrent.TimeoutException;

public class LoginApplicationTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(Objects.requireNonNull(LoginApplication.class.getResource("login-page.fxml")));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    @Before
    public void setUpClass() throws Exception {
    }

    @After
    public void afterEachTest() throws TimeoutException {
        release(new KeyCode[]{});
        release(new MouseButton[]{});
        FxToolkit.hideStage();
    }

/*    @Test
    public void clickOnCreateAcc() throws TimeoutException {
        sleep(1000);
        moveTo("#createAccButton");
        sleep(1000);
        clickOn("#createAccButton");
        sleep(1000);
        moveTo("#ageCheckBox");
        sleep(1000);
        clickOn("#ageCheckBox");
        sleep(1000);
        moveTo("#userField");
        sleep(1000);
        clickOn("#userField");
        write("testUser");

    }*/


    /* === UNIT TESTS === */

    //Unit tests that test all the basic stuff to make sure it all works.
    //Not everything works right now. I don't know how to do half the assertions because I can't find any TestFX documentation.

    //I think it would be useful if the app had methods that could be used by tests to open specific pages?
    //Right now, to access the Account Created Confirmation "Okay" button, the test has to create a working account
    //(integration test) to test that the OK button exists (unit test) which is not really ideal.


    //Main Screen | Click Login | Unit Test
    @Test
    public void clickOnLoginTest() {
        sleep(1000);
        clickOn("#loginButton");

        //TODO: Login does not seem to do anything for me - can't test until functionality is implemented(?)
    }

    //Main Screen | Click Exit | Unit Test
    @Test
    public void clickOnExit() {
        sleep(1000);
        clickOn("#exitButton");
    }

    //Main Screen | Click Create Account | Unit Test
    @Test
    public void clickOnCreateAccountTest(){
        sleep(1000);
        clickOn("#createAccButton");
        FxAssert.verifyThat(window("Account Creation"), WindowMatchers.isShowing());
    }

    //Main Screen | Enter text in username field | Unit Test
    @Test
    public void enterMainScreenUsernameTest(){
        sleep(1000);
        clickOn("#usernameTextField");
        sleep(1000);
        write("Test");
        sleep(1000);
        FxAssert.verifyThat("#usernameTextField", TextInputControlMatchers.hasText("Test"));
    }

    //Enter text in password field | Unit Test
    @Test
    public void enterMainScreenPasswordTest(){
        sleep(1000);
        clickOn("#userPassField");
        sleep(1000);
        write("password");
        sleep(1000);
        FxAssert.verifyThat("#userPassField", TextInputControlMatchers.hasText("password"));
    }

    //Account Creation | Enter text in username field | Unit Test
    @Test
    public void enterCreateUsernameTest(){
        sleep(1000);
        clickOn("#createAccButton");
        clickOn("#usernameField");
        write("username");
        FxAssert.verifyThat("#usernameField", TextInputControlMatchers.hasText("username"));
    }

    //Account Creation | Enter text in password field | Unit Test
    @Test
    public void enterCreatePasswordTest(){
        sleep(1000);
        clickOn("#createAccButton");
        clickOn("#passField");
        write("password");
        FxAssert.verifyThat("#passField", TextInputControlMatchers.hasText("password"));
    }

    //Account Creation | Enter text in confirm password field | Unit Test
    @Test
    public void enterCreateConfirmPasswordTest(){
        sleep(1000);
        clickOn("#createAccButton");
        clickOn("#passConfirmField");
        write("password");
        FxAssert.verifyThat("#passConfirmField", TextInputControlMatchers.hasText("password"));
    }

    //Account Creation | Click Age 13 Box | Unit Test
    @Test
    public void clickCreateCheckboxTest(){
        sleep(1000);
        clickOn("#createAccButton");
        clickOn("#ageCheckBox");

        //TODO: I don't know how to check the state of the checkbox yet.
    }

    //Account Creation | Click Create Account Button | Unit Test
    @Test
    public void createAccountButtonTest(){
        sleep(1000);
        clickOn("#createAccButton");
        clickOn("#createAccountButton");

        //TODO: How to check label text?
    }

    //Account Creation | Open Privacy Policy/T&C | Unit Test
    //StackOverflow says this isn't possible to test.
    //https://stackoverflow.com/questions/13085791/possible-to-check-if-a-website-is-open-in-browser-from-java?rq=1

    //TODO: Account Creation Confirmation | Click "Okay" | Unit Test
    //Need to create a working account before this can be tested. Not ideal - investigate alternative approach.

    //TODO: Account Creation | ANY LIMITS ON WHAT CHARACTERS ALLOWED IN USER/PASS??


    /* === INTEGRATION TESTS === */

    //TODO: Main Screen | Login real user real password | Integration Test with Login System

    //TODO: Main Screen | Login real user fake password | Integration Test with Login System

    //TODO: Main Screen | Login fake user fake password | Integration Test with Login System

    //TODO: Account Creation | Create New User | Integration Test with Login System

    //TODO: Account Creation | Create Existing User Attempt | Integration Test with Login System

    //TODO: Account Creation | Create User Attempt - Don't check 13 box
    //TODO: Account Creation | Create Blank User Attempt
    //TODO: Account Creation | Create User Attempt - Username Only
    //TODO: Account Creation | Create User Attempt - Password Only
    //TODO: Account Creation | Create User Attempt - Confirm Password Only
    //TODO: Account Creation | Create User Attempt - Username & Pass Only
    //TODO: Account Creation | Create User Attempt - Username & Confirm Only
    //TODO: Account Creation | Create User Attempt - Pass & Confirm Only
    //TODO: Account Creation | Create User Attempt - Mismatched Passwords

}
