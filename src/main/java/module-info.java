/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Will Pitchfork
    Date Created:   04/02/2022
    Last Updated:   03/03/2022
 */

module maptrix.wiseguide {
    requires junit;
    requires java.desktop;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.media;
    requires org.testfx;
    requires org.testfx.junit;


    opens GUI to javafx.fxml;
    exports GUI;
    exports serverclientstuff;
    exports tests;
}