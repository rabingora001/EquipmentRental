package edu.tacoma.uw.equipmentrental;

import edu.tacoma.uw.equipmentrental.authenticate.Account;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {

    @Test
    public void testAccountConstructor() {
        assertNotNull(new Account("rabin001@uw.edu", "test1@3"));
    }
    @Test
    public void testAccountConstructorBadEmail() {
        try {
            new Account("rabin001.edu", "testpwd1@");
            fail("Account created with invalid email");
        } catch(IllegalArgumentException e) {

        }
    }

}
