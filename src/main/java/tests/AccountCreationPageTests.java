package tests;

import GUI.AccountCreationController;
import GUI.LoginApplication;
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

/**
 * <p>
 *     Tests for the Account Creation Page.
 * </p>
 */
public class AccountCreationPageTests extends ApplicationTest {

    /**
     * <p>
     *     Reference to controller so that CheckBox can be interacted with directly.
     * </p>
     */
    private AccountCreationController controller;

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
    public void setUpClass() {
    }

    @After
    public void afterEachTest() throws TimeoutException {
        release(new KeyCode[]{});
        release(new MouseButton[]{});
        FxToolkit.hideStage();
    }

    //Unit Test | Confirm Username label is displayed
    @Test
    public void usernameLabelTest(){
        sleep(1000);
        FxAssert.verifyThat("#usernameLabel",LabeledMatchers.hasText("Username"));
    }

    //Unit Test | Confirm text can be entered in username field.
    @Test
    public void enterUsernameTest(){
        sleep(1000);
        clickOn("#usernameField");
        write("username");
        FxAssert.verifyThat("#usernameField", TextInputControlMatchers.hasText("username"));
    }

    //Unit Test | Confirm Password label is displayed
    @Test
    public void passwordLabelTest(){
        sleep(1000);
        FxAssert.verifyThat("#passwordLabel",LabeledMatchers.hasText("Password"));
    }

    //Unit Test | Confirm text can be entered in password field.
    @Test
    public void enterPasswordTest(){
        sleep(1000);
        clickOn("#passField");
        write("password");
        FxAssert.verifyThat("#passField", TextInputControlMatchers.hasText("password"));
    }

    //Unit Test | Confirm Password Confirmation label is displayed
    //Unit Test | Confirm Password label is displayed
    @Test
    public void passwordConfirmLabelTest(){
        sleep(1000);
        FxAssert.verifyThat("#passConfirmLabel",LabeledMatchers.hasText("Confirm Password"));
    }

    //Unit Test | Confirm text can be entered in confirm password field.
    @Test
    public void enterConfirmPasswordTest(){
        sleep(1000);
        clickOn("#passConfirmField");
        write("password");
        FxAssert.verifyThat("#passConfirmField", TextInputControlMatchers.hasText("password"));
    }

    //Unit Test | Confirm Age Checkbox label is displayed
    @Test
    public void ageCheckboxTextTest(){
        sleep(1000);
        FxAssert.verifyThat("#ageCheckBox",LabeledMatchers.hasText("I confirm I am over the age of 13"));
    }

    //Unit Test | Confirm checkbox can be clicked.
    @Test
    public void clickCheckboxTest(){
        sleep(1000);
        clickOn("#ageCheckBox");
        Assert.assertTrue(controller.ageCheckBox.isSelected());
    }

    //Unit Test | Confirm Privacy Policy Link is visible
    @Test
    public void privacyTextTest(){
        sleep(1000);
        FxAssert.verifyThat("#privacyPolicyLink",LabeledMatchers.hasText("Privacy Policy"));
    }

    //Unit Test | Confirm T&C Link is visible
    @Test
    public void tncTextTest(){
        sleep(1000);
        FxAssert.verifyThat("#termsLink",LabeledMatchers.hasText("Terms and Conditions"));
    }

    //Unit Test | Confirm Error Field is not displayed
    @Test
    public void errorFieldInvisibleTest(){
        sleep(1000);
        FxAssert.verifyThat("#errLabel",LabeledMatchers.hasText(""));
    }

    //Unit Test | Confirm "Create Account" button displays correct text
    @Test
    public void createAccountTextTest(){
        sleep(1000);
        FxAssert.verifyThat("#createAccountButton",LabeledMatchers.hasText("Create Account"));
    }

    //Unit Test | Confirm "Create Account" Button can be pressed.
    @Test
    public void createAccountButtonTest(){
        sleep(1000);
        clickOn("#createAccountButton");
        FxAssert.verifyThat("#errLabel", LabeledMatchers.hasText("You have not entered a username!"));
    }

    //Manual Tests | Provides an opportunity for manual tests to be performed
    //Tests to be carried out:
    // - Confirm T&C link works
    // - Confirm Privacy Policy link works
    // - Confirm GUI layout is correct.
    @Test
    public void manualTests(){
        sleep(1000000);
    }

}
