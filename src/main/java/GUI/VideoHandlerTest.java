package GUI;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import mediahandlers.VideoHandler;

import java.io.File;

public class VideoHandlerTest extends Application {

    @Override
    public void start(Stage stage){

        int width = 1280;
        int height = 720;

        File testFile = new File("C:\\Users\\Lee\\Videos\\clapping.mp4");

        VideoHandler test = new VideoHandler(testFile, height, width);

        Group root = new Group();
        root.getChildren().add(test.getMediaView());

        Scene testScene = new Scene(root, width, height, Color.BLACK);

        stage.setTitle("Video Handler Test");

        stage.setScene(testScene);

        stage.show();

        test.play();
    }

    public static void main(String[] args) {
        launch();
    }

}
