/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham
    Date Created:   09/03/2022
    Last Updated:   09/03/2022
 */

package GUI;

import VenueXMLThings.VenuePage;
import client.Client;
import javafx.fxml.FXML;

public class VenueDetailsController {

    Client client;

    String currVenue;

    VenuePage currVenuePage;

    public void setClient(Client client){this.client = client;}

    public void setCurrVenue(String currVenue, VenuePage currVenuePage){
        this.currVenue = currVenue;
        this.currVenuePage = currVenuePage;

        System.out.println(currVenuePage);
        System.out.println(currVenuePage.attributes);
    }



    @FXML
    //Always called by the FXML Loader
    public void initialize() {

    }

    //Will use the client to download relevant data and place it into the page.
    public void loadVenueData(){

    }



}
