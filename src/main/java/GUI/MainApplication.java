/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Will Pitchfork, Joe Ingham
    Date Created:   04/02/2022
    Last Updated:   11/05/2022
 */

package GUI;

import client.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import serverclientstuff.User;


import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

/**
 * <p>
 *     The main page that the application runs on
 * </p>
 */
public class MainApplication extends Application {


    /**
     * <p>
     *     The client that the application is going to use
     * </p>
     */
    private Client client;
    /**
     * <p>
     *     The current user being used by the GUI
     * </p>
     */
    private User currUser;


    /**
     * <p>
     *     Opens the FXML file and opens the GUI that is controlled by the FXML
     * </p>
     * @param stage the display that GUI is gonna be on
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {



        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("welcomeScreen.fxml"));


        Scene scene = new Scene(fxmlLoader.load(), 900, 600);

        //Set the client and current user for the controller
        MainController controller = fxmlLoader.getController();
        controller.setClient(client);
        controller.setUser(currUser);

        controller.loadListOfVenuesAndRoutes();

        //Sets the users favourite venues -- as long there are some
        String[] userFaveVenueList = client.requestFaveVenueList();

        if(!(userFaveVenueList == null)){
            currUser.setFaveVenues(userFaveVenueList);
            System.out.println("Set user faves: " + Arrays.toString(currUser.getFaveVenues()));
        }

        stage.setTitle("WiseGuide by Maptrix - " + client.getCurrVersion());
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);


    }

    /**
     * <p>
     *     Transfers the required info for the application
     * </p>
     * @param stage The stage to display the GUI on
     * @param client The client to be used for the server connection
     * @param currUser The current user
     * @throws IOException If the client cant connect to the server
     */
    public void transferInfoAndOpen(Stage stage, Client client, User currUser) throws IOException {

        this.client = client;
        this.currUser =  currUser;

        start(stage);


    }

    public static void main(String[] args) {
        launch();
    }


}