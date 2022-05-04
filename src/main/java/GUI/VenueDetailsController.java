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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import mediahandlers.TextManager;
import mediahandlers.ImageHandler;
import serverclientstuff.User;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class VenueDetailsController {

    Client client;

    String currVenue;

    VenuePage currVenuePage;

    User currUser;

    public void setClient(Client client){this.client = client;}

    public void setCurrVenue(String currVenue, VenuePage currVenuePage, User currUser){
        this.currVenue = currVenue;
        this.currVenuePage = currVenuePage;
        this.currUser = currUser;

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
    public Button faveVenueButton;



    @FXML
    //Always called by the FXML Loader
    public void initialize() {
        venueText.setEditable(false);



    }

    //Will use the client to download relevant data and place it into the page.
    public void loadVenueData() throws IOException {

        venueName.setText(currVenue);



        //Downloads every media element required by the venue xml
        for(int i = 0; i < currVenuePage.numberOfElements; i++) {

            //NOTE - REMEMBER YOU CHANGED THE SLASH DIRECTION
            //Text
            String textFile = (currVenuePage.getMediaSourceByID("text0"));

            System.out.println("File: " + textFile);

            client.requestFile(textFile);

            TextManager textManager = new TextManager(textFile, 470, 100);
            venueText.setText(textManager.loadTextFromFile());
            //Images
            String imageFile = (currVenuePage.getMediaSourceByID("image0"));

            client.requestFile(imageFile);

            File imageFilepath = new File(imageFile);

            ImageView imageView = new ImageView();
            ImageHandler imageHandler = new ImageHandler(imageFilepath, imageView);

            //venueImage.setImage(imageHandler.getCurrImage());


        }




    }


    public void checkIfFavourite() {

        if(currUser.getFaveVenues() != null){
            if(Arrays.asList(currUser.getFaveVenues()).contains(currVenue)){
                faveVenueButton.setText("UnFavourite");
            }
        }

    }


    @FXML
    public void favouriteButtonAction() throws IOException {
        if(faveVenueButton.getText().equals("Favourite")){
            client.addFavouriteVenue(currVenue);
            faveVenueButton.setText("UnFavourite");

            /* - May be required in the future
            //Turns the string array into a list
            List<String> faveVenues = Arrays.asList(currUser.getFaveVenues());

            //Adds the current venue to the current users list of favourite venues clientside
            faveVenues.add(currVenue);

            currUser.setFaveVenues(faveVenues.toArray(new String[0]));

            System.out.println(currUser.getUsername()+" faves: " + Arrays.toString(currUser.getFaveVenues()));
            */
        }

        else if(faveVenueButton.getText().equals("UnFavourite")){
            client.removeFavouriteVenue(currVenue);
            faveVenueButton.setText("Favourite");

            /* - May be required in the future
            List<String> faveVenues = Arrays.asList(currUser.getFaveVenues());
            faveVenues.remove(currVenue);
            currUser.setFaveVenues(faveVenues.toArray(new String[0]));

            System.out.println(currUser.getUsername()+" faves: " + Arrays.toString(currUser.getFaveVenues()));

             */
        }


    }



}
