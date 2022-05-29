package tests;

import GUI.AccountDetailsController;
import GUI.LoginApplication;
import client.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.WindowMatchers;
import org.testfx.matcher.control.LabeledMatchers;
import serverclientstuff.User;

import java.util.Objects;

public class AccountDetailsTests extends ApplicationTest {

    AccountDetailsController controller;
    Client client;
    User user;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(LoginApplication.class.getResource("account-details-page.fxml")));
        Parent mainNode = loader.load();
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
        this.controller = loader.getController();
        user = new User("test","12345");
        controller.setUser(user);
    }

    @Before
    public void setUpClass() throws Exception {
        client = new Client(); // Creates new instance of client object
        client.startConnection("127.0.0.1", 5555);
        controller.setClient(client);
    }

    @Test
    //Unit Test | Verify username labels
    public void usernameLabelTest(){
        FxAssert.verifyThat("#usernameText", Node::isVisible);
        FxAssert.verifyThat("#usernameText", LabeledMatchers.hasText("Username:"));
    }

    @Test
    //Unit Test | Verify correct username is shown
    public void usernameTest(){
        FxAssert.verifyThat("#usernameLabel", LabeledMatchers.hasText(user.getUsername()));
    }

    @Test
    //Unit Test | Verify password labels
    public void passwordLabelTest(){
        FxAssert.verifyThat("#passwordText", Node::isVisible);
        FxAssert.verifyThat("#passwordText", LabeledMatchers.hasText("Password:"));
    }

    @Test
    //Unit Test | Verify change username button
    public void changeUsernameButtonTextTest(){
        FxAssert.verifyThat("#changeNameButton",Node::isVisible);
        FxAssert.verifyThat("#changeNameButton",LabeledMatchers.hasText("Change"));
    }

    @Test
    //Unit Test | Verify change username button opens the right window
    public void changeUsernameButtonTest(){
        sleep(1000);
        clickOn("#changeNameButton");
        FxAssert.verifyThat(window("Change username"), WindowMatchers.isShowing());
    }

    @Test
    //Unit Test | Verify change password button
    public void changePasswordButtonTextTest(){
        FxAssert.verifyThat("#changePassButton",Node::isVisible);
        FxAssert.verifyThat("#changePassButton",LabeledMatchers.hasText("Change"));
    }

    @Test
    //Unit Test | Verify change username button opens the right window
    public void changePasswordButtonTest(){
        sleep(1000);
        clickOn("#changePassButton");
        FxAssert.verifyThat(window("Change Password"), WindowMatchers.isShowing());
    }

    @Test
    public void test(){
        sleep(10000);
    }

}
