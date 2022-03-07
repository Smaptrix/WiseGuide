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
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.WindowMatchers;

import java.util.Objects;
import java.util.concurrent.TimeoutException;

public class AccountCreatedPageTest extends ApplicationTest {

    /* ===== UNIT Test for Account Created Page ===== */

    private Stage stage; //Reference to JavaFX stage so that window showing status can be checked.

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(LoginApplication.class.getResource("account-created-window.fxml")));
        Parent mainNode = loader.load();
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
        this.stage = stage;
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

    //"Okay" Button Test
    @Test
    public void okayButtonTest(){
        sleep(1000);
        clickOn("#closePopupButton");
        //FxAssert.verifyThat(window("Account Created"), WindowMatchers.isNotShowing()); --This method does not work as it can't find a window w/o a title.
        Assert.assertFalse(stage.isShowing());
    }

}
