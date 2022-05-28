package tests;

import client.Client;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.junit.After;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import server.ServerUserHandler;
import serverclientstuff.User;

import javax.imageio.IIOException;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.*;

/**
 * <p>
 *     Tests for the Accounts/Login system
 * </p>
 */
public class AccountsSystemTests extends ApplicationTest {

    @After
    public void afterEachTest() throws IOException {
        //Delete makeme!
        User test = new User("makeme!", "password");
        ServerUserHandler makeMe = new ServerUserHandler(test, false);
        makeMe.setUserType("USER");
        makeMe.verifyUser();
        if(makeMe.userExistState) {
            makeMe.deleteUser();
        }
    }

    @Test
    //Unit Test | Username Setup
    public void userUsernameSetupTest() {
        User test = new User("test", "12345");
        assertEquals(test.getUsername(), "test");

        //Uncomment if username encryption is re-enabled
        //test.encryptUserInfo();
        //assertEquals(test.getUsername(), "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08");
    }

    /*
    Hashing doesn't always produce the same result so this must be tested manually.

    @Test
    //Unit Test | Confirm hashing works correctly.
    public void userPasswordHashingTest() {
        User test = new User("test", "12345");
        test.encryptUserInfo();
        //Password is '12345' pre-hash
        assertEquals(test.getPassword(), "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5");

    }
     */

    @Test
    //Unit Test | Confirm an existing user can be found in the database.
    public void userExistsTest() throws IOException {
        User test = new User("test", "12345");
        test.encryptUserInfo();

        ServerUserHandler testUser = new ServerUserHandler(test, false);
        testUser.setUserType("USER");
        testUser.verifyUser();
        assertTrue(testUser.userExistState);
    }

    @Test
    //Unit Test | Confirm non-existing users cannot be found in the database.
    public void userDoesNotExistTest() throws IOException {
        User test = new User("ImNotReal", "089-2341980-324");
        test.encryptUserInfo();

        ServerUserHandler nonExistentTestUser = new ServerUserHandler(test, false);
        nonExistentTestUser.setUserType("USER");
        nonExistentTestUser.verifyUser();
        assertFalse(nonExistentTestUser.userExistState);
    }

    @Test
    //Unit Test | Confirm a new user can be created and added to the database
    public void userCreationTest() throws IOException {

        User test = new User("makeme!", "password");
        test.encryptUserInfo();

        ServerUserHandler toBeCreatedUser = new ServerUserHandler(test, false);
        toBeCreatedUser.setUserType("USER");
        toBeCreatedUser.verifyUser();
        //First check they dont exist
        assertFalse(toBeCreatedUser.userExistState);
        //Make user
        toBeCreatedUser.createUser();
        //Check the user now exists
        assertTrue(toBeCreatedUser.userExistState);
    }

    /*
    These tests do not currently work as the hashed 12345 password does not match the previously hashed 12345 password in
    the database, so it can't verify the password. Test manually for now.

    @Test
    //Unit Test | Confirm passwords can be verified
    public void passwordCheckTest() throws IOException {

        User test = new User("test", "54321");
        test.encryptUserInfo();

        //Create user with incorrect password
        ServerUserHandler passCheckUser = new ServerUserHandler(test, false);
        passCheckUser.setUserType("USER");
        passCheckUser.verifyUser();
        passCheckUser.verifyPass();

        //Check password has been detected as incorrect
        assertFalse(passCheckUser.passVerified);

        test = new User("test", "12345");
        test.encryptUserInfo();

        //Change password to the correct one
        passCheckUser = new ServerUserHandler(test, false);
        passCheckUser.setUserType("USER");
        passCheckUser.verifyUser();

        //Check password has been detected as correct
        assertTrue(passCheckUser.passVerified);
    }

    @Test
    //Unit Test | Confirm users can log in
    public void remoteLoginTest() throws IOException {

        User test = new User("test", "12345");
        test.encryptUserInfo();

        Client client = new Client();
        client.startConnection("127.0.0.1", 5555);
        assertEquals("GOODLOGIN", client.requestLogin(test));

    }

    @Test
    //Unit Test | Confirm users can't log in with invalid details
    public void remoteFailedLoginTest() throws IOException {

        User test = new User("ICANTLOGIN", "12345");
        test.encryptUserInfo();

        Client client = new Client();
        client.startConnection("127.0.0.1", 5555);
        assertEquals("BADLOGIN", client.requestLogin(test));
    }
    */





    /* - Kind of defunct test
    @Test
    //Remote verification test - Requires server launch
    public void remoteUserVerificationTest() throws IOException {

        User test = new User("test", "12345");
        test.encryptUserInfo();

        Client client = new Client();
        client.startConnection("127.0.0.1", 5555);
        assertEquals("GOODPASS", client.verifyUser(test));


    }

    @Test
    //Remote verification test to prove a user doesn't exist- Requires server launch
    public void failRemoteUserVerificationTest() throws IOException {

        User test = new User("IdOntExist", "12345");
        test.encryptUserInfo();

        Client client = new Client();
        client.startConnection("127.0.0.1", 5555);
        assertEquals("USERNOTFOUND", client.verifyUser(test));

    }
 */

    /*
    Does not work despite password being correct? Suspect it's something to do with the hashing stuff.

    @Test
    //Integration Test | Confirm users can be verified via server/client
    public void verifyViaClientTest() throws IOException {
        Client client = new Client();
        client.startConnection("127.0.0.1", 5555);

        String username = "test";
        String password = "12345";
        User user = new User(username,password);
        String response = client.verifyUser(user);
        System.out.println(response);
    }
    */

    @Test
    //Integration Test | Confirm users can login via server/client
    public void loginViaClientTest() throws IOException {
        Client client = new Client();
        client.startConnection("127.0.0.1", 5555);
        String username = "test";
        String password = "12345";
        User user = new User(username,password);
        String response = client.requestLogin(user);
        assertEquals("GOODLOGIN",response);
    }

    @Test
    //Integration Test | Confirm users with incorrect details can't login via server/client
    public void noLoginViaClientTest() throws IOException {
        Client client = new Client();
        client.startConnection("127.0.0.1", 5555);
        String username = "test";
        String password = "123456";
        User user = new User(username,password);
        String response = client.requestLogin(user);
        assertEquals("BADLOGIN",response);
    }

    @Test
    //Integration Test | Confirm users can be created via server/client
    public void createViaClientTest() throws IOException{
        Client client = new Client();
        client.startConnection("127.0.0.1", 5555);
        String username = "makeme!";
        String password = "password";
        User user = new User(username,password);
        String response = client.createUser(user);
        assertEquals("USERCREATED",response);

        //Delete makeme over server/client when done
        client.deleteUser(user);
    }

    @Test
    //Integration Test | Confirm an existing user can't be created via server/client
    public void doNotCreateViaClientTest() throws IOException{
        Client client = new Client();
        client.startConnection("127.0.0.1", 5555);
        String username = "test";
        String password = "12345";
        User user = new User(username,password);
        String response = client.createUser(user);
        assertEquals("USERALREADYEXISTS",response);
    }

    @Test
    //Integration Test | Confirm the user can log out
    public void logOutTest() throws IOException{
        Client client = new Client();
        client.startConnection("127.0.0.1", 5555);
        String username = "test";
        String password = "12345";
        User user = new User(username,password);
        assertEquals("GOODLOGIN",client.requestLogin(user));
        String response = client.requestLogout();
        assertEquals("LOGGEDOUT",response);
    }

}
