package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import mediahandlers.VideoHandler;

import java.io.File;

public class VideoHandlerTest extends Application {

    @Override
    public void start(Stage stage){

        int width = 1280;
        int height = 720;

        String testFile = "C:\\Users\\Lee\\Videos\\clapping.mp4";

        VideoHandler test = new VideoHandler(testFile, height, width);

        Scene testScene = new Scene(test, width, height, Color.BLACK);

        stage.setTitle("Video Handler Test");

        stage.setScene(testScene);

        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

}
