/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham
    Date Created:   20/01/2022
    Last Updated:   24/02/2022
 */

package server;


import VenueXMLThings.VenueXMLParser;
import serverclientstuff.User;
import serverclientstuff.UserSecurity;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.transform.TransformerException;
import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.security.*;
import java.util.Arrays;

public class Server {


    //TODO - Could maybe compartmentalise some of these security functions to trim down this file


    //TODO - ONLY ONE WAY IS ACTUALLY ENCRYPTED - SERVER TO CLIENT IS NOT LOL?!


    //Should only be changed in the code
    private static final String SERVERVERSION = "Ver 0.60";


    private ServerSocket serverSocket;
    private Socket clientSocket;
    private DataOutputStream outputStream;
    private InputStream inStream;
    private String CurrDir;
    private String slashType;
    private ServerUserHandler currUserHandler;
    private User currUser;
    private FaveVenuesHandler faveVenuesHandler;


    private PrivateKey privateKey;

    private File serverPublicKey;

    private SecretKey symKey;

    private Cipher symmetricCipher;

    private boolean encryptionReady;


    //Generates a random public/private keypair
    private KeyPair generateKeyPair() throws NoSuchAlgorithmException {

        SecureRandom random = new SecureRandom();

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

        //Initialises the key pair generator with a key size of 2048
        keyPairGenerator.initialize(2048, random);


        //Generates the key pair and then returns it
        return keyPairGenerator.generateKeyPair();

    }


    //Generates the keypair for the start of the encryption of the socket connection
    private void startupEncryption() throws NoSuchAlgorithmException, IOException {

        encryptionReady = false;

        //Generate initial key pair
        KeyPair initKeyPair = generateKeyPair();

        //Seperates the keys out
        PublicKey publicKey = initKeyPair.getPublic();
        privateKey =  initKeyPair.getPrivate();

        //System.out.println("Public: " + publicKey);
        //System.out.println("Private: " + privateKey);

        //Gets the current server key directory
        String keyDirectory = CurrDir + "\\serverkeys";

        //Writes the public key to a file
        serverPublicKey = new File(keyDirectory + "\\serverpubkey.pub");

        FileOutputStream publicOut = new FileOutputStream(serverPublicKey);

        publicOut.write(publicKey.getEncoded());

        serverPublicKey.deleteOnExit();

        publicOut.close();

        System.out.println("Public key file created");

        //Writes the private key to a file
        File serverPrivateKey = new File(keyDirectory + "\\serverprivkey.key");

        FileOutputStream privateOut = new FileOutputStream(serverPrivateKey);

        privateOut.write(privateKey.getEncoded());

        serverPrivateKey.deleteOnExit();

        privateOut.close();

        System.out.println("Private key file created");


    }

