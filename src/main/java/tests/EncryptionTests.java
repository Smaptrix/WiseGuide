package tests;

import org.junit.Assert;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import serverclientstuff.User;
import serverclientstuff.UserSecurity;

import static org.junit.Assert.assertEquals;

public class EncryptionTests extends ApplicationTest {

    @Test
    //Unit Test | Confirm password hashing works.
    public void hashPasswordTest(){
        User user = new User("test","12345");
        user.setSalt("fb58b47c3b3f759080cff1a2883f1f97");
        UserSecurity userSecurity = new UserSecurity(user);
        Assert.assertEquals("180614082906af58f873bb44e200db520e3c860ad47ac24b13574ef3a7bb1876",userSecurity.hashPassword());
    }

    @Test
    //Integration Test | Confirm user information encryption works correctly.
    public void userPasswordHashingTest() {
        User test = new User("test", "12345");
        test.setSalt("fb58b47c3b3f759080cff1a2883f1f97");
        test.encryptUserInfo();
        //Password is '12345' pre-hash
        assertEquals(test.getPassword(), "180614082906af58f873bb44e200db520e3c860ad47ac24b13574ef3a7bb1876");
    }

    @Test
    //Unit Test | Confirm a salt is generated
    //Done by verifying a 32-character string is produced.
    public void saltCheckTest(){
        User user = new User("test","12345");
        UserSecurity userSecurity = new UserSecurity(user);
        String salt = UserSecurity.generateSalt();
        Assert.assertEquals(salt.length(),32);
    }

    @Test
    //Unit Test | Confirm salts are unique
    public void uniqueSaltTest(){
        User user = new User("test","12345");
        UserSecurity userSecurity = new UserSecurity(user);
        String salt1 = UserSecurity.generateSalt();
        String salt2 = UserSecurity.generateSalt();
        Assert.assertNotEquals(salt1,salt2);
    }

    @Test
    //Unit Test | Confirm hashing with a known salt works correctly.
    public void hashTest(){
        String dummySalt = "00000000000000000000000000000000";
        String result = UserSecurity.hashThis("test",dummySalt);
        Assert.assertEquals("49be2df48ddb17a0968d0dbb0f30b08efbfe9c537ca679c747b579450b56b36e",result);
    }

    //TODO: Tests for server/client message encryption and key generation?

}
