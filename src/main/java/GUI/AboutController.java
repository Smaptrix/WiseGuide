/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:         Joe Ingham
    Date Created:   01/03/2022
    Last Updated:   02/06/2022
 */
package GUI;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
/**
 * <p>
 *     Controls the about page of the GUI
 * </p>
 */
public class AboutController {


    /**
     * <p>
     *     The label that displays the current version number of the client and the server
     * </p>
     */
    @FXML
    Label verNumLabel;

    /**
     * <p>
     *     Sets the current version number of the client and the server
     * </p>
     * @param verString the current version number
     */
    public void setVerNum(String verString){
        verNumLabel.setText(verString);
    }
}
