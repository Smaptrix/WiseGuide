package GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.GeneralMatchers;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.matcher.control.TextInputControlMatchers;

import java.util.Objects;
import java.util.concurrent.TimeoutException;

public class AccountCreationPageTest extends ApplicationTest {

    /* ===== UNIT TESTS for Account Creation Page ===== */

    @Override
    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(Objects.requireNonNull(LoginApplication.class.getResource("account-create-page.fxml")));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    @Before
    public void setUpClass() throws Exception {
        //TODO: CREATE TESTING ACCOUNT
    }

    @After
    public void afterEachTest() throws TimeoutException {

        //TODO: DELETE ACCOUNTS THE TESTS CREATE

        release(new KeyCode[]{});
        release(new MouseButton[]{});
        FxToolkit.hideStage();
    }


    //Enter text in username field
    @Test
    public void enterCreateUsernameTest(){
        sleep(1000);
        clickOn("#usernameField");
        write("username");
        FxAssert.verifyThat("#usernameField", TextInputControlMatchers.hasText("username"));
    }


    //Enter text in password field
    @Test
    public void enterCreatePasswordTest(){
        sleep(1000);
        clickOn("#passField");
        write("password");
        FxAssert.verifyThat("#passField", TextInputControlMatchers.hasText("password"));
    }


    //Enter text in confirm password field
    @Test
    public void enterCreateConfirmPasswordTest(){
        sleep(1000);
        clickOn("#passConfirmField");
        write("password");
        FxAssert.verifyThat("#passConfirmField", TextInputControlMatchers.hasText("password"));
    }

    //TODO: Click Age 13 Box
    @Test
    public void clickCreateCheckboxTest(){
        sleep(1000);
        clickOn("#ageCheckBox"); //TODO: Make this press the box rather than the text.
        sleep(1000);
        //TODO: Figure out how to confirm checkbox was pressed.
        //https://stackoverflow.com/questions/22882791/javafx-check-if-a-checkbox-is-ticked - JavaFX not TestFX but may be helpful
    }

    //Click Create Account Button
    @Test
    public void createAccountButtonTest(){
        sleep(1000);
        clickOn("#createAccountButton");
        FxAssert.verifyThat("#errField", LabeledMatchers.hasText("You have not entered a username!"));
    }

    //Account Creation | Open Privacy Policy/T&C | Unit Test
    //StackOverflow says this isn't possible to test.
    //https://stackoverflow.com/questions/13085791/possible-to-check-if-a-website-is-open-in-browser-from-java?rq=1

}
