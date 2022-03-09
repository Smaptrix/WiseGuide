/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Will Pitchfork, Joe Ingham
    Date Created:   04/02/2022
    Last Updated:   24/02/2022
 */

package GUI;

import client.Client;
import javafx.application.Platform;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import serverclientstuff.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainController {

    private Client client;

    private User currUser;



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
                    scene = new Scene(fxmlLoader.load(), 420, 240);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                VenueDetailsController controller = fxmlLoader.getController();
                controller.setClient(client);
                controller.setCurrVenue((String) currentItemSelected);
                stage.setScene(scene);
                stage.setTitle((String) currentItemSelected);
                stage.show();

            }
        });





    }

    @FXML
    //Closes the window
    protected void onCloseButtonClick() throws IOException {
        // TODO: @Joe we need to look at this, give me a massive error for some reason but is happy to just close the program
        //Doesn't try to close a connection that isn't there
        //if(client.isConnected()) {
        //    client.closeConnection(); // Closes client connection safely.
        //}
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
    try{
        client.requestVenueLists();
    }catch(IOException e){
        System.out.print("Failed to download venue lists");
    }

    //Provides the controller with the list of venue types it should expect
    List<String> VenueTypes = Arrays.asList("Bars.txt", "Cafes.txt", "Clubs.txt", "FastFood.txt", "Pubs.txt", "Restaurants.txt");

    //Iterates through every venue type and acquires the relevant text file containing the venues
    for(int i = 0; i < VenueTypes.size(); i++){

            File currFile = client.getFile("VenueLists/" + VenueTypes.get(i));

            //Iterates through every line in the file and adds it to the listview
            try(BufferedReader br = new BufferedReader(new FileReader(currFile))){
                String line;
                while((line = br.readLine()) != null) {
                    venueList.getItems().add(line);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }






    }






}