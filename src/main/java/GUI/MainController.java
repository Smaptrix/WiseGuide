/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Will Pitchfork, Joe Ingham
    Date Created:   04/02/2022
    Last Updated:   24/04/2022
 */

package GUI;

import VenueXMLThings.VenueXMLParser;
import client.Client;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Scene;

import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import serverclientstuff.User;

import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Objects;

public class MainController {

    private Client client;

    private User currUser;

    private VenueXMLParser xml;

    private String selectedItem;

    protected String desiredMap = "baseMap";

    public int mouseX;
    public int mouseY;

    Image baseMapImage;
    Image centralCentralYorkImage;
    Image centralYorkImage;
    Image hesEastImage;
    Image SEYorkImage;
    Image southCentralYorkImage;
    Image westCentralYorkImage;

    Object currentItemSelected = new Object();

    public void setClient(Client client) {
        this.client = client;
    }

    public void setUser(User currUser) {
        this.currUser = currUser;
    }


    @FXML
    MenuItem closeButton;

    @FXML
    MenuItem accDetailsButton;

    @FXML
    MenuItem signOutButton;

    @FXML
    VBox mainWindow;

    @FXML
    MenuItem aboutButton;

    @FXML
    ListView venueList;

    @FXML
    ImageView mapView;

    MapController mapController;


    @FXML
    public void initialize() {

        mapController = new MapController();

        mapView.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent);

