package GUI;

import client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import serverclientstuff.User;

public class AccountDetailsController {

    //TODO - Add all the functionality
    //     - I.E Display User Details - Change User details
    //     - BEFORE THIS NEED TO DO USER INFO ENCRYPTION RATHER THAN HASHING

    Client client;

    User currUser;

    @FXML
    Label usernameField;



    @FXML
    public void initialize(){




    }

    public void setClient(Client client) {
        this.client = client;
    }





    public void setUser(User currUser) {
        this.currUser = currUser;
        usernameField.setText(currUser.getUsername());
    }


}
