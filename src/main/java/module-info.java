/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Will Pitchfork
    Date Created:   04/02/2022
    Last Updated:   07/02/2022
 */

module maptrix.wiseguide {
    requires junit;
    requires java.desktop;
    requires javafx.fxml;
    requires javafx.controls;
    requires org.testfx.junit;
    requires java.logging;
    requires org.testfx;
    requires javafx.web;


    opens GUI to javafx.fxml;
    exports GUI;
    exports serverclientstuff;
    exports TestFX;
}