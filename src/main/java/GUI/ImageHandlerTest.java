/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham
    Date Created:   18/02/2022
    Last Updated:   11/05/2022
 */
package GUI;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import mediahandlers.ImageHandler;

import java.io.File;

/**
 * A test for the ImageHandler developed by Joe
 */
public class ImageHandlerTest extends Application {


    @Override
    public void start(Stage stage) throws Exception {


     int height = 340;
     int width = 200;


     ImageView testView = new ImageView();

     File testFile = new File("src/main/resources/Maps/baseMap.png");

    ImageHandler test = new ImageHandler(testFile, testView);

    test.load(height, width);

    Group root = new Group(testView);

    Scene testScene = new Scene(root, width, height);

    stage.setTitle("Image Handler Test");

    stage.setScene(testScene);


    stage.show();





    }
    //Entry point for image manager test.
    public static void main(String[] args) {
        launch();
    }




}
