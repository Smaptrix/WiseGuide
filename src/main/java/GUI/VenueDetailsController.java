/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham
    Date Created:   09/03/2022
    Last Updated:   09/03/2022
 */

package GUI;

import VenueXMLThings.VenuePage;
import client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import mediahandlers.TextManager;
import mediahandlers.ImageHandler;

import java.io.File;
import java.io.IOException;

public class VenueDetailsController {

    Client client;

    String currVenue;

    VenuePage currVenuePage;

    public void setClient(Client client){this.client = client;}

    public void setCurrVenue(String currVenue, VenuePage currVenuePage){
        this.currVenue = currVenue;
        this.currVenuePage = currVenuePage;

        System.out.println(currVenuePage);
        System.out.println(currVenuePage.attributes);
        System.out.println(currVenuePage.children.get(0).attributes.get("include_source"));
    }

    @FXML
    public Label venueName;
    @FXML
    public TextArea venueText;
    @FXML
    public ImageView venueImage;
    @FXML
    public ImageView venueImage0;
    @FXML
    public ImageView venueImage1;
    @FXML
    public ImageView venueImage2;
    @FXML
    public ImageView venueImage3;
    @FXML
    public ImageView venueImage4;
    @FXML
    public ImageView venueImage5;
    @FXML
    //Always called by the FXML Loader
    public void initialize() {

    }

    //Will use the client to download relevant data and place it into the page.
    public void loadVenueData() throws IOException {

        venueName.setText(currVenue);


        int textIndex = 0;
        int photoIndex = 0;
        int maxPhotoIndex = 5;
        String imageFile = null;
        //ImageView[] imageViews = {venueImage0, venueImage1, venueImage2, venueImage3, venueImage4, venueImage5};
        //Downloads every media element required by the venue xml
        for(int i = 0; i < currVenuePage.numberOfElements; i++) {

            //NOTE - REMEMBER YOU CHANGED THE SLASH DIRECTION


            while (textIndex == 0) {
                //Text
                String textFile = (currVenuePage.getMediaSourceByID("text0"));
                System.out.println("File: " + textFile);

                client.requestFile(textFile);

                TextManager textManager = new TextManager(textFile, 470, 100);
                venueText.setText(textManager.loadTextFromFile());

                //Sets the text index to 1, as there's only one text file for each venue
                textIndex = 1;
            }

            imageFile = (currVenuePage.getMediaSourceByID("image" + photoIndex));
            System.out.println(imageFile);
            while (photoIndex <= maxPhotoIndex && imageFile != null) {
                //Images
                imageFile = (currVenuePage.getMediaSourceByID("image" + photoIndex));

                client.requestFile(imageFile);

                File imageFilepath = new File(imageFile);
                System.out.println("This is the file path:" + imageFilepath);

                ImageView imageView = new ImageView();

                ImageHandler imageHandler = new ImageHandler(imageFilepath, imageView);
                // TODO: Look into accessing this value instead of magic number
                imageHandler.load(220, 400);
                //venueImage0.setImage(imageHandler.getCurrImage());
                switch (photoIndex) {
                    case 0:
                        venueImage0.setImage(imageHandler.getCurrImage());
                        break;
                    case 1:
                        venueImage1.setImage(imageHandler.getCurrImage());
                        break;
                    case 2:
                        venueImage2.setImage(imageHandler.getCurrImage());
                        break;
                    case 3:
                        venueImage3.setImage(imageHandler.getCurrImage());
                        break;
                    case 4:
                        venueImage4.setImage(imageHandler.getCurrImage());
                        break;
                    case 5:
                        venueImage5.setImage(imageHandler.getCurrImage());
                        break;
                }

                photoIndex++;
            }
        }
    }
}
