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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mediahandlers.TextManager;
import mediahandlers.ImageHandler;
import serverclientstuff.Utils;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

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
    //Always called by the FXML Loader
    public void initialize() {

    }

    //Will use the client to download relevant data and place it into the page.
    public void loadVenueData() throws IOException {

        venueName.setText(currVenue);



        //Downloads every media element required by the venue xml
        for(int i = 0; i < currVenuePage.numberOfElements; i++) {

            //NOTE - REMEMBER YOU CHANGED THE SLASH DIRECTION

            int j = 0;
            while (j == 0) {
                //Text

                String textFile = (currVenuePage.getMediaSourceByID("text0"));
                System.out.println("File: " + textFile);

                client.requestFile(textFile);

                TextManager textManager = new TextManager(textFile, 470, 100);
                venueText.setText(textManager.loadTextFromFile());
                j = 1;
            }


            //Images
            String imageFile = (currVenuePage.getMediaSourceByID("image0"));

            client.requestFile(imageFile);

            File imageFilepath = new File(imageFile);

            ImageView imageView = new ImageView();
            ImageHandler imageHandler = new ImageHandler(imageFilepath, imageView);

            venueImage.setImage(imageHandler.getCurrImage());
        }
    }
}
