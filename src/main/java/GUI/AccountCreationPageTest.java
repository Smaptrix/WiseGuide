package GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
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
import org.testfx.matcher.base.GeneralMatchers;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.matcher.control.TextInputControlMatchers;

import java.awt.*;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

public class AccountCreationPageTest extends ApplicationTest {

    /* ===== UNIT TESTS for Account Creation Page ===== */

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

    //Click Age 13 Box
    //This is an absolutely scuffed way of doing this. TestFX's clickOn(checkbox) does not work so I have
    //instead attempted to click where the checkbox should be relative to the centre of the screen.
    //TODO: Currently only works on AC's screen size/resolution...
    //TODO: The relative checkbox position is not the same as when running integration tests. Investigate why.
    @Test
    public void clickCreateCheckboxTest(){
        sleep(1000);
        //clickOn("#ageCheckBox");
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        clickOn((screensize.getWidth()/2 - 92),(screensize.getHeight()/2-125));
        Assert.assertTrue(controller.ageCheckBox.isSelected());
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
