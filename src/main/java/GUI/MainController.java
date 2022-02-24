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
import serverclientstuff.User;

public class MainController {

    private Client client;

    private User user;

    public void setClient(Client client) {
        this.client = client;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public void initialize(Client client, User user){
       setUser(user);
       setClient(client);

       System.out.println("Current User: " + user.getUsername());
       System.out.println("Current Client: " + client);

    }


    @FXML
    protected void onHelloButtonClick() {
        
    }
}