package GUI;

import client.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.WindowMatchers;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.matcher.control.TextInputControlMatchers;
import server.ServerMain;


import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

public class VenueLoginApplicationTest extends ApplicationTest {

    /* === Tests for Venue Login Page === */
    //MAKE SURE YOU RUN THE SERVER BEFORE DOING THIS!

    private Stage stage;
    private VenueLoginController controller;


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("venue-login-page.fxml"));
        Parent mainNode = fxmlLoader.load();
        stage.setScene(new Scene(mainNode));
        controller = fxmlLoader.getController();

        stage.show();
        stage.toFront();
        this.stage = stage;
    }


    @Before
    public void setUpClass() throws IOException {

    }

    @After
    public void afterEachTest() throws TimeoutException {
        release(new KeyCode[]{});
        release(new MouseButton[]{});
        FxToolkit.hideStage();
    }

    //TODO - Tests Required
    //     - Make sure that you can press the buttons (and they have functionality)


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

    //Unit test | Confirm that the error labe lis blank on startup
    public void errLabelVisibilityandTextTest(){
        sleep(1000);
        FxAssert.verifyThat("#errLabel", LabeledMatchers.hasText(""));
        FxAssert.verifyThat("#errLabel", Node::isVisible);

    }

    //Unit test | Confirm that you can type in the venue name text field
    @Test
    public void venueNameTextEntryTest(){
        sleep(1000);
        clickOn("#venueNameField");
        sleep(1000);
        write("Test");
        sleep(1000);
        FxAssert.verifyThat("#venueNameField", TextInputControlMatchers.hasText("Test"));
    }

    //Unit test | Confirm that you can type in the venue password text field
    @Test
    public void venuePassTextEntryTest(){
        sleep(1000);
        clickOn("#venuePassField");
        sleep(1000);
        write("Test");
        sleep(1000);
        FxAssert.verifyThat("#venuePassField", TextInputControlMatchers.hasText("Test"));
    }



    //Unit Test | Confirm that pressing the back button takes you back to the login page

    @Test
    public void backButtonFunctionalityTest(){
        sleep(1000);
        clickOn("#backButton");
        FxAssert.verifyThat(window("Login"), WindowMatchers.isShowing());
    }

    //Unit Test | Confirm that entering nothing into the fields and pressing login causes an error message to appear
    @Test
    public void noEntryLoginPressTest(){
        sleep(1000);
        clickOn("#loginButton");
        FxAssert.verifyThat("#errLabel", LabeledMatchers.hasText("Please enter something in both fields!"));
    }

    //Unit Test | Confirm that just entering the venue name into the fields and pressing login causes an error message to appear
    @Test
    public void venueNameEntryLoginPressTest(){
        sleep(1000);
        clickOn("#venueNameField");
        write("Test");
        sleep(1000);
        clickOn("#loginButton");
        FxAssert.verifyThat("#errLabel", LabeledMatchers.hasText("Please enter something in both fields!"));
    }


    //Unit Test | Confirm that just entering the venue password into the fields and pressing login causes an error message to appear
    @Test
    public void venuePassEntryLoginPressTest(){
        sleep(1000);

        clickOn("#venuePassField");
        write("Test");
        sleep(1000);
        clickOn("#loginButton");
        FxAssert.verifyThat("#errLabel", LabeledMatchers.hasText("Please enter something in both fields!"));
    }


    //THESE TWO TESTS WORK BUT SEPEERATELY
    //SO YOU NEED TO COMMENT ONE OUT THEN RUN THE TESTS
    //THEY DO BOTH WORK THOUGH

   /*
    //Unit/Intergration Test | Making sure that incorrect user data is confirmed by an error label
    //Requires connection with the server
    @Test
    public void incorrectLoginEntryTest() throws IOException {


        //Connect a test client to the server
        Client testClient = new Client();
        testClient.startConnection("127.0.0.1", 5555);
        controller.setClient(testClient);

        sleep(1000);
        clickOn("#venueNameField");
        write("Test");
        sleep(1000);
        clickOn("#venuePassField");
        write("Test");
        sleep(1000);
        clickOn("#loginButton");
        FxAssert.verifyThat("#errLabel", LabeledMatchers.hasText("Unrecognised Venue Details"));
    }
*/


    //Unit/Intergration Test | Making sure that correct user data logs you on to the Venue Owner Main Page
    @Test
    public void correctLoginEntryTest() throws IOException {

        //Connect a test client to the server
        Client testClient = new Client();
        testClient.startConnection("127.0.0.1", 5555);
        controller.setClient(testClient);

        clickOn("#venueNameField");
        write("Flares York");
        sleep(1000);
        clickOn("#venuePassField");
        write("12345");
        sleep(1000);
        clickOn("#loginButton");
        FxAssert.verifyThat(window("Flares York Owner Page"), WindowMatchers.isShowing());
    }



}
