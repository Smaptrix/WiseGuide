/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham
    Date Created:   20/01/2022
    Last Updated:   24/02/2022
 */

package server;


import serverclientstuff.User;

import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;

public class Server {

    //Should only be changed in the code
    private static final String SERVERVERSION = "Ver 0.45";






    private ServerSocket serverSocket;
    private Socket clientSocket;
    private DataOutputStream outputStream;
    private BufferedReader inText;
    private String CurrDir;
    private String slashType;
    private ServerUserHandler currUserHandler;
    private User currUser;

    //Starts the server
    public void startup(int port) throws IOException {

        System.out.println("Creating new Server Socket at " + port);

        //Server formed
        serverSocket = new ServerSocket(port);

        System.out.println("Port Created\n");

        clientSocket = serverSocket.accept();

        System.out.println("After accept\n");

         //Reads text from the buffer
        inText = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        //Writes pure file bytes to output socket
        outputStream = new DataOutputStream(clientSocket.getOutputStream());

        osDetect();

        //Initialises the current user server user handler
        currUser = new User("", "");
        currUserHandler = new ServerUserHandler(currUser, false);
    }

    //Possibly not needed now that windows accepts unix slashes...
    private void osDetect(){
        //Stores the current directory that the application was launched from
        CurrDir = System.getProperty("user.dir");
        String operatingSys = System.getProperty("os.name");

        //Determines the slash type (back or forward) for file systems on unix/non-unix systems.
        if (operatingSys.startsWith("Windows")){
            slashType = "\\";
            System.out.println("Expecting Windows machine, actual machine: " + operatingSys);
        }
        else{
            slashType = "/";
        }
    }


    //Closes the server down
    public void stopConnections() throws IOException {
        System.out.println("Closing Down");
        inText.close();
        clientSocket.close();
        serverSocket.close();
        outputStream.close();
    }


