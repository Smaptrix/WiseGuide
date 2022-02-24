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
import serverclientstuff.User;

import java.io.IOException;

public class MainApplication extends Application {




    //TODO - Get client and user info into the Main app

    private Client client;
    private User user;

    public Client getClient() {
        return client;
    }

    public User getUser() {
        return user;
    }

    @Override
    public void start(Stage stage) throws IOException {



        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("welcomeScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("WiseGuide by Maptrix - V1.0.0");
        stage.setScene(scene);
        stage.show();

    }

    public void transferInfo(Stage stage, Client client, User user) throws IOException {

        this.client = client;
        this.user = user;

        start(stage);


    }

    public static void main(String[] args) {
        launch();
    }
}