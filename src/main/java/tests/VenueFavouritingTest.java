package tests;


import org.junit.Assert;
import org.junit.Test;
import server.FaveVenuesHandler;

import java.io.File;
import java.util.Arrays;

/**
 * <p>
 *     Tests for the venue favouring system.
 * </p>
 */
public class VenueFavouritingTest{


    @Test
    //Verifies that the list is loaded on initial creation
    public void loadVenueFavesTest(){

        FaveVenuesHandler test = new FaveVenuesHandler(new File("faveVenues.txt"));

        Assert.assertTrue(test.getFaveVenueMap() != null);
    }


    @Test
    //Verifies that you can add and then delete new user to the favourite venues list
    public void addAndDeleteNewUserTest(){

        FaveVenuesHandler test = new FaveVenuesHandler(new File("faveVenues.txt"));

        String username = "0987654321";

        test.addUser(username);

        Assert.assertTrue(test.getFaveVenueMap().containsKey(username));

        test.removeUser(username);

        Assert.assertFalse(test.getFaveVenueMap().containsKey(username));
    }


    @Test
    public void addAndDeleteFaveVenuesTest(){

        FaveVenuesHandler test = new FaveVenuesHandler(new File("faveVenues.txt"));

        String username = "0987654321";

        String venue = "TEST";

        String[] testList = {"TEST"};

        test.addFaveVenue(username, venue);

        Assert.assertTrue(test.getFaveVenueMap().containsKey(username));
        Assert.assertTrue(Arrays.equals(test.getFaveVenueMap().get(username), testList));



        test.removeFaveVenue(username, venue);

        Assert.assertTrue(test.getFaveVenueMap().containsKey(username));
        Assert.assertFalse(Arrays.equals(test.getFaveVenueMap().get(username), testList));

        test.removeUser(username);


    }







}
