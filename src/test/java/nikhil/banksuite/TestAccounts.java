package nikhil.banksuite;

import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.ObservableList;

public class TestAccounts {
    HomeUI home;
    ObservableList<Record> records;

    @Before
    public void setUp() {
        home = new HomeUI();
        records = home.doTransactions();
    }

    @Test 
    public void testNegatives() {
        for(var bot: home.getBots()) {
            assertFalse("Account negative", bot.getBalance()<0);
            if (bot.getBalance()<0) break;
        }
    }
}