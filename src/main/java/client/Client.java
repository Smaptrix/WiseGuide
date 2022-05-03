/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham
    Date Created:   27/01/2022
    Last Updated:   24/02/2022
 */
package client;

import serverclientstuff.User;
import serverclientstuff.UserSecurity;
import serverclientstuff.Utils;

import javax.crypto.*;
import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;





/**
 * Client class handles the client side server operation methods.
 */
public class Client {






    /**
     * CLIENTVERSION is the current version of the client
     */
    private static final String CLIENTVERSION = "Ver 0.60";

    /**
     * clientSocket is the client side socket.
     */
    private Socket clientSocket;

    /**
     * outStream is an output stream that allows the client to send bytes to the server
     */
    private OutputStream outStream;
    //Only need a printWriter as we won't be sending files back to the server, just text requests

    /**
     * inputStream allows reading of the files sent by the server.
     */
    private InputStream inputStream;
    //InputStream to read files

    /**
     * connected is a boolean that stores whether the client is connected to the server.
     */
    private boolean connected;

    /**
     * sameversion is a boolean that stores whether the client and server are the same version
     */

    private boolean sameVersion;


    /**
     * Dictionary stores where all the files are located.
     */
    public Map<String, File> fileLocations = new HashMap<>();


    private PublicKey serverPublicKey;


    //The clients symmetric key

    private SecretKey symKey;
    private File symmetricKeyFile;

    private Cipher symmetricCipher;



    /**
     * <p>
     * Starts the connection to the server by creating necessary objects and assigning the correct ip and port.
     * </p>
     *
     * @param ip The ip address of the server.
     * @param port The port of the client side.
     * @throws IOException Throws an IOException if it fails to connect to the server.
     */
    public void startConnection(String ip, int port) throws IOException {

        connected = false;

        try {
            clientSocket = new Socket(ip, port);
            outStream = clientSocket.getOutputStream();
            inputStream = clientSocket.getInputStream();
            System.out.println("Connection Opened");



            //Get the servers public key
            getServerEncryption();

            //Generate a symmetric key for use for the session
            generateSymmetricKey();

            //Sends the encrypted symmetric key to the server
            sendSymmetricKey();


            //Makes sure that the server and client are able to encrypt/decrypt messages
            if(!keyValidation()){
                System.out.println("Key validation failed... Shutting down");
                System.exit(-1);

            }

            //Verify that the server and client are running on the same version
            sameVersion = versionCheck();



            connected = true;
            clientSocket.setSoTimeout(1000);

        } catch (ConnectException e) {
            System.out.println("Failed to connect/Server Offline");
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            e.printStackTrace();
            System.out.println("Encryption Failed... Shutting down");
            System.exit(-1);
        }

    }

    //Generates the symmetric key for the use in the session
    private void generateSymmetricKey() throws NoSuchAlgorithmException {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(256);
        symKey = generator.generateKey();
    }





