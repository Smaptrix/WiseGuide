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
import javafx.scene.image.Image;
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
 * <p>
 *     Tests for the Image Handler.
 * </p>
 */
public class ImageHandlerTests extends ApplicationTest {

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
    public void imageCheckTest(){
        Image testImage = test.getCurrImage();
        Image image = new Image(testFile.toURI().toString());
        Assert.assertEquals(testImage.getUrl(),image.getUrl());
    }

    @Test
    //Unit Test | Size check
    public void imageSizeCheckTest(){
        Assert.assertEquals(340.0,test.getDesiredView().getFitHeight(),0);
        Assert.assertEquals(200.0,test.getDesiredView().getFitWidth(),0);
    }

    //Manual Tests | Provides an opportunity for manual tests to be performed
    //Tests to be carried out:
    // - Confirm that the displayed image is correct.
    // - Confirm that the quality of the image is as expected.
    // - Confirm that the image size is as expected.
    @Test
    public void manualTests(){
        sleep(1000000);
    }

}
