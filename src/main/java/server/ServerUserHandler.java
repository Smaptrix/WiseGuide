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
    public ServerUserHandler(User currUser) throws IOException {
        this.currUser = currUser;
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

            System.out.println(values[0]);

            if ((values[0]).equals(currUser.getUsername())) {
                userInfo = values;
                return true;
            }

        }

        return false;
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
            output.write(currUser.getUsername() + "," + currUser.getPassword() + "\n");
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


}
