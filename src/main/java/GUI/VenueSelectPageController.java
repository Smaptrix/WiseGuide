/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham
    Date Created:   03/05/2022
    Last Updated:   03/06/2022
 */

package GUI;

import XMLTools.VenueXMLParser;
import client.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ServerClientUtility.User;

import java.io.IOException;
import java.time.Instant;
import java.util.*;

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
    public CheckBox faveCheckBox;

    /**
     * The checkbox to indicate a user wants to see food venues
     */
    @FXML
    public CheckBox foodCheckBox;

    /**
     * The checkbox to indicate a user wants to see drinking venues
     */
    @FXML
    public CheckBox drinksCheckBox;

    /**
     * The checkbox to indicate a user wants to see sightseeing venues
     */
    @FXML
    public CheckBox sightseeingCheckBox;

    /**
     * The checkbox to indicate a user wants to see study space venues
     */
    @FXML
    public CheckBox studySpacesCheckBox;

    /**
     * The random venue to be opened's name (for testing purposes).
     */
    @FXML
    public String randomVenue;


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
        this.listOfVenues = new ArrayList<>(listOfVenues);
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
        //Create a copy of the list of venues so that it isn't edited when the button is pressed
        List<String> listOfPossibleVenues = new ArrayList<>(listOfVenues);

        //Check to make sure a venue type has been selected
        if(!drinksCheckBox.isSelected() & !foodCheckBox.isSelected() & !sightseeingCheckBox.isSelected() & !studySpacesCheckBox.isSelected()){
            errLabel.setText("You have not selected any venue types!");
            return false;
        }

        //Removes users non-favourite venues from the possible list if the favourite checkbox is ticked
        if(faveCheckBox.isSelected()){

            //If the user has no favourites (if the list doesn't exist)
            if(currUser.getFaveVenues() == null){
                errLabel.setText("You have no favourites!");
                return false;
            }

            //Get the current users favourite lists
            List<String> userFaveList = List.of(currUser.getFaveVenues());

            //If the user has no favourites (and the list exists, i.e. they have removed there last favourite)
            if(userFaveList.isEmpty()){
                errLabel.setText("You have no favourites!");
                return false;
            }

            //Only retain the values that are also stored in the user favourite list
            listOfPossibleVenues.retainAll(userFaveList);

        }

        //This will contain every possible venue from the given criteria
        //It is then compared to the possible list (which is edited by the favourites)
        List<String> tempVenueList = new ArrayList<>();

        //Retains the drinking venues in the possible list
        if(drinksCheckBox.isSelected()){

            //Create the list of drinking venues
            for(String s : listOfPossibleVenues){


                //Finds the venues with tags relating to drinking (nightclubs, bars and pubs)
                if(Objects.equals(xml.getPage("title", s).attributes.get("category"), "nightclub")){
                    tempVenueList.add(s);
                }
                if(Objects.equals(xml.getPage("title", s).attributes.get("category"), "bar")){
                    tempVenueList.add(s);
                }
                if(Objects.equals(xml.getPage("title", s).attributes.get("category"), "pub")){
                    tempVenueList.add(s);
                }

            }

        }

        //Retains the food venues in the possible list
        if(foodCheckBox.isSelected()){

            //Finds venues related to food (i.e. cafes, restaurants and fast_food)
            for(String s : listOfPossibleVenues){


                if(Objects.equals(xml.getPage("title", s).attributes.get("category"), "cafe")){
                    tempVenueList.add(s);
                }
                if(Objects.equals(xml.getPage("title", s).attributes.get("category"), "restaurant")){
                    tempVenueList.add(s);
                }
                if(Objects.equals(xml.getPage("title", s).attributes.get("category"), "fast_food")){
                    tempVenueList.add(s);
                }

            }

        }

        //Retains the sightseeing venues in the possible list
        if(sightseeingCheckBox.isSelected()){

            //Finds venues related to sightseeing (i.e. green_spaces and sightseeing venues)
            for(String s : listOfPossibleVenues){

                if(Objects.equals(xml.getPage("title", s).attributes.get("category"), "green_space")){
                    tempVenueList.add(s);
                }
                if(Objects.equals(xml.getPage("title", s).attributes.get("category"), "sightseeing")){
                    tempVenueList.add(s);
                }
            }
        }

        //Retains the study space venues in the possible list (i.e. study spaces)
        if(studySpacesCheckBox.isSelected()) {

            //Finds venues related to study spaces
            for (String s : listOfPossibleVenues) {


                if (Objects.equals(xml.getPage("title", s).attributes.get("category"), "study_space")) {
                    tempVenueList.add(s);
                }
            }
        }




        //Makes sure the temp venue list isn't null
        //If it isn't null it implies that the list has been added to
        //The warning here thrown by intellij is false (the tempVenuelist isn't always null)
        if(tempVenueList == null){
            errLabel.setText("You have not selected any venue types!");
            return false;
        }




        //Combines the two lists so that only suitable venues remain
        listOfPossibleVenues.retainAll(tempVenueList);

        //If the list of possible venues is empty
        if(listOfPossibleVenues.isEmpty()){
            errLabel.setText("You have no favourites of the selected types!");
            return false;
        }

        //Create a random object
        Random rand = new Random();

        //Pick a random item from the given possible venue list
        String randomVenue = listOfPossibleVenues.get(rand.nextInt(listOfPossibleVenues.size()));

        //Opens the venue details page of the given random venue
        venueDetailsOpener(randomVenue);
        //Return a true message to tell the system a venue was selected
        return true;
    }


    /**
     * This opens the venue details page for the given venue
     * @param venueName the name of the given venue
     */
    private void venueDetailsOpener(String venueName){

        //Open the given venues details page

        //Opens the generic venue page with the current venue selected which is used to populate the venue information
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("VenueDetailsPage.fxml"));
        Stage stage = new Stage();
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 900, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Get the controller for the current page
        VenueDetailsController controller = fxmlLoader.getController();
        //Set the client for the controller
        controller.setClient(client);
        controller.setCurrVenue(venueName.replaceAll("_"," "), xml.getPage("title", venueName), currUser);
        //Checks to see if the venue has been favourite by the user
        stage.setScene(scene);
        stage.setTitle(venueName.replaceAll("_"," "));
        stage.show();
        stage.setResizable(false);
        controller.checkIfFavourite();
        //Attempt to get the venue data
        try {
            controller.loadVenueData();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to get venue data");
        }


    }



    /**
     * Selects a random venue from the given venue list
     */
    @FXML
    public void onRandomVenueButtonPress(){
        //Create a random object
        Random rand = new Random(Instant.now().toEpochMilli());

        //Removes the possibility of opening a routes page.
        boolean venueOkay = false;
        while (!venueOkay){
            randomVenue = listOfVenues.get(rand.nextInt(listOfVenues.size()));
            venueOkay = !randomVenue.equals("Study_Day_Route") && !randomVenue.equals("Night_Out_Route") && !randomVenue.equals("Tourist_Day_Route");
        }

        //Pick a random item from the venue list
        venueDetailsOpener(randomVenue);

        //Close the venue selector page
        Stage currStage = (Stage) errLabel.getScene().getWindow();
        currStage.close();
    }


}
