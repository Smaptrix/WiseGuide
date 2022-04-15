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

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

public class AccountDeletionTest extends ApplicationTest {

    private AccountDeletionController controller;
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
    }

    @Test
    public void doesItWorkTest(){
        sleep(100000);
    }

    @Test
    public void testDeletionUserCreation() throws IOException {
        controller.createDeletionTestAccount();
    }

    @Test
    public void userDeletionTest() throws IOException {
        sleep (1000);
        controller.createDeletionTestAccount();
        clickOn("#usernameField");
        write("accountDeletionTestUser");
        clickOn("#passField");
        write("accountDeletionTest");
        clickOn("#passConfirmField");
        write("accountDeletionTest");
        clickOn("#deleteAccountButton");
        FxAssert.verifyThat("#errField", LabeledMatchers.hasText("accountDeletionTestUser was deleted."));
    }

}
