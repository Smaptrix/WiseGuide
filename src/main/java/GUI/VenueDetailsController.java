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
import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.media.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import mediahandlers.ShapeManager;
import mediahandlers.TextManager;
import mediahandlers.ImageHandler;
import serverclientstuff.User;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
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
     * @param currVenue The current venue that the program is displaying the details of
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
    public MediaView venueVideo;
    @FXML
    public ImageView venueImage;
    /**
     * <p>
     *     This button allows the user to favourite/un-favourite a venue
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
    public SubScene priceSubScene;
    @FXML
    public Group priceGroup;
    @FXML
    public Group ratingGroup;
    @FXML
    public TextArea image0AltText;
    @FXML
    public TextArea image1AltText;
    @FXML
    public TextArea image2AltText;
    @FXML
    public TextArea image3AltText;
    @FXML
    public TextArea image4AltText;
    @FXML
    public TextArea image5AltText;

    @FXML
    //Always called by the FXML Loader
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

        //Add alt text button image








        int textIndex = 0;
        int photoIndex = 0;
        int maxPhotoIndex = 5;
        String imageFile;
        //Downloads every media element required by the venue xml
        for(int i = 0; i < currVenuePage.numberOfElements; i++) {

            //NOTE - REMEMBER YOU CHANGED THE SLASH DIRECTION

            //Text onto the GUI
            while (textIndex == 0) {
                //Gets the text file
                String textFile = (currVenuePage.getMediaSourceByID("text0"));
                System.out.println("File: " + textFile);

                File tempTextFile = client.requestFile(textFile);

                //Places the text from the text file into the text manager
                TextManager textManager = new TextManager(tempTextFile.getPath(), 470, 100);
                //Loads the text onto the GUI
                venueText.setText(textManager.loadTextFromFile());

                //Sets the text index to 1, as there's only one text file for each venue
                textIndex = 1;

            }

            //Loads the first image file
            imageFile = (currVenuePage.getMediaSourceByID("image" + photoIndex));
            //Runs through and places an image in a slot up until all the slots are filled
            while (photoIndex <= maxPhotoIndex) {
                //Finding the image file
                imageFile = (currVenuePage.getMediaSourceByID("image" + photoIndex));

                //Making sure that the image file actually exists
               if(imageFile != null) {
                   //Requests the filepath for the image for
                    File  tempImageFile = client.requestFile(imageFile);



                    //Initialises the image view
                    ImageView imageView = new ImageView();

                    //Creates the image handler with the desired image filepath
                    ImageHandler imageHandler = new ImageHandler(tempImageFile, imageView);
                    // TODO: Look into accessing this value instead of magic number
                    //Loads the image into the GUI
                    imageHandler.load(220, 400);

                    //Decides which slot to place the image into
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
                //Increases the index
                photoIndex++;
            }

               //If the image is null break the while loop and stop attempting to load the images
               else{
                   break;
               }


            }
        }
        //Loads the shapes onto the page
        ShapeManager shapeManager = new ShapeManager();

        System.out.println("Price: " + currVenuePage.attributes.get("price"));
        //The price value of the current venue
        int price;
        //Sets the price value to 0 if there isn't a price field
        if (currVenuePage.attributes.get("price") != null){
            price = Integer.parseInt(currVenuePage.attributes.get("price"));
        } else {
            price = 0;
        }
        //Set up colours required for the shapes
        Color maptrixBlue = Color.web("0xAFD4E5");
        Color maptrixDarkBlue = Color.web("0x245164");

        //Initiates the 3 price circles
        Circle priceCircle0;
        Circle priceCircle1;
        Circle priceCircle2;

        //Initiates the three circle fill colours and sets them to null
        Color circleFill1 = null;
        Color circleFill2 = null;
        Color circleFill3 = null;

        //Colours in the relevant circles based on the price value
        switch (price){
            case 1:
                circleFill1 = maptrixBlue;
                break;
            case 2:
                circleFill1 = maptrixBlue;
                circleFill2 = maptrixBlue;
                break;
            case 3:
                circleFill1 = maptrixBlue;
                circleFill2 = maptrixBlue;
                circleFill3 = maptrixBlue;
                break;
            default:
                break;
        }
        //Draws the circles with the correct fill based on the price value
        priceCircle0 = shapeManager.drawCircle(20, -2, 20, circleFill1, maptrixDarkBlue, 1);
        priceCircle1 = shapeManager.drawCircle(100, -2, 20, circleFill2, maptrixDarkBlue, 1);
        priceCircle2 = shapeManager.drawCircle(180, -2, 20, circleFill3, maptrixDarkBlue, 1);

        priceGroup.getChildren().add(priceCircle0);
        priceGroup.getChildren().add(priceCircle1);
        priceGroup.getChildren().add(priceCircle2);


        System.out.println("Rating: " + currVenuePage.attributes.get("rating"));
        int rating;

        //Sets the rating value to 0 if there isn't a price field
        if (currVenuePage.attributes.get("rating") != null){
            rating = Integer.parseInt(currVenuePage.attributes.get("rating"));
        } else {
            rating = 0;
        }

        //Creates 5 instances of triangles
        Polygon triangle1;
        Polygon triangle2;
        Polygon triangle3;
        Polygon triangle4;
        Polygon triangle5;

        //Initiates the three triangle fill colours and sets them to null
        Color triangleFill1 = null;
        Color triangleFill2 = null;
        Color triangleFill3 = null;
        Color triangleFill4 = null;
        Color triangleFill5 = null;
        //Colours in the relevant triangles based on the rating value
        switch (rating) {
            case 1:
                triangleFill1 = maptrixBlue;
                break;
            case 2:
                triangleFill1 = maptrixBlue;
                triangleFill2 = maptrixBlue;
                break;
            case 3:
                triangleFill1 = maptrixBlue;
                triangleFill2 = maptrixBlue;
                triangleFill3 = maptrixBlue;
                break;
            case 4:
                triangleFill1 = maptrixBlue;
                triangleFill2 = maptrixBlue;
                triangleFill3 = maptrixBlue;
                triangleFill4 = maptrixBlue;
                break;
            case 5:
                triangleFill1 = maptrixBlue;
                triangleFill2 = maptrixBlue;
                triangleFill3 = maptrixBlue;
                triangleFill4 = maptrixBlue;
                triangleFill5 = maptrixBlue;
                break;
            default:
                break;
        }
        //Draws the triangles with the correct fill based on the price value
        triangle1 = shapeManager.drawTriangle(20,0,0, 40,40,40, triangleFill1, maptrixDarkBlue,1);
        triangle2 = shapeManager.drawTriangle(70,0,50, 40,90,40, triangleFill2, maptrixDarkBlue,1);
        triangle3 = shapeManager.drawTriangle(120,0,100, 40,140,40, triangleFill3, maptrixDarkBlue,1);
        triangle4 = shapeManager.drawTriangle(170,0,150, 40,190,40, triangleFill4, maptrixDarkBlue,1);
        triangle5 = shapeManager.drawTriangle(220,0,200, 40,240,40, triangleFill5, maptrixDarkBlue,1);

        ratingGroup.getChildren().add(triangle1);
        ratingGroup.getChildren().add(triangle2);
        ratingGroup.getChildren().add(triangle3);
        ratingGroup.getChildren().add(triangle4);
        ratingGroup.getChildren().add(triangle5);

        image0AltText.setVisible(false);
        image1AltText.setVisible(false);
        image2AltText.setVisible(false);
        image3AltText.setVisible(false);
        image4AltText.setVisible(false);
        image5AltText.setVisible(false);
    }

    /**
     * <p>
     *     The action that occurs when the close button is pressed.
     *     Closes the application
     * </p>
     * @throws IOException If the client is unable to connect to the server
     */

    //Closes the window
    @FXML
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

        System.out.println("curr: " + currVenue);

        if(currUser.getFaveVenues() != null){
            if(Arrays.asList(currUser.getFaveVenues()).contains(currVenue)){
                faveVenueButton.setText("UnFavourite");
            }
        }

    }

    /**
     * <p>
     *     This is the action that occurs when the user presses the favourite button
     *     It favourites/un-favourites the venue for the user
     * </p>
     * @throws IOException if the client cannot connect to the server
     */
    @FXML
    public void favouriteButtonAction() throws IOException {
        if(faveVenueButton.getText().equals("Favourite")){
            client.addFavouriteVenue(currVenue);
            faveVenueButton.setText("UnFavourite");

            String[] currFaves = currUser.getFaveVenues();
            //Makes sure the currFaves isn't null
            if(currFaves != null){

                //Turns the string array into a linked list
                List<String> faveVenues = new LinkedList(Arrays.asList(currUser.getFaveVenues()));

                //Adds the current venue to the current users list of favourite venues clientside
                faveVenues.add(currVenue);

                currUser.setFaveVenues(faveVenues.toArray(new String[0]));

                System.out.println(currUser.getUsername()+" faves: " + Arrays.toString(currUser.getFaveVenues()));
            }
            //If the user doesn't have a venue list
            else{
                //Create a venue list with the current venue inside it and give it to the current user
                String[] userFaves = {currVenue};

                currUser.setFaveVenues(userFaves);

            }


        }

        else if(faveVenueButton.getText().equals("UnFavourite")){
            client.removeFavouriteVenue(currVenue);
            faveVenueButton.setText("Favourite");

            //Turns the string array into a linked list
            List<String> faveVenues = new LinkedList(Arrays.asList(currUser.getFaveVenues()));

            //Removes the current venue from the current users favourite venue list clientside
            faveVenues.remove(currVenue);
            currUser.setFaveVenues(faveVenues.toArray(new String[0]));

            System.out.println(currUser.getUsername()+" faves: " + Arrays.toString(currUser.getFaveVenues()));
        }
    }

    public void altText0() {
        try {
            if (image0AltText.isVisible()) {
                image0AltText.setVisible(false);
                System.out.println("Hiding altText0");
            } else {
                image0AltText.setVisible(true);
                System.out.println("Displaying altText0");
            }

            //Gets the text file
            String altTextFile = (currVenuePage.getMediaSourceByID("altText0"));
            System.out.println("File: " + altTextFile);

            File tempTextFile = client.requestFile(altTextFile);

            //Places the text from the text file into the text manager
            TextManager textManager = new TextManager(tempTextFile.getPath(), 470, 100);
            //Loads the text onto the GUI
            image0AltText.setText(textManager.loadTextFromFile());
        } catch (Exception e) {
            image0AltText.setText("Oops... for some reason we don't have any alt text for this image");
        }
    }

    public void altText1() {
        try {
            if (image1AltText.isVisible()) {
                image1AltText.setVisible(false);
                System.out.println("Hiding altText1");
            } else {
                image1AltText.setVisible(true);
                System.out.println("Displaying altText1");
            }

            //Gets the text file
            String altTextFile = (currVenuePage.getMediaSourceByID("altText1"));
            System.out.println("File: " + altTextFile);

            File tempTextFile = client.requestFile(altTextFile);

            //Places the text from the text file into the text manager
            TextManager textManager = new TextManager(tempTextFile.getPath(), 470, 100);
            //Loads the text onto the GUI
            image1AltText.setText(textManager.loadTextFromFile());
        } catch (Exception e) {
            image1AltText.setText("Oops... for some reason we don't have any alt text for this image");
        }
    }

    public void altText2() {
        try {
            if (image2AltText.isVisible()) {
                image2AltText.setVisible(false);
                System.out.println("Hiding altText2");
            } else {
                image2AltText.setVisible(true);
                System.out.println("Displaying altText2");
            }

            //Gets the text file
            String altTextFile = (currVenuePage.getMediaSourceByID("altText2"));
            System.out.println("File: " + altTextFile);

            File tempTextFile = client.requestFile(altTextFile);

            //Places the text from the text file into the text manager
            TextManager textManager = new TextManager(tempTextFile.getPath(), 470, 100);
            //Loads the text onto the GUI
            image2AltText.setText(textManager.loadTextFromFile());
        } catch (Exception e) {
            image2AltText.setText("Oops... for some reason we don't have any alt text for this image");
        }
    }

    public void altText3() {
        try {
            if (image3AltText.isVisible()) {
                image3AltText.setVisible(false);
                System.out.println("Hiding altText3");
            } else {
                image3AltText.setVisible(true);
                System.out.println("Displaying altText3");
            }

            //Gets the text file
            String altTextFile = (currVenuePage.getMediaSourceByID("altText3"));
            System.out.println("File: " + altTextFile);

            File tempTextFile = client.requestFile(altTextFile);

            //Places the text from the text file into the text manager
            TextManager textManager = new TextManager(tempTextFile.getPath(), 470, 100);
            //Loads the text onto the GUI
            image3AltText.setText(textManager.loadTextFromFile());
        } catch (Exception e) {
            image3AltText.setText("Oops... for some reason we don't have any alt text for this image");
        }
    }

    public void altText4() {
        try {
            if (image4AltText.isVisible()) {
                image4AltText.setVisible(false);
                System.out.println("Hiding altText4");
            } else {
                image4AltText.setVisible(true);
                System.out.println("Displaying altText4");
            }

            //Gets the text file
            String altTextFile = (currVenuePage.getMediaSourceByID("altText4"));
            System.out.println("File: " + altTextFile);

            File tempTextFile = client.requestFile(altTextFile);

            //Places the text from the text file into the text manager
            TextManager textManager = new TextManager(tempTextFile.getPath(), 470, 100);
            //Loads the text onto the GUI
            image4AltText.setText(textManager.loadTextFromFile());
        } catch (Exception e) {
            image4AltText.setText("Oops... for some reason we don't have any alt text for this image");
        }
    }

    public void altText5() {
        try {
            if (image5AltText.isVisible()) {
                image5AltText.setVisible(false);
                System.out.println("Hiding altText5");
            } else {
                image5AltText.setVisible(true);
                System.out.println("Displaying altText5");
            }

            //Gets the text file
            String altTextFile = (currVenuePage.getMediaSourceByID("altText5"));
            System.out.println("File: " + altTextFile);

            File tempTextFile = client.requestFile(altTextFile);

            //Places the text from the text file into the text manager
            TextManager textManager = new TextManager(tempTextFile.getPath(), 470, 100);
            //Loads the text onto the GUI
            image5AltText.setText(textManager.loadTextFromFile());
        } catch (Exception e) {
            image5AltText.setText("Oops... for some reason we don't have any alt text for this image");
        }
    }
}
