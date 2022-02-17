package maptrix.wiseguide;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() throws IOException {
        welcomeText.setText("Welcome to JavaFX Application!");
        client.ClientMain.main(); // Pressing "Hello" button starts the client
    }

    @FXML
    private Label goodbyeText;

    @FXML
    protected void onExitButtonClick() throws IOException {
        welcomeText.setText("Client closed.");
        client.ClientMain.closeClient(); // Pressing "Exit" button closes the client
    }
}