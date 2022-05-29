package tests;

import GUI.ChangePasswordController;
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

public class ChangePasswordTests extends ApplicationTest {

    ChangePasswordController controller;
    Client client;
    User user;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(LoginApplication.class.getResource("change-password-page.fxml")));
        Parent mainNode = loader.load();
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
        this.controller = loader.getController();
        client = new Client(); // Creates new instance of client object
        user = new User("usernameChangeAccount","usernameChangePass");

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
        client.deleteUser(new User("usernameChangeAccount","usernameChangePass"));
        client.deleteUser(new User("usernameChangeAccount","usernameChangedPass"));
    }

    @Test
    //Unit Test | Confirm that the title displays correctly.
    public void titleTest(){
        FxAssert.verifyThat("#title", Node::isVisible);
        FxAssert.verifyThat("#title", LabeledMatchers.hasText("Change Password"));
    }

    @Test
    //Unit Test | Confirm that the "Current Password" label displays correctly.
    public void currPassLabelTest(){
        FxAssert.verifyThat("#currPassLabel", Node::isVisible);
        FxAssert.verifyThat("#currPassLabel", LabeledMatchers.hasText("Current Password"));
    }

    @Test
    //Unit Test | Confirm that the "New Password" label displays correctly.
    public void newPassLabelTest(){
        FxAssert.verifyThat("#newPassLabel", Node::isVisible);
        FxAssert.verifyThat("#newPassLabel", LabeledMatchers.hasText("New Password"));
    }

    @Test
    //Unit Test | Confirm that the "ConfirmNew Password" label displays correctly.
    public void confirmNewPassLabelTest(){
        FxAssert.verifyThat("#confirmLabel", Node::isVisible);
        FxAssert.verifyThat("#confirmLabel", LabeledMatchers.hasText("Confirm New Password"));
    }

    @Test
    //Unit Test | Confirm that the "Current Password" field can be typed in.
    public void currentPassFieldTest(){
        sleep(1000);
        FxAssert.verifyThat("#currPasswordField", TextInputControlMatchers.hasText(""));
        FxAssert.verifyThat("#currPasswordField",Node::isVisible);
        clickOn("#currPasswordField");
        write("test");
        FxAssert.verifyThat("#currPasswordField", TextInputControlMatchers.hasText("test"));
    }

    @Test
    //Unit Test | Confirm that the "New Password" field can be typed in.
    public void newPassFieldTest(){
        sleep(1000);
        FxAssert.verifyThat("#newPasswordField", TextInputControlMatchers.hasText(""));
        FxAssert.verifyThat("#newPasswordField",Node::isVisible);
        clickOn("#newPasswordField");
        write("test");
        FxAssert.verifyThat("#newPasswordField", TextInputControlMatchers.hasText("test"));
    }

    @Test
    //Unit Test | Confirm that the "Confirm New Password" field can be typed in.
    public void confirmNewPassFieldTest(){
        sleep(1000);
        FxAssert.verifyThat("#newPasswordConfirmField", TextInputControlMatchers.hasText(""));
        FxAssert.verifyThat("#newPasswordConfirmField",Node::isVisible);
        clickOn("#newPasswordConfirmField");
        write("test");
        FxAssert.verifyThat("#newPasswordConfirmField", TextInputControlMatchers.hasText("test"));
    }

    @Test
    //Unit Test | Verify that "Confirm" button displays correctly.
    public void confirmButtonTextTest(){
        FxAssert.verifyThat("#changePassButton",Node::isVisible);
        FxAssert.verifyThat("#changePassButton",LabeledMatchers.hasText("Confirm"));
    }

    @Test
    //Unit Test | Verify that "Confirm" button can be clicked.
    //Unit Test | Verify that password cannot be changed if all fields are left blank.
    public void confirmButtonTest(){
        sleep(1000);
        clickOn("#changePassButton");
        FxAssert.verifyThat("#errLabel",LabeledMatchers.hasText("You must fill in all the fields!"));
    }

    @Test
    //Integration Test | Verify that password cannot be changed if the current password is not entered.
    public void noCurrPassTest(){
        sleep(1000);
        clickOn("#newPasswordField");
        write("test");
        clickOn("#newPasswordConfirmField");
        write("test");
        clickOn("#changePassButton");
        FxAssert.verifyThat("#errLabel",LabeledMatchers.hasText("You must fill in all the fields!"));
    }

    @Test
    //Integration Test | Verify that password cannot be changed if only the current password is entered.
    public void onlyCurrPassTest(){
        sleep(1000);
        clickOn("#currPasswordField");
        write("test");
        clickOn("#changePassButton");
        FxAssert.verifyThat("#errLabel",LabeledMatchers.hasText("You must fill in all the fields!"));
    }

    @Test
    //Integration Test | Verify that password cannot be changed if the desired password is not entered.
    public void noDesiredPassTest(){
        sleep(1000);
        clickOn("#currPasswordField");
        write("test");
        clickOn("#newPasswordConfirmField");
        write("test");
        clickOn("#changePassButton");
        FxAssert.verifyThat("#errLabel",LabeledMatchers.hasText("Your new passwords don't match!"));
    }

    @Test
    //Integration Test | Verify that password cannot be changed if only the desired password is entered.
    public void onlyDesiredPassTest(){
        sleep(1000);
        clickOn("#newPasswordField");
        write("test");
        clickOn("#changePassButton");
        FxAssert.verifyThat("#errLabel",LabeledMatchers.hasText("Your new passwords don't match!"));
    }

    @Test
    //Integration Test | Verify that password cannot be changed if the desired password confirmation is not entered.
    public void noConfirmPassTest(){
        sleep(1000);
        clickOn("#currPasswordField");
        write("test");
        clickOn("#newPasswordField");
        write("test");
        clickOn("#changePassButton");
        FxAssert.verifyThat("#errLabel",LabeledMatchers.hasText("Your new passwords don't match!"));
    }

    @Test
    //Integration Test | Verify that password cannot be changed if only the desired password confirmation is entered.
    public void onlyConfirmPassTest(){
        sleep(1000);
        clickOn("#newPasswordConfirmField");
        write("test");
        clickOn("#changePassButton");
        FxAssert.verifyThat("#errLabel",LabeledMatchers.hasText("Your new passwords don't match!"));
    }

    @Test
    //Unit Test | Confirm that the error field is invisible by default.
    public void errorLabelTest(){
        FxAssert.verifyThat("#errLabel",Node::isVisible);
        FxAssert.verifyThat("#errLabel",LabeledMatchers.hasText(""));
    }

    @Test
    //Unit Test | Verify that the password can be changed if the details are correct.
    public void changePasswordTest() throws IOException {
        User newDetails = new User("usernameChangeAccount","usernameChangedPass");

        Assert.assertEquals("GOODLOGIN",client.requestLogin(user));
        String result = client.requestPasswordChange(user.getPassword(),newDetails.getPassword());
        Assert.assertEquals("PASSCHANGED",result);
        Assert.assertEquals("BADLOGIN",client.requestLogin(user));
        Assert.assertEquals("GOODLOGIN",client.requestLogin(newDetails));
    }

    @Test
    //Integration Test | Verify that the password can be changed via the GUI if the details are correct.
    public void changePasswordViaGUITest() throws IOException {
        User newDetails = new User("usernameChangeAccount","usernameChangedPass");
        sleep(1000);
        clickOn("#currPasswordField");
        write(user.getPassword());
        clickOn("#newPasswordField");
        write(newDetails.getPassword());
        clickOn("#newPasswordConfirmField");
        write(newDetails.getPassword());
        clickOn("#changePassButton");
        sleep(1000);
        Assert.assertEquals("usernameChangedPass", controller.getUser().getPassword());
    }

    @Test
    //Unit Test | Verify that the password cannot be changed if the details are incorrect.
    public void changePasswordRejectTest() throws IOException {
        User wrongDetails = new User("usernameChangeAccount","notcorrect");
        User newDetails = new User("usernameChangeAccount","usernameChangedPass");

        Assert.assertEquals("GOODLOGIN",client.requestLogin(user));
        String result = client.requestPasswordChange(wrongDetails.getPassword(),newDetails.getPassword());
        Assert.assertEquals("INCORRECTPASS",result);
        Assert.assertEquals("GOODLOGIN",client.requestLogin(user));
        Assert.assertEquals("BADLOGIN",client.requestLogin(wrongDetails));
        Assert.assertEquals("BADLOGIN",client.requestLogin(newDetails));
    }

    @Test
    //Unit Test | Verify that the password cannot be changed via the GUI if the details are incorrect.
    public void changePasswordViaGUIRejectTest() throws IOException {
        User newDetails = new User("usernameChangeAccount","usernameChangedPass");
        sleep(1000);
        clickOn("#currPasswordField");
        write("wrongpassword");
        clickOn("#newPasswordField");
        write(newDetails.getPassword());
        clickOn("#newPasswordConfirmField");
        write(newDetails.getPassword());
        clickOn("#changePassButton");
        FxAssert.verifyThat("#errLabel",LabeledMatchers.hasText("Your current password is incorrect!"));
    }

}
