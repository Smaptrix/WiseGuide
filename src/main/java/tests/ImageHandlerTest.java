/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham
    Date Created:   18/02/2022
    Last Updated:   11/05/2022
 */
package tests;


import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import mediahandlers.ImageHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;

import java.io.File;
import java.util.Objects;

/**
 * A test for the ImageHandler developed by Joe
 */
public class ImageHandlerTest extends ApplicationTest {

    ImageHandler test;
    Group root;

    @Override
    public void start(Stage stage) throws Exception {

        int height = 340;
        int width = 200;


        ImageView testView = new ImageView();

        File testFile = new File("src/main/resources/Maps/baseMap.png");

        test = new ImageHandler(testFile, testView);

        test.load(height, width);

        root = new Group(testView);

        Scene testScene = new Scene(root, width, height);

        stage.setTitle("Image Handler Test");

        stage.setScene(testScene);


        stage.show();

    }
    @Before
    public void setUpClass() {

    }


    @Test
    //Unit test- make sure the image is loaded and visible
    public void imageVisibleTest(){


        String filepath = "file:/C:/Users/jsphi/eclipse-workspace/WiseGuide/src/main/resources/Maps/baseMap.png";

        FxAssert.verifyThat(root, Node::isVisible);

        Assert.assertTrue(Objects.equals(test.getCurrImage().getUrl(), filepath));
    }














}