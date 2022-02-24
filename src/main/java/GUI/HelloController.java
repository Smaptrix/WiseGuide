package GUI;

import client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    //public Button closeButton;
    Client client; // Declare empty client

    @FXML
    private Label welcomeText;

    @FXML
    private Button openButton;

    @FXML
    protected void openButtonAction() throws IOException {
        welcomeText.setText("Welcome to WiseGuide!"); // Gui opening text
        client = new Client(); // Creates new instance of client object
        client.startConnection("127.0.0.1", 5555); // Pressing "Hello" button starts the client

    }

    @FXML
    private Button closeButton;

    @FXML
    protected void closeButtonAction() throws IOException {
        welcomeText.setText("Client closed."); // Gui closing text
        client.closeConnection(); // Pressing "Exit" button closes the client
        Stage stage = (Stage) closeButton.getScene().getWindow(); // Get a handle to the stage
        stage.close(); // Closes the stage/gui
    }
}