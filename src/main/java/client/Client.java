/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham
    Date Created:   27/01/2022
    Last Updated:   11/05/2022
 */
package client;

import serverclientstuff.User;
import serverclientstuff.UserSecurity;


import javax.crypto.*;
import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;


import java.security.*;

import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;





/**
 * Client class handles the client side application operation methods.
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
     * fileLocations stores where all the files are located.
     */
    public Map<String, File> fileLocations = new HashMap<>();

    /**
     * serverPublicKey stores the public key of the server
     */
    private PublicKey serverPublicKey;


    /**
     * symKey is they symmetric key used for communciation between the server & client
     */
    private SecretKey symKey;
    /**
     * symmetricKeyFile is the file that contains the symmetric key
     */
    private File symmetricKeyFile;

    /**
     * symmetricCIpher is the cipher that is used to encrypt/decrypt communications
     */
    private Cipher symmetricCipher;


    /**
     * faveVenues is a list of the current users favorite venues
     */
    private String[] faveVenues;



    /**
     * <p>
     * Starts the connection to the server by creating necessary objects and assigning the determined ip and ports.
     * </p>
     *
     * @param ip The ip address of the server.
     * @param port The port of the client side.
     * @throws IOException Throws an IOException if it fails to connect to the server.
     */
    public void startConnection(String ip, int port) throws IOException {

        //Startup means not connected yet
        connected = false;

        try {
            //Creates the new sockets and input/output streams
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


            //Once everything is booted and checked the client registers as connected
            connected = true;
            //Set the socket timeout to 1000ms so that if the sever crashed the app doesnt crash straight away
            clientSocket.setSoTimeout(1000);

        } catch (ConnectException e) {
            System.out.println("Failed to connect/Server Offline");
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            e.printStackTrace();
            System.out.println("Encryption Failed... Shutting down");
            System.exit(-1);
        }

    }

    /**
     * Generates a random symmetric key for use in the session
     * @throws NoSuchAlgorithmException if the specified algorithm does not exist
     */
    private void generateSymmetricKey() throws NoSuchAlgorithmException {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        //Generates a key of size 256 bytes
        generator.init(256);
        symKey = generator.generateKey();
    }


    /**
     * Gets the servser public key for use of encrypting the generated symmetric key
     * @throws IOException if the connection is not available
     */
    private void getServerEncryption() throws IOException {

        //Sends the request to get the public key
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

        //Reads the specified number of bytes from the inputstream
        byte[] encPublicKey = readBytes(bytesToRead);


        System.out.println("The file is a : " + dataType + " file and it is : " + bytesToRead + " long.");

        //Generate thhe key spec from the recieved data
        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encPublicKey);

        //Generate the key using a keyfactory using the given keyspec
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            serverPublicKey = keyFactory.generatePublic(pubKeySpec);
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }



    }

    /**
     * Encodes the gneerated symmetric key and sends it to the server for the current session
     * @throws NoSuchPaddingException If the given padding type does not exist
     * @throws NoSuchAlgorithmException If the given alogirthm does not exist
     * @throws InvalidKeyException If the given key is incorrect/invalid
     * @throws IllegalBlockSizeException If the input size to the algorithm is incorrect
     * @throws BadPaddingException If the amount of padding on the block is incorrect
     * @throws IOException If the client is unable to connect to the server
     */
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
                end = true;
            }
        }
        //Clears the outputStream of any excess data
        out.flush();

        System.out.println("Key written");

        //Set the socket back to the correct write verision
        outStream = clientSocket.getOutputStream();


    }


    /**
     * This function is used to validate that the server has the same symmetric key
     * @return True - The server key has been validated. False - The server key hasn't been validated
     * @throws IOException If the client cannot connect to the server
     */
    private boolean keyValidation() throws IOException {


        //This command tells the server to repeat back whatever the next message is
        String unencryptedCommand = "ECHO";

        //The test message that the server has to repeat back
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



        //Sends the command and encrypts it
        sendMessage(combinedString, true);



        //If the recieved message is the same as the sent message - the key is validated
        if(receiveAcknowledgement(true).equals(unencryptedMessage)){
            return true;
        }
        else{
            //If the client is unable  to verify the servers key
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
     * @throws IOException if client cannot connect to the server
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


        //Magic number 3 - because we know that the file extension is only going to be max three letters

        byte[] DataTypeBytes = new byte[3];

        for (int i = 0; i < 3; i++) {
            DataTypeBytes[i] = (byte) inputStream.read();
        }

        String dataType = new String(DataTypeBytes, StandardCharsets.UTF_8);


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
     * @throws IOException if the client is unable to open the file
     */
    private File BytesToFile(byte[] data, String fileName, String fileType) throws IOException {

        //Creates a new temp file - Identifiable by custom prefix
        File currFile = new File(String.valueOf(Files.createTempFile("WG_", "." + fileType)));


        //Creates a temp file out of the data received, so that when the program closes the data isn't saved
        FileOutputStream os = new FileOutputStream(currFile);

        //Writes the recieved data to the FileOutPutStreams
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
     * @throws IOException if the client is unable to connect to the server
     */
    public String requestLogin(User currUser) throws IOException {
        sendMessage("LOGIN", true);

        System.out.println("LOGIN MESSAGE SENT");

        sendMessage(currUser.getUsername(), true);

        sendMessage(currUser.getPassword(), true);


        return receiveAcknowledgement(true);

    }


    /**
     * <p>
     * Recieves a one line acknowledgement from the server
     * </p>
     * @return the acknowledgement in string form
     * @throws IOException if the server is unable to connect to the server
     */
    public String receiveAcknowledgement(boolean decrypt) throws IOException {

        //Read in the file size of the expected file
        //Dont have to worry about large files here as this is only messages
        int fileSize = inputStream.read();

        //Read the filesize in bytes
        byte[] data = readBytes(fileSize);

        //Initalise the string
        String ack = null;
        //If we want to decrypt the message
        if (decrypt) {

            //Turn the cipher to decrypt mode
            try {
                symmetricCipher.init(Cipher.DECRYPT_MODE, symKey);
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }

            //Decrypt the data using the cipher
            byte[] decryptedData = new byte[0];
            try {
                decryptedData = symmetricCipher.doFinal(data);
            } catch (IllegalBlockSizeException | BadPaddingException e) {
                e.printStackTrace();
            }

            //Turn the decrypted data into the string
            ack = new String(decryptedData, StandardCharsets.UTF_8);

            System.out.println(ack);

            //Turn the cipher back into encrypt mode
            try {
                symmetricCipher.init(Cipher.ENCRYPT_MODE, symKey);
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }
        }
        //If we don't want to decrypt the message
        else {

            ack = new String(data, StandardCharsets.UTF_8);

            System.out.println(ack);
        }


        return ack;
    }

    /**
     * <p>
     * Asks the user to verify if the users information is correct
     * Differs to a login
     * </p>
     * @param currUser the current user that you want the server to verify
     * @return the verification status from the server
     * @throws IOException if the client cannot connect to the server
     */
    public String verifyUser(User currUser) throws IOException {
        sendMessage("VERIFYUSER", true);

        sendMessage(currUser.getUsername(), true);

        sendMessage(currUser.getPassword(), true);

        return receiveAcknowledgement(true);

    }


    /**
     * <p>
     * Requests the user to create a user with the provided information
     * </p>
     * @param currUser the user that the client wants the server to create
     * @return the status of the user creation
     * @throws IOException if the client cannot connect to the server
     */
    public String createUser(User currUser) throws IOException {

        boolean success = false;

        //Send the command
        sendMessage("CREATEUSER", true);

        //Send the user data
        sendMessage(currUser.getUsername(), true);

        sendMessage(currUser.getPassword(), true);


        String ack = receiveAcknowledgement(true);

        //Waits for a request from the server
        //Generates a salt for the user for extra security
        if(ack.equals("SENDSALT")) {
            currUser.setSalt(UserSecurity.generateSalt());
            System.out.println("User: " + currUser.getUsername() + " Salt: " +  currUser.getSalt());

            sendMessage(currUser.getSalt(), true);
            return receiveAcknowledgement(true);
        }
        else{

            return ack;

        }


    }

    /**
     * <p>
     * Deletes the selected user account
     * </p>
     * @return whether the deletion was successful or not.
     * @throws IOException if
     */
    public String deleteUser(User currUser) throws IOException {
        sendMessage("DELETEUSER",true);
        sendMessage(currUser.getUsername(),true);
        sendMessage(currUser.getPassword(),true);
        return receiveAcknowledgement(true);
    }

    /**
     * <p>
     * Requests that the server logs the current user out
     * </p>
     * @return the logout state of the server
     * @throws IOException if the client cannot connect to the server
     */
    public String requestLogout() throws IOException {

        //Send the command
        sendMessage("LOGOUT", true);
        outStream.flush();

        return receiveAcknowledgement(true);
    }

    /**
     * <p>
     * Requests the server to check the versions of the client/server pair against eachother
     * </p>
     * @return the versionCheck response from the server
     * @throws IOException if the client cannot connect to the server
     */
    private boolean versionCheck() throws IOException {

        //Send the command
        sendMessage("VERSIONCHECK", true);

        sendMessage(CLIENTVERSION, true);

        String ack = receiveAcknowledgement(true);

        return ack.equals("SAMEVER");
    }

    /**
     * Requests the files from the server containing the list of the venues
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

        return fileLocations.get(fileName);

    }

    /**
     * Requests that the server change a users username
     * @param desiredUsername the new name that the user would like
     * @return the result of the name change
     * @throws IOException if the client cannot connect to the server
     */
    public String requestUserNameChange(String desiredUsername) throws IOException {

        //Sends the command
        sendMessage("CHANGENAME", true);

        //Sends the requested username
        sendMessage(desiredUsername, true);


        return receiveAcknowledgement(true);

    }


    /**
     * Requests that the server change a users password
     * @param enteredPassword the users current password
     * @param newPassword the users new password
     * @return the result of the password change on the server
     * @throws IOException if the client cannot connect to the server
     */
    public String requestPasswordChange(String enteredPassword, String newPassword) throws IOException {

        sendMessage("CHANGEPASS", true);

        sendMessage(enteredPassword, true);

        sendMessage(newPassword,true);

        return receiveAcknowledgement(true);


    }


    /**
     * Attempts to log a venue into the server
     * @param venueName the name of the venue logging in
     * @param venuePass the password of the venue logging in
     * @return the result of the login request
     * @throws IOException if the client cannot connect to the server
     */
    public String requestVenueLogin(String venueName, String venuePass) throws IOException {
        sendMessage("VENUELOGIN",true);

        sendMessage(venueName,true);
        sendMessage(venuePass,true);

        return receiveAcknowledgement(true);
    }


    /**
     * Requests that the server delete a file pertaining to the currently logged in venue
     * @param filePath the filepath to the file that the venue would like to delete
     * @return the result of deleting the file on the server
     * @throws IOException if the client cannot connect to the server
     */
    public String requestDeleteFile(String filePath) throws IOException {
        sendMessage("DELETEVENUEFILE",true);

        sendMessage(filePath,true);

        return receiveAcknowledgement(true);


    }


/*
    //Requests that the client can send a file to the server
    //Maybe change so that it sends an email and then we discuss it
    //Rather than having any user be able to upload any file they want
    public String requestUploadFile(File filetoUpload) throws IOException {
        sendMessage("UPLOADFILE",true);


        return receiveAcknowledgement(true);

    }
*/



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


    /**
     * Checks to see if a file is already downloaded
     * @param fileName The name of the file being checked
     * @return True - The file is downloaded. False - The file has not been downloaded
     */
    public boolean isFileDownloaded(String fileName){

        if(fileLocations.containsKey(fileName)){
            System.out.println("File is already downloaded");
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Requests the current users favourite venues
     * @return the list of the users favourite venues
     * @throws IOException If the client cannot connect to the server
     */
    public String[] requestFaveVenueList() throws IOException {
        sendMessage("FAVELIST", true);

        String venueListString =  receiveAcknowledgement(true);

        if(!(venueListString.equals("EMPTY"))) {
            return venueListString.split("\\.");
        }

        else{
            return null;
        }
    }


    /**
     * Requests that the server add a new venue to the current users favourite list
     * @param venueName the name of the venue to be added to the list
     * @throws IOException if the client cannot connect to the server
     */
    public void addFavouriteVenue(String venueName) throws IOException {
        sendMessage("FAVEVENUE", true);

        sendMessage(venueName, true);


        receiveAcknowledgement(true);


    }

    /**
     * Requests that the server remove a venue from the current users favourite list
     * @param venueName the name of the venue to be removed from the list
     * @throws IOException if the client cannot connect to the server
     */
    public void removeFavouriteVenue(String venueName) throws IOException {
        sendMessage("UNFAVEVENUE", true);

        sendMessage(venueName, true);

        receiveAcknowledgement(true);

    }


    /**
     * Sends a message to the server (Overload of another function)
     * @param toSend The bytes to sned to the server
     * @param doEncrypt Whether to encrypt the data
     */
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


    /**
     * A wrapper to send messages to the server
     * @param toSend the string to send to the server
     * @param doEncrypt whether you want to encrypt the message or not
     */
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


