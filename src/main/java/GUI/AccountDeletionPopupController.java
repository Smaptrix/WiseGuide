package GUI;

import client.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class AccountDeletionPopupController {

    private Client client;
    public void setClient(Client client){ this.client = client; };
    private boolean testingMode = false;
    public void setTestingMode(boolean mode){ this.testingMode = mode; };
    public boolean buttonPressed = false; //For testing purposes
    private boolean nullClientDetected = false;


    @FXML
    Label infoLabel1;

    @FXML
    Label infoLabel0;

    @FXML
    Label infoLabel2;

    @FXML
    Button closePopUpButton;

    public void closePopUpButton() throws IOException {

        buttonPressed = true;
        Stage stage = (Stage) closePopUpButton.getScene().getWindow();

        if(testingMode){
            setErrorMessage(true);
        } else if(!nullClientDetected){
            if(client != null){
                //Close popup window
                stage.close();
                reopenLoginPage();
            } else {
                nullClientDetected = true;
                infoLabel0.setText("An error occurred.");
                infoLabel1.setText("The connection to the server was lost.");
                infoLabel2.setText("WiseGuide will be closed.");
                //App will close when user next presses button.
            }
        } else {
            stage.close();
        }
    }

    public void setErrorMessage(boolean error){
        if(error){
            infoLabel0.setText("An error occurred.");
            infoLabel1.setText("The account could not be deleted.");
            infoLabel2.setText("DELETEFAILURE");
        }
    }

    public void reopenLoginPage() throws IOException {
        System.out.println("Attempting to log out the client.");
        client.requestLogout();
        System.out.println("Opening the login page...");

        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("login-page.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 640, 400);
        System.out.println("Loaded login page again");
        LoginController controller = fxmlLoader.getController();
        controller.setClient(client);
        stage.setScene(scene);
        stage.setTitle("Welcome to WiseGuide");
        stage.show();
        stage.setResizable(false);

    }

}
