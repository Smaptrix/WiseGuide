/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Will Pitchfork, Joe Ingham
    Date Created:   04/02/2022
    Last Updated:   11/05/2022
 */

package GUI;

import VenueXMLThings.VenueXMLParser;
import client.Client;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Scene;

import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import serverclientstuff.User;

import java.awt.Desktop;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *     The controller for the main page of the application
 * </p>
 */
public class MainController {

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
     *     The currently selected venue in the list in the sidebar
     * </p>
     */
    private String selectedItem;

    /**
     * <p>
     *     The currently selected route in the list in the sidebar
     * </p>
     */
    private String selectedRoute;

    /**
     * <p>
     *     The map we desire to display
     * </p>
     */
    protected String desiredMap = "baseMap";

    /**
     * <p>
     *     The mouses position on the screen
     * </p>
     */
    public int mouseX;
    public int mouseY;

    /**
     * <p>
     *     The main map image
     * </p>
     */
    Image baseMapImage;
    /**
     * <p>
     *     The central  of york central image map
     * </p>
     */
    Image centralCentralYorkImage;
    /**
     *<p>
     *      The central york image map
     *</p>
     */
    Image centralYorkImage;
    /**
    *   <p>
     *  The heslington east image map
     *</p>
     */
    Image hesEastImage;
    /**
     * <p>
     *     The south east york image map
     * </p>
     */
    Image SEYorkImage;
    /**
     * <p>
     *     The south of central york image map
     * </p>
     */
    Image southCentralYorkImage;
    /**
     * <p>
     *     The west of central york image map
     * </p>
     */
    Image westCentralYorkImage;

    /**
     * <p>
     *     The current object selected in the list in the sidebar
     * </p>
     */
    Object currentItemSelected = new Object();

    /**
     * <p>
     *     The current route selected in the list in the sidebar
     * </p>
     */
    Object currentRouteSelected = new Object();

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
     *     The top menu bar
     * </p>
     */
    @FXML
    MenuBar menuBar;

    /**
     * <p>
     *     The File menu
     * </p>
     */
    @FXML
    Menu menuBarFile;

    /**
     * <p>
     *     The Accounts menu
     * </p>
     */
    @FXML
    Menu menuBarAccount;

    /**
     * <p>
     *     The Help menu
     * </p>
     */
    @FXML
    Menu menuBarHelp;

    /**
     * <p>
     *     The button on the menubar which closes the application
     * </p>
     */
    @FXML
    public MenuItem closeButton;

    /**
     * <p>
     *     The button on the menubar that lets the user access their details page
     * </p>
     */
    @FXML
    public MenuItem accDetailsButton;

    /**
     * <p>
     *     The button on the menubar that lets the user logout of the application
     * </p>
     */
    @FXML
    public MenuItem signOutButton;

    /**
     * <p>
     *     The uhhhh - Will what is this?
     * </p>
     */
    @FXML
    VBox mainWindow;

    /**
     * <p>
     *     The button on the menubar that allows the user to access the about page
     * </p>
     */
    @FXML
    public MenuItem aboutButton;

    /**
     * <p>
     *     The button on the menubar that allows the user to access the manual
     * </p>
     */
    @FXML
    public MenuItem manualButton;

    /**
     * <p>
     *     The list on the sidebar which displays the list of venues
     * </p>
     */
    @FXML
    ListView venueList;

    /**
     * <p>
     *     The accordion which holds the collapsable venue types.
     * </p>
     */
    @FXML
    Accordion venueAccordion;

    /**
     * <p>
     *     The list on the sidebar which displays the list of bars
     * </p>
     */
    @FXML
    ListView barsList;

    /**
     * <p>
     *     The list on the sidebar which displays the list of cafes
     * </p>
     */
    @FXML
    ListView cafesList;

    /**
     * <p>
     *     The list on the sidebar which displays the list of clubs
     * </p>
     */
    @FXML
    ListView clubsList;

    /**
     * <p>
     *     The list on the sidebar which displays the list of fast food places
     * </p>
     */
    @FXML
    ListView fastFoodList;

    /**
     * <p>
     *     The list on the sidebar which displays the list of pubs
     * </p>
     */
    @FXML
    ListView pubsList;

