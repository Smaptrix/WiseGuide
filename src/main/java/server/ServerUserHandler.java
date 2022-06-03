/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham
    Date Created:   24/02/2022
    Last Updated:   03/06/2022
 */

package server;

import ServerClientUtility.User;

import java.io.*;


/**
 * <p>
 *     Handles the communication between the server and the user database
 * </p>
 */
public class ServerUserHandler {


    /**
     * <p>
     *     The current type of user logged into the server
     *      They can either be USER (The default user, i.e. the customer) or VENUE (The owner of a venue)
     * </p>
     */
    private String userType;


    /**
     * <p>The relevant file storing the user data (user or venue)</p>
     */
    private static String dataFile;

    /**
     * <p>The current user actually logged into the server</p>
     */
    private User currUser;
    /**
     * <p>Whether a user exists or not - used primarily for checking if usernames are taken</p>
     */
    public boolean userExistState;
    /**
     * <p>Where the current user info is stored for quick access</p>
     *
     */
    private String[] userInfo;

    /**
     * <p> If the current password is correct or not</p>
     */
    public boolean passVerified;


    /**
     * <p>The constructor for a ServerUserHandler Object</p>
     * @param currUser The current user logged in (or attempting to login) to the server
     * @param autoVerify Whether or not to check the users details automatically
     * @throws IOException If the ServerUserHandler cannot access the database
     */
    public ServerUserHandler(User currUser, boolean autoVerify) throws IOException {
        this.currUser = currUser;

        if(autoVerify){
            verifyUser();
        }
    }


    /**
     * <p>Checks a users details (their username and entered password)</p>
     * @throws IOException
     */
    public void verifyUser() throws IOException {
        userExistState = findUser();

        //If the user doesn't exist there is no need to  verify the password
        if (userExistState) {
            passVerified = verifyPass();


        } else {
            passVerified = false;
        }
    }


