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
        FxAssert.verifyThat("#barsList", ListViewMatchers.hasListCell("The Stone Roses Bar"));
        FxAssert.verifyThat("#barsList", ListViewMatchers.hasListCell("Dusk"));
        FxAssert.verifyThat("#barsList", ListViewMatchers.hasListCell("Evil Eye"));
        FxAssert.verifyThat("#barsList", ListViewMatchers.hasListCell("All Bar One York"));
    }

    //Unit Test | Check clubs lists
    @Test
    public void clubsListTest(){
        sleep(1000);
        controller.loadListOfVenuesAndRoutes();
        sleep(1000);
        FxAssert.verifyThat("#clubsPane",Node::isVisible);
        FxAssert.verifyThat("#clubsPane",LabeledMatchers.hasText("Clubs"));
        FxAssert.verifyThat("#clubsList",Node::isVisible);
        FxAssert.verifyThat("#clubsList", ListViewMatchers.hasListCell("Flares York"));
        FxAssert.verifyThat("#clubsList", ListViewMatchers.hasListCell("Club Salvation"));
        FxAssert.verifyThat("#clubsList", ListViewMatchers.hasListCell("Kuda"));
        FxAssert.verifyThat("#clubsList", ListViewMatchers.hasListCell("Popworld York"));
        FxAssert.verifyThat("#clubsList", ListViewMatchers.hasListCell("Revolution York"));
        FxAssert.verifyThat("#clubsList", ListViewMatchers.hasListCell("Flares York"));
    }

    //Unit Test | Check cafe lists
    @Test
    public void cafesListTest(){
        sleep(1000);
        controller.loadListOfVenuesAndRoutes();
        sleep(1000);
        FxAssert.verifyThat("#cafesPane",Node::isVisible);
        FxAssert.verifyThat("#cafesPane",LabeledMatchers.hasText("Cafés"));
        FxAssert.verifyThat("#cafesList",Node::isVisible);
        FxAssert.verifyThat("#cafesList", ListViewMatchers.hasListCell("The Cat's Whiskers"));
        FxAssert.verifyThat("#cafesList", ListViewMatchers.hasListCell("Drift-In York"));
        FxAssert.verifyThat("#cafesList", ListViewMatchers.hasListCell("Brew & Brownie"));
        FxAssert.verifyThat("#cafesList", ListViewMatchers.hasListCell("Lucky Days"));
    }

    //Unit Test | Check restaurant lists
    @Test
    public void restaurantsListTest(){
        sleep(1000);
        controller.loadListOfVenuesAndRoutes();
        sleep(1000);
        FxAssert.verifyThat("#restaurantsPane",Node::isVisible);
        FxAssert.verifyThat("#restaurantsPane",LabeledMatchers.hasText("Restaurants"));
        FxAssert.verifyThat("#restaurantsList",Node::isVisible);
        FxAssert.verifyThat("#restaurantsList", ListViewMatchers.hasListCell("Il Paradiso Del Cibo"));
        FxAssert.verifyThat("#restaurantsList", ListViewMatchers.hasListCell("Spark York C.I.C"));
        FxAssert.verifyThat("#restaurantsList", ListViewMatchers.hasListCell("Cosy Club"));
        FxAssert.verifyThat("#restaurantsList", ListViewMatchers.hasListCell("Cresci Pizzeria"));
    }

    //Unit Test | Check fast food lists
    @Test
    public void fastFoodListTest(){
        sleep(1000);
        controller.loadListOfVenuesAndRoutes();
        sleep(1000);
        FxAssert.verifyThat("#foodPane",Node::isVisible);
        FxAssert.verifyThat("#foodPane",LabeledMatchers.hasText("Fast Food"));
        FxAssert.verifyThat("#foodPane",Node::isVisible);
        FxAssert.verifyThat("#fastFoodList", ListViewMatchers.hasListCell("Deniz Best Kebab"));
        FxAssert.verifyThat("#fastFoodList", ListViewMatchers.hasListCell("NaNa Noodle Bar"));
        FxAssert.verifyThat("#fastFoodList", ListViewMatchers.hasListCell("Cecil's Pizza - Grill"));
        FxAssert.verifyThat("#fastFoodList", ListViewMatchers.hasListCell("Efes Pizza"));
    }

    //Unit Test | Check pubs lists
    @Test
    public void pubListTest(){
        sleep(1000);
        controller.loadListOfVenuesAndRoutes();
        sleep(1000);
        FxAssert.verifyThat("#pubsPane",Node::isVisible);
        FxAssert.verifyThat("#pubsPane",LabeledMatchers.hasText("Pubs"));
        FxAssert.verifyThat("#pubsPane",Node::isVisible);
        FxAssert.verifyThat("#pubsList", ListViewMatchers.hasListCell("Charles XII"));
        FxAssert.verifyThat("#pubsList", ListViewMatchers.hasListCell("The Waggon & Horses"));
        FxAssert.verifyThat("#pubsList", ListViewMatchers.hasListCell("The Black Bull"));
        FxAssert.verifyThat("#pubsList", ListViewMatchers.hasListCell("The Rook & Gaskill"));
    }

    //Unit Test | Check green spaces lists
    @Test
    public void greenListTest(){
        sleep(1000);
        controller.loadListOfVenuesAndRoutes();
        sleep(1000);
        FxAssert.verifyThat("#greenPane",Node::isVisible);
        FxAssert.verifyThat("#greenPane",LabeledMatchers.hasText("Green Spaces"));
        FxAssert.verifyThat("#greenPane",Node::isVisible);
        FxAssert.verifyThat("#greenSpacesList", ListViewMatchers.hasListCell("Museum Gardens"));
        FxAssert.verifyThat("#greenSpacesList", ListViewMatchers.hasListCell("Rowntree Park"));
        FxAssert.verifyThat("#greenSpacesList", ListViewMatchers.hasListCell("Dean's Park"));
        FxAssert.verifyThat("#greenSpacesList", ListViewMatchers.hasListCell("Millennium Fields"));
    }

    //Unit Test | Check study spaces lists
    @Test
    public void studyListTest(){
        sleep(1000);
        controller.loadListOfVenuesAndRoutes();
        sleep(1000);
        FxAssert.verifyThat("#studyPane",Node::isVisible);
        FxAssert.verifyThat("#studyPane",LabeledMatchers.hasText("Study Spaces"));
        FxAssert.verifyThat("#studyPane",Node::isVisible);
        FxAssert.verifyThat("#studySpacesList", ListViewMatchers.hasListCell("University of York JB Morrell Library"));
        FxAssert.verifyThat("#studySpacesList", ListViewMatchers.hasListCell("Piazza Building"));
        FxAssert.verifyThat("#studySpacesList", ListViewMatchers.hasListCell("The Ron Cooke Hub"));
        FxAssert.verifyThat("#studySpacesList", ListViewMatchers.hasListCell("Roger Kirk Centre"));
    }

    //Unit Test | Check sightseeing lists
    @Test
    public void sightseeingListTest(){
        sleep(1000);
        controller.loadListOfVenuesAndRoutes();
        sleep(1000);
        FxAssert.verifyThat("#sightseeingPane",Node::isVisible);
        FxAssert.verifyThat("#sightseeingPane",LabeledMatchers.hasText("Sightseeing"));
        FxAssert.verifyThat("#sightseeingPane",Node::isVisible);
        FxAssert.verifyThat("#sightseeingList", ListViewMatchers.hasListCell("York City Walls"));
        FxAssert.verifyThat("#sightseeingList", ListViewMatchers.hasListCell("York Minster"));
        FxAssert.verifyThat("#sightseeingList", ListViewMatchers.hasListCell("National Railway Museum York"));
        FxAssert.verifyThat("#sightseeingList", ListViewMatchers.hasListCell("The Hole In Wand"));
        FxAssert.verifyThat("#sightseeingList", ListViewMatchers.hasListCell("York's Chocolate Story"));
    }

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

    //Integration Test | Open a Fast Food Place (#foodPane defaults to Efes Pizza)
    @Test
    public void openFastFoodTest(){
        sleep(1000);
        controller.loadListOfVenuesAndRoutes();
        sleep(1000);
        clickOn("#foodPane");
        sleep(1000);
        doubleClickOn("#fastFoodList");
        sleep(1000);
        FxAssert.verifyThat(window("Efes Pizza"), WindowMatchers.isShowing());
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

    //Integration Test | Open a Sightseeing Space (#sightseeingList defaults to The Hole In Wand)
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
        FxAssert.verifyThat(window("The Hole In Wand"), WindowMatchers.isShowing());
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
