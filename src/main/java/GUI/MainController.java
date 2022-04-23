/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Will Pitchfork, Joe Ingham
    Date Created:   04/02/2022
    Last Updated:   24/02/2022
 */

package GUI;

import VenueXMLThings.VenueXMLParser;
import client.Client;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import serverclientstuff.User;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.List;

public class MainController {

    private Client client;

    private User currUser;

    private VenueXMLParser xml;

    public int mouseX;
    public int mouseY;

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


    @FXML
    public void initialize() {

        //Defines what happens when you double click a venue in the venue list
        venueList.setOnMouseClicked(click -> {

            if (click.getClickCount() == 2) {
                //Use ListView's getSelected Item
                Object currentItemSelected = venueList.getSelectionModel()
                        .getSelectedItem();


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

            }
        });


        // If mouse is double clicked on the map, get x and y coordinates
        mapView.setOnMouseClicked(click -> {
            Point p = MouseInfo.getPointerInfo().getLocation();


            if (click.getClickCount() == 2) {
                mouseX = (int) p.getX();
                mouseY = (int) p.getY();

                System.out.println("X Position = " + mouseX);
                System.out.println("Y Position = " + mouseY);
            }

            // Doesn't quite work, detects whole screen, not the window.
        });
    }

    // TODO: could someone help me look at this as this is how I get the scene mouse position not the screen. cant get it to work though
    /*
    EventHandler<MouseEvent> mouseEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            String eType = mouseEvent.getEventType().toString();
            if(eType.equals("MOUSE_PRESSED")) {
                mouseX = (int) mouseEvent.getSceneX();
                mouseY = (int) mouseEvent.getSceneY();
                System.out.println(mouseX + " ... " + mouseY);
            }
        }
    }; */

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
        stage.setScene(scene);
        stage.setTitle("Account Creation");
        stage.show();
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

}