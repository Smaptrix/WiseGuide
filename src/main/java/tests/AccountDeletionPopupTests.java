package tests;

import GUI.AccountDeletionPopupController;
import GUI.LoginApplication;
import client.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.WindowMatchers;
import org.testfx.matcher.control.LabeledMatchers;

import java.io.IOException;
import java.util.Objects;

/**
 * <p>
 *     Tests for the Account Deleted Popup.
 * </p>
 */
public class AccountDeletionPopupTests extends ApplicationTest {

    /**
     * <p>
     *     The popup controller.
     * </p>
     */
    private AccountDeletionPopupController controller;

    /**
     * <p>
     *     The current client.
     * </p>
     */
    public Client client;

    /**
     * <p>
     *     The current stage.
     * </p>
     */
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(LoginApplication.class.getResource("account-deleted-window.fxml")));
        Parent mainNode = loader.load();
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
        this.controller = loader.getController();
        this.stage = stage;
    }

    //Unit Test | Confirm Button Text
    @Test
    public void popupButtonTextTest(){
        sleep(1000);
        FxAssert.verifyThat("#closePopUpButton", LabeledMatchers.hasText("Okay"));
    }

    //Unit Test | Confirm Button can be Clicked
    @Test
    public void popupButtonTest(){
        sleep(1000);
        clickOn("#closePopUpButton");
        Assert.assertTrue(controller.buttonPressed);
    }

    //Unit Test | Confirm default labels
    @Test
    public void defaultLabelsTest(){
        sleep(1000);
        FxAssert.verifyThat("#infoLabel0",LabeledMatchers.hasText(""));
        FxAssert.verifyThat("#infoLabel1",LabeledMatchers.hasText(""));
        FxAssert.verifyThat("#infoLabel2",LabeledMatchers.hasText("Account Deleted"));
    }

    //Unit Test | Confirm Erroneous Labels
    //The method to display the error message can not be called directly due to it running
    //on the UI thread rather than the main thread. Instead, testingMode is activated by a
    //boolean change and the setErrorMessage method is triggered by the button press.
    //Not an ideal test, but works for now.
    @Test
    public void errorLabelsTest(){
        sleep(1000);
        controller.setTestingMode(true);
        clickOn("#closePopUpButton");
        FxAssert.verifyThat("#infoLabel0",LabeledMatchers.hasText("An error occurred."));
        FxAssert.verifyThat("#infoLabel1",LabeledMatchers.hasText("The account could not be deleted."));
        FxAssert.verifyThat("#infoLabel2",LabeledMatchers.hasText("DELETEFAILURE"));
    }

    //Unit Test | Confirm Null Client Labels
    @Test
    public void nullClientLabelsTest(){
        sleep(1000);
        clickOn("#closePopUpButton");
        FxAssert.verifyThat("#infoLabel0",LabeledMatchers.hasText("An error occurred."));
        FxAssert.verifyThat("#infoLabel1",LabeledMatchers.hasText("The connection to the server was lost."));
        FxAssert.verifyThat("#infoLabel2",LabeledMatchers.hasText("WiseGuide will be closed."));
    }

    //Confirm that popup closes when okay is pressed (normal)
    @Test
    public void popupCloseTest() throws IOException {
        sleep(1000);
        client = new Client(); // Creates new instance of client object
        client.startConnection("127.0.0.1", 5555);
        controller.setClient(client);
        clickOn("#closePopUpButton");
        Assert.assertFalse(stage.isShowing());
    }

    //Confirm that popup closes when okay is pressed (null client)
    @Test
    public void popupNullClientCloseTest(){
        sleep(1000);
        controller.setClient(null);
        clickOn("#closePopUpButton");
        clickOn("#closePopUpButton");
        Assert.assertFalse(stage.isShowing());
    }

    //Integration Test | Confirm login page is reopened
    @Test
    public void confirmLoginReopen() throws IOException {
        sleep(1000);
        client = new Client(); // Creates new instance of client object
        client.startConnection("127.0.0.1", 5555);
        controller.setClient(client);
        clickOn("#closePopUpButton");
        sleep(1000);
        FxAssert.verifyThat(window("Welcome to WiseGuide"), WindowMatchers.isShowing());
    }

}
