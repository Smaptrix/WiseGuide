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

import java.io.IOException;

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
        System.out.println(currVenuePage.children.get(0).children.get(0).attributes.get("include_source"));
    }



    @FXML
    //Always called by the FXML Loader
    public void initialize() {

    }

    //Will use the client to download relevant data and place it into the page.
    public void loadVenueData() throws IOException {

        //Downloads every media element required by the venue xml
        for(int i = 0; i < currVenuePage.numberOfElements; i++){

            //NOTE - REMEMBER YOU CHANGED THE SLASH DIRECTION

            String file = (currVenuePage.children.get(0).children.get(0).attributes.get("include_source")).replaceAll("/", "\\");



            client.requestFile(file);

        }


    }



}
