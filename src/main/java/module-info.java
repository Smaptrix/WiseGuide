module maptrix.wiseguide {
    requires junit;
    requires java.desktop;
    requires javafx.fxml;
    requires javafx.controls;


    opens maptrix.wiseguide to javafx.fxml;
    exports maptrix.wiseguide;
    exports serverclientstuff;
}