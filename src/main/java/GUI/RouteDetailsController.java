/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Ben Alexander
    Date Created:   20/05/2022
    Last Updated:   26/05/2022
 */
package GUI;

import XMLTools.VenuePage;
import XMLTools.VenueXMLParser;
import client.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mediahandlers.AudioHandler;
import mediahandlers.ImageHandler;
import mediahandlers.TextManager;
import ServerClientUtility.User;

import java.io.File;
import java.io.IOException;

public class RouteDetailsController {

    /**
     * <p>
     *     The client  being used by the GUI to connect to the sever
     * </p>
     */
    private Client client;

    /**
     * <p>
     *     The current user that is logged into the application
     * </p>
     */
    private User currUser;

    /**
     * <p>
     *     The xml file containing all of the venue data
     * </p>
     */
    private VenueXMLParser xml;

    /**
     * <p>
     *     The main window for the page
     * </p>
     */
    @FXML
    private VBox mainWindow;

    /**
     * <p>
     *     The route name displayed at the top of the page
     * </p>
     */
    @FXML
    private Label routeName;

    /**
     * <p>
     *     The text-box container that will contain the description of the route
     * </p>
     */
    @FXML
    private TextArea routeText;

    /**
     * <p>
     *     The image of the map showing the route
     * </p>
     */
    @FXML
    private ImageView routeImage;

    /**
     * <p>
     *     The grid which holds the rest of the images.
     * </p>
     */
    @FXML
    private GridPane imageGrid;

    /**
     * <p>
     *     The left image on the smaller maps
     * </p>
     */
    @FXML
    private ImageView routeImage0;

    /**
     * <p>
     *     The middle image on the smaller maps
     * </p>
     */
    @FXML
    private ImageView routeImage1;

    /**
     * <p>
     *     The right image on the smaller maps
     * </p>
     */
    @FXML
    private ImageView routeImage2;

    /**
     * <p>
     *     Current Route name
     * </p>
     */
    private String currRoute;

    /**
     * <p>
     *     The page of the current route selected
     * </p>
     */
    private VenuePage currRoutePage;

    /**
     * <p>
     *     Shows the venues in the route
     * </p>
     */
    @FXML
    private ListView venuesList;

    /**
     * <p>
     *     This button allows the user to go back to the main application
     * </p>
     */
    @FXML
    public Button goBack;

    /**
     * <p>
     *     Menu item that closes the application.
     * </p>
     */
    @FXML
    public MenuItem menuClose;

    /**
     * <p>
     *     Menu item for the about page
     * </p>
     */
    @FXML
    public MenuItem aboutButton;

    /**
     * <p>
     *     Anchor pane which holds the audio description.
     * </p>
     */
    @FXML
    private AnchorPane audioAnchorPane;

    /**
     * <p>
     *     The current object selected in the list in the sidebar
     * </p>
     */
    private Object currentItemSelected = new Object();

    /**
     * <p>
     *     The currently selected venue in the list in the sidebar
     * </p>
     */
    private String selectedItem;

    /**
     * <p>
     *     The handler for the audio display.
     * </p>
     */
    private AudioHandler audioHandler;

    /**
     * <p>
     *     The index of the current image presented.
     * </p>
     */
    private int currentImageIndex;

    /**
     * <p>
     *     The number of map images shown on the screen.
     * </p>
     */
    private int maxImageIndex;


    /**
     * <p>
     *     Sets the client to be used by the main application
     * </p>
     * @param client The client to be used
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * <p>
     *     Sets the user that is logged into the application
     * </p>
     * @param currUser The user that is logged in
     */
    public void setUser(User currUser) {
        this.currUser = currUser;
    }

    /**
     * <p>
     *     Closes the application when the close button is clicked
     * </p>
     */
    @FXML
    private void onCloseButtonClick(){
        if(audioHandler != null) {
            audioHandler.pause();
            audioHandler = null;
        }
        System.exit(0);
    }

    /**
     * <p>
     *     The action that occurs when the back button is pressed
     *     Goes back to the main application
     * </p>
     */
    @FXML
    private void onBackButtonPress() {
        if(audioHandler != null) {
            audioHandler.pause();
            audioHandler = null;
        }

        Stage currStage = (Stage) mainWindow.getScene().getWindow();
        currStage.close();
    }

