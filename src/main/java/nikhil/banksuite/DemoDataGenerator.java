package nikhil.banksuite;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

class DemoDataGenerator {
    public static ClientUI generateFakeClient() {
        ClientUI cl = new ClientUI("Client1", 100, 56000.0, "password");
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

    private static Date getRandomDate() {
        // https://www.fileformat.info/tip/java/date2millis.htm
        Date startDate = new Date(1104537600000l); //Saturday, 1 January 2005 00:00:00
        Date endDate = new Date(1569801600000l); //Monday, 30 September 2019 00:00:00 
        long random = ThreadLocalRandom.current().nextLong(startDate.getTime(), endDate.getTime());
        return new Date(random);
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
