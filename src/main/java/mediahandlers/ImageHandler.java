package mediahandlers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;


//Handles Images
public class ImageHandler extends MediaHandler{


    Image currImage;

    //Gets the image from the given filepath
    public void imageFromFile(){

      File imgFile = new File(filePath);

      currImage = new Image(imgFile.toURI().toString());



    }

    //Places the image into the desired image view
    public void intoImgView(ImageView imageView){

        imageView.setImage(currImage);


    }

}