    //Recieves the privates public key for the encryption
    private void getServerEncryption() throws IOException {

        sendMessage("SENDPUBLIC", false);


        //SAME CODE AS FILE REQUEST EXCEPT WE DONT CHOOSE THE FILE

        //Tells us how many bytes are telling us how big the file is
        int numOfFileSizeBytes = inputStream.read();

        System.out.println("We have " + numOfFileSizeBytes + " file size bytes to read");

        //Reads the next set amount of bytes to decode the file size
        byte[] bytesToReadBytes = new byte[numOfFileSizeBytes];

        for (int i = 0; i < numOfFileSizeBytes; i++) {
            bytesToReadBytes[i] = (byte) inputStream.read();
        }

        int bytesToRead = ByteBuffer.wrap(bytesToReadBytes).getInt();


        //Magic number 3 - because we know that the file extension is only going to be three letters
        byte[] DataTypeBytes = new byte[3];

        for (int i = 0; i < 3; i++) {
            DataTypeBytes[i] = (byte) inputStream.read();
        }

        String dataType = new String(DataTypeBytes, StandardCharsets.UTF_8);

        System.out.println(dataType);


        byte[] encPublicKey = readBytes(bytesToRead);


        System.out.println("The file is a : " + dataType + " file and it is : " + bytesToRead + " long.");

        //Once we have the array of bytes, we then reconstruct that into the actual file.
        //Don't need to save the file actually...
        //File serverPublicKeyFile = BytesToFile(encPublicKey, "severPubKey", dataType);


        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encPublicKey);

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            serverPublicKey = keyFactory.generatePublic(pubKeySpec);
            System.out.println(serverPublicKey);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }



    }

    //Encrypts clients public key and send it to server
    private void sendSymmetricKey() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        //First encrypt the clients public key with the servers public key
        Cipher rsaCipher = Cipher.getInstance("RSA");

        //Initialise the cipher with the servers public key
        rsaCipher.init(Cipher.ENCRYPT_MODE, serverPublicKey);

        //Encode the public key with the servers public key
        byte[] encodedSymKey = rsaCipher.doFinal(symKey.getEncoded());


        //Writes the encoded public key to a file
        try {
            symmetricKeyFile = BytesToFile(encodedSymKey, "symKey", "sec");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Encoded symmetric key file!");


        //Opens a one time output data stream to send the file
        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

        //Get the length of the keyfile
        long symKeyFileSize = symmetricKeyFile.length();

        byte[] fileSizeInBytes = ByteBuffer.allocate(4).putInt((int) symKeyFileSize).array();

        int fileSizeInBytesLen = fileSizeInBytes.length;

        //Tell the server how many bytes to expect regarding the length
        out.write(fileSizeInBytesLen);

        //Writes the fileSize in bytes to the client
        for (byte fileSizeInByte : fileSizeInBytes) {
            out.write(fileSizeInByte);
        }


        //Construct a byte array from the file we want to send and send that across network
        FileInputStream fileStream = new FileInputStream(symmetricKeyFile);
        byte[] buffer = fileStream.readAllBytes();
        fileStream.close();

        boolean end = false;
        int bytesSent = 0;

        //Sends the file byte by byte
        while(!end){
            out.write(buffer[bytesSent]);

            bytesSent += 1;

            //Testing purposes only
            //System.out.println(buffer[bytesSent]);

            if(bytesSent == symKeyFileSize){
                System.out.println("We have written: " + bytesSent + " bytes");

                end = true;
            }
        }
        //Clears the outputStream of any excess data
        out.flush();

        System.out.println("Key written");

        //Set the socket back to the text write
        outStream = clientSocket.getOutputStream();


    }


    //Recieves the symmetric key to use for the rest of the session
    private boolean keyValidation() throws IOException {

        System.out.println(symKey);

        //Plan - Encrypt Command and send it
        //     - Encrypt test message and send it
        //     - Have server decrypt the command, then decrypt the message then echo it back
        //     -Check on client to make sure that the messages are the same

        String unencryptedCommand = "ECHO";


        String unencryptedMessage = "thisisatestmessage";

        //Creates the cipher to use for the rest of the  and determines the algorithm to use
        //SHOULD BE AES
        try {
            symmetricCipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }

        //Initialises the cipher in encryption mode and gives it the symmetric key
        try {
            symmetricCipher.init(Cipher.ENCRYPT_MODE, symKey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        //Combine the test command with the test message
        String combinedString = unencryptedCommand + " " + unencryptedMessage;



        //Sends the encrypted command
        sendMessage(combinedString, true);

        System.out.println("Sent encrypted command");


        if(receiveAcknowledgement().equals(unencryptedMessage)){
            return true;
        }
        else{
            System.out.print("Unable to verify encryptyed connection");
            return false;
        }



    }


    /**
     * <p>
     * Closes the connection to the server.
     * </p>
     * @throws IOException Throws an IOException if the client fails to close the connection.
     */
    public void closeConnection() throws IOException {

        sendMessage("Close Connection", true);
        inputStream.close();
        outStream.close();
        clientSocket.close();
        System.out.println("Connection Closed");
        connected = false;

    }

    /**
     * <p>
     * Sends a test message to the server and stores the result as a string and returns it.
     * </p>
     * @return Returns the result of the test response.
     * @throws IOException Throws an IOException if the client fails to send a message.
     */
    public String sendTestMessage() throws IOException {


        sendMessage("ECHO " + "test", true);

        int stringSize = inputStream.read();

        byte[] data = readBytes(stringSize);


        return new String(data, StandardCharsets.UTF_8);
    }


    /**
     * <p>
     * Sends an ECHO message to the server and returns the result as a string.
     * </p>
     * @param msg The message to send to the server.
     * @return The message returned by the server.
     * @throws IOException
     */
    public String echoMessage(String msg) throws IOException {

        System.out.println("ECHO REQUEST: " + msg);

        sendMessage("ECHO " + msg, true);

        int fileSize = inputStream.read();


        byte[] data = readBytes(fileSize);


        return new String(data, StandardCharsets.UTF_8);

    }





    /**
     * <p>
     *     Requests a file from the server for transfer and download
     * </p>
     * @param fileName the name of the file the client wants to download
     * @return The bytes sent by the server
     * @throws IOException if
     */
    public File requestFile(String fileName) throws IOException {

        //Checks to see if a file has already been downloaded
        //This is so that the same file is not downloaded twice
        if(isFileDownloaded(fileName)){
            return null;
        }


        System.out.println("GET REQUEST: " + fileName);

        sendMessage("GET " + fileName, true);




        //Tells us how many bytes are telling us how big the file is
        int numOfFileSizeBytes = inputStream.read();

        System.out.println("We have " + numOfFileSizeBytes + " file size bytes to read");

        //Reads the next set amount of bytes to decode the file size
        byte[] bytesToReadBytes = new byte[numOfFileSizeBytes];

        for (int i = 0; i < numOfFileSizeBytes; i++) {
            bytesToReadBytes[i] = (byte) inputStream.read();
        }

        int bytesToRead = ByteBuffer.wrap(bytesToReadBytes).getInt();


        //Magic number 3 - because we know that the file extension is only going to be three letters
        byte[] DataTypeBytes = new byte[3];

        for (int i = 0; i < 3; i++) {
            DataTypeBytes[i] = (byte) inputStream.read();
        }

        String dataType = new String(DataTypeBytes, StandardCharsets.UTF_8);

        System.out.println(dataType);


        byte[] data = readBytes(bytesToRead);


        System.out.println("The file is a : " + dataType + " file and it is : " + bytesToRead + " long.");

        //Once we have the array of bytes, we then reconstruct that into the actual file.
        return BytesToFile(data, fileName, dataType);

    }


    /**
     * <p>
     * Reads a specific amount of bytes from the inputStream
     * </p>
     * @param bytesToRead the number of bytes to read from the inputStream
     * @return a byte array of data read from the input stream
     */
    private byte[] readBytes(int bytesToRead) {

        //Initialises a new byte array of size predetermined by our network protocol
        byte[] data = new byte[bytesToRead];

        boolean end = false;
        int bytesRead = 0;


        //Reads bytes up until the count has been reached
        while (!end) {

            try {

                data[bytesRead] = (byte) inputStream.read();

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
        return data;
    }


    /**
     * <p>
     * Reconstructs a raw byte array to a file and saves it in the temp folder
     * </p>
     * @param data the raw byte array
     * @param fileName the name of the requested file to construct
     * @param fileType the type of the requested file
     * @return the filepath of the downlaoded file
     * @throws IOException if
     */
    private File BytesToFile(byte[] data, String fileName, String fileType) throws IOException {

        //Creates a new temp file - Identifiable by custom prefix
        File currFile = new File(String.valueOf(Files.createTempFile("WG_", "." + fileType)));


        //Creates a temp file out of the data received, so that when the program closes the data isn't saved
        FileOutputStream os = new FileOutputStream(currFile);

        os.write(data);

        fileLocations.put(fileName, currFile);

        //Schedules the file to be deleted when the application closes
        currFile.deleteOnExit();

        os.close();

        //Saves file in temp position
        System.out.println("File saved at: " + currFile);

        return currFile;
    }


    /**
     * <p>
     * Makes an attempt to login to the server with the provided user data
     * </p>
     * @param currUser The user attempting to login
     * @return the acknowledgement to the request from the server
     * @throws IOException if
     */
    public String requestLogin(User currUser) throws IOException {
        sendMessage("LOGIN", true);

        System.out.println("LOGIN MESSAGE SENT");

        sendMessage(currUser.getUsername(), true);

        sendMessage(currUser.getPassword(), true);


        return receiveAcknowledgement();

    }


    /**
     * <p>
     * Recieves a one line acknowledgement from the server
     * </p>
     * @return the acknowledgement in string form
     * @throws IOException if
     */
    public String receiveAcknowledgement() throws IOException {

        int fileSize = inputStream.read();


        byte[] data = readBytes(fileSize);

        String ack = new String(data, StandardCharsets.UTF_8);

        System.out.println(ack);

        return ack;
    }






    /**
     * <p>
     * Asks the user to verify if the users information is correct
     * Differs to a login
     * </p>
     * @param currUser the current user that you want the server to verify
     * @return the verification status from the server
     * @throws IOException if
     */
    public String verifyUser(User currUser) throws IOException {
        sendMessage("VERIFYUSER", true);

        sendMessage(currUser.getUsername(), true);

        sendMessage(currUser.getPassword(), true);

        return receiveAcknowledgement();

    }


    /**
     * <p>
     * Requests the user to create a user with the provided information
     * </p>
     * @param currUser the user that the client wants the server to create
     * @return the status of the user creation
     * @throws IOException if
     */
    public String createUser(User currUser) throws IOException {

        sendMessage("CREATEUSER", true);

        sendMessage(currUser.getUsername(), true);

        sendMessage(currUser.getPassword(), true);

        //Should timeout if nothing respond
        if(receiveAcknowledgement().equals("SENDSALT")) {
            currUser.setSalt(UserSecurity.generateSalt());
            System.out.println("User: " + currUser.getUsername() + " Salt: " +  currUser.getSalt());

            sendMessage(currUser.getSalt(), true);
        }

        return receiveAcknowledgement();
    }


    /**
     * <p>
     * Requests that the server logs the current user out
     * </p>
     * @return the logout state of the server
     * @throws IOException if
     */
    public String requestLogout() throws IOException {

        sendMessage("LOGOUT", true);
        outStream.flush();

        return receiveAcknowledgement();
    }

    /**
     * <p>
     * Requests the server to check the versions of the client/server pair against eachother
     * </p>
     * @return the versionCheck response from the server
     * @throws IOException if
     */
    private boolean versionCheck() throws IOException {

        sendMessage("VERSIONCHECK", true);

        sendMessage(CLIENTVERSION, true);

        String ack = receiveAcknowledgement();

        if(ack.equals("SAMEVER")){
            return true;
        }
        else{
            return false;
        }



    }

    /**
     * Requests the files from the server containing the list of the venues
     * @return
     */
    public void requestVenueXMLFile() throws IOException {

        //Requests a file containing the list of every venue contained within the venue types
        requestFile("venuesLocation.xml");
    }


    /**
     * Gives the directory path for the required file
     * @param fileName name of the file you desire to fine
     * @return the directory path of specified file
     */
    public File getFile(String fileName){


        //System.out.println("File requested: " + fileName);

        return fileLocations.get(fileName);

    }


    //TODO - Could be made more rigourous, but assumes server and client have same user

    //Requests that the server requests a users name
    public String requestUserNameChange(String desiredUsername) throws IOException {


        sendMessage("CHANGENAME", true);


        sendMessage(desiredUsername, true);




        return receiveAcknowledgement();

    }


    //Requests that the server changes the users password
    public String requestPasswordChange(String enteredPassword, String newPassword) throws IOException {

        sendMessage("CHANGEPASS", true);

        sendMessage(enteredPassword, true);

        sendMessage(newPassword,true);

        return receiveAcknowledgement();


    }



    //Requests that the server log a venue in
    public String requestVenueLogin(String venueName, String venuePass) throws IOException {
        sendMessage("VENUELOGIN",true);

        sendMessage(venueName,true);
        sendMessage(venuePass,true);

        return receiveAcknowledgement();
    }


    //Requests that the server delete a file from a venues directory
    public String requestDeleteFile(String filePath) throws IOException {
        sendMessage("DELETEVENUEFILE",true);

        sendMessage(filePath,true);

        return receiveAcknowledgement();


    }


    //Requests that the client can send a file to the server
    //Maybe change so that it sends an email and then we discuss it
    //Rather than having any user be able to upload any file they want
    public String requestUploadFile(File filetoUpload) throws IOException {
        sendMessage("UPLOADFILE",true);



        return receiveAcknowledgement();

    }


    /**
     *
     * @return The connection status to the server
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     *
     * @return The current version of the client
     */
    public String getCurrVersion() {return CLIENTVERSION;}

    /**
     *
     * @return the boolean of whether the server/client pair are the same version
     */
    public boolean isSameVersion() {
        return sameVersion;
    }



    public boolean isFileDownloaded(String fileName){

        if(fileLocations.containsKey(fileName)){
            System.out.println("File is already downloaded");
            return true;
        }
        else{
            return false;
        }

    }


    //Overloaded functions to let client send strings or bytes

    //Provides a wrapper for functions to send strings to the server and also bytes
    private void sendMessage(byte[] toSend, boolean doEncrypt){

        if(doEncrypt) {

            try {
                toSend = symmetricCipher.doFinal(toSend);
            } catch (IllegalBlockSizeException | BadPaddingException e) {
                e.printStackTrace();
            }

            int numOfBytes = toSend.length;

            try {
                outStream.write(numOfBytes);
                outStream.write(toSend);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{

            int numOfBytes = toSend.length;

            try {
                outStream.write(numOfBytes);
                outStream.write(toSend);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    //Provides a wrapper for functions to send strings to the server and also bytes
    private void sendMessage(String toSend, boolean doEncrypt)  {

        byte[] toSendBytes = toSend.getBytes();

        if(doEncrypt) {

            try {
                toSendBytes = symmetricCipher.doFinal(toSendBytes);
            } catch (IllegalBlockSizeException | BadPaddingException e) {
                e.printStackTrace();
            }

            int numOfBytes = toSendBytes.length;

            try {
                outStream.write(numOfBytes);
                outStream.write(toSendBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{

            int numOfBytes = toSendBytes.length;

            try {
                outStream.write(numOfBytes);
                outStream.write(toSendBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}


