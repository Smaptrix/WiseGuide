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
import org.testfx.osgi.service.TestFx;

import java.io.IOException;
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

    //WIP test that Lee was working on
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


    /* ===== UNIT TESTS for Main Login Screen ===== */

    //Unit tests that test all the basic stuff to make sure it all works.
    //Not everything works right now. I don't know how to do half the assertions because I can't find any TestFX documentation.

    //TODO: Click Login Button
    @Test
    public void clickOnLoginTest() {
        sleep(1000);
        clickOn("#loginButton");
        //TODO: Login empty field checks not yet implemeted - test cannot be written yet.
    }

    //TODO: Click Exit Button
    @Test
    public void clickOnExit() {
        sleep(1000);
        clickOn("#exitButton");
        //TODO: How to verify that stage was closed? WindowMatchers.isNotShowing() does not work as window does not have a title.
    }

    //Click Create Account Button
    @Test
    public void clickOnCreateAccountTest(){
        sleep(1000);
        clickOn("#createAccButton");
        FxAssert.verifyThat(window("Account Creation"), WindowMatchers.isShowing());
    }

    //Enter text in username field
    @Test
    public void enterMainScreenUsernameTest(){
        sleep(1000);
        clickOn("#usernameTextField");
        sleep(1000);
        write("Test");
        sleep(1000);
        FxAssert.verifyThat("#usernameTextField", TextInputControlMatchers.hasText("Test"));
    }

    //Enter text in password field
    @Test
    public void enterMainScreenPasswordTest(){
        sleep(1000);
        clickOn("#userPassField");
        sleep(1000);
        write("password");
        sleep(1000);
        FxAssert.verifyThat("#userPassField", TextInputControlMatchers.hasText("password"));
    }

    //TODO: Account Creation | ANY LIMITS ON WHAT CHARACTERS ALLOWED IN USER/PASS??

}
