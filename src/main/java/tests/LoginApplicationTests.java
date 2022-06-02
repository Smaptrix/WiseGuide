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
import org.testfx.matcher.control.TextInputControlMatchers;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

/**
 * <p>
 *     Tests for the Login Screen.
 * </p>
 */
public class LoginApplicationTests extends ApplicationTest {

    /**
     * <p>
     *     The current stage.
     * </p>
     */
    private Stage stage;

    /**
     * <p>
     *     The login controller.
     * </p>
     */
    private LoginController controller;

    /**
     * <p>
     *     The current client.
     * </p>
     */
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


    //Unit Test | Confirm "Login" button text is correct.
    @Test
    public void loginButtonTextTest() {
        sleep(1000);
        FxAssert.verifyThat("#loginButton", LabeledMatchers.hasText("Login"));
    }

    //Unit Test | Confirm "Login" button can be pressed.
    @Test
    public void loginButtonTest() {
        sleep(1000);
        clickOn("#loginButton");
        FxAssert.verifyThat("#errorLabel", LabeledMatchers.hasText("You have not entered a username!"));
    }

    //Unit Test | Confirm "Create Account" button text is correct.
    @Test
    public void createAccountLoginButtonTextTest() {
        sleep(1000);
        FxAssert.verifyThat("#createAccButton", LabeledMatchers.hasText("Create Account"));
    }


    //Unit Test | Confirm "Create Account" Button can be pressed.
    @Test
    public void createAccountLoginButtonTest(){
        sleep(1000);
        clickOn("#createAccButton");
        FxAssert.verifyThat(window("Account Creation"), WindowMatchers.isShowing());
    }

    //Unit Test | Confirm the username label text is correct.
    @Test
    public void loginUsernameLabelTest(){
        sleep(1000);
        FxAssert.verifyThat("#usernameLabel",LabeledMatchers.hasText("Username"));
    }


    //Unit Test | Confirm text can be entered in username field.
    @Test
    public void enterLoginUsernameTest(){
        sleep(1000);
        clickOn("#usernameTextField");
        sleep(1000);
        write("Test");
        sleep(1000);
        FxAssert.verifyThat("#usernameTextField", TextInputControlMatchers.hasText("Test"));
    }

    //Unit Test | Confirm the password label text is correct.
    @Test
    public void loginPasswordLabelTest(){
        sleep(1000);
        FxAssert.verifyThat("#passwordLabel",LabeledMatchers.hasText("Password"));
    }

    //Unit Test | Confirm text can be entered in password field.
    @Test
    public void enterLoginPasswordTest(){
        sleep(1000);
        clickOn("#userPassField");
        sleep(1000);
        write("password");
        sleep(1000);
        FxAssert.verifyThat("#userPassField", TextInputControlMatchers.hasText("password"));
    }

    //Unit Test | Confirm that error label is not visible by default.
    @Test
    public void loginErrorLabelInvisibleTest(){
        sleep(1000);
        FxAssert.verifyThat("#errorLabel", LabeledMatchers.hasText(""));
    }

    //Unit Test | Confirm that test account can be created
    @Test
    public void loginTestAccountTest() throws IOException {
        sleep(1000);
        controller.createTestAccount();
    }

    //Unit Test | Confirm that clicking the maptrix logo opens the venue login page
    @Test
    public void openVenueLoginPageTest(){
        sleep(1000);
        clickOn("#maptrixLogo");
        FxAssert.verifyThat(window("Venue Login"), WindowMatchers.isShowing());
    }

    //Manual Tests | Provides an opportunity for manual tests to be performed
    //Tests to be carried out:
    // - Confirm GUI layout is correct.
    @Test
    public void manualTests(){
        sleep(1000000);
    }

}
