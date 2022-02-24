package GUI;

import TestFX.TestFXBase;
import javafx.scene.Scene;
import org.junit.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxRobotException;
import org.testfx.framework.junit.ApplicationTest;

public class LoginAppTest extends TestFXBase {

    @Test(expected = FxRobotException.class)
    public void clickOnCreateAcc() {
        sleep(1000);
        moveTo("#createAccButton");
        sleep(1000);
        clickOn("#createAccButton");
        sleep(1000);
        //moveTo();

    }

    @Test(expected = FxRobotException.class)
    public void clickOnLogin() {
        sleep(1000);
        clickOn("#loginButton");
    }

    @Test(expected = FxRobotException.class)
    public void clickOnExit() {
        sleep(1000);
        clickOn("#exitButton");
    }

}
