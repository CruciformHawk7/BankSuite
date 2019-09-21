package nikhil.banksuite;

import java.util.GregorianCalendar;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

class Home {
    // Number of bots that will be using the transaction
    private static final int BOTCOUNT = 10;
    // Random Names for these bots
    private static final String[] NAMES = { "Margaret Waller", "Shyla Castillo", "Hazel Rivera", 
        "Aleah Mullins", "Kendra Page", "Deja Shah", "Zoie Bowen", "Benjamin Beasley", 
        "Leonel Wells", "Heaven Martin", "Nola Odom", "Gemma Grey" };

    // Various payment methods
    private static final String[] DEBITREMARKS = { "VIA DEBIT CARD", "VIA CREDIT CARD", 
        "VIA NEFT", "VIA CHEQUE", "VIA PHONE", "VIA IMPS", "VIA RTGS", "VIA BANK TRANSFER"};

    private static final String[] CREDITREMARKS = { "VIA NEFT", "VIA CHEQUE", "VIA PHONE", 
        "VIA IMPS", "VIA RTGS", "VIA BANK TRANSFER"};

    private static long accountCreation = 788918400000l;
    //Sunday, 1 January 1995 00:00

    private ObservableList<ClientUI> bots;
    public int transactionCount;

    Home() {
        bots = FXCollections.observableArrayList();
        for (int i = 0; i<BOTCOUNT; i++) {
            String[] splitName = NAMES[i].split("\\s+");
            ClientUI cl = new ClientUI(splitName[0].toLowerCase(), splitName[0], splitName[1], nextDate(accountCreation), 
                    getRandomNumber(0, 200), getRandomNumber(5000, 50000), "password");
            bots.add(cl);
        }
        transactionCount = 0;
    }

    protected Record nextTransaction() {

        int sender = getRandomNumber(0, BOTCOUNT-1);
        int reciever = getRandomNumber(0, BOTCOUNT-1);
        String remark;
        if (reciever == sender && reciever < (BOTCOUNT - 1)) reciever++; 
        else if (reciever == sender && reciever == BOTCOUNT) reciever--;
        //System.err.println(reciever);
        
        TransactionType transactionType = generateRandomType();
        GregorianCalendar transactionDate = nextDate(accountCreation);
        double transactionAmount = (double)getRandomNumber(200,15000);

        if (transactionType == TransactionType.Debit) {
            remark = DEBITREMARKS[getRandomNumber(0, DEBITREMARKS.length-1)];
            if(transactionAmount<bots.get(sender).getBalance()) return null;
            bots.get(sender).removeBalance(transactionAmount);
            bots.get(reciever).addBalance(transactionAmount);
        } else {
            remark = CREDITREMARKS[getRandomNumber(0, CREDITREMARKS.length-1)];
            bots.get(sender).addBalance(transactionAmount);
            bots.get(reciever).removeBalance(transactionAmount);
        }

        Record senderRecord = new Record(transactionCount, transactionAmount, transactionDate, 
            transactionType, sender, reciever, remark);
        Record recieverRecord = new Record(transactionCount++, transactionAmount, transactionDate, 
            toggleTransactionType(transactionType), sender, reciever, remark);

        bots.get(sender).transactions.add(senderRecord);
        bots.get(reciever).transactions.add(recieverRecord);

        return senderRecord;
    }

    private static TransactionType generateRandomType() {
        TransactionType ts;
        int p = getRandomNumber(0,1);
        if (p==0) ts = TransactionType.Credit;
        else ts = TransactionType.Debit;
        return ts;
    }

    private static TransactionType toggleTransactionType(TransactionType transactionType) {
        if (transactionType == TransactionType.Credit) return TransactionType.Debit;
        else return TransactionType.Credit;
    }

    private static GregorianCalendar nextDate(long fromDate) {
        // https://www.fileformat.info/tip/java/date2millis.htm
        GregorianCalendar date = new GregorianCalendar(); 
        date.setTimeInMillis(fromDate);
        accountCreation += getRandomNumber(604800000, Integer.MAX_VALUE);
        return date;
    }

    private static int getRandomNumber(int min, int max) {
        Random r = new Random();
        return r.nextInt(max-min+1)+min;
    }

    public ClientUI getBotAt(int index) { return bots.get(index); }
}