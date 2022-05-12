/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham
    Date Created:   09/03/2022
    Last Updated:   11/05/2022
 */

package GUI;

import VenueXMLThings.VenuePage;
import client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import mediahandlers.TextManager;
import mediahandlers.ImageHandler;
import serverclientstuff.User;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Controls the venue details page
 */
public class VenueDetailsController {

    /**
     * The client that the GUI is using to connect to the server
     */
    Client client;

    /**
     * The venue that the venue details page is displaying the details of
     */
    String currVenue;

    /**
     * The current venue page xml which is being displayed
     */
    VenuePage currVenuePage;

    /**
     * The user that is currently logged into the application
     */
    User currUser;

    /**
     * Sets the client to be used by the controller to communicate with the server
     * @param client
     */
    public void setClient(Client client){this.client = client;}

    /**
     * Sets the current venue for the controller
     * @param currVenue The current venue that the program is dispalying the details of
     * @param currVenuePage The xml data for the current venue page
     * @param currUser The current user logged into the application
     */
    public void setCurrVenue(String currVenue, VenuePage currVenuePage, User currUser){
        //Sets the relevant data
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
    public MenuItem closeButton;
    @FXML
    VBox mainWindow;
    @FXML
    MenuItem aboutButton;
    @FXML
    MenuItem backButton;

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

    @FXML
    //Closes the window
    protected void onCloseButtonClick() throws IOException {

        //Doesn't try to close a connection that isn't there
        if(client.isConnected()) {
            client.closeConnection(); // Closes client connection safely.
        }
        System.exit(0);
        //Platform.exit();
    }

    @FXML
    public void onAboutButtonPress() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("about-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 300);
        Stage stage = new Stage();

        AboutController controller = fxmlLoader.getController();

        controller.setVerNum(client.getCurrVersion());
        System.out.println("Opening about page");

        stage.setTitle("WiseGuide by Maptrix - " + client.getCurrVersion());
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    @FXML
    public void onBackButtonPress() throws IOException {
        Stage currStage = (Stage) mainWindow.getScene().getWindow();
        currStage.close();
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
