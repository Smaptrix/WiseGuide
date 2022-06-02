package tests;

import GUI.MainApplication;
import GUI.MainController;
import client.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import ServerClientUtility.User;

import java.io.IOException;

/**
 * <p>
 *     Tests for the Map.
 * </p>
 */
public class MapTests extends ApplicationTest {

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
        controller.loadListOfVenuesAndRoutes();
    }

    //Manual Tests | Opens the map (logged into test user) to allow for manual testing.
    //Tests to be carried out:
    // - Confirm that the map displays correctly.
    // - Confirm that the venue labels display correctly.
    // - Confirm that all the zoom-ins and out work correctly.
    // - Confirm you can't zoom out to a zoom level that doesn't exist.
    // - Confirm that all venue markers open the correct venue page.
    @Test
    public void manualTests(){
        sleep(1000000);
    }

}
