package GUI;

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
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;

import java.util.Objects;
import java.util.concurrent.TimeoutException;

public class LoginApplicationTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(Objects.requireNonNull(LoginApplication.class.getResource("login-page.fxml")));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    @Before
    public void setUpClass() throws Exception {
    }

    @After
    public void afterEachTest() throws TimeoutException {
        release(new KeyCode[]{});
        release(new MouseButton[]{});
        FxToolkit.hideStage();
    }

    @Test
    public void clickOnCreateAcc() throws TimeoutException {
        sleep(1000);
        moveTo("#createAccButton");
        sleep(1000);
        clickOn("#createAccButton");
        sleep(1000);
        moveTo("#ageCheckBox");
        sleep(1000);
        clickOn("#ageCheckBox");
        sleep(1000);
        moveTo("#userField");
        sleep(1000);
        clickOn("#userField");
        write("testUser");

    }


    @Test
    public void clickOnLogin() {
        sleep(1000);
        clickOn("#loginButton");
    }

    @Test
    public void clickOnExit() {
        sleep(1000);
        clickOn("#exitButton");
    }

}
