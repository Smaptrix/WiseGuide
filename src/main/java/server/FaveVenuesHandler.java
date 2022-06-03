/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham
    Date Created:   03/05/2022
    Last Updated:   03/06/2022
 */

package server;

import java.io.*;
import java.util.*;


/**
 * <p>
 *     Controls the hashmap text file which contains every users favourite venues
 * </p>
 */

public class FaveVenuesHandler {

    /**
     *<p>
     *      Contains the list of every users favourite venues
     *</p>
     */
    File faveVenueFile;
    /**
     * <p>
     *     The users username is the key, the value is the list of venues
     * </p>
     */
    HashMap<String, String[]> faveVenueMap;


    /**
     * <p>
     *     The constructor
     * </p>
     * @param faveVenueFile
     */
    public FaveVenuesHandler(File faveVenueFile){
        this.faveVenueFile = faveVenueFile;
        //Loads the hashmap from the text file
        try {
            loadHashMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>
     *     Adds a new favourite venue to the users list
     * </p>
     * @param username the users username
     * @param venueName the name of the venue to be favourited
     */
    public void addFaveVenue(String username, String venueName){

        //Makes sure a user exists in the hashmap
        if(!faveVenueMap.containsKey(username)){
            addUser(username);
        }

        //Creates a new array with an extra space
        String[] result = Arrays.copyOf(faveVenueMap.get(username), faveVenueMap.get(username).length + 1);

        //Places the venue name into the extra space
        result[result.length -1] = venueName;

        //Replace the current venue list with the new one
        faveVenueMap.replace(username, result);

        //Saves the hashmap to the text file
        saveHashMap();
    }

    /**
     * <p>
     *     Removes a users favourite venue from there favourites list
     * </p>
     * @param username the users username
     * @param venueName the name of the venue
     */
    public void removeFaveVenue(String username, String venueName){
        //If a user doesn't exist don't do anything
        if(!faveVenueMap.containsKey(username)){
            return;
        }
        else{
            //Turns the string array into a list, so we can remove the venue
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

    /**
     * <p>
     *     Adds a new user to the hashmap
     * </p>
     * @param username the users username
     */
    public void addUser(String username){

        //Makes sure that the user isn't already in there
        if(!faveVenueMap.containsKey(username)) {
            faveVenueMap.put(username, new String[0]);
            saveHashMap();
        }
    }

    /**
     * <p>
     *     Removes a user from the hashmap
     * </p>
     * @param username the users username
     */
    public void removeUser(String username){

        //Makes sure that the user is in the list before trying to delete them
        if(faveVenueMap.containsKey(username)) {
            faveVenueMap.remove(username);

            saveHashMap();
        }
    }

    /**
     * <p>
     *     Saves the hashmap into a text file
     * </p>
     */
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

    /**
     * <p>
     *     Opens the text file and interprets the hashmap
     * </p>
     * @throws IOException if the server is unable to read the data from the file
     */
    private void loadHashMap() throws IOException {

        //Initialises the hashmap
        faveVenueMap = new HashMap<>();

        //Creates a reader for the file
        BufferedReader br = new BufferedReader(new FileReader(faveVenueFile));
        String line;

        //Goes through every line in the file
        while((line = br.readLine()) != null){



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

    /**
     * <p>
     *     Returns the favourite venue list for a given user
     * </p>
     * @param username the users username
     * @return The list of venues as a string with a full stop between each venue name
     */
    public String faveVenueList(String username){

        //Checks to make sure the user is in there first
        if(faveVenueMap.containsKey(username)) {

            String[] faveVenueList = faveVenueMap.get(username);

            String faveVenueListString = "";

            //Turn the string array into one long string, so it's easier to send
            for (int i = 0; faveVenueList.length > i; i++) {
                faveVenueListString = faveVenueListString.concat(faveVenueList[i] + ".");
            }




            return faveVenueListString;
        }
        else{
            return null;
        }

    }


    /**
     * <p>
     *     Changes the name of a user in the favourite venue list to represent there new name
     * </p>
     * @param oldUsername the old username of the user
     * @param newUsername the new username of the user
     */
    public void nameChange(String oldUsername, String newUsername){

        if(faveVenueMap.containsKey(oldUsername)) {
            String[] faveVenueList = faveVenueMap.get(oldUsername);

            faveVenueMap.put(newUsername, faveVenueList);

            faveVenueMap.remove(oldUsername);

            saveHashMap();
        }

    }


    public File getFaveVenueFile() {
        return faveVenueFile;
    }

    public HashMap<String, String[]> getFaveVenueMap() {
        return faveVenueMap;
    }
}
