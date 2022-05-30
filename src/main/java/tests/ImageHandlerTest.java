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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import mediahandlers.ImageHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.WindowMatchers;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

/**
 * A test for the ImageHandler developed by Joe
 */
public class ImageHandlerTest extends ApplicationTest {

    ImageHandler test;
    Group root;

    File testFile = new File("src/main/resources/Maps/baseMap.png");

    @Override
    public void start(Stage stage) throws Exception {

        int height = 340;
        int width = 200;


        ImageView testView = new ImageView();



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

        FxAssert.verifyThat(root, Node::isVisible);

        String filepath = "src/main/resources/Maps/baseMap.png";
        String currDir = System.getProperty("user.dir");
        currDir = currDir.replace('\\','/');

        String expectedPath = "file:/"+currDir+"/src/main/resources/Maps/baseMap.png";
        String actualPath = test.getCurrImage().getUrl();

        Assert.assertEquals(expectedPath,actualPath);
    }

    @Test
    //Unit Test | Image check
    //This might technically do the exact same thing as the above test...
    public void imageCheck(){
        Image testImage = test.getCurrImage();
        Image image = new Image(testFile.toURI().toString());
        Assert.assertEquals(testImage.getUrl(),image.getUrl());
    }

    @Test
    //Unit Test | Size check
    public void imageSizeCheck(){
        Assert.assertEquals(340.0,test.getDesiredView().getFitHeight(),0);
        Assert.assertEquals(200.0,test.getDesiredView().getFitWidth(),0);
    }

}
