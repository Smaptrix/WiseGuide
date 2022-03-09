/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham
    Date Created:   09/03/2022
    Last Updated:   09/03/2022
 */

package GUI;

import client.Client;
import javafx.fxml.FXML;

public class VenueDetailsController {

    Client client;

    String currVenue;

    public void setClient(Client client){this.client = client;}

    public void setCurrVenue(String currVenue){this.currVenue = currVenue;}


    @FXML
    //Always called by the FXML Loader
    public void initialize() {

    }

    //Will use the client to download relevant data and place it into the page.
    public void loadVenueData(){

    }



}
