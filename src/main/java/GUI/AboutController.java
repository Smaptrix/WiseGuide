package GUI;

import client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AboutController {



    @FXML
    Label verNumLabel;



    //SEts the current version number
    public void setVerNum(String verString){
        verNumLabel.setText(verString);
    }





}
