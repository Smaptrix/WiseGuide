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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.WindowMatchers;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.matcher.control.TextInputControlMatchers;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

public class LoginApplicationTest extends ApplicationTest {

    /* ===== Tests for Main Login Screen ===== */

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

    //TODO: Username and Password Labels require IDs to test.

    //Unit Test | Confirm "Login" button text is correct.
    @Test
    public void loginTextTest() {
        sleep(1000);
        FxAssert.verifyThat("#loginButton", LabeledMatchers.hasText("Login"));
    }

    //Unit Test | Confirm "Create Account" button text is correct.
    @Test
    public void createAccountTextTest() {
        sleep(1000);
        FxAssert.verifyThat("#createAccButton", LabeledMatchers.hasText("Create Account"));
    }

    /*Unit Test | Confirm "Exit" button text is correct.
    @Test
    //TODO: Exit button no longer exists - remove if not re-adding button.
    public void exitTextTest() {
        sleep(1000);
        FxAssert.verifyThat("#exitButton", LabeledMatchers.hasText("Exit"));
    }
    */

    //Unit Test | Confirm "Login" button can be pressed.
    @Test
    public void clickOnLoginTest() {
        sleep(1000);
        clickOn("#loginButton");
        FxAssert.verifyThat("#errorLabel", LabeledMatchers.hasText("You have not entered a username!"));
    }

    /*Unit Test | Confirm "Exit" button can be pressed.
    @Test
    //TODO: Exit button no longer exists - remove if not re-adding button.
    public void clickOnExit() {
        sleep(1000);
        clickOn("#exitButton");
        Assert.assertFalse(stage.isShowing());
    }
     */

    //Unit Test | Confirm "Create Account" Button can be pressed.
    @Test
    public void clickOnCreateAccountTest(){
        sleep(1000);
        clickOn("#createAccButton");
        FxAssert.verifyThat(window("Account Creation"), WindowMatchers.isShowing());
    }

    //Unit Test | Confirm text can be entered in username field.
    @Test
    public void enterMainScreenUsernameTest(){
        sleep(1000);
        clickOn("#usernameTextField");
        sleep(1000);
        write("Test");
        sleep(1000);
        FxAssert.verifyThat("#usernameTextField", TextInputControlMatchers.hasText("Test"));
    }

    //Unit Test | Confirm text can be entered in password field.
    @Test
    public void enterMainScreenPasswordTest(){
        sleep(1000);
        clickOn("#userPassField");
        sleep(1000);
        write("password");
        sleep(1000);
        FxAssert.verifyThat("#userPassField", TextInputControlMatchers.hasText("password"));
    }

    //Unit Test | Confirm that error label is not visible by default.
    @Test
    public void errorLabelInvisibleTest(){
        sleep(1000);
        FxAssert.verifyThat("#errorLabel", LabeledMatchers.hasText(""));
    }

    //Unit Test | Confirm that test account can be created
    @Test
    public void loginTestAccountTest() throws IOException {
        sleep(1000);
        controller.createTestAccount();
    }
}