        //Defines what happens when you double-click a venue in the venue list
        venueList.setOnMouseClicked(click -> {

            if (click.getClickCount() == 2) {
                openSelectedVenue();
            }
        });
    }

    /**
     * Opens the Venue Details Page when a venue is selected.
     */
    protected void openSelectedVenue() {
        //Use ListView's getSelected Item
        if (selectedItem == null) {
            currentItemSelected = venueList.getSelectionModel()
                    .getSelectedItem();
        } else {
                currentItemSelected = selectedItem;
        }

        //Opens the generic venue page with the current venue selected which is used to populate the venue information
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
        controller.setCurrVenue((String) currentItemSelected, xml.getPage("title", (String) currentItemSelected));
        try {
            controller.loadVenueData();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to get venue data");
        }
        stage.setScene(scene);
        stage.setTitle((String) currentItemSelected);
        stage.show();
        stage.setResizable(false);
    }

    EventHandler<MouseEvent> mouseEvent = new EventHandler<>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            mouseX = (int) mouseEvent.getSceneX();
            mouseY = (int) mouseEvent.getSceneY();

            System.out.println(mouseX + " ... " + mouseY);
            selectVenueOnMap();
            if (!Objects.equals(selectedItem, "ignore")) {
                openSelectedVenue();
            }
        }
    };

    public void selectVenueOnMap() {
        Point2D mousePosition = new Point2D(mouseX, mouseY);
        createImageObjects();

        if (Objects.equals(desiredMap, "baseMap")) {
            baseMapSelecting(mousePosition);
        } else if (Objects.equals(desiredMap, "hesEastMap")) {
            UoYMapSelecting(mousePosition);
        }
    }

    private void baseMapSelecting(Point2D mousePosition) {
        if ((mousePosition.getX() > mapController.getBase_NRM_min().getX()) && (mousePosition.getX() < mapController.getBase_NRM_max().getX()) && (mousePosition.getY() > mapController.getBase_NRM_min().getY()) && (mousePosition.getY() < mapController.getBase_NRM_max().getY())) {
            selectedItem = "National Railway Museum York";
        } else if ((mousePosition.getX() > mapController.getBase_25_min().getX()) && (mousePosition.getX() < mapController.getBase_25_max().getX()) && (mousePosition.getY() > mapController.getBase_25_min().getY()) && (mousePosition.getY() < mapController.getBase_25_max().getY())) {
            selectedItem = "ignore";
            desiredMap = "CentralYorkMap";
            mapView.setImage(centralYorkImage);
        } else if ((mousePosition.getX() > mapController.getBase_SW3_min().getX()) && (mousePosition.getX() < mapController.getBase_SW3_max().getX()) && (mousePosition.getY() > mapController.getBase_SW3_min().getY()) && (mousePosition.getY() < mapController.getBase_SW3_max().getY())) {
            selectedItem = "ignore";
            desiredMap = "SEYorkMap";
            mapView.setImage(SEYorkImage);
        } else if ((mousePosition.getX() > mapController.getBase_UoY_min().getX()) && (mousePosition.getX() < mapController.getBase_UoY_max().getX()) && (mousePosition.getY() > mapController.getBase_UoY_min().getY()) && (mousePosition.getY() < mapController.getBase_UoY_max().getY())) {
            selectedItem = "ignore";
            desiredMap = "hesEastMap";
            mapView.setImage(hesEastImage);
        } else if ((mousePosition.getX() > mapController.getBase_Charles_min().getX()) && (mousePosition.getX() < mapController.getBase_Charles_max().getX()) && (mousePosition.getY() > mapController.getBase_Charles_min().getY()) && (mousePosition.getY() < mapController.getBase_Charles_max().getY())) {
            selectedItem = "Charles XII";
        } else if ((mousePosition.getX() > mapController.getBase_RKC_min().getX()) && (mousePosition.getX() < mapController.getBase_RKC_max().getX()) && (mousePosition.getY() > mapController.getBase_RKC_min().getY()) && (mousePosition.getY() < mapController.getBase_RKC_max().getY())) {
            selectedItem = "Roger Kirk Centre";
        } else if ((mousePosition.getX() > mapController.getBase_JBM_min().getX()) && (mousePosition.getX() < mapController.getBase_JBM_max().getX()) && (mousePosition.getY() > mapController.getBase_JBM_min().getY()) && (mousePosition.getY() < mapController.getBase_JBM_max().getY())) {
            selectedItem = "University of York JB Morrell Library";
        } else if ((mousePosition.getX() > mapController.getBase_rowntree_min().getX()) && (mousePosition.getX() < mapController.getBase_rowntree_max().getX()) && (mousePosition.getY() > mapController.getBase_rowntree_min().getY()) && (mousePosition.getY() < mapController.getBase_rowntree_max().getY())) {
            selectedItem = "Rowntree Park";
        } else if ((mousePosition.getX() > mapController.getBase_millennium_min().getX()) && (mousePosition.getX() < mapController.getBase_millennium_max().getX()) && (mousePosition.getY() > mapController.getBase_millennium_min().getY()) && (mousePosition.getY() < mapController.getBase_millennium_max().getY())) {
            selectedItem = "Millennium Fields";
        } else {
            selectedItem = "ignore";
            desiredMap = "baseMap";
            mapView.setImage(baseMapImage);
        }
    }

    private void UoYMapSelecting(Point2D mousePosition) {
        if ((mousePosition.getX() > mapController.getUoY3_JBM_min().getX()) && (mousePosition.getX() < mapController.getUoY3_JBM_max().getX()) && (mousePosition.getY() > mapController.getUoY3_JBM_min().getY()) && (mousePosition.getY() < mapController.getUoY3_JBM_max().getY())) {
            selectedItem = "University of York JB Morrell Library";
        } else if ((mousePosition.getX() > mapController.getUoY3_RKC_min().getX()) && (mousePosition.getX() < mapController.getUoY3_RKC_max().getX()) && (mousePosition.getY() > mapController.getUoY3_RKC_min().getY()) && (mousePosition.getY() < mapController.getUoY3_RKC_max().getY())) {
            selectedItem = "Roger Kirk Centre";
        } else if ((mousePosition.getX() > mapController.getUoY3_Charles_min().getX()) && (mousePosition.getX() < mapController.getUoY3_Charles_max().getX()) && (mousePosition.getY() > mapController.getUoY3_Charles_min().getY()) && (mousePosition.getY() < mapController.getUoY3_Charles_max().getY())) {
            selectedItem = "Charles XII";
        } else if ((mousePosition.getX() > mapController.getUoY3_BlackBull_min().getX()) && (mousePosition.getX() < mapController.getUoY3_BlackBull_max().getX()) && (mousePosition.getY() > mapController.getUoY3_BlackBull_min().getY()) && (mousePosition.getY() < mapController.getUoY3_BlackBull_max().getY())) {
            selectedItem = "The Black Bull";
        } else if ((mousePosition.getX() > mapController.getUoY3_Cecils_min().getX()) && (mousePosition.getX() < mapController.getUoY3_Cecils_max().getX()) && (mousePosition.getY() > mapController.getUoY3_Cecils_min().getY()) && (mousePosition.getY() < mapController.getUoY3_Cecils_max().getY())) {
            selectedItem = "Cecil's Pizza - Grill";
        } else if ((mousePosition.getX() > mapController.getUoY3_RCH_min().getX()) && (mousePosition.getX() < mapController.getUoY3_RCH_max().getX()) && (mousePosition.getY() > mapController.getUoY3_RCH_min().getY()) && (mousePosition.getY() < mapController.getUoY3_RCH_max().getY())) {
            selectedItem = "The Ron Cooke Hub";
        } else if ((mousePosition.getX() > mapController.getUoY3_Piazza_min().getX()) && (mousePosition.getX() < mapController.getUoY3_Piazza_max().getX()) && (mousePosition.getY() > mapController.getUoY3_Piazza_min().getY()) && (mousePosition.getY() < mapController.getUoY3_Piazza_max().getY())) {
            selectedItem = "Piazza Building";
        } else {
            selectedItem = "ignore";
            desiredMap = "baseMap";
            mapView.setImage(baseMapImage);
        }

    }

    private void createImageObjects() {
        try {
            //baseMapImage = new Image(new FileInputStream("@../resources/Maps/baseMap.png"));
            baseMapImage = new Image(new FileInputStream("src/main/resources/Maps/baseMap.png"));
            centralCentralYorkImage = new Image(new FileInputStream("src/main/resources/Maps/centralCentralYorkMap.png"));
            centralYorkImage = new Image(new FileInputStream("src/main/resources/Maps/centralYorkMap.png"));
            hesEastImage = new Image(new FileInputStream("src/main/resources/Maps/hesEastMap.png"));
            SEYorkImage = new Image(new FileInputStream("src/main/resources/Maps/SEYorkMap.png"));
            southCentralYorkImage = new Image(new FileInputStream("src/main/resources/Maps/southCentralYorkMap.png"));
            westCentralYorkImage = new Image(new FileInputStream( "src/main/resources/Maps/westCentralYorkMap.png"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("uh oh...");
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
    //Opens the account details page
    protected void onAccDetailsButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("account-details-page.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 420, 240);
        AccountDetailsController controller = fxmlLoader.getController();
        controller.setClient(client);
        controller.setUser(currUser);
        controller.setMapStage((Stage)mainWindow.getScene().getWindow());
        stage.setScene(scene);
        stage.setTitle("Account Creation");
        stage.show();
        stage.setResizable(false);
    }


    @FXML
    //Logs the user out and reopens the login page
    protected void onSignOutButtonClick() throws IOException {

        if(client.isConnected()) {
            client.requestLogout();
        }

        //Reopens the login page
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("login-page.fxml"));

        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 640, 400);

        System.out.println("Loaded login page again");

        LoginController controller = fxmlLoader.getController();
        controller.setClient(client);
        stage.setScene(scene);
        stage.setTitle("Welcome to WiseGuide");
        stage.show();
        stage.setResizable(false);


        Stage currStage = (Stage) mainWindow.getScene().getWindow();
        currStage.close();

    }

    @FXML
    protected void onAboutButtonPress() throws IOException {


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
     * Loads the list of venues into the listview on the main application
     */
    protected void loadListOfVenues() {


            //Tries to download the venue lists from the server
            try {
                client.requestVenueXMLFile();
                System.out.println("OUT");
            } catch (IOException e) {
                System.out.print("Failed to download venue lists");
            }
        



    //Provides the controller with the list of venue types it should expect
    xml = new  VenueXMLParser(client.getFile("venuesLocation.xml"));

    List<String> venueNameList = xml.getPageNames();


    //Iterates through every venue name and adds it to the menu item
        for (String s : venueNameList) {

            //Strips the header and final quotation mark from each title
            String stripped_title = s.substring(7, s.length() -1);

            venueList.getItems().add(stripped_title);


        }
    }

/*
    private final Point2D base_NRM_min = new Point2D(344, 202);
    private final Point2D base_NRM_max = new Point2D(373, 240);

    private final Point2D base_25_min = new Point2D(437, 200);
    private final Point2D base_25_max = new Point2D(475, 249);

    private final Point2D base_SW3_min = new Point2D(509, 251);
    private final Point2D base_SW3_max = new Point2D(545, 295);

    private final Point2D base_UoY_min = new Point2D(738, 336);
    private final Point2D base_UoY_max = new Point2D(776, 388);

    private final Point2D base_Charles_min = new Point2D(665, 347);
    private final Point2D base_Charles_max = new Point2D(695, 388);

    private final Point2D base_RKC_min = new Point2D(599, 347);
    private final Point2D base_RKC_max = new Point2D(626, 383);

    private final Point2D base_JBM_min = new Point2D(630, 309);
    private final Point2D base_JBM_max = new Point2D(656, 345);

    private final Point2D base_rowntree_min = new Point2D(439, 319);
    private final Point2D base_rowntree_max = new Point2D(465, 354);

    private final Point2D base_millennium_min = new Point2D(445, 384);
    private final Point2D base_millennium_max = new Point2D(474, 420);

    private final Point2D UoY3_JBM_min = new Point2D(308, 283);
    private final Point2D UoY3_JBM_max = new Point2D(336, 321);

    private final Point2D UoY3_RKC_min = new Point2D(243, 391);
    private final Point2D UoY3_RKC_max = new Point2D(270, 428);

    private final Point2D UoY3_Charles_min = new Point2D(444, 418);
    private final Point2D UoY3_Charles_max = new Point2D(471, 456);

    private final Point2D UoY3_BlackBull_min = new Point2D(467, 138);
    private final Point2D UoY3_BlackBull_max = new Point2D(495, 175);

    private final Point2D UoY3_Cecils_min = new Point2D(728, 242);
    private final Point2D UoY3_Cecils_max = new Point2D(756, 276);

    private final Point2D UoY3_RCH_min = new Point2D(710, 362);
    private final Point2D UoY3_RCH_max = new Point2D(739, 399);

    private final Point2D UoY3_Piazza_min = new Point2D(763, 332);
    private final Point2D UoY3_Piazza_max = new Point2D(791, 360);


    // Getters for all points
    public Point2D getBase_NRM_min() {
        return base_NRM_min;
    }
    public Point2D getBase_NRM_max() {
        return base_NRM_max;
    }

    public Point2D getBase_25_min() {
        return base_25_min;
    }
    public Point2D getBase_25_max() {
        return base_25_max;
    }

    public Point2D getBase_SW3_min() {
        return base_SW3_min;
    }
    public Point2D getBase_SW3_max() {
        return base_SW3_max;
    }

    public Point2D getBase_UoY_min() {
        return base_UoY_min;
    }
    public Point2D getBase_UoY_max() {
        return base_UoY_max;
    }

    public Point2D getBase_Charles_min() {
        return base_Charles_min;
    }
    public Point2D getBase_Charles_max() {
        return base_Charles_max;
    }

    public Point2D getBase_RKC_min() {
        return base_RKC_min;
    }
    public Point2D getBase_RKC_max() {
        return base_RKC_max;
    }

    public Point2D getBase_JBM_min() {
        return base_JBM_min;
    }
    public Point2D getBase_JBM_max() {
        return base_JBM_max;
    }

    public Point2D getBase_rowntree_min() {
        return base_rowntree_min;
    }
    public Point2D getBase_rowntree_max() {
        return base_rowntree_max;
    }

    public Point2D getBase_millennium_min() {
        return base_millennium_min;
    }
    public Point2D getBase_millennium_max() {
        return base_millennium_max;
    }

    public Point2D getUoY3_JBM_min() {
        return UoY3_JBM_min;
    }
    public Point2D getUoY3_JBM_max() {
        return UoY3_JBM_max;
    }

    public Point2D getUoY3_RKC_min() {
        return UoY3_RKC_min;
    }
    public Point2D getUoY3_RKC_max() {
        return UoY3_RKC_max;
    }

    public Point2D getUoY3_Charles_min() {
        return UoY3_Charles_min;
    }
    public Point2D getUoY3_Charles_max() {
        return UoY3_Charles_max;
    }

    public Point2D getUoY3_BlackBull_min() {
        return UoY3_BlackBull_min;
    }
    public Point2D getUoY3_BlackBull_max() {
        return UoY3_BlackBull_max;
    }

    public Point2D getUoY3_Cecils_min() {
        return UoY3_Cecils_min;
    }
    public Point2D getUoY3_Cecils_max() {
        return UoY3_Cecils_max;
    }

    public Point2D getUoY3_RCH_min() {
        return UoY3_RCH_min;
    }
    public Point2D getUoY3_RCH_max() {
        return UoY3_RCH_max;
    }

    public Point2D getUoY3_Piazza_min() {
        return UoY3_Piazza_min;
    }
    public Point2D getUoY3_Piazza_max() {
        return UoY3_Piazza_max;
    }

 */
}