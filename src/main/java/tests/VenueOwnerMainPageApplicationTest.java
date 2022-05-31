package tests;

import GUI.LoginApplication;
import GUI.VenueOwnerMainPageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
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
        //     - Check button functionality


        @Test
        //Unit Test | Verify that the title is visible and displays the correct text
        public void titleLabelVisibilityAndTextTest(){
            sleep(1000);
            FxAssert.verifyThat("#titleLabel", LabeledMatchers.hasText("VENUE ADMIN PAGE: "));
            FxAssert.verifyThat("#titleLabel", Node::isVisible);
        }

        @Test
        //Unit Test | Verify that the log out button is visible and displays the correct text
        public void logOutbuttonVisibilityAndTextTest(){
            sleep(1000);
            FxAssert.verifyThat("#logOutButton", LabeledMatchers.hasText("Log Out"));
            FxAssert.verifyThat("#logOutButton", Node::isVisible);
        }

        @Test
        //Unit Test | Verify that the list view is visible
        public void listViewVisibilityTest(){
            sleep(1000);
            FxAssert.verifyThat("#fileList", Node::isVisible);
        }

        @Test
        //Unit Test | Verify that the Open File Button is visible and displays the correct text
        public void openFileButtonVisibilityAndTextTest(){
            sleep(1000);
            FxAssert.verifyThat("#openFileButton", LabeledMatchers.hasText("Open File"));
            FxAssert.verifyThat("#openFileButton", Node::isVisible);
        }

    @Test
    //Unit Test | Verify that the Delete File Button is visible and displays the correct text
    public void deleteFileButtonVisibilityAndTextTest(){
        sleep(1000);
        FxAssert.verifyThat("#deleteFileButton", LabeledMatchers.hasText("Delete File"));
        FxAssert.verifyThat("#deleteFileButton", Node::isVisible);
    }


    /*
    @Test
    //Unit Test | Verify that the Add File Button is visible and displays the correct text
    public void addFileButtonVisibilityAndTextTest(){
        sleep(1000);
        FxAssert.verifyThat("#addFileButton", LabeledMatchers.hasText("Add New File"));
        FxAssert.verifyThat("#addFileButton", Node::isVisible);
    }
    */


    @Test
    //Unit & Integration Test | Verify that the log out button goes back to the venue login page
    public void logOutButtonFunctionalityTest(){
       sleep(1000);
       clickOn("#logOutButton");
       FxAssert.verifyThat(window("Venue Login"), WindowMatchers.isShowing());
    }






}

