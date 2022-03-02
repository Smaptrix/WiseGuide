package GUI;

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
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.WindowMatchers;

import java.util.Objects;
import java.util.concurrent.TimeoutException;

public class AccountCreatedPageTest extends ApplicationTest {

    /* ===== UNIT Test for Account Created Page ===== */

    @Override
    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(Objects.requireNonNull(LoginApplication.class.getResource("account-created-window.fxml")));
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

    //TODO: "Okay" Button Test
    @Test
    public void okayButtonTest(){
        sleep(1000);
        clickOn("#closePopupButton");

        //TODO: How to test window was closed? WindowMatchers does not work as the window does not have a "title" it can search for.
    }

}
