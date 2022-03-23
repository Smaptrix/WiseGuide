package GUI;

import client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import serverclientstuff.User;

public class VenueOwnerMainPageController {

    Client client;
    User currUser;

    @FXML
    Label titleLabel;

    public void setClient(Client client) {
        this.client = client;
    }

    public void setCurrUser(User currUser) {
        this.currUser = currUser;
        titleLabel.setText("VENUE ADMIN PAGE: " + currUser.getUsername());
    }
}
