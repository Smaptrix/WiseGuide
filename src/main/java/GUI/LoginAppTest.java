package GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.junit.Test;
import java.io.IOException;

import org.testfx.framework.junit.ApplicationTest;

public class LoginAppTest extends ApplicationTest {

    public LoginApplication app;
    public Stage stage;

    @Test
    public void appTest() throws IOException {
        app = new LoginApplication();


    }


}
