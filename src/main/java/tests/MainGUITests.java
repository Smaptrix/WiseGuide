package tests;

import GUI.MainApplication;
import GUI.MainController;
import client.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.WindowMatchers;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.matcher.control.ListViewMatchers;
import serverclientstuff.User;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <p>
 *     Tests for the main screen.
 * </p>
 */
public class MainGUITests extends ApplicationTest {

    private MainController controller;
    User testUser = new User("testUser","testUserPass");

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("welcomeScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        controller = fxmlLoader.getController();
        stage.setScene(scene);
        stage.show();
        stage.setTitle("WiseGuide by Maptrix");
        stage.setResizable(false);
    }

    @Before
    public void setUpClass() throws IOException {
        Client client = new Client(); // Creates new instance of client object
        client.startConnection("127.0.0.1", 5555);
        controller.setClient(client);
        controller.setUser(testUser);

        //Disabling this for now as it's not really needed for this set of tests.
        /*
        controller.loadListOfVenuesAndRoutes();
        String[] userFaveVenueList = client.requestFaveVenueList();
        if(!(userFaveVenueList == null)){
            testUser.setFaveVenues(userFaveVenueList);
            System.out.println("Set user faves: " + Arrays.toString(testUser.getFaveVenues()));
        }
         */
    }

    @After
    public void afterEachTest() throws TimeoutException {
        release(new KeyCode[]{});
        release(new MouseButton[]{});
        FxToolkit.hideStage();
    }

    @Test
    //Unit Test | Verify that the interface opens.
    public void openInterfaceTest(){
        FxAssert.verifyThat(window("WiseGuide by Maptrix"), WindowMatchers.isShowing());

        //Uncomment to pause for 50k ms after opening - allows for easy manual testing if needed.
        //sleep(50000);
    }

    @Test
    //Unit Test | Verify that clicking "File" opens the file menu.
    public void menuBarFileTest(){
        sleep(1000);
        clickOn("#menuBarFileButton");
        FxAssert.verifyThat("#closeButton", Node::isVisible);
    }

    @Test
    //Unit Test | Verify that the file menu texts are correct.
    public void menuBarFileTextTest(){
        sleep(1000);
        clickOn("#menuBarFile");
        FxAssert.verifyThat("#menuBarFile", LabeledMatchers.hasText("File"));
        Assert.assertEquals("Close WiseGuide",controller.closeButton.getText());
    }

    /*
    @Test
    //Unit Test | Verify that the program closes when you press the close button.
    //This test must be run manually because tests can't run after system.exit.
    public void appCloseTest(){
        sleep(1000);
        clickOn("#menuBarFile");
        clickOn("#closeButton");
        FxAssert.verifyThat(window("WiseGuide by Maptrix"), WindowMatchers.isNotShowing());
    }
    */

    @Test
    //Unit Test | Verify that clicking "Account" opens the accounts menu.
    public void menuBarAccountTest(){
        sleep(1000);
        clickOn("#menuBarAccount");
        FxAssert.verifyThat("#accDetailsButton", Node::isVisible);
        FxAssert.verifyThat("#signOutButton", Node::isVisible);
    }

    @Test
    //Unit Test | Verify that the account menu texts are correct.
    public void menuBarAccountTextTest(){
        sleep(1000);
        clickOn("#menuBarAccount");
        FxAssert.verifyThat("#menuBarAccount", LabeledMatchers.hasText("Account"));
        Assert.assertEquals("Account Details",controller.accDetailsButton.getText());
        Assert.assertEquals("Sign Out",controller.signOutButton.getText());
    }

    @Test
    //Unit Test | Verify that the account details page opens
    public void accountDetailsOpenTest(){
        sleep(1000);
        clickOn("#menuBarAccount");
        clickOn("#accDetailsButton");
        FxAssert.verifyThat(window("Account Details"), WindowMatchers.isShowing());
    }

    @Test
    //Unit Test | Verify that signing out works
    public void signOutTest(){
        sleep(1000);
        clickOn("#menuBarAccount");
        clickOn("#signOutButton");
        FxAssert.verifyThat(window("Welcome to WiseGuide"), WindowMatchers.isShowing());
    }

    @Test
    //Unit Test | Verify that clicking "Help" opens the accounts menu.
    public void menuBarHelpTest(){
        sleep(1000);
        clickOn("#menuBarHelp");
        FxAssert.verifyThat("#aboutButton", Node::isVisible);
        FxAssert.verifyThat("#manualButton", Node::isVisible);
    }

    @Test
    //Unit Test | Verify that the help menu texts are correct.
    public void menuBarHelpTextTest(){
        sleep(1000);
        clickOn("#menuBarHelp");
        FxAssert.verifyThat("#menuBarHelp", LabeledMatchers.hasText("Help"));
        Assert.assertEquals("About",controller.aboutButton.getText());
        Assert.assertEquals("Manual",controller.manualButton.getText());
    }

