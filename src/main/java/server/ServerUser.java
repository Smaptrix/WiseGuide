package server;

import serverclientstuff.User;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServerUser {

    private User currUser;
    public boolean userExistState;
    private String[] userInfo;
    public boolean passVerified;


    public ServerUser(User currUser) throws IOException {
        this.currUser = currUser;
        userExistState = findUser();
        passVerified = verifyPass();
    }


    //TODO - PARSE userDATA file and find/verify users
    //TODO - Add new users to userData

    //Checks to see if a user exists
    private boolean findUser() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("userDatabase.txt"));
        String line;
        while((line = br.readLine()) != null) {
            String[] values = line.split(",");
                if((values[0]).equals(currUser.getUsername())){
                    userInfo = values;
                    System.out.println("User: "+ userInfo[0] +" exists!");
                    return true;
                }

        }




        return false;
    }

    //Checks to see if the users password is correct
    public boolean verifyPass(){


        return false;
    }


    public void setCurrUser(User currUser) {
        this.currUser = currUser;
    }
}
