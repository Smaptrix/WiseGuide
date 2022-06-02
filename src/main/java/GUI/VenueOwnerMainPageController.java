/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham
    Date Created:   09/03/2022
    Last Updated:   12/05/2022
 */
package GUI;

import XMLTools.VenuePage;
import XMLTools.VenueXMLParser;
import client.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ServerClientUtility.User;
import ServerClientUtility.Utils;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;


/**
 * <p>
 *     This class controllers the venue owner main page
 * </p>
 */
public class VenueOwnerMainPageController {
    /**
     * <p>
     *     This is the client that the GUI is using to communicate with the server
     * </p>
     */
    private Client client;
    /**
     * <p>
     *     This is the current venue user that is logged in
     * </p>
     */
    private User currUser;
    /**
     * <p>
     *     This is the start of the filepath for the current device
     * </p>
     */
    private String filePathStart;
    /**
     * <p>
     *     This is the parser that reads the xml
     * </p>
     */
    private VenueXMLParser xml;

    /**
     *<p>
     *      This is the label that displays the title of the current page
     *</p>
     */
    @FXML
    Label titleLabel;
    /**
     * <p>
     *     This is the list that displays the currents files that are stored on the server of the venue
     * </p>
     */
    @FXML
    ListView fileList;

    /**
     * <p>
     *     This button when pressed opens the currently selected file
     * </p>
     */
    @FXML
    Button openFileButton;
    /**
     * <p>
     *     This button when pressed logs the venue user out
     * </p>
     */
    @FXML
    Button logOutButton;

    /**
     * <p>
     *     LEGACY -When pressed this button allows the user to add a file to the server - LEGACY
     * </p>
     */
    @FXML
    Button addFileButton;


    /**
     * <p>
     *     This function runs whenever this controller is opened
     * </p>
     */
    @FXML
    public void initialize(){
        //REMINDER - MADE THIS BUTTON INVISIBLE BECAUSE IT IS AN UNFINISHED FEATURE
        addFileButton.setVisible(false);
    }

    /**
     * <p>
     *     This sets the client to be used by the GUI
     * </p>
     * @param client the client we want the GUI to use
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * <p>
     *     This sets the current user to the desired venue user
     * </p>
     * @param currUser The venue user that is logged in
     */
    public void setCurrUser(User currUser) {
        this.currUser = currUser;
        titleLabel.setText("VENUE ADMIN PAGE: " + currUser.getUsername());
        //Replaces space with _ so that file paths remain correct
        filePathStart = "Venues/Clubs/" + (currUser.getUsername()).replace(" ", "_") + "/";
    }


    /**
     *<p>
     *      This populates the list on the main page with the files that are stored on the server
     *      This allows the currently logged in venue user to see the files
     *</p>
     */
    public void populateFileList(){

        //Download the xml file - we don't care if the user has access to it
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

            String fileName;

            //TODO - ASK BEN ABOUT GETTING THE INCLUDE SOURCE FOR A TEXT FILE
            if(currVenuePage.children.get(i).include_source != null){
                fileName = currVenuePage.children.get(i).include_source;

                int filePathLength = filePathStart.length();

                fileName = fileName.substring(filePathLength);

                fileList.getItems().add(fileName);
            }

        }

    }



    /**
     * <p>
     *     This action occurs when the open file button is pressed
     *     This opens the currently selected file in the list on the device
     * </p>
     */
    @FXML
    private void onOpenFileButtonPress(){

        System.out.println("Open File Button Pressed");

        //Make sure that something is selected to it doesn't request a file that doesn't exist
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

    /**
     * <p>
     *     This action occurs when the delete file button is pressed
     *     Deletes the selected files on the server and also removes the reference from the xml file
     * </p>
     */
    @FXML
    private void onDeleteFileButtonPress(){

        System.out.println("Delete File Button Pressed");

        if (fileList.getSelectionModel().getSelectedItem() != null) {


            //Request Server delete a file
            try {

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

    /**
     * <p>
     *     UNUSED CODE
     *     Lets the venue user upload files to the server
     * </p>
     */
    @FXML
    //Adds a new file to the server's directory, and into the XML file
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
        stage.setResizable(false);
        File selectedFile = fileChooser.showOpenDialog(stage);

        System.out.println("INFO: The method that's supposed to be called here is commented out.");
        /*try {

            client.requestUploadFile(selectedFile);
        } catch (IOException e) {
            //TODO - Add error message?
            e.printStackTrace();
        }*/

    }

    /**
     * <p>
     *     The action that occurs when the logout button is pressed
     *      Logs the user out of the application
     * </p>
     * @throws IOException
     */
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
        stage.setResizable(false);


        Stage currStage = (Stage) logOutButton.getScene().getWindow();
        currStage.close();

    }



}
