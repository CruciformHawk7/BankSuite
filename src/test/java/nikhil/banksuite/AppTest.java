package nikhil.banksuite;

import static org.junit.Assert.assertTrue;

import java.util.GregorianCalendar;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    ClientUI client;

    protected void setUp() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(1575909755000l);
        client = new ClientUI("name", "firstName", "lastName", gc, 001, 50000.00, "password");
    }

    @Test
    public void testClientGenerate() {
        assertTrue("Name is name", client.getName() == "name");
        assertTrue("Firstname", client.getFirstName() == "firstName");
        assertTrue("LastName", client.getLastName() == "lastName");
        assertTrue("Account Number", client.getAccountNumber() == 1);
        assertTrue("Balance", client.getBalance() == 50000.00);
        assertTrue("Password", client.verifyPassword("password"));
    }
}
