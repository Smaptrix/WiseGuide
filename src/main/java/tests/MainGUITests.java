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
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.WindowMatchers;
import org.testfx.matcher.control.LabeledMatchers;
import serverclientstuff.User;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

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

    //Unit Test | Verify that the Manual Works
    //This test must be done manually as there is no way for the program to test that the correct browser page opened.

}
