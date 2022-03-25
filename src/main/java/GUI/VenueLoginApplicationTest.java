package GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.matcher.control.TextInputControlMatchers;

import java.util.Objects;
import java.util.concurrent.TimeoutException;

public class VenueLoginApplicationTest extends ApplicationTest {

    /* === Tests for Venue Login Page === */

    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(Objects.requireNonNull(LoginApplication.class.getResource("venue-login-page.fxml")));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
        this.stage = stage;
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

    //TODO - Tests Required
    //     - Make sure labels are visible
    //     - Make sure you can type in the fields
    //     - Make sure that you can press the buttons


    //Unit test | Confirm title label is visible and has the correct text
    @Test
    public void titleVisibilityAndTextTest(){
        sleep(1000);
        FxAssert.verifyThat("#titleLabel", LabeledMatchers.hasText("Venue Login"));
        FxAssert.verifyThat("#titleLabel", Node::isVisible);
    }

    //Unit test | Confirm Venue Name Label is visible and has the correct text
    @Test
    public void venueNameLabelVisibilityAndTextTest(){
        sleep(1000);
        FxAssert.verifyThat("#venueNameLabel", LabeledMatchers.hasText("Venue Name"));
        FxAssert.verifyThat("#venueNameLabel", Node::isVisible);
    }

    //Unit test | Confirm Venue Password Label is visible and has the correct text
    @Test
    public void venuePassLabelVisibilityAndTextTest(){
        sleep(1000);
        FxAssert.verifyThat("#venuePassLabel", LabeledMatchers.hasText("Password"));
        FxAssert.verifyThat("#venuePassLabel", Node::isVisible);
    }

    //Unit test | Confirm Back button has the right text and is visible
    @Test
    public void backButtonVisibilityAndTextTest(){
        sleep(1000);
        FxAssert.verifyThat("#backButton", LabeledMatchers.hasText("Back"));
        FxAssert.verifyThat("#backButton", Node::isVisible);
    }

    //Unit test | Confirm that the login button has the right text and is visible
    @Test
    public void loginButtonVisibilityAndTextTest(){
        sleep(1000);
        FxAssert.verifyThat("#loginButton", LabeledMatchers.hasText("Login"));
        FxAssert.verifyThat("#loginButton", Node::isVisible);
    }

    //Unit test | Confirm that the Venue name field is empty and visible
    @Test
    public void venueNameFieldVisibilityandTextTest(){
        sleep(1000);
        FxAssert.verifyThat("#venueNameField", TextInputControlMatchers.hasText(""));
        FxAssert.verifyThat("#venueNameField", Node::isVisible);
    }

    //Unit test | Confirm that the Venue name field is visible
    @Test
    public void venuePassFieldVisibilityandTextTest(){
        sleep(1000);
        FxAssert.verifyThat("#venuePassField", TextInputControlMatchers.hasText(""));
        FxAssert.verifyThat("#venuePassField", Node::isVisible);
    }

}
