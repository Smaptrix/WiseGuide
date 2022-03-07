package mediahandlers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;


//Handles Images
public class ImageHandler extends MediaHandler{


    Image currImage;
    ImageView desiredView;


    public ImageHandler(File filePath, ImageView desiredView){
        setFilePath(filePath);
        this.desiredView = desiredView;




    }




    //Gets the image from the given filepath
    private void imageFromFile(){
      currImage = new Image(filePath.toURI().toString());



    }

    //Places the image into the desired image view
    private void intoImgView(){

        desiredView.setImage(currImage);


    }

    //Loads the desired image into the image view required
    public void load(int height, int width){
        imageFromFile();
        intoImgView();

        desiredView.setFitHeight(height);
        desiredView.setFitWidth(width);

        System.out.println(currImage);
    }


    public Image getCurrImage() {
        return currImage;
    }

    public ImageView getDesiredView() {
        return desiredView;
    }
}
