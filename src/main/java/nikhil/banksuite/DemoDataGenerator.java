package nikhil.banksuite;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

class DemoDataGenerator {
    public static Client generateFakeClient() {
        Client cl = new Client("Client1", 100, 56000, "password");
        for (int i = 0; i< (Math.random()%20)+5; i++)
            cl.transactions.add(new Record(getRandomNumber(50), getRandomNumber(50000), getRandomDate(), generateRandomType(),
                                getRandomNumber(50), getRandomNumber(50), "Test"));
        return cl;
    }

    private static TransactionType generateRandomType() {
        TransactionType ts;
        int p = getRandomNumber(1);
        if (p==0) ts = TransactionType.Credit;
        else ts = TransactionType.Debit;
        return ts;
    }

    private static int getRandomNumber(int bound) {
        Random r = new Random();
        return r.nextInt(bound);
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
}
