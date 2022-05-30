package tests;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import mediahandlers.AudioHandler;
import mediahandlers.VideoHandler;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.File;

/**
 * <p>
 *     Tests for the Audio Handler.
 * </p>
 */
public class AudioHandlerTests extends ApplicationTest {

    AudioHandler audioHandler;

    @Override
    public void start(Stage stage){

        int width = 1280;
        int height = 720;

        File testFile = new File("Applause.mp3");

        String source = testFile.toURI().toString();

        audioHandler = new AudioHandler(testFile);

        Scene testScene = new Scene(audioHandler, width, height, Color.BLACK);

        stage.setTitle("Audio Handler Test");

        stage.setScene(testScene);

        stage.show();

    }

    //TODO
    @Test
    public void manualTest(){
        sleep(100000);
    }
}
