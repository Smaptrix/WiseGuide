package GUI;

import VenueXMLThings.VenueXMLParser;
import client.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
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
     * The label that displays any error information
     */
    @FXML
    Label errLabel;


    /**
     * The checkbox to indicate a user only wants to see their favourite venues
     */
    @FXML
    CheckBox faveCheckBox;

    /**
     * The checkbox to indicate a user wants to see food venues
     */
    @FXML
    CheckBox foodCheckBox;

    /**
     * The checkbox to indicate a user wants to see drinking venues
     */
    @FXML
    CheckBox drinkCheckBox;

    /**
     * The checkbox to indicate a user wants to see sightseeing venues
     */
    @FXML
    CheckBox sightseeingCheckBox;

    /**
     * The checkbox to indicate a user wants to see study space venues
     */
    @FXML
    CheckBox studySpacesCheckBox;




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
     * @return true - if a venue was able to be selected with the given criteria
     */
    @FXML
    public boolean onVenuePickButtonPress(){

        List<String> listOfPossibleVenues = listOfVenues;

        //Removes users non-favourite venues from the possible list
        if(faveCheckBox.isSelected()){

            //Get the current users faveourite lists
            List<String> userFaveList = List.of(currUser.getFaveVenues());

            if(userFaveList.isEmpty()){
                errLabel.setText("You have no favourites!");
                return false;
            }

            //Only retain the values that are also stored in the user favourite list
            listOfPossibleVenues.retainAll(userFaveList);

            System.out.println("Current possible list: " + listOfPossibleVenues);
        }

        //Retains the drinking venues in the possible list
        if(drinkCheckBox.isSelected()){

        }

        //Retains the food venues in the possible list
        if(foodCheckBox.isSelected()){

        }

        //Retains the sightseeing venues in the possible list
        if(sightseeingCheckBox.isSelected()){

        }

        //Retains the study space venues in the possible list
        if(studySpacesCheckBox.isSelected()){

        }




        errLabel.setText("Unknown Error");
        return false;
    }

    /**
     * Checks to see if the given venue list is empty
     * @param venueList the list of venues
     * @return true if the list is empty, false others
     */
    private boolean emptyListChecker(List<String> venueList){

        return false;
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

        Stage currStage = (Stage) errLabel.getScene().getWindow();
        currStage.close();
    }




}
