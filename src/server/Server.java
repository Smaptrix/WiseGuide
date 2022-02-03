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
    private DataOutputStream outFile;
    private PrintWriter outText;
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




        //Writes text to the buffer
        outText = new PrintWriter(clientSocket.getOutputStream(), true);


        //Reads text from the buffer
        inText = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        //Writes pure file bytes to output socket
        outFile = new DataOutputStream(clientSocket.getOutputStream());

        //Stores the current directory that the application was launched from
        CurrDir = System.getProperty("user.dir");

        //Used for Unit Testing
        return "Server now accepting connections";
    }


    //Closes the server down
    public void stopConnections() throws IOException {
        inText.close();
        outText.close();
        clientSocket.close();
        serverSocket.close();
        outFile.close();
    }


    //Contentiously listens to the buffer
    public void bufferListen() throws IOException {

        String inputLine;

        try{

            while ((inputLine = inText.readLine()) != null) {
                System.out.println("Listening...");
                if ("Close Connection".equals(inputLine)) {
                    outText.println("Connection Closed");
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
                outText.println(requestSplit[1]);
                System.out.println("Response sent: " + requestSplit[1]);
                break;
            default:
                System.out.println(requestIn + " : Invalid command");
                outText.println("Error 404: Text Request Code Not Found");
                break;
        }
    }


    // ToDo - Test this actually works!!!! Need to make an example file then reconstruct client side

    //Sends a file across the socket (after it has been broken down into its bytes)
    public void sendFile(Path filepath) throws IOException {


        try {
            System.out.println("File stored at: " + filepath);
            //Only open this when need to send a file




            //Sends a data packet telling the client to expect a file of a certain size
            long byteSize = Files.size(filepath);
            outFile.write((int) byteSize);
            outFile.flush();

            //Tells the client what type of file to expect
            String fileType = filepath.toString();
            String[] fileTypeSplit = fileType.split("\\.");
            outText.println(fileTypeSplit[1]);
            outFile.flush();


            //Construct a byte array and send that across network
            FileInputStream fileStream = new FileInputStream(String.valueOf(filepath));
            byte[] buffer = fileStream.readAllBytes();
            fileStream.close();

            boolean end = false;
            int bytesSent = 0;

            while(!end){
                outFile.write(buffer[bytesSent]);
                System.out.println(buffer[bytesSent]);
                bytesSent += 1;

                if(bytesSent == byteSize){
                    System.out.println("We have written: " + bytesSent + " bytes");

                    end = true;
                }
            }


            //Clears the outputStream of any excess data
            outFile.flush();


        }catch(NoSuchFileException e){
            System.out.println("File not found");
        }
    }







}