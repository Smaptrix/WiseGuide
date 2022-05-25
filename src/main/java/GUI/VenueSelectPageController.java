package GUI;

import VenueXMLThings.VenueXMLParser;
import client.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import serverclientstuff.User;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Controls the GUI page that the user can use to have a venue selected for them
 */
public class VenueSelectPageController {

    /**
     * The client that is connected to the server
     */
    Client client;

    /**
     * The current user that is logged into the application
     */
    User currUser;

    /**
     * The list of the venues available for the user to view
     */
    List<String> listOfVenues;

    /**
     * <p>
     *     The xml file containing all of the venue data
     * </p>
     */
    private VenueXMLParser xml;

    /**
     * Sets the client to be used by the controller
     * @param client the client
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Sets the current user logged into the app
     * @param currUser the user
     */
    public void setCurrUser(User currUser) {
        this.currUser = currUser;
    }

    /**
     * Sets the list of venues
     * @param listOfVenues the venue list
     */
    public void setListOfVenues(List<String> listOfVenues) {
        this.listOfVenues = listOfVenues;
    }

    /**
     * Sets the current xml file
     * @param xml the xml file
     */
    public void setXml(VenueXMLParser xml) {
        this.xml = xml;
    }

    /**
     * Looks at the options selected by the user, then selects a relevant venue
     */
    @FXML
    public void onVenuePickButtonPress(){

    }

    /**
     * Selects a random venue from the given venue list
     */
    @FXML
    public void onRandomVenueButtonPress(){

        //Create a random object
        Random rand = new Random();

        //Pick a random item from the venue list
        String randomVenue = listOfVenues.get(rand.nextInt(listOfVenues.size()));

        //Open that random venues details page
        // TODO: add an extra scene for loading page
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
        controller.setCurrVenue( randomVenue, xml.getPage("title", randomVenue), currUser);
        //Checks to see if the venue has been favourite by the user
        stage.setScene(scene);
        stage.setTitle(randomVenue);
        stage.show();
        stage.setResizable(false);
        controller.checkIfFavourite();
        try {
            controller.loadVenueData();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to get venue data");
        }

        //Close the venue selector page




    }




}
