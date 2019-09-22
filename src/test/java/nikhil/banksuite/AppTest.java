package nikhil.banksuite;

import static org.junit.Assert.assertTrue;

import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    ClientUI client;

    @Before 
    public void setUp() throws Exception {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(1575909755000l);
        client = new ClientUI("name", "firstName", "lastName", gc, 001, 50000.00, "password");
    }

    @Test public void testName() {
        assertTrue("Name is name", client.getName() == "name");
    }

    @Test public void testFirstLastName() {
        assertTrue("Firstname", client.getFirstName() == "firstName");
        assertTrue("LastName", client.getLastName() == "lastName");
    }

    @Test public void testAccountNumber() {
        assertTrue("Account Number", client.getAccountNumber() == 1);
    }

    @Test public void testBalance() {
        assertTrue("Balance", client.getBalance() == 50000.00);
    }

    @Test public void testBalanceAdd() {
        client.addBalance(2000);
        assertTrue("Balance", client.getBalance() == 52000.00);
    }

    @Test public void testBalanceSubtract() {
        client.removeBalance(100);
        assertTrue("Balance", client.getBalance() == 49900.00);
    }

    @Test public void testBoth() {
        client.addBalance(2000);
        client.removeBalance(200);
        assertTrue("Balance", client.getBalance() == 51800.00);
    }

    @Test public void testLogin() {
        assertTrue("Password", client.verifyPassword("password"));
    }
}

