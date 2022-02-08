package server;

import serverclientstuff.User;

public class ServerUser {

    private User currUser;
    public boolean userExistState;
    public boolean passVerified;


    public ServerUser(User currUser){
        this.currUser = currUser;
        userExistState = findUser();
        passVerified = verifyPass();
    }


    //TODO - PARSE userDATA file and find/verify users
    //TODO - Add new users

    //Checks to see if a user exists
    private boolean findUser(){

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
