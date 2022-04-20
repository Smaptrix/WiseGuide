package GUI;

import VenueXMLThings.VenuePage;
import VenueXMLThings.VenueXMLParser;
import client.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import serverclientstuff.User;
import serverclientstuff.Utils;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;



//TODO - Do we want to display the files that are needed by the XML? OR that we have in our directory?

public class VenueOwnerMainPageController {

    private Client client;
    private User currUser;
    private String filePathStart;
    private VenueXMLParser xml;

    @FXML
    Label titleLabel;

    @FXML
    ListView fileList;

    @FXML
    Button openFileButton;

    @FXML
    Button logOutButton;

    @FXML
    Button addFileButton;


    @FXML
    public void initialize(){

        //REMINDER - MADE THIS BUTTON INVISIBLE BECAUSE IT IS AN UNFINISHED FEATURE
        addFileButton.setVisible(false);


    }


    public void setClient(Client client) {
        this.client = client;
    }

    public void setCurrUser(User currUser) {
        this.currUser = currUser;
        titleLabel.setText("VENUE ADMIN PAGE: " + currUser.getUsername());
        //Replaces spaces with _ so that file paths remain correct
        filePathStart = "Venues/Clubs/" + (currUser.getUsername()).replace(" ", "_") + "/";
    }


    //Populates the list on the venue owner page of the current files in the XML
    public void populateFileList(){

        //Download the xml file - we dont care if the user has access to it
        //This is because all the venues will be on the main application publicly
        //No sensitive data is stored in the venue xml file
        try {
            client.requestVenueXMLFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        xml = new VenueXMLParser(client.getFile("venuesLocation.xml"));


        VenuePage currVenuePage = xml.getPage("title", currUser.getUsername());

        int numOfFiles = currVenuePage.numberOfElements;

        for(int i = 0; i < numOfFiles; i++){
            String fileName = currVenuePage.children.get(i).children.get(0).attributes.get("include_source");

            int filePathLength = filePathStart.length();

            fileName = fileName.substring(filePathLength);

            fileList.getItems().add(fileName);
        }

    }



    @FXML
    //Downloads and opens the requested file by the venue owner
    private void onOpenFileButtonPress(){

        System.out.println("Open File Button Pressed");

        //Make sure that something is selected to it doesnt request a file that doesnt exist
       if(fileList.getSelectionModel().getSelectedItem() != null){
           //Requests the selected file from the server
           try {
               String filePath = filePathStart + fileList.getSelectionModel().getSelectedItem();
               client.requestFile(filePath);
               Utils.openFile(client.getFile(filePath));
           } catch (IOException e) {
               System.out.println("Unable to download file!");
           }
       }

    }


    @FXML
    //Deletes a file from the servers directory, and in the XML file --- WIP
    //WARNING - Will actually delete the file on the server and not change the xml for now
    private void onDeleteFileButtonPress(){

        System.out.println("Delete File Button Pressed");

        if (fileList.getSelectionModel().getSelectedItem() != null) {


            //Request Server delete a file
            try {
                //Could cause memory leak ish thingy here, not deleting a file but removing its reference from the XML
                client.requestDeleteFile(filePathStart + fileList.getSelectionModel().getSelectedItem());
                fileList.getItems().remove(fileList.getSelectionModel().getSelectedItem());
            } catch (IOException e) {
                e.printStackTrace();
            }


            //Remove the tag from the current XML file on the PC
            try {
                xml.removeChildMedia("title", currUser.getUsername(), filePathStart + fileList.getSelectionModel().getSelectedItem());
            } catch (TransformerException e) {
                e.printStackTrace();
            }


        }


    }


    @FXML
    //Adds a new file to the servers directory, and into the XML file
    //Uploading files to a server could be very dangerous
    //WIP!!! - BUTTON IS INVISIBLE
    private void onAddFileButtonPress(){

        System.out.println("Add File Button Pressed!");

        FileChooser fileChooser = new FileChooser();

        //Make sure that the user can only select certain types of files
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
                ,new FileChooser.ExtensionFilter("Image Files", "*.png")
        );

        //Opens the file explorer so the venue user can select a file
        Stage stage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(stage);

        try {
            client.requestUploadFile(selectedFile);
        } catch (IOException e) {
            //TODO - Add error message?
            e.printStackTrace();
        }

    }

    @FXML
    private void onLogOutButtonPress() throws IOException {
        System.out.println("Log out button pressed");

        //Reopens the login page
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("venue-login-page.fxml"));

        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 640, 400);

        System.out.println("Loaded venue login page again");

        VenueLoginController controller = fxmlLoader.getController();
        controller.setClient(client);
        stage.setScene(scene);
        stage.setTitle("Venue Login");
        stage.show();


        Stage currStage = (Stage) logOutButton.getScene().getWindow();
        currStage.close();

    }


    //TODO - ADD NEW FILES (REMEMBER WE HAVE TO CHANGE THE FILE ON THE SERVER AS WELL)


}
