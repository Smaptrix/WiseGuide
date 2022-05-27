package tests;

import GUI.LoginApplication;
import GUI.MainApplication;
import GUI.MainController;
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
import serverclientstuff.User;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

public class MainGUITests extends ApplicationTest {

    private Client client;
    private MainController controller;
    User testUser = new User("testUser","testUserPass");

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("welcomeScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        controller = fxmlLoader.getController();
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    @Before
    public void setUpClass() throws IOException {
        client = new Client(); // Creates new instance of client object
        client.startConnection("127.0.0.1", 5555);
        controller.setClient(client);
        controller.setUser(testUser);
        controller.loadListOfVenuesAndRoutes();
        String[] userFaveVenueList = client.requestFaveVenueList();
        if(!(userFaveVenueList == null)){
            testUser.setFaveVenues(userFaveVenueList);
            System.out.println("Set user faves: " + Arrays.toString(testUser.getFaveVenues()));
        }
    }

    @After
    public void afterEachTest() throws TimeoutException {
        release(new KeyCode[]{});
        release(new MouseButton[]{});
        FxToolkit.hideStage();
    }

    @Test
    public void test(){
        sleep(50000);
    }

    @Test
    public void aboutPageOpenText(){
        sleep(1000);
        clickOn("#menuBarHelpButton").clickOn("#aboutButton");
        FxAssert.verifyThat(window("About Page"), WindowMatchers.isShowing());
    }

}
