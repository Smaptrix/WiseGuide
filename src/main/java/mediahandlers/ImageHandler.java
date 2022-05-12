/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham
    Date Created:   28/03/2022
    Last Updated:   12/05/2022
 */
package mediahandlers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;


/**
 * This handles loading images and placing them onto the GUI
 */
public class ImageHandler extends MediaHandler{

    /**
     * The current image
     */
    Image currImage;
    /**
     * The place where we wish to display the image
     */
    ImageView desiredView;

    /**
     * The constructor for the ImageHandler
     * @param filePath the filepath to the image
     * @param desiredView the view that we want to display the image in
     */
    public ImageHandler(File filePath, ImageView desiredView){
        setFilePath(filePath);
        this.desiredView = desiredView;

    }


    /**
     * This loads the image from the given filepath
     */
    private void imageFromFile(){
      currImage = new Image(filePath.toURI().toString());
    }

    /**
     * This places the current image into the image view
     */
    private void intoImgView(){
        desiredView.setImage(currImage);
    }

    /**
     * <p>
     *     Loads the image from the file and places it into the desired image view
     * </p>
     * @param height the desired height for the image
     * @param width the desired width for the image
     */
    public void load(int height, int width){
        imageFromFile();
        intoImgView();

        desiredView.setFitHeight(height);
        desiredView.setFitWidth(width);

        System.out.println(currImage);
    }

    /**
     * @return the current image
     */
    public Image getCurrImage() {
        return currImage;
    }

    /**
     * @return the current ImageView
     */
    public ImageView getDesiredView() {
        return desiredView;
    }
}
