/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Will Pitchfork
    Date Created:   04/02/2022
    Last Updated:   04/02/2022
 */

package GUI;


import client.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import serverclientstuff.User;


import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

public class MainApplication extends Application {




    //TODO - Get client and user info into the Main app

    private Client client;
    private User currUser;



    @Override
    public void start(Stage stage) throws IOException {



        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("welcomeScreen.fxml"));


        Scene scene = new Scene(fxmlLoader.load(), 900, 600);

        MainController controller = fxmlLoader.getController();
        controller.setClient(client);
        controller.setUser(currUser);

        controller.loadListOfVenues();

        //Sets the users favourite venues -- as long there are some
        String[] userFaveVenueList = client.requestFaveVenueList();
        if(!(userFaveVenueList == null)){
            currUser.setFaveVenues(userFaveVenueList);
            System.out.println(Arrays.toString(currUser.getFaveVenues()));
        }

        stage.setTitle("WiseGuide by Maptrix - " + client.getCurrVersion());
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);


    }

    public void transferInfoAndOpen(Stage stage, Client client, User currUser) throws IOException {

        this.client = client;
        this.currUser =  currUser;

        start(stage);


    }

    public static void main(String[] args) {
        launch();
    }


}