    /**
     * <p>
     *     The list on the sidebar which displays the list of restaurants
     * </p>
     */
    @FXML
    ListView restaurantsList;

    /**
     * <p>
     *     The list on the sidebar which displays the list of green spaces
     * </p>
     */
    @FXML
    ListView greenSpacesList;

    /**
     * <p>
     *     The list on the sidebar which displays the list of study spaces
     * </p>
     */
    @FXML
    ListView studySpacesList;

    /**
     * <p>
     *     The list on the sidebar which displays the list of sightseeing places
     * </p>
     */
    @FXML
    ListView sightseeingList;

    /**
     * <p>
     *     The list of  the routes available
     * </p>
     */
    @FXML
    ListView routesList;

    /**
     * <p>
     *     The image which displays the current map view
     * </p>
     */
    @FXML
    ImageView mapView;

    /**
     * <p>
     *     The controller which controls the map
     * </p>
     */
    MapController mapController;


    /**
     * <p>
     *     Runs at the start of the GUI being opened
     * </p>
     */
    @FXML
    public void initialize() {

        mapController = new MapController();
        mapView.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent);


        //Defines what happens when you double-click a venue in the venue list
        barsList.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                openSelectedVenue("bars");
            }
        });
        clubsList.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                openSelectedVenue("clubs");
            }
        });
        cafesList.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                openSelectedVenue("cafes");
            }
        });
        restaurantsList.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                openSelectedVenue("restaurants");
            }
        });
        fastFoodList.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                openSelectedVenue("fastFood");
            }
        });
        pubsList.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                openSelectedVenue("pubs");
            }
        });
        greenSpacesList.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                openSelectedVenue("greenSpaces");
            }
        });
        studySpacesList.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                openSelectedVenue("studySpaces");
            }
        });
        sightseeingList.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                openSelectedVenue("sightSeeing");
            }
        });
        routesList.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                openSelectedRoute();
            }
        });


    }

    /**
     * <p>
     *     Opens the Venue Details Page when a venue is selected.
     * </p>
     */
    protected void openSelectedVenue(String venueType) {
        //Use ListView's getSelected Item
        if (selectedItem == null) {
            //System.out.println("Expanded Pane ID: " + venueAccordion.getExpandedPane().getId());

            switch(venueType) {
                case "clubs":
                    currentItemSelected = clubsList.getSelectionModel().getSelectedItem();
                    break;
                case "bars":
                    currentItemSelected = barsList.getSelectionModel().getSelectedItem();
                    break;
                case "cafes":
                    currentItemSelected = cafesList.getSelectionModel().getSelectedItem();
                    break;
                case "restaurants":
                    currentItemSelected = restaurantsList.getSelectionModel().getSelectedItem();
                    break;
                case "fastFood":
                    currentItemSelected = fastFoodList.getSelectionModel().getSelectedItem();
                    break;
                case "pubs":
                    currentItemSelected = pubsList.getSelectionModel().getSelectedItem();
                    break;
                case "greenSpaces":
                    currentItemSelected = greenSpacesList.getSelectionModel().getSelectedItem();
                    break;
                case "studySpaces":
                    currentItemSelected = studySpacesList.getSelectionModel().getSelectedItem();
                    break;
                case "sightSeeing":
                    currentItemSelected = sightseeingList.getSelectionModel().getSelectedItem();
                    break;
                case "any":
                    currentItemSelected = venueList.getSelectionModel().getSelectedItem();
                    break;
                default:
                    System.out.println("Error no venue type of " + venueType);
            }

        } else {
            currentItemSelected = selectedItem;
        }
        // TODO: add an extra scene for loading page - @WILL
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
        System.out.println("THIS IS THE VENUE NAME SEARCHING: " + ((String) ((String) currentItemSelected)).replaceAll(" ", "_"));
        controller.setCurrVenue((String) currentItemSelected, xml.getPage("title", (String) ((String) currentItemSelected).replaceAll(" ", "_")), currUser);
        //Checks to see if the venue has been favourite by the user
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
     *     Opens the route selected in the list
     * </p>
     */
    private void openSelectedRoute() {
        if(selectedRoute == null) {
            currentRouteSelected = routesList.getSelectionModel().getSelectedItem();
        } else {
            currentRouteSelected = selectedRoute;
        }
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("RouteDetailsPage.fxml"));
        Stage stage = new Stage();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 900, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }
        RouteDetailsController controller = fxmlLoader.getController();
        controller.setClient(client);
        System.out.println("THIS IS THE ROUTE NAME SEARCHING: " + ((String) ((String) currentRouteSelected)).replaceAll(" ", "_"));
        //TODO Sort out xml read-ins in controller
        controller.setCurrRoute((String) currentRouteSelected, xml.getPage("title", (String) ((String) currentRouteSelected).replaceAll(" ", "_")), currUser);
        stage.setScene(scene);
        stage.setTitle((String) currentRouteSelected);
        stage.show();
        stage.setResizable(false);

        try {
            controller.loadRouteData();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to get route data");
        }

    }

    //Gets the position of the mouse on a click
    EventHandler<MouseEvent> mouseEvent = new EventHandler<>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            mouseX = (int) mouseEvent.getSceneX();
            mouseY = (int) mouseEvent.getSceneY();

            System.out.println(mouseX + " ... " + mouseY);
            selectVenueOnMap();
            if (!Objects.equals(selectedItem, "ignore")) {
                openSelectedVenue("any");
            }
            selectedItem = null;
        }
    };

    /**
     * <p>
     *     Called when a venue is selected on a map?
     * </p>
     */
    public void selectVenueOnMap() {
        Point2D mousePosition = new Point2D(mouseX, mouseY);
        createImageObjects();

        if (Objects.equals(desiredMap, "baseMap")) {
            baseMapSelecting(mousePosition);
        } else if (Objects.equals(desiredMap, "hesEastMap")) {
            UoYMapSelecting(mousePosition);
        } else if (Objects.equals(desiredMap, "SEYorkMap")) {
            seMapSelecting(mousePosition);
        } else if (Objects.equals(desiredMap, "CentralYorkMap")) {
            cenMapSelecting(mousePosition);
        } else if (Objects.equals(desiredMap, "southCentralMap")) {
            cenSouthMapSelection(mousePosition);
        } else if (Objects.equals(desiredMap, "westCentralMap")) {
            cenWestMapSelection(mousePosition);
        } else if (Objects.equals(desiredMap, "centralCentralMap")) {
            cenCenMapSelection(mousePosition);
        }
    }

    /**
     * <p>
     *     used to select the venue on the main map based on the mouse position
     * </p>
     * @param mousePosition The current position of the mouse
     */
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

    /**
     * <p>
     *     Map selection for the University of York map
     * </p>
     * @param mousePosition The position of the map
     */
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

    /**
     * <p>
     *     Map selection for the SE York map
     * </p>
     * @param mousePosition The position of the map
     */
    private void seMapSelecting(Point2D mousePosition) {
        if ((mousePosition.getX() > mapController.getSe_efes_min().getX()) && (mousePosition.getX() < mapController.getSe_efes_max().getX()) && (mousePosition.getY() > mapController.getSe_efes_min().getY()) && (mousePosition.getY() < mapController.getSe_efes_max().getY())) {
            selectedItem = "Efes_Pizza";
        } else if ((mousePosition.getX() > mapController.getSe_rook_min().getX()) && (mousePosition.getX() < mapController.getSe_rook_max().getX()) && (mousePosition.getY() > mapController.getSe_rook_min().getY()) && (mousePosition.getY() < mapController.getSe_rook_max().getY())) {
            selectedItem = "The Rook & Gaskill";
        } else if ((mousePosition.getX() > mapController.getSe_waggon_min().getX()) && (mousePosition.getX() < mapController.getSe_waggon_max().getX()) && (mousePosition.getY() > mapController.getSe_waggon_min().getY()) && (mousePosition.getY() < mapController.getSe_waggon_max().getY())) {
            selectedItem = "The Waggon & Horses";
        } else if ((mousePosition.getX() > mapController.getSe_spark_min().getX()) && (mousePosition.getX() < mapController.getSe_spark_max().getX()) && (mousePosition.getY() > mapController.getSe_spark_min().getY()) && (mousePosition.getY() < mapController.getSe_spark_max().getY())) {
            selectedItem = "Spark York C.I.C";
        } else if ((mousePosition.getX() > mapController.getSe_paradiso_min().getX()) && (mousePosition.getX() < mapController.getSe_paradiso_max().getX()) && (mousePosition.getY() > mapController.getSe_paradiso_min().getY()) && (mousePosition.getY() < mapController.getSe_paradiso_max().getY())) {
            selectedItem = "Il Paradiso Del Cibo";
        } else {
            selectedItem = "ignore";
            desiredMap = "baseMap";
            mapView.setImage(baseMapImage);
        }
    }

    private void cenMapSelecting(Point2D mousePosition) {
        if ((mousePosition.getX() > mapController.getCen_west_min().getX()) && (mousePosition.getX() < mapController.getCen_west_max().getX()) && (mousePosition.getY() > mapController.getCen_west_min().getY()) && (mousePosition.getY() < mapController.getCen_west_max().getY())) {
            selectedItem = "ignore";
            desiredMap = "westCentralMap";
            mapView.setImage(westCentralYorkImage);
        } else if ((mousePosition.getX() > mapController.getCen_cen_min().getX()) && (mousePosition.getX() < mapController.getCen_cen_max().getX()) && (mousePosition.getY() > mapController.getCen_cen_min().getY()) && (mousePosition.getY() < mapController.getCen_cen_max().getY())) {
            selectedItem = "ignore";
            desiredMap = "centralCentralMap";
            mapView.setImage(centralCentralYorkImage);
        } else if ((mousePosition.getX() > mapController.getCen_south_min().getX()) && (mousePosition.getX() < mapController.getCen_south_max().getX()) && (mousePosition.getY() > mapController.getCen_south_min().getY()) && (mousePosition.getY() < mapController.getCen_south_max().getY())) {
            selectedItem = "ignore";
            desiredMap = "southCentralMap";
            mapView.setImage(southCentralYorkImage);
        } else if ((mousePosition.getX() > mapController.getCen_musGard_min().getX()) && (mousePosition.getX() < mapController.getCen_musGard_max().getX()) && (mousePosition.getY() > mapController.getCen_musGard_min().getY()) && (mousePosition.getY() < mapController.getCen_musGard_max().getY())) {
            selectedItem = "Museum Gardens";
        } else if ((mousePosition.getX() > mapController.getCen_deans_min().getX()) && (mousePosition.getX() < mapController.getCen_deans_max().getX()) && (mousePosition.getY() > mapController.getCen_deans_min().getY()) && (mousePosition.getY() < mapController.getCen_deans_max().getY())) {
            selectedItem = "Dean's Park";
        } else if ((mousePosition.getX() > mapController.getCen_minster_min().getX()) && (mousePosition.getX() < mapController.getCen_minster_max().getX()) && (mousePosition.getY() > mapController.getCen_minster_min().getY()) && (mousePosition.getY() < mapController.getCen_minster_max().getY())) {
            selectedItem = "York Minster";
        } else if ((mousePosition.getX() > mapController.getCen_cityWalls_min().getX()) && (mousePosition.getX() < mapController.getCen_cityWalls_max().getX()) && (mousePosition.getY() > mapController.getCen_cityWalls_min().getY()) && (mousePosition.getY() < mapController.getCen_cityWalls_max().getY())) {
            selectedItem = "York City Walls";
        } else if ((mousePosition.getX() > mapController.getCen_brew_min().getX()) && (mousePosition.getX() < mapController.getCen_brew_max().getX()) && (mousePosition.getY() > mapController.getCen_brew_min().getY()) && (mousePosition.getY() < mapController.getCen_brew_max().getY())) {
            selectedItem = "Brew & Brownie";
        } else if ((mousePosition.getX() > mapController.getCen_lucky_min().getX()) && (mousePosition.getX() < mapController.getCen_lucky_max().getX()) && (mousePosition.getY() > mapController.getCen_lucky_min().getY()) && (mousePosition.getY() < mapController.getCen_lucky_max().getY())) {
            selectedItem = "Lucky Days";
        } else if ((mousePosition.getX() > mapController.getCen_cats_min().getX()) && (mousePosition.getX() < mapController.getCen_cats_max().getX()) && (mousePosition.getY() > mapController.getCen_cats_min().getY()) && (mousePosition.getY() < mapController.getCen_cats_max().getY())) {
            selectedItem = "The Cat's Whiskers";
        } else if ((mousePosition.getX() > mapController.getCen_evil_min().getX()) && (mousePosition.getX() < mapController.getCen_evil_max().getX()) && (mousePosition.getY() > mapController.getCen_evil_min().getY()) && (mousePosition.getY() < mapController.getCen_evil_max().getY())) {
            selectedItem = "Evil Eye";
        } else if ((mousePosition.getX() > mapController.getCen_choc_min().getX()) && (mousePosition.getX() < mapController.getCen_choc_max().getX()) && (mousePosition.getY() > mapController.getCen_choc_min().getY()) && (mousePosition.getY() < mapController.getCen_choc_max().getY())) {
            selectedItem = "York's Chocolate Story";
        } else if ((mousePosition.getX() > mapController.getCen_spark_min().getX()) && (mousePosition.getX() < mapController.getCen_spark_max().getX()) && (mousePosition.getY() > mapController.getCen_spark_min().getY()) && (mousePosition.getY() < mapController.getCen_spark_max().getY())) {
            selectedItem = "Spark York C.I.C";
        } else if ((mousePosition.getX() > mapController.getCen_paradiso_min().getX()) && (mousePosition.getX() < mapController.getCen_paradiso_max().getX()) && (mousePosition.getY() > mapController.getCen_paradiso_min().getY()) && (mousePosition.getY() < mapController.getCen_paradiso_max().getY())) {
            selectedItem = "Il Paradiso Del Cibo";
        } else {
            selectedItem = "ignore";
            desiredMap = "baseMap";
            mapView.setImage(baseMapImage);
        }
    }

    private void cenSouthMapSelection(Point2D mousePosition) {
        if ((mousePosition.getX() > mapController.getCenSouth_cosy_min().getX()) && (mousePosition.getX() < mapController.getCenSouth_cosy_max().getX()) && (mousePosition.getY() > mapController.getCenSouth_cosy_min().getY()) && (mousePosition.getY() < mapController.getCenSouth_cosy_max().getY())) {
            selectedItem = "Cosy Club";
        } else if ((mousePosition.getX() > mapController.getCenSouth_spark_min().getX()) && (mousePosition.getX() < mapController.getCenSouth_spark_max().getX()) && (mousePosition.getY() > mapController.getCenSouth_spark_min().getY()) && (mousePosition.getY() < mapController.getCenSouth_spark_max().getY())) {
            selectedItem = "Spark York C.I.C";
        } else if ((mousePosition.getX() > mapController.getCenSouth_paradiso_min().getX()) && (mousePosition.getX() < mapController.getCenSouth_paradiso_max().getX()) && (mousePosition.getY() > mapController.getCenSouth_paradiso_min().getY()) && (mousePosition.getY() < mapController.getCenSouth_paradiso_max().getY())) {
            selectedItem = "Il Paradiso Del Cibo";
        } else if ((mousePosition.getX() > mapController.getCenSouth_cresci_min().getX()) && (mousePosition.getX() < mapController.getCenSouth_cresci_max().getX()) && (mousePosition.getY() > mapController.getCenSouth_cresci_min().getY()) && (mousePosition.getY() < mapController.getCenSouth_cresci_max().getY())) {
            selectedItem = "Cresci Pizzeria";
        } else if ((mousePosition.getX() > mapController.getCenSouth_hole_min().getX()) && (mousePosition.getX() < mapController.getCenSouth_hole_max().getX()) && (mousePosition.getY() > mapController.getCenSouth_hole_min().getY()) && (mousePosition.getY() < mapController.getCenSouth_hole_max().getY())) {
            selectedItem = "The Hole In Wand";
        } else if ((mousePosition.getX() > mapController.getCenSouth_deniz_min().getX()) && (mousePosition.getX() < mapController.getCenSouth_deniz_max().getX()) && (mousePosition.getY() > mapController.getCenSouth_deniz_min().getY()) && (mousePosition.getY() < mapController.getCenSouth_deniz_max().getY())) {
            selectedItem = "Deniz Best Kebab";
        } else if ((mousePosition.getX() > mapController.getCenSouth_drift_min().getX()) && (mousePosition.getX() < mapController.getCenSouth_drift_max().getX()) && (mousePosition.getY() > mapController.getCenSouth_drift_min().getY()) && (mousePosition.getY() < mapController.getCenSouth_drift_max().getY())) {
            selectedItem = "Drift-In York";
        } else if ((mousePosition.getX() > mapController.getCenSouth_roses_min().getX()) && (mousePosition.getX() < mapController.getCenSouth_roses_max().getX()) && (mousePosition.getY() > mapController.getCenSouth_roses_min().getY()) && (mousePosition.getY() < mapController.getCenSouth_roses_max().getY())) {
            selectedItem = "The Stone Roses Bar";
        } else if ((mousePosition.getX() > mapController.getCenSouth_kuda_min().getX()) && (mousePosition.getX() < mapController.getCenSouth_kuda_max().getX()) && (mousePosition.getY() > mapController.getCenSouth_kuda_min().getY()) && (mousePosition.getY() < mapController.getCenSouth_kuda_max().getY())) {
            selectedItem = "Cosy Club";
        } else {
            selectedItem = "ignore";
            desiredMap = "CentralYorkMap";
            mapView.setImage(centralYorkImage);
        }
    }

    private void cenWestMapSelection(Point2D mousePosition) {
        if ((mousePosition.getX() > mapController.getCenWest_popworld_min().getX()) && (mousePosition.getX() < mapController.getCenWest_popworld_max().getX()) && (mousePosition.getY() > mapController.getCenWest_popworld_min().getY()) && (mousePosition.getY() < mapController.getCenWest_popworld_max().getY())) {
            selectedItem = "Popworld York";
        } else if ((mousePosition.getX() > mapController.getCenWest_salvos_min().getX()) && (mousePosition.getX() < mapController.getCenWest_salvos_max().getX()) && (mousePosition.getY() > mapController.getCenWest_salvos_min().getY()) && (mousePosition.getY() < mapController.getCenWest_salvos_max().getY())) {
            selectedItem = "Club Salvation";
        } else if ((mousePosition.getX() > mapController.getCenWest_flares_min().getX()) && (mousePosition.getX() < mapController.getCenWest_flares_max().getX()) && (mousePosition.getY() > mapController.getCenWest_flares_min().getY()) && (mousePosition.getY() < mapController.getCenWest_flares_max().getY())) {
            selectedItem = "Flares York";
        } else if ((mousePosition.getX() > mapController.getCenWest_revs_min().getX()) && (mousePosition.getX() < mapController.getCenWest_revs_max().getX()) && (mousePosition.getY() > mapController.getCenWest_revs_min().getY()) && (mousePosition.getY() < mapController.getCenWest_revs_max().getY())) {
            selectedItem = "Revolution York";
        } else if ((mousePosition.getX() > mapController.getCenWest_allBarOne_min().getX()) && (mousePosition.getX() < mapController.getCenWest_allBarOne_max().getX()) && (mousePosition.getY() > mapController.getCenWest_allBarOne_min().getY()) && (mousePosition.getY() < mapController.getCenWest_allBarOne_max().getY())) {
            selectedItem = "All Bar One York";
        } else if ((mousePosition.getX() > mapController.getCenWest_dusk_min().getX()) && (mousePosition.getX() < mapController.getCenWest_dusk_max().getX()) && (mousePosition.getY() > mapController.getCenWest_dusk_min().getY()) && (mousePosition.getY() < mapController.getCenWest_dusk_max().getY())) {
            selectedItem = "Dusk";
        } else {
            selectedItem = "ignore";
            desiredMap = "CentralYorkMap";
            mapView.setImage(centralYorkImage);
        }
    }

    private void cenCenMapSelection(Point2D mousePosition) {
        if ((mousePosition.getX() > mapController.getCenCen_brew_min().getX()) && (mousePosition.getX() < mapController.getCenCen_brew_max().getX()) && (mousePosition.getY() > mapController.getCenCen_brew_min().getY()) && (mousePosition.getY() < mapController.getCenCen_brew_max().getY())) {
            selectedItem = "Brew & Brownie";
        } else if ((mousePosition.getX() > mapController.getCenCen_evil_min().getX()) && (mousePosition.getX() < mapController.getCenCen_evil_max().getX()) && (mousePosition.getY() > mapController.getCenCen_evil_min().getY()) && (mousePosition.getY() < mapController.getCenCen_evil_max().getY())) {
            selectedItem = "Evil Eye";
        } else if ((mousePosition.getX() > mapController.getCenCen_cats_min().getX()) && (mousePosition.getX() < mapController.getCenCen_cats_max().getX()) && (mousePosition.getY() > mapController.getCenCen_cats_min().getY()) && (mousePosition.getY() < mapController.getCenCen_cats_max().getY())) {
            selectedItem = "The Cat's Whiskers";
        } else if ((mousePosition.getX() > mapController.getCenCen_revs_min().getX()) && (mousePosition.getX() < mapController.getCenCen_revs_max().getX()) && (mousePosition.getY() > mapController.getCenCen_revs_min().getY()) && (mousePosition.getY() < mapController.getCenCen_revs_max().getY())) {
            selectedItem = "Revolution York";
        } else if ((mousePosition.getX() > mapController.getCenCen_allBarOne_min().getX()) && (mousePosition.getX() < mapController.getCenCen_allBarOne_max().getX()) && (mousePosition.getY() > mapController.getCenCen_allBarOne_min().getY()) && (mousePosition.getY() < mapController.getCenCen_allBarOne_max().getY())) {
            selectedItem = "All Bar One York";
        } else if ((mousePosition.getX() > mapController.getCenCen_dusk_min().getX()) && (mousePosition.getX() < mapController.getCenCen_dusk_max().getX()) && (mousePosition.getY() > mapController.getCenCen_dusk_min().getY()) && (mousePosition.getY() < mapController.getCenCen_dusk_max().getY())) {
            selectedItem = "Dusk";
        } else if ((mousePosition.getX() > mapController.getCenCen_lucky_min().getX()) && (mousePosition.getX() < mapController.getCenCen_lucky_max().getX()) && (mousePosition.getY() > mapController.getCenCen_lucky_min().getY()) && (mousePosition.getY() < mapController.getCenCen_lucky_max().getY())) {
            selectedItem = "Lucky Days";
        } else if ((mousePosition.getX() > mapController.getCenCen_choc_min().getX()) && (mousePosition.getX() < mapController.getCenCen_choc_max().getX()) && (mousePosition.getY() > mapController.getCenCen_choc_min().getY()) && (mousePosition.getY() < mapController.getCenCen_choc_max().getY())) {
            selectedItem = "York's Chocolate Story";
        } else if ((mousePosition.getX() > mapController.getCenCen_nana_min().getX()) && (mousePosition.getX() < mapController.getCenCen_nana_max().getX()) && (mousePosition.getY() > mapController.getCenCen_nana_min().getY()) && (mousePosition.getY() < mapController.getCenCen_nana_max().getY())) {
            selectedItem = "NaNa Noodle Bar";
        } else if ((mousePosition.getX() > mapController.getCenCen_nana2_min().getX()) && (mousePosition.getX() < mapController.getCenCen_nana2_max().getX()) && (mousePosition.getY() > mapController.getCenCen_nana2_min().getY()) && (mousePosition.getY() < mapController.getCenCen_nana2_max().getY())) {
            selectedItem = "NaNa Noodle Bar";
        } else if ((mousePosition.getX() > mapController.getCenCen_deniz_min().getX()) && (mousePosition.getX() < mapController.getCenCen_deniz_max().getX()) && (mousePosition.getY() > mapController.getCenCen_deniz_min().getY()) && (mousePosition.getY() < mapController.getCenCen_deniz_max().getY())) {
            selectedItem = "Deniz Best Kebab";
        } else if ((mousePosition.getX() > mapController.getCenCen_drift_min().getX()) && (mousePosition.getX() < mapController.getCenCen_drift_max().getX()) && (mousePosition.getY() > mapController.getCenCen_drift_min().getY()) && (mousePosition.getY() < mapController.getCenCen_drift_max().getY())) {
            selectedItem = "Drift-In York";
        } else {
            selectedItem = "ignore";
            desiredMap = "CentralYorkMap";
            mapView.setImage(centralYorkImage);
        }
    }

    /**
     * <p>
     *     Finds the files for the relevant image objects for the map
     * </p>
     */
    private void createImageObjects() {
        try {
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

    /**
     * <p>
     *     Closes the application when the close button is clicked
     * </p>
     * @throws IOException if the client cannot connect to the server
     */
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
     *     Opens the account details page if the account details page is clocked
     * </p>
     * @throws IOException If the GUI cannot find the fxml page
     */
    @FXML
    //Opens the account details page
    public void onAccDetailsButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("account-details-page.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 420, 240);
        AccountDetailsController controller = fxmlLoader.getController();
        controller.setClient(client);
        controller.setUser(currUser);
        controller.setMapStage((Stage)mainWindow.getScene().getWindow());
        stage.setScene(scene);
        stage.setTitle("Account Details");
        stage.show();
        stage.setResizable(false);
    }


    /**
     *<p>
     *     Signs the user out of the application when the sign out button is pressed
     *</p>
     * @throws IOException If the client cannot connect to the server / or the fxml page cannot be found
     */
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


    /**
     * <p>
     *     Loads the list of venues into the listview on the main application
     * </p>
     */
    public void loadListOfVenuesAndRoutes() {


        //Tries to download the venue lists from the server
        try {
            client.requestVenueXMLFile();
            System.out.println("OUT");
        } catch (IOException e) {
            System.out.print("Failed to download venue lists");
        }




        //Provides the controller with the list of venue types it should expect
        xml = new VenueXMLParser(client.getFile("venuesLocation.xml"));

        List<String> venueNameList = xml.getPageNames();


        //Iterates through every venue name and adds it to the menu item
        for (String s : venueNameList) {

            //Strips the header and final quotation mark from each title
            String stripped_title = s.substring(7, s.length() -1);

            System.out.println(stripped_title);

            switch(xml.getPage("title", stripped_title).attributes.get("category")) {
                case "nightclub":
                    clubsList.getItems().add(stripped_title.replaceAll("_", " "));
                    break;
                case "bar":
                    barsList.getItems().add(stripped_title.replaceAll("_", " "));
                    break;
                case "cafe":
                    cafesList.getItems().add(stripped_title.replaceAll("_", " "));
                    break;
                case "restaurant":
                    restaurantsList.getItems().add(stripped_title.replaceAll("_", " "));
                    break;
                case "fast_food":
                    fastFoodList.getItems().add(stripped_title.replaceAll("_", " "));
                    break;
                case "pub":
                    pubsList.getItems().add(stripped_title.replaceAll("_", " "));
                    break;
                case "green_space":
                    greenSpacesList.getItems().add(stripped_title.replaceAll("_", " "));
                    break;
                case "study_space":
                    studySpacesList.getItems().add(stripped_title.replaceAll("_", " "));
                    break;
                case "sightseeing":
                    sightseeingList.getItems().add(stripped_title.replaceAll("_", " "));
                    break;
                case "route":
                    routesList.getItems().add(stripped_title.replaceAll("_", " "));
                    break;
                default:
                    System.out.println("Error: No category " + xml.getPage("title", stripped_title).attributes.get("category") + " for page " + stripped_title);
                    break;
            }


            //venueList.getItems().add(stripped_title.replaceAll("_", " "));


        }
    }


    /**
     * This opens the user manual which is stored in the Maptrix File Directory on Google Drive
     */
    @FXML
    public void onUserManualMenuButtonPress(){

        try {
            Desktop.getDesktop().browse(new URL("https://docs.google.com/document/d/1w9P1IKH5lbeHghuY0YJpdP8F_PjkEj6R/edit?usp=sharing&ouid=111971918555544856801&rtpof=true&sd=true").toURI());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


    }


    /**
     * This opens the venue selector page on the GUI - it is linked to the venue select menu item
     */
    @FXML
    public void onVenueSelectorMenuButtonPress() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("venue-selector-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = new Stage();

        VenueSelectPageController controller = fxmlLoader.getController();


        controller.setClient(client);
        controller.setCurrUser(currUser);
        controller.setXml(xml);

        //Get the current list of venues from the main application
        List<String> listOfVenues = xml.getPageNames();

        List<String> strippedListOfVenues =  new ArrayList();

        for(String s: listOfVenues){
           s =  s.substring(7, s.length() -1);
           strippedListOfVenues.add(s);
        }

        controller.setListOfVenues(strippedListOfVenues);

        System.out.println("Opening venue select page");

        stage.setTitle("Venue Select!");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);


    }


}