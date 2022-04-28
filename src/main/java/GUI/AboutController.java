/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:         Joe Ingham
    Date Created:   01/03/2022
    Last Updated:   07/03/2022
 */
package GUI;


import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Controls the about page of the GUI
 */
public class AboutController {


    /**
     * The label that displays the current version number
     */
    @FXML
    Label verNumLabel;


    /**
     * Sets the current version number
     * @param verString the current version number
     */
    public void setVerNum(String verString){
        verNumLabel.setText(verString);
    }
}