    /**
     * <p>Checks the relevant database to find if a user exists or not</p>
     * @return True - If the user exists. False - Otherwise
     * @throws IOException If the Handler cannot access the relevant database
     */
    private boolean findUser() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(dataFile));
        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");


            if ((values[0]).equals(currUser.getUsername())) {
                userInfo = values;

                return true;
            }

        }

        return false;
    }

    /**
     * <p>Deletes the given user from the database</p>
     * @return True - if the deletion was successfull. False - Otherwise
     * @throws IOException If the Handler cannot access the database
     */
    public boolean deleteUser() throws IOException {

        File database = new File("userDatabase.txt");
        File tempFile = new File("tempDatabase.txt");

        BufferedReader br = new BufferedReader(new FileReader(database));
        BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
        String userToRemove = currUser.getUsername();
        String line;

        //Copies all lines from database into new file EXCEPT for user we wish to delete.
        while((line = br.readLine()) != null){
            String[] values = line.split(",");
            if(!(values[0].equals(userToRemove))){
                bw.write(line + System.getProperty("line.separator"));
            }
        }

        //Close buffers and run garbage collection (doesn't work if you don't do the gc! Java bug)
        bw.flush();
        bw.close();
        br.close();
        bw = null;
        br = null;
        System.gc();

        //Delete the old database.
        //Sometimes, the .delete() would fail for absolutely no specified reason. The files are 100% NOT open at this
        //point, so there's no reason for it to fail other than Java messing things up.
        //If one these random failures occurs, we'll just try again. Max # of tries = delIteratorMax variable.
        //NOTE: Adding this "try again" seems to have stopped the random errors occurring in the first place. I don't know why.
        boolean delSuccess = database.delete();
        int delIterator = 0;
        int delIteratorMax = 5;
        while(!delSuccess && (delIterator < delIteratorMax)){
            delIterator++;
            System.out.println("The database deletion was not successful. Trying again...");
            delSuccess = database.delete();
        }

        boolean renameSuccess = tempFile.renameTo(database);
        if(!renameSuccess) {
            System.out.println("The database rename was not successful."); //Debug
        }
        boolean success = !(findUser());
        if(!success) {
            System.out.println("The user is still in the database."); //Debug
        }

        return (success);
    }

    /**
     * <p>Finds if the given username is already taken</p>
     * @param username the username to be checked
     * @return True - If the username is taken, False - Otherwise
     * @throws IOException If the Handler cannot access the database
     */
    public static boolean findUserName(String username) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(dataFile));
        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");

            if ((values[0]).equals(username)) {

                return true;
            }

        }

        return false;
    }


    /**
     * <p>Changes the current users, to the given username</p>
     * @param desiredName the desired username
     * @throws IOException If the Handler cannot access the database
     */
    public void changeUserName(String desiredName) throws IOException {

        //Finds the line that we need to update
        BufferedReader br = new BufferedReader(new FileReader(dataFile));

        String line;
        String input = "";

        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");


            //If the current username matches the line, the line is replaced with the new data
            if ((values[0]).equals(currUser.getUsername())) {
                values[0] = desiredName;

                input += desiredName + "," + currUser.getPassword() + "," + currUser.getSalt() + '\n';
            }

            //If the lines don't match, the line is ignored
            else {
                input += line + '\n';
            }
        }


        currUser.setUsername(desiredName);

        FileOutputStream out = new FileOutputStream(dataFile);
        out.write(input.getBytes());
        out.close();

        System.out.println("File closed");

    }


    /**
     * <p>Changes the current users username</p>
     * @param desiredPass The new desired password
     * @throws IOException If the handler cannot access the database
     */
    public void changeUserPass (String desiredPass) throws IOException {

        //Finds the line that we need to update
        BufferedReader br = new BufferedReader(new FileReader(dataFile));
        String line;
        String input = "";

        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");


            //If the current username matches the line, the line is replaced with the new data
            if ((values[0]).equals(currUser.getUsername())) {
                values[0] = desiredPass;

                input += currUser.getUsername() + "," + desiredPass + "," + currUser.getSalt() + '\n';

            }

            //If the lines don't match, the line is ignored
            else {
                input += line + '\n';
            }
        }
        FileOutputStream out = new FileOutputStream(dataFile);
        out.write(input.getBytes());
        out.close();

    }


    /**
     * <p>Verifies the entered password for the user</p>
     * @return True - If the password is correct, False - Otherwise
     */
    public boolean verifyPass() {
        return (currUser.getPassword()).equals(userInfo[1]);
    }

    /**
     * <p>Creates a new user (from the already given user) and adds them to the database</p>
     * @throws IOException IF the handler cannot access the database
     */
    public void createUser() throws IOException {
        if (userExistState) {
            System.out.println("This user already exists");
        } else {
            System.out.println("Creating a new user...");
            Writer output = new BufferedWriter(new FileWriter(dataFile, true));
            //This may need to be adapted depending on the kind of user info we want to hold
            output.write(currUser.getUsername() + "," + currUser.getPassword() + "," +  currUser.getSalt() + "\n");
            output.close();
            userExistState = findUser();
            passVerified = verifyPass();
        }

    }

    @Override
    /**
     * <p>To String function, prints the username, if the user exists, and if the entered password is correct</p>
     */
    public String toString() {
        return "ServerUser{" +
                "currUser=" + currUser +
                ", userExistState=" + userExistState +
                ", passVerified=" + passVerified +
                '}';
    }


    /**
     * <p>
     *     Clears the current user information from the handler
     *     Should only done after a logout
     * </p>
     */
    public void clear(){
        currUser = null;
        userExistState = false;
        userInfo = null;
        passVerified = false;
    }

    /**
     * <p>Sets the current user in the handler</p>
     * @param currUser
     */
    public void setCurrUser(User currUser) {
        this.currUser = currUser;


    }

    /**
     * <p>Gets the users password salt</p>
     * @return the salt
     */
    public String getcurrUserSalt() {

        return userInfo[2];

    }

    /**
     * <p>Gets the current user</p>
     * @return the current user
     */
    public User getcurrUser() {

        return currUser;
    }

    /**
     * <p>Gets the current users type</p>
     * @return the user type
     */
    public String getUserType() {
        return userType;
    }

    /**
     * <p>Sets the current users type</p>
     * @param userType VENUE or USER
     */
    public void setUserType(String userType) {

        if (userType.equals("USER")){
            dataFile = "userDatabase.txt";
        }
        else if (userType.equals("VENUE")){
            dataFile = "venueDatabase.txt";
        }

        System.out.println("This login is of type: " + userType);


        this.userType = userType;
    }
}
