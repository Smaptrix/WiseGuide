package server;

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class Server {




    private ServerSocket serverSocket;
    private Socket clientSocket;
    private DataOutputStream outputStream;
    private BufferedReader inText;
    private int port;
    private String CurrDir;

    //Starts the server
    public String startup(int port) throws IOException {

        this.port = port;

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

        //Stores the current directory that the application was launched from
        CurrDir = System.getProperty("user.dir");

        //Used for Unit Testing
        return "Server now accepting connections";
    }


    //Closes the server down
    public void stopConnections() throws IOException {
        inText.close();
        clientSocket.close();
        serverSocket.close();
        outputStream.close();
    }


    //Contentiously listens to the buffer
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
            System.out.println("Socket Closed (Client may have closed!)");
        }
    }






    //Requests in form "Request Code Type" + " " + "Request Information"
    public void requestParser(String requestIn) throws IOException {
        String[] requestSplit = requestIn.split(" ");
        switch(requestSplit[0]) {
            case "GET":

                //Should send file stored at the location of the current directory with the filename provided
                sendFile(Path.of((CurrDir + "\\" + requestSplit[1])));

                break;
            case "ECHO":
                //Echos the request back (mainly for testing)
                sendResponse(requestSplit[1], true);
                System.out.println("Response sent: " + requestSplit[1]);
                break;
            default:
                System.out.println(requestIn + " : Invalid command");
                sendResponse("Error 404: Text Request Code Not Found", false);
                break;
        }
    }


    // ToDo -  SEND LARGE FILE I.E IMAGE (DOESN'T CURRENTLY WORK)

    //Sends a file across the socket (after it has been broken down into its bytes)
    public void sendFile(Path filepath) throws IOException {


        try {
            System.out.println("File stored at: " + filepath);
            //Only open this when need to send a file


            //Sends a data packet telling the client to expect a file of a certain size
            long byteSize = Files.size(filepath);

            System.out.print("File Size: " + byteSize);
            outputStream.write((int) byteSize);
            outputStream.flush();

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
                System.out.println(buffer[bytesSent]);
                bytesSent += 1;

                if(bytesSent == byteSize){
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
    private void sendResponse(String response, Boolean sendSize) throws IOException {

        //Turns the string into its byte array
        byte[] responseInBytes = response.getBytes(StandardCharsets.UTF_8);

        if (sendSize == true){
            //Sends the size of the response first
            int sizeOfResponse = responseInBytes.length;
            outputStream.write(sizeOfResponse);
        }




        outputStream.write(responseInBytes);

        outputStream.flush();


    }




}