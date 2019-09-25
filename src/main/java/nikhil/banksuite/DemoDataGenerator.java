package nikhil.banksuite;

import java.io.FileWriter;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Random;

class DemoDataGenerator {
    public static ClientUI generateFakeClient() {
        GregorianCalendar g = new GregorianCalendar();
        g.setTimeInMillis(1104537600000l);
        ClientUI cl = new ClientUI("Client1", "Client", "One", g, 100, 56000.0, "password");
        for (int i = 0; i< (Math.random()%20)+5; i++)
            cl.transactions.add(new Record(getRandomNumber(0, 50), getRandomNumber(0, 50000), randomGDate(), generateRandomType(),
                               (1400000 + getRandomNumber(0, 50)), getRandomNumber(0, 50), "Test"));
        return cl;
    }

    private static TransactionType generateRandomType() {
        TransactionType ts;
        int p = getRandomNumber(0,1);
        if (p==0) ts = TransactionType.Credit;
        else ts = TransactionType.Debit;
        return ts;
    }

    private static int getRandomNumber(int min, int max) {
        Random r = new Random();
        return r.nextInt(max-min+1)+min;
    }

    public static void writeToFile(String path) throws IOException{
        Client c = generateFakeClient();
        FileWriter f = new FileWriter(path);
        f.write(c.toString());
        f.close();
    }

    private static GregorianCalendar randomGDate() {
        GregorianCalendar gc = new GregorianCalendar();
        int year = getRandomNumber(2005, 2019);
        int month = getRandomNumber(1, 12);
        int date = getRandomNumber(1, 28);
        int hour = getRandomNumber(0, 23);
        int minute = getRandomNumber(1, 60);
        int seconds = getRandomNumber(1, 60);
        gc.set(year, month, date, hour, minute, seconds);
        return gc;
    }
}