    //Communicates with the client to get the symmetric key
    private void getClientEncryption() throws IOException {

        //Sends the servers public key file to the client
        sendFile(Path.of(serverPublicKey.getPath()), false);

        DataInputStream in = new DataInputStream(clientSocket.getInputStream());

        //Recieve the length of the size data
        int numOfFileSizeBytes = in.read();

        //Recieve the size data

        byte[] bytesToReadBytes = new byte[numOfFileSizeBytes];

        for (int i = 0; i < numOfFileSizeBytes; i++) {
            bytesToReadBytes[i] = (byte) in.read();
        }

        int bytesToRead = ByteBuffer.wrap(bytesToReadBytes).getInt();

        //Recieve the actual data


        //Similar code to the clients read bytes but we only need it the once for the server - JI

        //Initialises a new byte array of size predetermined by our network protocol
        byte[] keyData = new byte[bytesToRead];

        boolean end = false;
        int bytesRead = 0;


        //Reads bytes up until the count has been reached
        while (!end) {

            try {
                keyData[bytesRead] = (byte) in.read();
                //System.out.println(data[bytesRead]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Increment Byte count
            bytesRead += 1;
            if (bytesRead == bytesToRead) {
                // System.out.println("We have read: " + bytesRead);
                end = true;
            }

        }


        System.out.println("Key read");

        //Sets the input socket back to the text reader
        inStream = clientSocket.getInputStream();

        //Decrypt the key
        try {
            decryptSymmetricKey(keyData);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }


    }


    //Decrypts the symmetric key from the client then "remembers" it
    private void decryptSymmetricKey(byte[] encryptedKeyData) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException {

        //Decrypt key data
        Cipher decryptionCipher = Cipher.getInstance("RSA");
        try {
            decryptionCipher.init(Cipher.DECRYPT_MODE, privateKey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        byte[] decryptedSymmetricKey = decryptionCipher.doFinal(encryptedKeyData);


        //Rebuild the key using the encoded key bytes
        symKey = new SecretKeySpec(decryptedSymmetricKey, 0, decryptedSymmetricKey.length, "AES");

        System.out.println(symKey);


        //Creates the symmetric Cipher with which to decrypt messages from the client
        symmetricCipher = Cipher.getInstance("AES");

        //symmetricCipher.init(Cipher.DECRYPT_MODE, symKey);


        encryptionReady = true;

    }




    //Starts the server
    public void startup(int port) throws IOException{

        osDetect();
        System.out.println("Creating new Server Socket at " + port);

        //Server formed
        serverSocket = new ServerSocket(port);

        System.out.println("Port Created\n");



        //Generate the first parts of the encryption
        try {
            startupEncryption();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Encryption failed, aborting server launch");
            System.exit(-1);
        }


        clientSocket = serverSocket.accept();

        System.out.println("After accept\n");

        //Reverts back to original socket type
        inStream = clientSocket.getInputStream();

        //Writes pure file bytes to output socket
        outputStream = new DataOutputStream(clientSocket.getOutputStream());

        //Initialises the current user server user handler
        currUser = new User("", "");
        currUserHandler = new ServerUserHandler(currUser, false);
        faveVenuesHandler = new FaveVenuesHandler(new File("faveVenues.txt"));

        //TEST FEATURES
        //faveVenuesHandler.addUser("tester");
        //faveVenuesHandler.removeUser("deleteme");
        //faveVenuesHandler.addFaveVenue("joe", "ADDEDVENUE");
        //faveVenuesHandler.removeFaveVenue("joe", "DELETEME");
        faveVenuesHandler.faveVenueList("joe");

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
        inStream.close();
        clientSocket.close();
        serverSocket.close();
        outputStream.close();
        System.exit(1);
    }


    //Contentiously listens to the input buffer
    //Used to wait for requests from the client
    public void bufferListen() throws IOException {

        int bytesToRead;

        try{

            while (true) {
                System.out.println("Waiting for byte size");
                if ((bytesToRead = inStream.read()) != 0) {


                    System.out.println("Listening...");

                    byte[] inputLine =  recieveMessage(bytesToRead);


                    System.out.println("Encryption done: " + encryptionReady);
                    if (encryptionReady) {
                        symmetricCipher.init(Cipher.DECRYPT_MODE, symKey);



                        System.out.println("Input size: " + inputLine.length);

                        byte[] decryptedInpLineBytes = symmetricCipher.doFinal(inputLine);

                        System.out.println("Decrypted input line in bytes: " + Arrays.toString(decryptedInpLineBytes));

                        String decryptedInputLine = new String(decryptedInpLineBytes, StandardCharsets.UTF_8);


                        System.out.println("Request Received: " + decryptedInputLine);

                        requestParser(decryptedInputLine);
                        //Preencryption request parser
                    } else {
                        requestParser(new String(inputLine));
                    }
                }
            }
        } catch (IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | InvalidKeyException ex) {
            ex.printStackTrace();
        }

        catch (SocketException e){
            System.out.println("Lost connnection to client");
            e.printStackTrace();
        }
    }






    //Requests in form "Request Code Type" + " " + "Request Information"
    public void requestParser(String requestIn) throws IOException, NoSuchAlgorithmException {

        //TODO - CLOSE CONNECTION REQUEST


        String[] requestSplit = requestIn.split(" ");
        switch(requestSplit[0]) {
            case "GET":

                //Should send file stored at the location of the current directory with the filename provided
                sendFile(Path.of((CurrDir + slashType + requestSplit[1])), true);

                break;
            case "ECHO":
                //Echos the request back (mainly for testing)
                sendResponse(requestSplit[1], true, true);
                System.out.println("Response sent: " + requestSplit[1]);
                break;

            //Creates a new user and adds it to the database
            case "VERIFYUSER":
                currUserHandler.setUserType("USER");
                receiveLogin(0);
                break;

            case "LOGIN":
                currUserHandler.setUserType("USER");
                receiveLogin(1);
                break;

            case "CREATEUSER":
                currUserHandler.setUserType("USER");
                receiveLogin(2);
                break;


            case "CHANGENAME":
                currUserHandler.setUserType("USER");
                changeUsername();
                break;

            case "CHANGEPASS":
                currUserHandler.setUserType("USER");
                changePassword();
                break;

            case "LOGOUT":
                currUserHandler.setUserType("USER");
                logout();
                break;

            case "VERSIONCHECK":
                versionCheck();
                break;

            case "VENUELOGIN":
                currUserHandler.setUserType("VENUE");
                receiveLogin(1);
                break;

            case "DELETEVENUEFILE":
                currUserHandler.setUserType("VENUE");
                deleteVenueFile();
                break;

            case "SENDPUBLIC":
                getClientEncryption();
                break;


            case "FAVEVENUE":
                favouriteNewVenue();
                break;



            case "UNFAVEVENUE":
                    unfavouriteVenue();
                    break;

            case "FAVELIST":
                    sendFaveVenueList();
                    break;



            default:
                System.out.println(requestIn + " : Invalid command");
                sendResponse("Error 404: Request Code '" + requestIn + "' Not Found", false, true);
                break;
        }
    }

    //Sends the current users list of favourite venues
    private void sendFaveVenueList() throws IOException {

        String faveVenueListString = faveVenuesHandler.faveVenueList(currUser.getUsername());

        //Tells the client if there are no favourite venues
        if(faveVenueListString == null){
            sendResponse("EMPTY", true, true);
        }
        else{
            sendResponse(faveVenueListString, true, true);
        }

    }

    private void favouriteNewVenue() throws IOException {

        String venueToFavourite = recieveMessageAsString(inStream.read());

        faveVenuesHandler.addFaveVenue(currUser.getUsername(), venueToFavourite);

        sendResponse("ADDED", true, true);
    }


    private void unfavouriteVenue() throws IOException {
        String venueToUnFavourite = recieveMessageAsString(inStream.read());

        faveVenuesHandler.removeFaveVenue(currUser.getUsername(), venueToUnFavourite);

        sendResponse("REMOVED", true, true);
    }


    //Sends a file across the socket (after it has been broken down into its bytes)
    private void sendFile(Path filepath, Boolean encrypt) throws IOException {

        if(encrypt){
            try {
                symmetricCipher.init(Cipher.ENCRYPT_MODE, symKey);
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }
        }


        try {
            System.out.println("File stored at: " + filepath);


            //Sends a data packet telling the client to expect a file of a certain size
            long fileSize = Files.size(filepath);

            System.out.println("File Size: " + fileSize);

            byte[] fileSizeInBytes = ByteBuffer.allocate(4).putInt((int) fileSize).array();



            int fileSizeInBytesLen = fileSizeInBytes.length;


            //Tells the client how many bytes are determining the size of the file
            outputStream.write(fileSizeInBytesLen);

            //System.out.println("Sent file size length");

            //Writes the fileSize in bytes to the client
            for (byte fileSizeInByte : fileSizeInBytes) {
                outputStream.write(fileSizeInByte);
            }

            // System.out.println("sent file size");

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

                //Testing purposes only
                //System.out.println(buffer[bytesSent]);

                if(bytesSent == fileSize){
                    System.out.println("We have written: " + bytesSent + " bytes");

                    end = true;
                }
            }
            //Clears the outputStream of any excess data
            outputStream.flush();

            System.out.println("All done!");

            if(encrypt) {
                symmetricCipher.init(Cipher.DECRYPT_MODE, symKey);
            }

        }catch(NoSuchFileException | InvalidKeyException e){
            System.out.println("File not found");
        }
    }


    //No need to tell the client to expect a string it should already be expecting it
    //Sends a response to the client

    private void sendResponse(String response, Boolean sendSize, Boolean encrypt) throws IOException {


        //outputStream.flush();
        //Turns the string into its byte array
        byte[] responseInBytes = response.getBytes(StandardCharsets.UTF_8);
        if (encrypt) {
            try {
                symmetricCipher.init(Cipher.ENCRYPT_MODE, symKey);
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }


            byte[] encryptedResponse = new byte[0];

            try {
                encryptedResponse = symmetricCipher.doFinal(responseInBytes);
            } catch (IllegalBlockSizeException | BadPaddingException e) {
                e.printStackTrace();
            }

            if (sendSize) {
                //Sends the size of the response first
                int sizeOfResponse = encryptedResponse.length;

                outputStream.writeByte(sizeOfResponse);
            }
            outputStream.write(encryptedResponse);
        }
        else{
            if (sendSize){
                int sizeOfResponse = responseInBytes.length;

                outputStream.writeByte(sizeOfResponse);
            }

            outputStream.write(responseInBytes);

        }


    }



    private void sendResponse(String response, Boolean sendSize) throws IOException {


        //outputStream.flush();
        //Turns the string into its byte array
        byte[] responseInBytes = response.getBytes(StandardCharsets.UTF_8);

            if (sendSize){
                int sizeOfResponse = responseInBytes.length;

                outputStream.writeByte(sizeOfResponse);
            }

            outputStream.write(responseInBytes);




    }









    //Mode decides whether it verifies user data or logs in
    public void receiveLogin(Integer mode) throws IOException {


        //Reads the input stream for the size of the message
        String loginName = recieveMessageAsString(inStream.read());

        String loginPass = recieveMessageAsString(inStream.read());




        //Hashing server side as we can access the users salt
        currUser = new User(loginName, loginPass);

        currUserHandler.setCurrUser(currUser);


        //Determine current users statuses - will always fail at the password level
        currUserHandler.verifyUser();





        //Verification Mode - Mainly for testing
        if(mode == 0){

            //If user exists and pass is correct(Good for login, bad for user creation)
            if(currUserHandler.userExistState & currUserHandler.passVerified) {
                sendResponse("USERFOUND", true, true);
            }


            else {
                sendResponse("USERNOTFOUND", true, true);

            }

        }



        //Login Mode
        if(mode == 1) {

            System.out.println("Login mode!");


            //If the user exists grab there salt then encrypt there data
            //Breaks if user has no salt
            if(currUserHandler.userExistState){
                currUser.setSalt(currUserHandler.getcurrUserSalt());
                currUser.encryptUserInfo();
                currUserHandler.verifyUser();
            }


            //Verifies the user data
            if(!(currUserHandler.userExistState && currUserHandler.passVerified)){
                //If the users data is incorrect - let the client know

                System.out.println("Not logged in!");
                sendResponse("BADLOGIN", true, true);
            }
            else{
                //If the users data is verified - sets the server user to the user provided
                currUser = new User(loginName, loginPass);
                System.out.println("Logged in!");
                sendResponse("GOODLOGIN", true, true);
                System.out.println("Login message sent!");
            }




        }

        //User Creation Mode
        else if(mode == 2){

            if(!(currUserHandler.userExistState)){

                sendResponse("SENDSALT", true, true);
                currUser.setSalt(recieveMessageAsString(inStream.read()));
                currUser.encryptUserInfo();
                currUserHandler.createUser();
                sendResponse("USERCREATED", true, true);
            }

            else{
                sendResponse("USERALREADYEXISTS", true, true);
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
            sendResponse("LOGGEDOUT", true, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Successfully logged out");


    }

    //Checks that the client and server versions are the same
    private void versionCheck() throws IOException {

        String clientVersion = recieveMessageAsString(inStream.read());;

        System.out.println("Client ver: " + clientVersion);
        System.out.println("Server ver: " + SERVERVERSION);

        if(clientVersion.equals(SERVERVERSION)){
            sendResponse("SAMEVER", true, true);
        }
        else{
            sendResponse("DIFFVER", true, true);
        }



    }


    private void changeUsername() throws IOException {


        String desiredUsername = recieveMessageAsString(inStream.read());;

        //If the username is taken
        if (ServerUserHandler.findUserName(desiredUsername)){
            sendResponse("USERNAMETAKEN", true, true);
        }
        else {
            faveVenuesHandler.nameChange(currUserHandler.getcurrUser().getUsername(), desiredUsername);
            currUserHandler.changeUserName(desiredUsername);
            sendResponse("NAMECHANGED", true, true);
        }
    }

    private void changePassword() throws IOException {

        String currPass = recieveMessageAsString(inStream.read());;

        String newPass = recieveMessageAsString(inStream.read());;


        //If the password entered doesnt match the current password
        if(!(UserSecurity.hashThis(currPass, currUserHandler.getcurrUserSalt()).equals(UserSecurity.hashThis(currUser.getPassword(), currUserHandler.getcurrUserSalt())))){

            sendResponse("INCORRECTPASS", true, true);
        }
        else{

            String hashedDesiredPass = UserSecurity.hashThis(newPass, currUserHandler.getcurrUserSalt());

            currUserHandler.changeUserPass(hashedDesiredPass);

            currUserHandler.getcurrUser().setPassword(hashedDesiredPass);

            currUser.setPassword(newPass);

            sendResponse("PASSCHANGED", true, true);



        }

    }

    //Deletes the requested venue file
    private void deleteVenueFile() throws IOException {

        //Gets the filepath from the client
        File fileToDelete = new File(recieveMessageAsString(inStream.read()));



        System.out.println("File to delete: " + fileToDelete);

        //Delete the file from the PC
        if(fileToDelete.delete()){
            //Delete the file from the XML
            VenueXMLParser xml = new VenueXMLParser(new File("venuesLocation.xml"));
            try {
                //MAke sure the slashes are consistent with the direction in the venue XML file
                xml.removeChildMedia("title", currUser.getUsername(), (String.valueOf(fileToDelete)).replace("\\", "/"));
            } catch (TransformerException e) {
                e.printStackTrace();
            }
            sendResponse("File Deleted", true, true);
        }
        else{
            sendResponse("File Deletion Error", true, true);
        }







    }

    //Overloaded functions to let you decide if you want to recieve the pure bytes or the string

    //Reads n number of bytes from the socket
    private byte[] recieveMessage(int n) {

        byte[] readBytes = new byte[0];
        try {
            readBytes = inStream.readNBytes(n);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return readBytes;

    }

    //Reads n number of bytes from the socket - turns them into a string
    private String recieveMessageAsString(int n) {

        byte[] readBytes = new byte[0];
        try {
            readBytes = inStream.readNBytes(n);
        } catch (IOException e) {
            e.printStackTrace();
        }



        //Decrypt the message
        String unencryptedMsg = null;
        try {
            symmetricCipher.init(Cipher.DECRYPT_MODE, symKey);
            unencryptedMsg = new String(symmetricCipher.doFinal(readBytes));
            symmetricCipher.init(Cipher.ENCRYPT_MODE, symKey);
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }

        return unencryptedMsg;
    }


}