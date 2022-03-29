package GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class VenueOwnerMainPageApplicationTest extends ApplicationTest {


        /* === Tests for Venue Main Page === */
        //MAKE SURE YOU RUN THE SERVER BEFORE DOING THIS!

        private VenueOwnerMainPageController controller;

        @Override
        public void start(Stage stage) throws Exception {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("venue-owner-main-page.fxml"));
            Parent mainNode = fxmlLoader.load();
            stage.setScene(new Scene(mainNode));
            controller = fxmlLoader.getController();

            stage.show();
            stage.toFront();
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
        //     - Check everything on the page is there and visible
        //     - Check button functionality


    }

