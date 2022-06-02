package server;

import serverclientstuff.User;

import java.io.*;






public class ServerUserHandler {


    //Will either be USER or VENUE
    private String userType;


    //Will be decided upon setting the user type
    private static String dataFile;

    private User currUser;
    public boolean userExistState;
    private String[] userInfo;
    public boolean passVerified;

    private int delIteratorMax = 5;


    //Creates the user serverside
    public ServerUserHandler(User currUser, boolean autoVerify) throws IOException {
        this.currUser = currUser;

        if(autoVerify){
            verifyUser();
        }
    }


    public void verifyUser() throws IOException {
        userExistState = findUser();

        //If the user doesn't exist there is no need to  verify the password
        if (userExistState) {
            passVerified = verifyPass();


        } else {
            passVerified = false;
        }
    }


    //Checks to see if a user exists
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

    //Note - no refactor to use this because this is a serverside package only
    //Determines if a provided username is already taken
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


    //Changes the current username
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






    //Changes the current username
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








    //Checks to see if the users' password is correct
    public boolean verifyPass() {
        return (currUser.getPassword()).equals(userInfo[1]);
    }

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
    public String toString() {
        return "ServerUser{" +
                "currUser=" + currUser +
                ", userExistState=" + userExistState +
                ", passVerified=" + passVerified +
                '}';
    }


    public void clear(){
        currUser = null;
        userExistState = false;
        userInfo = null;
        passVerified = false;
    }

    public void setCurrUser(User currUser) {
        this.currUser = currUser;


    }

    public String getcurrUserSalt() {

        return userInfo[2];

    }

    public User getcurrUser() {

        return currUser;
    }

    public String getUserType() {
        return userType;
    }

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
