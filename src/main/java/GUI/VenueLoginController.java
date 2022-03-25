package GUI;

import client.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import serverclientstuff.User;

import java.io.IOException;

public class VenueLoginController {

    Client client;

    User currUser;

    @FXML
    TextField venueNameField;

    @FXML
    PasswordField venuePassField;

    @FXML
    Button loginButton;

    @FXML
    Button backButton;

    @FXML
    Label errLabel;


    public void setClient(Client client){
        this.client = client;
    }




    @FXML
    private void onLoginButtonPress() throws IOException {

        //Make sure teh fields arent empty
        if(venueNameField.getText().isEmpty() || venuePassField.getText().isEmpty()){
            errLabel.setText("Please enter something in both fields!");
        }
        else{

            currUser = new User(venueNameField.getText(), venuePassField.getText());


            //Checks to see if the login data was correct
            if(client.requestVenueLogin(currUser.getUsername(), currUser.getPassword()).equals("BADLOGIN")){
                errLabel.setText("Unrecognised Venue Details");
            }
            else{
                //If the login data is correct
                errLabel.setText("");
                System.out.println("Good login!");

                //Opens the main venue owner page
                FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("venue-owner-main-page.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(fxmlLoader.load(), 450, 470);


                VenueOwnerMainPageController controller = fxmlLoader.getController();
                controller.setClient(client);
                controller.setCurrUser(currUser);
                controller.populateFileList();
                stage.setScene(scene);
                stage.setTitle(currUser.getUsername() + " Owner Page");
                stage.show();


                Stage currStage = (Stage) backButton.getScene().getWindow();
                currStage.close();
            }

        }



    }
    //TODO - IDEAS
    //     - FOR THE VENUE PRODUCE A LIST OF ALL FILES CURRENTLY STORED IN VENUES DIRECTORY
    //     - LET THE VENUE ADD OR DELETE MORE FILES?!?!



    @FXML
    private void onBackButtonPress() throws IOException {

        //Reopens the login page
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("login-page.fxml"));

        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 640, 400);

        System.out.println("Loaded login page again");

        LoginController controller = fxmlLoader.getController();
        controller.setClient(client);
        stage.setScene(scene);
        stage.setTitle("Welcome to WiseGuide");
        stage.show();


        Stage currStage = (Stage) backButton.getScene().getWindow();
        currStage.close();

    }


}
