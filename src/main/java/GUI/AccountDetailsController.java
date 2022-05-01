package GUI;

import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import serverclientstuff.User;

import java.io.IOException;

public class AccountDetailsController {

    //TODO - Add password change functionality


    Client client;

    User currUser;

    private Stage mapStage;

    @FXML
    Label usernameLabel;

    @FXML
    Button changeNameButton;

    @FXML
    Button changePassButton;

    @FXML
    Button deleteAccountButton;



    @FXML
    public void initialize(){

    }

    public void setClient(Client client) {
        this.client = client;

    }


    public void setUser(User currUser) {
        this.currUser = currUser;
        usernameLabel.setText(currUser.getUsername());
        System.out.println(currUser.getUsername());
    }


    @FXML
    protected void onchangeNameButtonPress() throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("change-username-page.fxml"));

        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 600, 375);
        ChangeUsernameController controller = fxmlLoader.getController();
        controller.setClient(client);
        controller.setUser(currUser);
        stage.setScene(scene);
        stage.setTitle("Change username");
        stage.show();


        //Close the current page
        Stage currStage = (Stage) changeNameButton.getScene().getWindow();
        currStage.close();





    }


    @FXML
    protected void onchangePassButtonPress() throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("change-password-page.fxml"));

        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 600, 375);
        ChangePasswordController controller = fxmlLoader.getController();
        controller.setClient(client);
        controller.setUser(currUser);
        stage.setScene(scene);
        stage.setTitle("Change Password");
        stage.show();


        //Close the current page
        Stage currStage = (Stage) changeNameButton.getScene().getWindow();
        currStage.close();





    }

    //TODO: Button on the fxml may not be centred
    @FXML
    public void onDeleteAccountButtonPress(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("account-delete-page.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 300, 350);
        AccountDeletionController controller = fxmlLoader.getController();
        controller.setClient(client);
        controller.setCurrUser(currUser);
        controller.setParentStage((Stage)deleteAccountButton.getScene().getWindow());
        controller.setMapStage(mapStage);
        //controller.setTestingMode(testingMode); //Uncomment if a testingMode if a testingMode is added to main screen
        stage.setScene(scene);
        stage.setTitle("Account Deletion");
        stage.show();
        stage.setResizable(false);
    }

    public void setMapStage(Stage mapStage) {
        this.mapStage = mapStage;
    }
}
