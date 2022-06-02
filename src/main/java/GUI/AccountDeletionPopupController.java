package GUI;

import client.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * <p>
 *     Controls the account deletion popup.
 * </p>
 */
public class AccountDeletionPopupController {

    /**
     * <p>
     *     The current client.
     * </p>
     */
    private Client client;

    /**
     * <p>
     *     Sets the current client.
     * </p>
     * @param client The current client.
     */
    public void setClient(Client client){ this.client = client; }

    /**
     * <p>
     *     A boolean set to true if the deletion system is being accessed by automatic tests and may require some features to be bypassed.
     * </p>
     */
    private boolean testingMode = false;

    /**
     * <p>
     *     Activates or deactivates the controller's testing mode.
     * </p>
     * @param mode Whether testing mode should be true or false.
     */
    public void setTestingMode(boolean mode){ this.testingMode = mode; }

    /**
     * <p>
     *     A boolean set to true if the button has been pressed (for testing purposes).
     * </p>
     */
    public boolean buttonPressed = false; //For testing purposes

    /**
     * <p>
     *     A boolean set to true if the current client is null.
     * </p>
     */
    private boolean nullClientDetected = false;


    /**
     * <p>
     *     The first line of the information text.
     * </p>
     */
    @FXML
    Label infoLabel0;

    /**
     * <p>
     *     The second line of the information text.
     * </p>
     */
    @FXML
    Label infoLabel1;

    /**
     * <p>
     *     The third line of the information text.
     * </p>
     */
    @FXML
    Label infoLabel2;

    /**
     * <p>
     *     The "okay" button.
     * </p>
     */
    @FXML
    Button closePopUpButton;

    /**
     * <p>
     *     Closes the popup and reopens the login screen.
     * </p>
     * @throws IOException if the client and server lose connection.
     */
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

    /**
     * <p>
     *     Changes the text to an error message if appropriate.
     * </p>
     * @param error Whether an error has occurred (true) or not.
     */
    public void setErrorMessage(boolean error){
        if(error){
            infoLabel0.setText("An error occurred.");
            infoLabel1.setText("The account could not be deleted.");
            infoLabel2.setText("DELETEFAILURE");
        }
    }

    /**
     * <p>
     *     Reopens the login window.
     * </p>
     * @throws IOException if the client and server lose connection.
     */
    public void reopenLoginPage() throws IOException {

        client.requestLogout();

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
