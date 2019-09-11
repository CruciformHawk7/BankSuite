package nikhil.banksuite;

import java.util.GregorianCalendar;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

class Home {

    private static int BOTCOUNT = 10;
    private static String[] NAMES = { "Margaret Waller", "Shyla Castillo", "Hazel Rivera", 
        "Aleah Mullins", "Kendra Page", "Deja Shah", "Zoie Bowen", "Benjamin Beasley", 
        "Leonel Wells", "Heaven Martin", "Nola Odom", "Gemma Gay" };

    ObservableList<ClientUI> bots;

    Home() {
        bots = FXCollections.observableArrayList();
        for (int i = 0; i<BOTCOUNT; i++) {
            String[] splitName = NAMES[i].split("\\s+");
            ClientUI cl = new ClientUI(splitName[0].toLowerCase(), splitName[0], splitName[1], getRandomDate(), 
                    getRandomNumber(0, 200), getRandomNumber(5000, 50000), "password");
            bots.add(cl);
        }
    }

    private static GregorianCalendar getRandomDate() {
        // https://www.fileformat.info/tip/java/date2millis.htm
        GregorianCalendar date = new GregorianCalendar(); 
        date.setTimeInMillis(ThreadLocalRandom.current().nextLong(788918400000l));
        //from 1 January 1970 00:00:00 to 1 January 1995 00:00:00 
        return date;
    }

    private static int getRandomNumber(int min, int max) {
        Random r = new Random();
        return r.nextInt(max-min+1)+min;
    }
}