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
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.matcher.control.TextInputControlMatchers;

import java.awt.*;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

public class AccountCreationPageTest extends ApplicationTest {

    /* ===== Tests for Account Creation Page ===== */

    private AccountCreationController controller; //Reference to controller so that CheckBox can be interacted with directly.

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(LoginApplication.class.getResource("account-create-page.fxml")));
        Parent mainNode = loader.load();
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
        this.controller = loader.getController();
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

    //Unit Test | Confirm Username label is displayed
    //TODO: Username label requires ID to test

    //Unit Test | Confirm Password label is displayed
    //TODO: Password label requires ID to test

    //Unit Test | Confirm Password Confirmation label is displayed
    //TODO: Password Confirmation label requires ID to test

    //Unit Test | Confirm Error Field is not displayed
    //TODO

    //Unit Test | Confirm Age Checkbox label is displayed
    @Test
    public void ageCheckboxTextTest(){
        sleep(1000);
        FxAssert.verifyThat("#ageCheckBox",LabeledMatchers.hasText("I confirm I am over the age of 13"));
    }

    //Unit Test | Confirm Privacy Policy Link is visible
    @Test
    public void privacyTextTest(){
        sleep(1000);
        FxAssert.verifyThat("#privacyPolicyLink",LabeledMatchers.hasText("Privacy Policy"));
    }

    //Unit Test | Confirm T&C Link is visible
    @Test
    public void TCTextTest(){
        sleep(1000);
        FxAssert.verifyThat("#termsLink",LabeledMatchers.hasText("Terms and Conditions"));
    }

    //Unit Test | Confirm "Create Account" button displays correct text
    @Test
    public void CreateAccountTextTest(){
        sleep(1000);
        FxAssert.verifyThat("#createAccountButton",LabeledMatchers.hasText("Create Account"));
    }

    //Unit Test | Confirm text can be entered in username field.
    @Test
    public void enterCreateUsernameTest(){
        sleep(1000);
        clickOn("#usernameField");
        write("username");
        FxAssert.verifyThat("#usernameField", TextInputControlMatchers.hasText("username"));
    }

    //Unit Test | Confirm text can be entered in password field.
    @Test
    public void enterPasswordTest(){
        sleep(1000);
        clickOn("#passField");
        write("password");
        FxAssert.verifyThat("#passField", TextInputControlMatchers.hasText("password"));
    }

    //Unit Test | Confirm text can be entered in confirm password field.
    @Test
    public void enterConfirmPasswordTest(){
        sleep(1000);
        clickOn("#passConfirmField");
        write("password");
        FxAssert.verifyThat("#passConfirmField", TextInputControlMatchers.hasText("password"));
    }

    //Unit Test | Confirm checkbox can be clicked.
    @Test
    public void clickCheckboxTest(){
        sleep(1000);
        clickOn("#ageCheckBox");
        Assert.assertTrue(controller.ageCheckBox.isSelected());
    }

    //Unit Test | Confirm "Create Account" Button can be pressed.
    @Test
    public void createAccountButtonTest(){
        sleep(1000);
        clickOn("#createAccountButton");
        FxAssert.verifyThat("#errField", LabeledMatchers.hasText("You have not entered a username!"));
    }

    //Unit Test | Confirm Privacy Policy/T&C links work.
    //StackOverflow says this isn't possible to test.
    //https://stackoverflow.com/questions/13085791/possible-to-check-if-a-website-is-open-in-browser-from-java?rq=1

}
