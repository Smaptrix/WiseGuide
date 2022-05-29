package tests;

import javafx.application.Application;
import org.testfx.framework.junit.ApplicationTest;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import mediahandlers.VideoHandler;
import org.junit.Test;

import java.io.File;

public class VideoHandlerTest extends ApplicationTest {

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

    @Test
    public void manualTest(){
        sleep(100000);
    }

    /*
    public static void main(String[] args) {
        launch();
    }
     */

}
