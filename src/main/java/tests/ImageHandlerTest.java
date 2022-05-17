/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham
    Date Created:   18/02/2022
    Last Updated:   11/05/2022
 */
package tests;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import mediahandlers.ImageHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.io.File;
import java.util.concurrent.TimeoutException;

/**
 * A test for the ImageHandler developed by Joe
 */
public class ImageHandlerTest extends ApplicationTest {


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
    @Before
    public void setUpClass() {

    }














}
