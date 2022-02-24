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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import serverclientstuff.User;

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
    public void initialize(){



    }

    @FXML
    //Closes the window
    protected void onCloseButtonClick(){
          Platform.exit();
    }


}