    //Contentiously listens to the input buffer
    //Used to wait for requests from the client
    public void bufferListen() throws IOException {

        String inputLine;

        try{

            while ((inputLine = inText.readLine()) != null) {
                System.out.println("Listening...");
                if ("Close Connection".equals(inputLine)) {
                    sendResponse("Connection Closed", true);
                    stopConnections();
                    break;
                } else {
                    System.out.println("Request Received: " + inputLine);

                    requestParser(inputLine);
            }
        }

        }catch (SocketException e){
            System.out.println("Lost connnection to client");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }






    //Requests in form "Request Code Type" + " " + "Request Information"
    public void requestParser(String requestIn) throws IOException, NoSuchAlgorithmException {
        String[] requestSplit = requestIn.split(" ");
        switch(requestSplit[0]) {
            case "GET":

                //Should send file stored at the location of the current directory with the filename provided
                sendFile(Path.of((CurrDir + slashType + requestSplit[1])));

                break;
            case "ECHO":
                //Echos the request back (mainly for testing)
                sendResponse(requestSplit[1], true);
                System.out.println("Response sent: " + requestSplit[1]);
                break;

            //Creates a new user and adds it to the database



            case "VERIFYUSER":
                receiveLogin(0);
                break;

            case "LOGIN":
                receiveLogin(1);
                break;

            case "CREATEUSER":
                receiveLogin(2);
                break;


            case "CHANGENAME":
                changeUsername();
                break;

            case "LOGOUT":
                logout();
                break;

            case "VERSIONCHECK":
                versionCheck();
                break;


            default:
                System.out.println(requestIn + " : Invalid command");
                sendResponse("Error 404: Request Code '" + requestIn + "' Not Found", false);
                break;
        }
    }




    //Sends a file across the socket (after it has been broken down into its bytes)
    private void sendFile(Path filepath) throws IOException {


        try {
            System.out.println("File stored at: " + filepath);


            //Sends a data packet telling the client to expect a file of a certain size
            long fileSize = Files.size(filepath);

            System.out.println("File Size: " + fileSize);

            byte[] fileSizeInBytes = ByteBuffer.allocate(4).putInt((int) fileSize).array();

            int fileSizeInBytesLen = fileSizeInBytes.length;

            //Tells the client how many bytes are determining the size of the file
            outputStream.write(fileSizeInBytesLen);

            //Writes the fileSize in bytes to the client
            for (byte fileSizeInByte : fileSizeInBytes) {
                outputStream.write(fileSizeInByte);
            }

            //Tells the client what type of file to expect
            String fileType = filepath.toString();
            String[] fileTypeSplit = fileType.split("\\.");
            sendResponse(fileTypeSplit[1], false);
            outputStream.flush();


            //Construct a byte array from the file we want to send and send that across network
            FileInputStream fileStream = new FileInputStream(String.valueOf(filepath));
            byte[] buffer = fileStream.readAllBytes();
            fileStream.close();

            boolean end = false;
            int bytesSent = 0;

            while(!end){
                outputStream.write(buffer[bytesSent]);

                bytesSent += 1;

                if(bytesSent == fileSize){
                    System.out.println("We have written: " + bytesSent + " bytes");

                    end = true;
                }
            }
            //Clears the outputStream of any excess data

            outputStream.flush();


        }catch(NoSuchFileException e){
            System.out.println("File not found");
        }
    }


    //No need to tell the client to expect a string it should already be expecting it
    //Sends a response to the client

    private void sendResponse(String response, Boolean sendSize) throws IOException {


        //outputStream.flush();
        //Turns the string into its byte array
        byte[] responseInBytes = response.getBytes(StandardCharsets.UTF_8);

        if (sendSize){
            //Sends the size of the response first
            int sizeOfResponse = responseInBytes.length;

            System.out.println("File Size in bytes: " + sizeOfResponse);

            outputStream.writeByte(sizeOfResponse);

        }



        outputStream.write(responseInBytes);



        //outputStream.flush();


    }




    //Mode decides whether it verifies user data or logs in
    public void receiveLogin(Integer mode) throws IOException {


        String loginName = inText.readLine();

        String loginPass = inText.readLine();




        //Hashing server side as we can access the users salt
        currUser.setUsername(loginName);
        currUser.setPassword(loginPass);

        currUserHandler.setCurrUser(currUser);


        //Determine current users statuses - will always fail at the password level
        currUserHandler.verifyUser();


        System.out.println("Users information has been checked!");
        System.out.println("Mode: " + mode);


        //Verification Mode - Mainly for testing
        if(mode == 0){

            //If user exists and pass is correct(Good for login, bad for user creation)
            if(currUserHandler.userExistState & currUserHandler.passVerified) {
                sendResponse("USERFOUND", true);
            }


            else {
                sendResponse("USERNOTFOUND", true);

            }

        }



        //Login Mode
        if(mode == 1) {

            System.out.println("Login mode!");

            //If the user exists grab there salt then encrypt there data
            if(currUserHandler.userExistState){
                currUser.setSalt(currUserHandler.getcurrUserSalt());
                currUser.encryptUserInfo();
                currUserHandler.verifyUser();
            }

            //Verifies the user data
            if(!(currUserHandler.userExistState && currUserHandler.passVerified)){
                //If the users data is incorrect - let the client know

                System.out.println("Not logged in!");
                sendResponse("BADLOGIN", true);
            }
            else{
                //If the users data is verified - sets the server user to the user provided
                currUser = new User(loginName, loginPass);
                System.out.println("Logged in!");
                sendResponse("GOODLOGIN", true);
                System.out.println("Login message sent!");
            }

        }

        //User Creation Mode
        else if(mode == 2){

            if(!(currUserHandler.userExistState)){

                sendResponse("SENDSALT", true);
                currUser.setSalt(inText.readLine());
                currUser.encryptUserInfo();
                currUserHandler.createUser();
                sendResponse("USERCREATED", true);
            }

            else{
                sendResponse("USERALREADYEXISTS", true);
                System.out.println("User already exists");

            }



        }
        else{
            System.out.println("Unrecognised login mode!");
        }


    }


    //Logs the user out of the server
    private void logout() {

        //deletes the current information regarding the user
        //Guarantees that the server wont accidently stick on
        //Have to be careful because this makes things null
        currUser.clear();
        currUserHandler.clear();


        try {
            sendResponse("LOGGEDOUT", true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Successfully logged out");


    }

    //Checks that the client and server versions are the same
    private void versionCheck() throws IOException {

        String clientVersion = inText.readLine();

        if(clientVersion.equals(SERVERVERSION)){
            sendResponse("SAMEVER", true);
        }
        else{
            sendResponse("DIFFVER", true);
        }



    }


    private void changeUsername() throws IOException {



        String desiredUsername = inText.readLine();


        //If the username is taken
        if (ServerUserHandler.findUserName(desiredUsername)){





            sendResponse("USERNAMETAKEN", true);
        }

        else {
            currUserHandler.changeUserName(desiredUsername);
            sendResponse("NAMECHANGED", true);
        }


    }


}