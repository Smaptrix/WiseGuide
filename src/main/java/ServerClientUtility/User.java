/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Joe Ingham
    Date Created:   08/02/2022
    Last Updated:   04/06/2022
 */

package ServerClientUtility;


/**
 * <p>The actual user of the application</p>
 */
public class User {

    /**
     * <p>The users identifying and unique name</p>
     */
    private String username;
    /**
     * <p>The users secret password to verify that it's them</p>
     */
    private String password;

    /**
     * <p>The salt that is unique to the user and added to their password to increase security</p>
     */
    private String salt;

    /**
     * The users list of favourite venues
     */
    private String[] faveVenues;

    /**
     * <p> The user constructor</p>
     * @param username The username of the user
     * @param password the password of the user
     */
    public User(String username, String password)  {

        this.username = username;
        this.password = password;


    }

    /**
     * <p>Encrypts the users password</p>
     */
    public void encryptUserInfo()  {

        UserSecurity userSecurity =  new UserSecurity(this);

        this.password = userSecurity.hashPassword();
    }

    /**
     * @return the users username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @return the users password
     */
    public String getPassword() {
        return password;
    }

    /**
     * <p>Sets the username of the current user object</p>
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * <p>Sets the password of the current user object</p>
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * <p>Sets the salt of the current user object</p>
     * @param salt the salt
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     *
     * @return the current users salt
     */
    public String getSalt(){
        return salt;
    }

    /**
     * <p>Sets the list of favourite venues for the user</p>
     * @param faveVenues the list of favourite venues
     */
    public void setFaveVenues(String[] faveVenues) {
        this.faveVenues = faveVenues;
    }

    /**
     *
     * @return the users favourite venue list
     */
    public String[] getFaveVenues() {
        return faveVenues;
    }

    /**
     * <p>The to string function, it prints the username and password of the user</p>
     * @return the to string data
     */
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", encodedPass='" + password + '\'' +
                '}';
    }


    /**
     * Clears the current username and password of the user
     */
    public void clear(){
        username = "";
        password = "";
        salt = "";

    }
}