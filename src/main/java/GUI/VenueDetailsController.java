/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham
    Date Created:   09/03/2022
    Last Updated:   12/05/2022
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
 * <p>
 *     Controls the venue details page
 * </p>
 */
public class VenueDetailsController {

    /**
     * <p>
     *     The client that the GUI is using to connect to the server
     * </p>
     */
    Client client;

    /**
     * <p>
     *     The venue that the venue details page is displaying the details of
     * </p>
     */
    String currVenue;

    /**
     * <p>
     *     The current venue page xml which is being displayed
     * </p>
     */
    VenuePage currVenuePage;

    /**
     * <p>
     *     The user that is currently logged into the application
     * </p>
     */
    User currUser;

    /**
     * <p>
     *     Sets the client to be used by the controller to communicate with the server
     * </p>
     * @param client
     */
    public void setClient(Client client){this.client = client;}

    /**
     * <p>
     *     Sets the current venue for the controller
     * </p>
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

    /**
     * <p>
     *     This label is the label that displays the name of the venue
     * </p>
     */
    @FXML
    public Label venueName;
    /**
     * <p>
     *     This text area is the area that displays the text information regarding the venue
     * </p>
     */
    @FXML
    public TextArea venueText;
    /**
     * <p>
     *     This ImageView is the place that displays the images of the venue
     * </p>
     */
    @FXML
    public ImageView venueImage;
    /**
     * <p>
     *     This button allows the user to favourite/unfavourite a venue
     * </p>
     */
    @FXML
    public Button faveVenueButton;


    /**
     * <p>
     *     This button is on the menubar and allows the user to close the application
     * </p>
     */
    @FXML
    public MenuItem closeButton;
    /**
     * <p>
     *     This VBox is the box that holds all of the GUI inside of it
     * </p>
     */
    @FXML
    VBox mainWindow;
    /**
     * <p>
     *     This button on the menubar allows the user to open the about page
     * </p>
     */
    @FXML
    MenuItem aboutButton;
    /**
     * <p>
     *     This button on the menubar allows the user to go back to the main application
     * </p>
     */
    @FXML
    MenuItem backButton;

    /**
     * <p>
     *     This function is always called by the GUI when it opens up
     * </p>
     */
    @FXML
    public void initialize() {
        venueText.setEditable(false);



    }

    /**
     * <p>
     *     Loads the venue data from the xml file to display on the GUI
     * </p>
     * @throws IOException when the client cannot connect to the server
     */
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

    /**
     * <p>
     *     The action that occurs when the close button is pressed.
     *     Closes the application
     * </p>
     * @throws IOException If the client is unable to connect to the server
     */
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

    /**
     * <p>
     *     The action that occurs when the about button is pressed
     *     Opens the about page
     * </p>
     * @throws IOException if the client cannot connect to the server
     */
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

    /**
     * <p>
     *     The action that occurs when the back button is pressed
     *     Goes back to the main application
     * </p>
     */
    @FXML
    public void onBackButtonPress() {
        Stage currStage = (Stage) mainWindow.getScene().getWindow();
        currStage.close();
    }

    /**
     *<p>
     *     This function checks to see if the venue is a user favourite
     *</p>
     */
    public void checkIfFavourite() {

        if(currUser.getFaveVenues() != null){
            if(Arrays.asList(currUser.getFaveVenues()).contains(currVenue)){
                faveVenueButton.setText("UnFavourite");
            }
        }

    }

    /**
     * <p>
     *     This is the action that occurs when the user presses the favourite button
     *     It favourites/unfavourites the venue for the user
     * </p>
     * @throws IOException if the client cannot connect to the server
     */
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
