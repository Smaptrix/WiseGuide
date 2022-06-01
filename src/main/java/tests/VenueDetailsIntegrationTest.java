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

/**
 * <p>
 *     Tests for the Venue Details page.
 * </p>
 */
public class VenueDetailsIntegrationTest extends ApplicationTest {


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


    @Test
    //Checks the venue details page can actually be opened
    public void venueDetailsPageOpenTest(){

        //Log into the application
        clickOn("#usernameTextField");
        write("test");
        clickOn("#userPassField");
        write("12345");
        clickOn("#loginButton");

        sleep(1000);

        //Open the venue page using the side list
        doubleClickOn("#venueList");
        sleep(1000);

        //The title of the default venue in the venue list
        String venueTitle = "Dusk";

        //Verify that the correct window is open
        //Needs to be programmatic but cannot get the current controller of the current page?
        FxAssert.verifyThat(window(venueTitle), WindowMatchers.isShowing());

        //Verify that the title label at the top of the page has the correct label
        FxAssert.verifyThat("#venueName", LabeledMatchers.hasText(venueTitle));

        /* CURRENTLY DOESN't WORK BECAUSE THERE IS ANOTHER TEXT AREA INSIDE THE TEXT AREA
        String venueText = "UID: 008\n" +
                "Dusk\n" +
                "\n" +
                "Address: 8 New St, York YO1 8RA\n" +
                "Phone: 01904634851\n" +
                "Email: duskyork@gmail.com\n" +
                "\n" +
                "Description:\n" +
                "By day: Freshly baked bread. Artisan coffee. Stacked homemade burgers. Wraps, Sandwiches, platters (including plenty of vegetarian and vegan options). Gluten free substitutes. Free wifi. Dog friendly. Outside seating area. A soundtrack to help while away the hours. Brownies. A cheeky craft beer (or 2?)[\n" +
                "By Night: Cocktails. A party. Music from almost every genre. Specially selected wines. Lots of mates. The odd singalong. Live music. A few famous faces (only sometimes). Glastonbury without the wellies.\n" +
                "\n" +
                "Opening Times: \n" +
                "Monday: 10:00 - 02:00 \n" +
                "Tuesday: 10:00 - 02:00 \n" +
                "Wednesday: 10:00 - 02:00 \n" +
                "Thursday: 10:00 - 02:00 \n" +
                "Friday: 10:00 - 02:00 \n" +
                "Saturday: 10:00 - 02:00 \n" +
                "Sunday: 10:00 - 02:00 ";

        //Verify that the text on the venue page is correct
        FxAssert.verifyThat("#venueText", LabeledMatchers.hasText(venueText));

        */



    }




}
