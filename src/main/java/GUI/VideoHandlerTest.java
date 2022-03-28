package GUI;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import mediahandlers.VideoHandler;

import java.io.File;

public class VideoHandlerTest extends Application {

    @Override
    public void start(Stage stage){

        int width = 1280;
        int height = 720;

        File testFile = new File("C:\\Users\\Lee\\Videos\\clapping.mp4");

        VideoHandler test = new VideoHandler(testFile);

        Group root = new Group();
        root.getChildren().add(test.getMediaView());

        test.setFitHeight(test.get);
        test.setFitWidth(width);


        Scene testScene = new Scene(root, width, height);

        stage.setTitle("Video Handler Test");

        stage.setScene(testScene);

        stage.show();

        test.play();
    }

    public static void main(String[] args) {
        launch();
    }

}
