package tests;

import GUI.ChangeUsernameController;
import GUI.LoginApplication;
import client.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.matcher.control.TextInputControlMatchers;
import serverclientstuff.User;

import java.io.IOException;
import java.util.Objects;

/**
 * <p>
 *     Tests for the username changing system.
 * </p>
 */
public class ChangeUsernameTests extends ApplicationTest {

    ChangeUsernameController controller;
    Client client;
    User user;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(LoginApplication.class.getResource("change-username-page.fxml")));
        Parent mainNode = loader.load();
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
        this.controller = loader.getController();
        client = new Client(); // Creates new instance of client object
        user = new User("usernameChangeAccount", "usernameChangePass");

        client.startConnection("127.0.0.1", 5555);
        client.createUser(user);

        controller.setClient(client);
        controller.setUser(user);
    }

    @Before
    public void setUpClass() throws Exception {
    }

    @After
    public void afterEach() throws IOException {
        client.deleteUser(new User("usernameChangeAccount", "usernameChangePass"));
        client.deleteUser(new User("usernameChangedAccount", "usernameChangedPass"));
    }

    @Test
    //Unit Test | Confirm that the title displays correctly.
    public void titleTest() {
        FxAssert.verifyThat("#title", Node::isVisible);
        FxAssert.verifyThat("#title", LabeledMatchers.hasText("Change Username"));
    }

    @Test
    //Unit Test | Confirm that the "Current Username" label displays correctly.
    public void usernameLabelTest() {
        FxAssert.verifyThat("#usernameText", Node::isVisible);
        FxAssert.verifyThat("#usernameText", LabeledMatchers.hasText("Current Username"));
    }

    @Test
    //Unit Test | Confirm that username displays correctly.
    public void usernameTest() {
        sleep(1000);
        FxAssert.verifyThat("#usernameLabel", LabeledMatchers.hasText("usernameChangeAccount"));
    }

    @Test
    //Unit Test | Confirm that the "Desired Username" label displays correctly.
    public void desiredUsernameLabelTest() {
        FxAssert.verifyThat("#desiredText", Node::isVisible);
        FxAssert.verifyThat("#desiredText", LabeledMatchers.hasText("Desired Username"));
    }

    @Test
    //Unit Test | Confirm that the "Desired Username" field can be typed in.
    public void desiredUsernameFieldTest() {
        sleep(1000);
        FxAssert.verifyThat("#desiredNameField", TextInputControlMatchers.hasText(""));
        FxAssert.verifyThat("#desiredNameField", Node::isVisible);
        clickOn("#desiredNameField");
        write("test");
        FxAssert.verifyThat("#desiredNameField", TextInputControlMatchers.hasText("test"));
    }

    @Test
    //Unit Test | Verify that "Confirm" button displays correctly.
    public void confirmButtonTextTest() {
        FxAssert.verifyThat("#confirmButton", Node::isVisible);
        FxAssert.verifyThat("#confirmButton", LabeledMatchers.hasText("Confirm"));
    }

    @Test
    //Unit Test | Confirm that the error field is invisible by default.
    public void errorLabelTest() {
        FxAssert.verifyThat("#errLabel", Node::isVisible);
        FxAssert.verifyThat("#errLabel", LabeledMatchers.hasText(""));
    }

    @Test
    //Unit Test | Confirm that the button can be clicked.
    //Unit Test | Confirm blank names are rejected.
    public void confirmButtonTest() {
        sleep(1000);
        clickOn("#confirmButton");
        FxAssert.verifyThat("#errLabel", LabeledMatchers.hasText("You cannot have a blank name!"));
    }

    @Test
    //Integration Test | Confirm the username cannot be changed to what it already is.
    public void sameUsernameTest() {
        controller.setTestingMode(true);
        sleep(1000);
        clickOn("#desiredNameField");
        write("usernameChangeAccount");
        clickOn("#confirmButton");
        FxAssert.verifyThat("#errLabel", LabeledMatchers.hasText("This is your current name!"));
    }

    @Test
    //Integration Test | Confirm the username cannot be changed to a taken username.
    public void takenUsernameTest() {
        sleep(1000);
        clickOn("#desiredNameField");
        write("test");
        clickOn("#confirmButton");
        FxAssert.verifyThat("#errLabel", LabeledMatchers.hasText("That name is already taken!"));
    }

    @Test
    //Integration Test | Confirm the username cannot be changed to a reserved username.
    public void reservedUsernameTest() {
        sleep(1000);
        clickOn("#desiredNameField");
        //This name is reserved as long as testing mode is off.
        write("usernameChangedAccount");
        clickOn("#confirmButton");
        FxAssert.verifyThat("#errLabel", LabeledMatchers.hasText("That username is not allowed."));
    }

    @Test
    //Integration Test | Confirm the username can be changed to a non-taken username via the GUI.
    public void changeUsernameViaGUITest() {
        controller.setTestingMode(true);
        sleep(1000);
        clickOn("#desiredNameField");
        write("usernameChangedAccount");
        clickOn("#confirmButton");
        Assert.assertEquals("usernameChangedAccount", controller.getUser().getUsername());
    }

    @Test
    //Unit Test | Confirm usernames can be changed
    public void changeUsernameTest() throws IOException {

        User oldDetails = new User("usernameChangeAccount", "usernameChangePass");
        User newDetails = new User("usernameChangedAccount", "usernameChangePass");

        Assert.assertEquals("GOODLOGIN", client.requestLogin(oldDetails));
        String result = client.requestUserNameChange("usernameChangedAccount");
        Assert.assertEquals("NAMECHANGED", result);
        Assert.assertEquals("BADLOGIN", client.requestLogin(oldDetails));
        Assert.assertEquals("GOODLOGIN", client.requestLogin(newDetails));
    }

    //Manual Tests | Provides an opportunity for manual tests to be performed
    //Tests to be carried out:
    // - Confirm GUI layout is correct.
    @Test
    public void manualTests(){
        sleep(1000000);
    }
}
