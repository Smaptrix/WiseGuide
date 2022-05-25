package tests;

import GUI.AccountDeletionController;
import GUI.LoginApplication;
import client.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.matcher.control.TextInputControlMatchers;
import server.ServerUserHandler;
import serverclientstuff.User;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

/**
 * <p>
 *     Tests for the Account Deletion System.
 * </p>
 */
public class AccountDeletionTests extends ApplicationTest {

    /**
     * <p>
     *     The account deletion controller.
     * </p>
     */
    private AccountDeletionController controller;

    /**
     * <p>
     *     The current client.
     * </p>
     */
    public Client client;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(LoginApplication.class.getResource("account-delete-page.fxml")));
        Parent mainNode = loader.load();
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
        this.controller = loader.getController();
    }

    @Before
    public void setUpClass() throws Exception {
        client = new Client(); // Creates new instance of client object
        client.startConnection("127.0.0.1", 5555);
        controller.setClient(client);
    }

    @After
    public void afterEachTest() throws TimeoutException {
        release(new KeyCode[]{});
        release(new MouseButton[]{});
        FxToolkit.hideStage();
        controller.setTestingMode(false);
    }

    //For Debug Purposes Only: Open the GUI and do nothing (allows programmer to interact with GUI)
    @Test
    public void doesItWorkTest(){
        sleep(10000000);
    }

    //Unit Test | Confirm an internal testing user can be created.
    @Test
    public void testDeletionUserCreation() throws IOException {
        controller.createDeletionTestAccount();
        //No assertion needed.
    }

    //Integration Test | Confirm User Can Be Deleted with the GUI
    @Test
    public void userDeletionGUITest() throws IOException {
        sleep (1000);
        controller.setTestingMode(true);
        controller.createDeletionTestAccount();
        controller.setCurrUser(new User("accountDeletionTestUser","accountDeletionTest"));
        clickOn("#passField");
        write("accountDeletionTest");
        clickOn("#passConfirmField");
        write("accountDeletionTest");
        clickOn("#delCheckBox");
        clickOn("#deleteAccountButton");
        clickOn("#closePopUpButton");
    }

    //Unit Test | Confirm Error Field is not displayed
    @Test
    public void errorFieldInvisibleTest(){
        sleep(1000);
        FxAssert.verifyThat("#errField",LabeledMatchers.hasText(""));
    }

    //Unit Test | Confirm delete account explanation is displayed
    @Test
    public void deleteExplanationTest(){
        sleep(1000);
        FxAssert.verifyThat("#infoLabel",LabeledMatchers.hasText("Enter your password to confirm that you would like to delete your WiseGuide account."));
    }

    //Unit Test | Confirm "Delete Account" button displays correct text
    @Test
    public void DeleteAccountTextTest(){
        sleep(1000);
        FxAssert.verifyThat("#deleteAccountButton",LabeledMatchers.hasText("Delete Account"));
    }

    //Unit Test | Confirm that checkbox text is correct.
    @Test
    public void checkboxTextTest(){
        sleep(1000);
        FxAssert.verifyThat("#delCheckBox",LabeledMatchers.hasText("I understand that deletion is permanent."));
    }

    //Unit Test | Confirm that checkbox can be clicked.
    @Test
    public void clickCheckboxTest(){
        sleep(1000);
        clickOn("#delCheckBox");
        Assert.assertTrue(controller.delCheckBox.isSelected());
    }

    //Unit Test | Confirm text can be entered in password field.
    @Test
    public void enterPasswordTest(){
        sleep(1000);
        clickOn("#passField");
        write("password");
        FxAssert.verifyThat("#passField", TextInputControlMatchers.hasText("password"));
    }

    //Unit Test | Confirm text can be entered in confirm password field.
    @Test
    public void enterConfirmPasswordTest(){
        sleep(1000);
        clickOn("#passConfirmField");
        write("password");
        FxAssert.verifyThat("#passConfirmField", TextInputControlMatchers.hasText("password"));
    }

    //Unit Test | Confirm "Create Account" Button can be pressed.
    @Test
    public void deleteAccountButtonTest(){
        sleep(1000);
        clickOn("#deleteAccountButton");
        FxAssert.verifyThat("#errField", LabeledMatchers.hasText("You have not entered a password!"));
    }

    //Integration Test | Confirm account can't be deleted if checkbox is not clicked.
    @Test
    public void noCheckboxDeleteTest() {
        sleep(1000);
        clickOn("#passField");
        write("accountDeletionTest");
        clickOn("#passConfirmField");
        write("accountDeletionTest");
        clickOn("#deleteAccountButton");
        FxAssert.verifyThat("#errField",LabeledMatchers.hasText("You must click the checkbox to continue."));
    }

    //Integration Test | Confirm that account with mismatched passwords cannot be deleted.
    @Test
    public void mismatchPasswordsTest(){
        sleep(1000);
        clickOn("#passField");
        write("passwordA");
        clickOn("#passConfirmField");
        write("passwordB");
        clickOn("#deleteAccountButton");
        FxAssert.verifyThat("#errField",LabeledMatchers.hasText("The passwords do not match!"));
    }

    //Integration Test | Confirm that account cannot be deleted if password is not correct
    @Test
    public void deleteWrongPasswordTest() throws IOException {
        sleep (1000);
        controller.setTestingMode(true);
        controller.createDeletionTestAccount();
        controller.setCurrUser(new User("accountDeletionTestUser","accountDeletionTest"));
        clickOn("#passField");
        write("accountDeletionTest1");
        clickOn("#passConfirmField");
        write("accountDeletionTest1");
        clickOn("#delCheckBox");
        clickOn("#deleteAccountButton");
        FxAssert.verifyThat("#errField",LabeledMatchers.hasText("User details are incorrect."));
    }

    //Integration Test | Confirm that reserved accounts cannot be deleted.
    //Currently not needed as username field was removed and only a logged-in account can be deleted.
    /*@Test
    public void reservedNoDeleteTest() throws IOException {
        sleep(1000);
        controller.setTestingMode(false);
        controller.createDeletionTestAccount();
        clickOn("#usernameField");
        write("accountDeletionTestUser");
        clickOn("#passField");
        write("accountDeletionTest");
        clickOn("#passConfirmField");
        write("accountDeletionTest");
        clickOn("#deleteAccountButton");
        FxAssert.verifyThat("#errField",LabeledMatchers.hasText("The selected user cannot be deleted."));
    }

     */
}
