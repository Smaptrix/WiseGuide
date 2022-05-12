package server;

import java.io.*;
import java.util.*;


//Controls the hashmap textfile which contains every users favourite venues
public class FaveVenuesHandler {

    //Filepath to the favourite venue file
    File faveVenueFile;
    //The hashmap that contains the user as a key, and then the list of venues as the value
    HashMap<String, String[]> faveVenueMap;



    //Constructor for this class
    public FaveVenuesHandler(File faveVenueFile){
        this.faveVenueFile = faveVenueFile;
        //Loads the hashmap from the textfile
        try {
            loadHashMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Adds a new favourite venue to the users list
    public void addFaveVenue(String username, String venueName){

        //Makes sure a user exists in the hashmap
        if(!faveVenueMap.containsKey(username)){
            addUser(username);
        }

        //Creates a new array with an extra space
        String[] result = Arrays.copyOf(faveVenueMap.get(username), faveVenueMap.get(username).length + 1);

        //Places the venuename into the extra space
        result[result.length -1] = venueName;

        //Replace the current venue list with the new one
        faveVenueMap.replace(username, result);

        //Saves the hashmap to the text file
        saveHashMap();
    }

    //Removes a users favourite venue from there favourites list
    public void removeFaveVenue(String username, String venueName){
        //If a user doesn't exist dont do anything
        if(!faveVenueMap.containsKey(username)){
            return;
        }
        else{
            //Turns the string array into a list so we can remove the venue
            List<String> list = new ArrayList<String>(Arrays.asList(faveVenueMap.get(username)));
            //Remove the venue
            list.remove(venueName);

            //Turn the list back into a string array
            String[] result = list.toArray(new String[0]);

            //Replaces the old list with the new list
            faveVenueMap.replace(username, result);

        }

        //Saves the map
        saveHashMap();

    }

    //Adds a new user to the hashmap
    public void addUser(String username){

        //Makes sure that the user isn't already in there
        if(!faveVenueMap.containsKey(username)) {
            faveVenueMap.put(username, new String[0]);
            saveHashMap();
        }
    }

    //Removes a user from the hashmap
    public void removeUser(String username){

        //Makes sure that the user is in the list before trying to delete them
        if(faveVenueMap.containsKey(username)) {
            faveVenueMap.remove(username);

            saveHashMap();
        }
    }

    //Saves the hashmap into a text file
    private void saveHashMap(){

        try {
            //Opens the file which we want to write on
            FileWriter hashMapFileWriter = new FileWriter(faveVenueFile);

            //Runs through every entry in the hashmaps
            for(Map.Entry<String, String[]> entry : faveVenueMap.entrySet()){
                //Places the username first
                hashMapFileWriter.write(entry.getKey() + ",");
                //Places the list of venues next
                for(int i = 0; entry.getValue().length > i; i++){
                    hashMapFileWriter.write( entry.getValue()[i] + ".");
                }
                //Adds a new line to represent a new entry
                hashMapFileWriter.write("\n");
            }

            //Close the file to save it
            hashMapFileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }





    }

    //Opens the file and interprets the hashmap
    private void loadHashMap() throws IOException {

        //Initialises the hashmap
        faveVenueMap = new HashMap<>();

        //Creates a reader for the file
        BufferedReader br = new BufferedReader(new FileReader(faveVenueFile));
        String line;

        //Goes through every line in the file
        while((line = br.readLine()) != null){

            System.out.println(line);

            //splits the line into the key and value pair
            String[] userVenuesPair = line.split(",");

            //If there are entries into the favourite venues list
            if(userVenuesPair.length > 1) {
                //Splits the String of venues into an array
                String[] venueArray = userVenuesPair[1].split("\\.");
                //Places the username and the array of venues into the hashmap
                faveVenueMap.put(userVenuesPair[0], venueArray);
            }


        }
        //Message to let the system aware that the venues have been loaded
        System.out.println("Fave venues loaded!");

    }


    public String faveVenueList(String username){

        //Checks to make sure the user is in there first
        if(faveVenueMap.containsKey(username)) {

            String[] faveVenueList = faveVenueMap.get(username);

            String faveVenueListString = "";

            //Turn the string array into one long string so its easier to send
            for (int i = 0; faveVenueList.length > i; i++) {
                faveVenueListString = faveVenueListString.concat(faveVenueList[i] + ".");
            }


            System.out.println(faveVenueListString);

            return faveVenueListString;
        }
        else{
            return null;
        }

    }


    public void nameChange(String oldUsername, String newUsername){

        if(faveVenueMap.containsKey(oldUsername)) {
            String[] faveVenueList = faveVenueMap.get(oldUsername);

            faveVenueMap.put(newUsername, faveVenueList);

            faveVenueMap.remove(oldUsername);

            saveHashMap();
        }

    }





}
