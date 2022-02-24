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




    }

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
            case "LOGIN":
                receiveLogin(1);
                break;


            case "VERIFYUSER":
                receiveLogin(0);
                break;

            default:
                System.out.println(requestIn + " : Invalid command");
                sendResponse("Error 404: Request Code Not Found", false);
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
    //Sends a response to the client - Used by ECHO requests
    private void sendResponse(String response, Boolean sendSize) throws IOException {

        //Turns the string into its byte array
        byte[] responseInBytes = response.getBytes(StandardCharsets.UTF_8);

        if (sendSize){
            //Sends the size of the response first
            int sizeOfResponse = responseInBytes.length;
            outputStream.write(sizeOfResponse);
        }



        outputStream.write(responseInBytes);

        outputStream.flush();


    }


    /*TODO - Refactor login - verify the users info first
        Might be fine as is - If client recieves bad login then figure out what is wrong?
     */

    //Mode decides whether it verifies user data or logs in
    public void receiveLogin(Integer mode) throws IOException, NoSuchAlgorithmException {


        String loginName = inText.readLine();

        String loginPass = inText.readLine();

        currUserHandler = new ServerUserHandler(new User(loginName, loginPass));

        //Verification Mode - Mainly for user creation
        if(mode == 0){

            //If user exists and pass is correct(Good for login, bad for user creation)
            if(currUserHandler.userExistState & currUserHandler.passVerified){
                sendResponse("GOODPASS", true);

            }
            //If password is correct
            else if (currUserHandler.userExistState & !currUserHandler.passVerified){
                sendResponse("BADPASS", true);
            }

            else {
                sendResponse("USERNOTFOUND", true);
            }

        }


        //Login Mode
        else if(mode == 1) {

            //Verifies the user data
            if(!(currUserHandler.userExistState && currUserHandler.passVerified)){
                //If the users data is incorrect - let the client know
                currUserHandler = null;
                sendResponse("BADLOGIN", true);
            }
            else{
                //If the users data is verified - sets the server user to the user provided
                currUser = new User(loginName, loginPass);
                sendResponse("GOODLOGIN", true);
            }

        }
        else{
            System.out.println("Unrecognised login mode!");
        }
    }







}