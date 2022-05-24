package GUI;

import VenueXMLThings.VenuePage;
import VenueXMLThings.VenueXMLParser;
import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mediahandlers.ImageHandler;
import mediahandlers.TextManager;
import serverclientstuff.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class RouteDetailsController {

    /**
     * <p>
     *     The client  being used by the GUI to conect to the sever
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
    VBox mainWindow;

    /**
     * <p>
     *     The route name displayed at the top of the page
     * </p>
     */
    @FXML
    Label routeName;

    /**
     * <p>
     *     The text-box container that will contain the description of the route
     * </p>
     */
    @FXML
    TextArea routeText;

    /**
     * <p>
     *     The image of the map showing the route
     * </p>
     */
    @FXML
    ImageView routeImage;

    /**
     * <p>
     *     The audio description of the route
     * </p>
     */
    @FXML
    SubScene routeAudio;

    /**
     * <p>
     *     Current Route name
     * </p>
     */
    String currRoute;

    /**
     * <p>
     *     The page of the current route selected
     * </p>
     */
    VenuePage currRoutePage;

    /**
     * <p>
     *     Shows the venues in the route
     * </p>
     */
    @FXML
    ListView venuesList;

    /**
     * <p>
     *     This button on the menubar allows the user to go back to the main application
     * </p>
     */
    @FXML
    MenuItem backButton;

    /**
     * <p>
     *     The current object selected in the list in the sidebar
     * </p>
     */
    Object currentItemSelected = new Object();

    /**
     * <p>
     *     The currently selected venue in the list in the sidebar
     * </p>
     */
    String selectedItem;

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
        System.exit(0);
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
     * <p>
     *     Runs at the start of the GUI being opened
     * </p>
     */
    @FXML
    public void initialize() {
        venuesList.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                openSelectedVenue();
            }
        });
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
        controller.setClient(client);
        System.out.println("THIS IS THE VENUE NAME SEARCHING: " + ((String) currentItemSelected).replaceAll(" ", "_"));

        controller.setCurrVenue((String) currentItemSelected, xml.getPage("title", (String) ((String) currentItemSelected).replaceAll(" ", "_")), currUser);//Checks to see if the venue has been favourite by the user
        stage.setScene(scene);
        stage.setTitle((String) currentItemSelected);
        stage.show();
        stage.setResizable(false);
        controller.checkIfFavourite();
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
    public void onAboutButtonPress() throws IOException {
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


    public void loadRouteData() throws IOException {
        routeName.setText(currRoute);

        xml = new VenueXMLParser(client.getFile("venuesLocation.xml"));

        int textIndex = 0;
        int photoIndex = 0;
        int maxPhotoIndex = 5;
        String imageFile = null;
        //Downloads every media element required by the venue xml

        //Text onto the GUI
        //Gets the text file
        String textFile = (currRoutePage.getMediaSourceByID("text0"));
        System.out.println("File: " + textFile);

        //TODO crashes if opened more than once due to the file already being downloaded

        String tempFile = String.valueOf(client.requestFile(textFile).toPath());

        //Places the text from the text file into the text manager
        TextManager textManager = new TextManager(tempFile, 470, 100);
        //Loads the text onto the GUI
        routeText.setText(textManager.loadTextFromFile());


        //VENUE LIST
        String venueTextFile = (currRoutePage.getMediaSourceByID("textVenues"));

        System.out.println("Venues text file: " + venueTextFile);

        String tempVenuesFile = String.valueOf(client.requestFile(venueTextFile).toPath());

        TextManager venuesTextManager = new TextManager(tempVenuesFile, 470, 100);

        String[] VenuesForList = venuesTextManager.loadTextFromFile().split(",");

        for (int j = 0; j < VenuesForList.length; j++) {
            venuesList.getItems().add(VenuesForList[j].replaceAll("_", " "));
        }

        //IMAGE
        imageFile = (currRoutePage.getMediaSourceByID("map" + photoIndex));

        //Making sure that the image file actually exists
        if (imageFile != null) {
            //Requests the filepath for the image for
            client.requestFile(imageFile);

            //Sets up the filepath for the image
            File imageFilepath = new File(imageFile);
            System.out.println("This is the file path:" + imageFilepath);

            //Initialises the image view
            ImageView imageView = new ImageView();

            //Creates the image handler with the desired image filepath
            ImageHandler imageHandler = new ImageHandler(imageFilepath, imageView);
            // TODO: Look into accessing this value instead of magic number
            //Loads the image into the GUI
            imageHandler.load(220, 400);

            routeImage.setImage(imageHandler.getCurrImage());

        }
    }

    public void setCurrRoute(String route, VenuePage routePage, User user) {
        this.currRoute = route;
        this.currRoutePage = routePage;
        this.currUser = user;
    }

}
