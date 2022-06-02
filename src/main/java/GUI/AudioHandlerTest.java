package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import mediahandlers.AudioHandler;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class AudioHandlerTest extends Application {

    private Scene scene;

    @Override
    public void start(Stage stage) {

        File testFile = new File("Applause.mp3");

        AudioHandler test = new AudioHandler(testFile);

        test.load();

        scene = new Scene(test, 400, 40, Color.BLACK);

        stage.setScene(scene);
        stage.toFront();
        stage.setTitle("Audio Handler Test");

        stage.show();
    }

    @Before
    public void setUpClass() {
        if(scene != null) {
            scene = null;
        }
    }

    @Test
    public void NoAutoPlay() {

        File testFile = new File("Applause.mp3");

        AudioHandler test = new AudioHandler(testFile);

        test.load();

        scene = new Scene(test, 400, 40, Color.BLACK);
    }

    @Test
    public void AutoPlay() {

        File testFile = new File("Applause.mp3");

        AudioHandler test = new AudioHandler(testFile);

        test.setAutoplay(true);

        test.load();

        scene = new Scene(test, 400, 40, Color.BLACK);
    }

    public static void main(String[] args) { launch(); }

}
