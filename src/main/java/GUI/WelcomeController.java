/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Will Pitchfork
    Date Created:   04/02/2022
    Last Updated:   04/02/2022
 */

package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

public class WelcomeController {
    public AnchorPane mainPane;
    public MenuBar menuBar;
    public SplitPane horizontalSplitPane;
    public AnchorPane leftPane;
    public SplitPane leftSplitPane;
    public TitledPane venuesPane;
    public ScrollPane venueScrollPane;
    public TitledPane routesPane;
    public ScrollPane routesScrollPane;
    public AnchorPane mapPane;


    @FXML
    protected void onHelloButtonClick() {
        
    }
}