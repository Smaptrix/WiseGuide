package server;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


//Controls the hashmap textfile which contains every users favourite venues
public class FaveVenuesHandler {

    File faveVenueFile;
    HashMap<String, String[]> faveVenueMap;

    //TODO - Comment it up


    public FaveVenuesHandler(File faveVenueFile){
        this.faveVenueFile = faveVenueFile;
        try {
            loadHashMap();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(faveVenueMap);



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

    public void removeFaveVenue(String username, String venueName){


        saveHashMap();

    }

    public void addUser(String username){

        //Makes sure that the user isn't already in there
        if(!faveVenueMap.containsKey(username)) {
            faveVenueMap.put(username, new String[0]);
            saveHashMap();
        }
    }

    public void removeUser(String username){

        //Makes sure that the user is in the list before trying to delete them
        if(faveVenueMap.containsKey(username)) {
            faveVenueMap.remove(username);

            saveHashMap();
        }
    }


    private void saveHashMap(){

        try {
            FileWriter hashMapFileWriter = new FileWriter(faveVenueFile);


            for(Map.Entry<String, String[]> entry : faveVenueMap.entrySet()){
                hashMapFileWriter.write(entry.getKey() + ",");

                for(int i = 0; entry.getValue().length > i; i++){
                    hashMapFileWriter.write( entry.getValue()[i] + ".");
                }
                hashMapFileWriter.write("\n");
            }


            hashMapFileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }





    }

    private void loadHashMap() throws IOException {

        faveVenueMap = new HashMap<>();

        BufferedReader br = new BufferedReader(new FileReader(faveVenueFile));
        String line;

        //Places every user and there favourite venues into the hashmap
        while((line = br.readLine()) != null){

            System.out.println(line);

            String[] userVenuesPair = line.split(",");


            if(userVenuesPair.length > 1) {
                String[] venueArray = userVenuesPair[1].split("\\.");

                faveVenueMap.put(userVenuesPair[0], venueArray);
            }


        }

        System.out.println("Fave venues loaded!");

    }



}
