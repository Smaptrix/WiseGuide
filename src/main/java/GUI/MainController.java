/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Will Pitchfork
    Date Created:   04/02/2022
    Last Updated:   20/02/2022
 */

package GUI;

import client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    Button helloButton;

    @FXML
    public void initialize(){

    }


    @FXML
    protected void onHelloButtonClick() {
        System.out.println(currUser);
        System.out.println(client);

    }
}