    //Unit Test | Verify that the about menu works
    @Test
    public void aboutPageOpenTest(){
        sleep(1000);
        clickOn("#menuBarHelp").clickOn("#aboutButton");
        FxAssert.verifyThat(window("About Page"), WindowMatchers.isShowing());
    }

    //Unit Test | Check bar lists
    @Test
    public void barsListTest(){
        sleep(1000);
        controller.loadListOfVenuesAndRoutes();
        sleep(1000);
        FxAssert.verifyThat("#barsPane",Node::isVisible);
        FxAssert.verifyThat("#barsPane",LabeledMatchers.hasText("Bars"));
        FxAssert.verifyThat("#barsList",Node::isVisible);
        FxAssert.verifyThat("#barsList", ListViewMatchers.hasListCell("All Bar One York"));
    }

    //TODO: Check the other lists

    //Integration Test | Open a Bar (#barsList defaults to All Bar One York)
    @Test
    public void openBarTest(){
        sleep(1000);
        controller.loadListOfVenuesAndRoutes();
        sleep(1000);
        clickOn("#barsPane");
        sleep(1000);
        doubleClickOn("#barsList");
        sleep(1000);
        FxAssert.verifyThat(window("All Bar One York"), WindowMatchers.isShowing());
    }

    //Integration Test | Open a Cafe (#cafesList defaults to Lucky Days)
    @Test
    public void openCafeTest(){
        sleep(1000);
        controller.loadListOfVenuesAndRoutes();
        sleep(1000);
        clickOn("#cafesPane");
        sleep(1000);
        doubleClickOn("#cafesList");
        sleep(1000);
        FxAssert.verifyThat(window("Lucky Days"), WindowMatchers.isShowing());
    }

    //Integration Test | Open a Club (#clubsList defaults to Popworld)
    @Test
    public void openClubTest(){
        sleep(1000);
        controller.loadListOfVenuesAndRoutes();
        sleep(1000);
        clickOn("#clubsPane");
        sleep(1000);
        doubleClickOn("#clubsList");
        sleep(1000);
        FxAssert.verifyThat(window("Popworld York"), WindowMatchers.isShowing());
    }

    //Integration Test | Open a Fast Food Venue (#pubsList defaults to The Rook & Gaskill)
    @Test
    public void openPubTest(){
        sleep(1000);
        controller.loadListOfVenuesAndRoutes();
        sleep(1000);
        clickOn("#pubsPane");
        sleep(1000);
        doubleClickOn("#pubsList");
        sleep(1000);
        FxAssert.verifyThat(window("The Rook & Gaskill"), WindowMatchers.isShowing());
    }

    //Integration Test | Open a Restaurant (#restaurantsPane defaults to Cresci Pizzeria)
    @Test
    public void openRestaurantTest(){
        sleep(1000);
        controller.loadListOfVenuesAndRoutes();
        sleep(1000);
        clickOn("#restaurantsPane");
        sleep(1000);
        doubleClickOn("#restaurantsList");
        sleep(1000);
        FxAssert.verifyThat(window("Cresci Pizzeria"), WindowMatchers.isShowing());
    }

    //Integration Test | Open a Green Space (#greenSpacesList defaults to Millennium Fields)
    @Test
    public void openGreenSpaceTest(){
        sleep(1000);
        controller.loadListOfVenuesAndRoutes();
        sleep(1000);
        clickOn("#greenPane");
        sleep(1000);
        controller.venueScrollPane.setVvalue(10);
        doubleClickOn("#greenSpacesList");
        sleep(1000);
        FxAssert.verifyThat(window("Millennium Fields"), WindowMatchers.isShowing());
    }

    //Integration Test | Open a Study Space (#studySpacesList defaults to RKC)
    @Test
    public void openStudySpaceTest(){
        sleep(1000);
        controller.loadListOfVenuesAndRoutes();
        sleep(1000);
        clickOn("#studyPane");
        sleep(1000);
        controller.venueScrollPane.setVvalue(10);
        doubleClickOn("#studySpacesList");
        sleep(1000);
        FxAssert.verifyThat(window("Roger Kirk Centre"), WindowMatchers.isShowing());
    }

    //Integration Test | Open a Sightseeing Space (#sightseeingList defaults to The Hole in Wand)
    @Test
    public void openSightseeingTest(){
        sleep(1000);
        controller.loadListOfVenuesAndRoutes();
        sleep(1000);
        clickOn("#sightseeingPane");
        sleep(1000);
        controller.venueScrollPane.setVvalue(10);
        doubleClickOn("#sightseeingList");
        sleep(1000);
        FxAssert.verifyThat(window("The Hole in Wand"), WindowMatchers.isShowing());
    }

    //Manual Tests | Provides an opportunity for manual tests to be performed
    //Tests to be carried out:
    // - Confirm close button works
    // - Confirm manual opens
    // - Confirm GUI layout is correct.
    @Test
    public void manualTests(){
        sleep(1000000);
    }

}
