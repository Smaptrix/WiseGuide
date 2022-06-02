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

    //Manual Tests | Provides an opportunity for manual tests to be performed
    //Tests to be carried out:
    // - Confirm the play/pause button plays the audio when no audio is already playing.
    // - Confirm the correct audio file plays.
    // - Confirm that the audio quality is as expected.
    // - Confirm the play/pause button pauses the audio if it is already playing.
    // - Confirm the user can seek through the audio with the progress bar slider.
    // - Confirm the volume can be adjusted.
    // - Confirm that, visually, the GUI layout is as expected.
    @Test
    public void manualTests(){
        sleep(1000000);
    }
}
