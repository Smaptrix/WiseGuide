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

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class VenueSelectorIntegrationTest extends ApplicationTest {


    public Client client;
    private LoginController controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("login-page.fxml"));
        Parent mainNode = fxmlLoader.load();
        stage.setScene(new Scene(mainNode));

        this.controller = fxmlLoader.getController();
        stage.show();
        stage.toFront();

    }


    @Before
    public void setUpClass() throws IOException {
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


    //Run the server before this test/
    //Works if the test account has the default options (i.e. Flares is a favourite venue)
    @Test
    public void venueSelectionIntegrationTest(){

        //Log the user into the program
        sleep(1000);
        clickOn("#usernameTextField");
        write("test");
        sleep(1000);
        clickOn("#userPassField");
        write("12345");
        sleep(1000);
        clickOn("#loginButton");
        sleep(1000);


        //Open the venue selector page
        clickOn("#venueSelectMenu");
        sleep(1000);
        clickOn("#venueSelectorMenuItem");
        //Verify that the venue selector page is open
        FxAssert.verifyThat(window("Venue Select!"), WindowMatchers.isShowing());

        sleep(1000);
        //verify venue cant be selected with no venue options chosen
        clickOn("#venuePickButton");
        sleep(1000);
        FxAssert.verifyThat("#errLabel", LabeledMatchers.hasText("You have not selected any venue types!"));

        //Verify venue can't be selected if no venues of the selected type & favourite button exist
        sleep(1000);
        clickOn("#foodCheckBox");
        clickOn("#faveCheckBox");
        clickOn("#venuePickButton");
        sleep(1000);
        FxAssert.verifyThat("#errLabel", LabeledMatchers.hasText("You have no favourites of the selected types!"));
        sleep(1000);

        //Verify that the if the favourite checkbox and the drinks checkbox are selected the favourited drinks venue opens up
        //Should be Flares
        clickOn("#drinksCheckBox");
        clickOn("#venuePickButton");
        sleep(1000);

        String fave_Drinks_Venue = "Flares_York";

        FxAssert.verifyThat(window(fave_Drinks_Venue), WindowMatchers.isShowing());




    }



}
