package tests;

import javafx.application.Application;
import org.testfx.framework.junit.ApplicationTest;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import mediahandlers.VideoHandler;
import org.junit.Test;

import java.io.File;

/**
 * <p>
 *     Tests for the video handler.
 * </p>
 */
public class VideoHandlerTests extends ApplicationTest {

    VideoHandler videoHandler;

    @Override
    public void start(Stage stage){

        int width = 1280;
        int height = 720;

        File testFile = new File("clapping.mp4");

        String source = testFile.toURI().toString();

        videoHandler = new VideoHandler(source, height, width);

        Scene testScene = new Scene(videoHandler, width, height, Color.BLACK);

        stage.setTitle("Video Handler Test");

        stage.setScene(testScene);

        stage.show();

    }

    //Manual Tests | Provides an opportunity for manual tests to be performed
    //Tests to be carried out:
    // - Confirm the play/pause button plays the video when no video is already playing.
    // - Confirm the audio plays with the video.
    // - Confirm the correct video file plays.
    // - Confirm the correct audio plays.
    // - Confirm that the video and audio quality is as expected.
    // - Confirm the play/pause button pauses the video if it is already playing.
    // - Confirm the audio pauses with the video.
    // - Confirm the user can seek through the video with the progress bar slider.
    // - Confirm the audio does not desync from the video when seeking.
    // - Confirm the volume can be adjusted.
    // - Confirm that, visually, the GUI layout is as expected.
    // - Confirm that the video’s size is correct.
    @Test
    public void manualTests(){
        sleep(1000000);
    }

    /*
    public static void main(String[] args) {
        launch();
    }
     */

}