    /**
     * <p>
     *     Runs at the start of the GUI being opened
     * </p>
     */
    @FXML
    public void initialize() {

        currentImageIndex = 0;
        venuesList.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                openSelectedVenue();
            }
        });


        routeImage.setOnMouseClicked(click -> {
            try {
                changeImageViewed();
            } catch (IOException e) {
                System.out.println("Error: Cannot display current image: " + (currentImageIndex+1));
            }
        });


    }

    /**
     * <p>
     *     Changes the image viewed at the top.
     * </p>
     */
    private void changeImageViewed() throws IOException {
        currentImageIndex++;
        if(currentImageIndex > maxImageIndex) {currentImageIndex = 0;}
        String imageFile = (currRoutePage.getMediaSourceByID("map" + currentImageIndex));
        File tempImage = client.getFile(imageFile);
        ImageHandler imageHandler = new ImageHandler(tempImage, routeImage);
        imageHandler.load(250, 420);
        for(int i = 0; i <= maxImageIndex; i++) {
            if(i == currentImageIndex) {
                imageGrid.getChildren().get(i).setOpacity(1);
            } else {
                imageGrid.getChildren().get(i).setOpacity(0.5);
            }
        }
    }

    /**
     * <p>
     *     Opens the venue that has been double clicked
     * </p>
     */
    private void openSelectedVenue() {
        if(selectedItem == null) {
            currentItemSelected = venuesList.getSelectionModel().getSelectedItem();
        } else {
            currentItemSelected = selectedItem;
        }
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("VenueDetailsPage.fxml"));
        Stage stage = new Stage();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 900, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }

        VenueDetailsController controller = fxmlLoader.getController();

        try {
            controller.setClient(client);

            controller.setCurrVenue((String) currentItemSelected, xml.getPage("title", ((String) currentItemSelected).replaceAll(" ", "_")), currUser);//Checks to see if the venue has been favourite by the user
            stage.setScene(scene);
            stage.setTitle((String) currentItemSelected);
            stage.show();
            stage.setResizable(false);
            controller.checkIfFavourite();
        } catch(Exception E) {
            System.out.println("No Venue found in XML");
        }

        try {
            controller.loadVenueData();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to get venue data");
        }
    }

    /**
     * <p>
     *     Opens the about page when the about button is pressed
     * </p>
     * @throws IOException If the GUI cannot find the fxml page
     */
    @FXML
    private void onAboutButtonPress() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("about-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 300);
        Stage stage = new Stage();

        AboutController controller = fxmlLoader.getController();

        controller.setVerNum(client.getCurrVersion());

        System.out.println("Opening about page");

        stage.setTitle("About Page");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    /**
     * <p>
     *     Downloads all of the relevant files for the route and places theme into the GUI
     * </p>
     * @throws IOException
     */
    public void loadRouteData() throws IOException {
        routeName.setText(currRoute);

        xml = new VenueXMLParser(client.getFile("venuesLocation.xml"));

        //Downloads every media element required by the venue xml

        //TEXT
        String textFile = (currRoutePage.getMediaSourceByID("text0"));

        if(textFile != null) {

            String tempFile = String.valueOf(client.requestFile(textFile));

            //Places the text from the text file into the text manager
            TextManager textManager = new TextManager(tempFile, 470, 100);
            //Loads the text onto the GUI
            routeText.setText(textManager.loadTextFromFile());
        }


        //VENUE LIST TEXT
        String venueTextFile = (currRoutePage.getMediaSourceByID("textVenues"));

        if(venueTextFile != null) {

            String tempVenuesFile = String.valueOf(client.requestFile(venueTextFile));

            TextManager venuesTextManager = new TextManager(tempVenuesFile, 470, 100);

            String[] VenuesForList = venuesTextManager.loadTextFromFile().split(",");

            for (int j = 0; j < VenuesForList.length - 1; j++) {
                venuesList.getItems().add(VenuesForList[j].replaceAll("_", " "));
            }
        }

        //IMAGES
        int imageIndex = 0;

        while(currRoutePage.getMediaSourceByID("map" + imageIndex) != null) {

            String imageFile = (currRoutePage.getMediaSourceByID("map" + imageIndex));

            File tempImage = client.requestFile(imageFile);

            if(imageIndex == 0) {
                ImageHandler imageHandler = new ImageHandler(tempImage, routeImage);

                imageHandler.load(250, 420);
            }

            ImageView imageViewSmall = new ImageView();

            ImageHandler smallImageHandler = new ImageHandler(tempImage, imageViewSmall);

            imageGrid.add(imageViewSmall, imageIndex, 0);

            smallImageHandler.load(100, 125);

            imageIndex++;

        }

        maxImageIndex = imageIndex-1;


        //AUDIO

        String audioFile = currRoutePage.getMediaSourceByID("audio0");

        if (audioFile != null) {

            File tempAudio = client.requestFile(audioFile);

            System.out.println("This is the file path:" + tempAudio);

            Stage stage =  new Stage();

            audioHandler = new AudioHandler(tempAudio);

            audioHandler.load();

            audioAnchorPane.getChildren().add(audioHandler);


        }

        imageGrid.getChildren().get(0).setOpacity(1);

        for(int i = 1; i<=maxImageIndex; i++) {
            imageGrid.getChildren().get(i).setOpacity(0.3);
        }

    }

    /**
     * <p>
     *     Sets the current route page data.
     * </p>
     * @param route route name.
     * @param routePage route page (VenuePage).
     * @param user user details.
     */
    public void setCurrRoute(String route, VenuePage routePage, User user) {
        this.currRoute = route;
        this.currRoutePage = routePage;
        this.currUser = user;
    }

}
