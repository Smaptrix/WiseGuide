package server;

import serverclientstuff.User;

import java.io.*;






public class ServerUserHandler {

    private User currUser;
    public boolean userExistState;
    private String[] userInfo;
    public boolean passVerified;


    //TODO - Delete User?


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
        BufferedReader br = new BufferedReader(new FileReader("userDatabase.txt"));
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


    //Note - no refactor to use this because this is a serverside package only
    //Determines if a provided username is already taken
    public static boolean findUserName(String username) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("userDatabase.txt"));
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
        BufferedReader br = new BufferedReader(new FileReader("userDatabase.txt"));
        String line;
        String input = "";

        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");


            //If the current username matches the line, the line is replaced with the new data
            if ((values[0]).equals(currUser.getUsername())) {
                values[0] = desiredName;

                input += desiredName + "," + currUser.getPassword() + "," + currUser.getSalt() + '\n';

                System.out.println("CHANGED LINE: " + input);

            }

            //If the lines dont match, the line is ignored
            else {

                input += line + '\n';

            }
        }

            FileOutputStream out = new FileOutputStream("userDatabase.txt");
            out.write(input.getBytes());
            out.close();

            System.out.println("File closed");




        }











    //Checks to see if the users password is correct
    public boolean verifyPass() {

        return (currUser.getPassword()).equals(userInfo[1]);
    }

    public void createUser() throws IOException {
        if (userExistState) {
            System.out.println("This user already exists");
        } else {
            System.out.println("Creating a new user...");
            Writer output = new BufferedWriter(new FileWriter("userDatabase.txt", true));
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

    public void setCurrUser(User currUser) throws IOException {
        this.currUser = currUser;


    }

    public String getcurrUserSalt() {

        return userInfo[2];

    }
}
