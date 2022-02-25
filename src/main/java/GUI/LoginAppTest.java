package GUI;

import TestFX.TestFXBase;
import javafx.scene.Scene;
import org.junit.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxRobotException;
import org.testfx.api.FxToolkit;
import org.testfx.api.FxToolkitContext;
import org.testfx.framework.junit.ApplicationTest;

import java.util.concurrent.TimeoutException;

public class LoginAppTest extends TestFXBase {

    @Test
    public void clickOnCreateAcc() throws TimeoutException {
        sleep(1000);
        moveTo("#createAccButton");
        sleep(1000);
        clickOn("#createAccButton");
        sleep(1000);
        /*moveTo("#ageCheckBox");
        sleep(1000);
        clickOn("#ageCheckBox");
        sleep(1000);*/


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
