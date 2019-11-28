package nikhil.banksuite;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Random;

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

    public static ObservableList<ClientUI> bots;
    public static int transactionCount;
    ArrayList<Integer> accountIds;

    Home() {
        bots = FXCollections.observableArrayList();
        accountIds = new ArrayList<Integer>();
        for (int i = 0; i<BOTCOUNT; i++) {
            String[] splitName = NAMES[i].split(" ");
            int acNo = getRandomNumber(0, 200);
            while (accountIds.contains(acNo)) acNo = getRandomNumber(0, 200);
            accountIds.add(acNo);
            ClientUI cl = new ClientUI(splitName[0].toLowerCase(), splitName[0], splitName[1], nextDate(accountCreation), 
                    acNo, getRandomNumber(5000, 50000), "password");
            bots.add(cl);
        }
        for (int i = 0; i<bots.size()-1; i++)  
            bots.get(i).setAccounts(accountIds);
        transactionCount = 0;
    }

    public int getBotId(int accountNumber) {
        return accountIds.indexOf(accountNumber);
    }

    protected Record nextTransaction() {

        int sender = getRandomNumber(0, BOTCOUNT-1);
        int receiver = getRandomNumber(0, BOTCOUNT-1);
        String remark;
        if (receiver == sender && receiver < (BOTCOUNT - 1)) receiver++; 
        else if (receiver == sender && receiver == BOTCOUNT) receiver--;
        
        TransactionType transactionType = generateRandomType();
        GregorianCalendar transactionDate = nextDate(accountCreation);
        double transactionAmount = (double)getRandomNumber(200,15000);

        Record senderRecord, receiverRecord;

        if (transactionType == TransactionType.Debit) {
            // sender = debit, receiver = credit
            remark = DEBITREMARKS[getRandomNumber(0, DEBITREMARKS.length-1)];
            if(transactionAmount>bots.get(sender).getBalance()) return null;
            bots.get(sender).removeBalance(transactionAmount);
            bots.get(receiver).addBalance(transactionAmount);
            senderRecord = new Record(transactionCount, transactionAmount, transactionDate, 
                TransactionType.Debit, bots.get(sender).getAccountNumber(), bots.get(receiver).getAccountNumber(), remark);
            receiverRecord = new Record(transactionCount++, transactionAmount, transactionDate, 
                TransactionType.Credit, bots.get(sender).getAccountNumber(), 
                bots.get(receiver).getAccountNumber(), remark);
            bots.get(sender).transactions.add(senderRecord);
            bots.get(receiver).transactions.add(receiverRecord);
        } else {
            remark = CREDITREMARKS[getRandomNumber(0, CREDITREMARKS.length-1)];
            if(transactionAmount>bots.get(receiver).getBalance()) return null;
            bots.get(sender).addBalance(transactionAmount);
            bots.get(receiver).removeBalance(transactionAmount);
            senderRecord = new Record(transactionCount, transactionAmount, transactionDate, 
                TransactionType.Credit, bots.get(sender).getAccountNumber(), bots.get(receiver).getAccountNumber(), remark);
            receiverRecord = new Record(transactionCount++, transactionAmount, transactionDate, 
                TransactionType.Debit, bots.get(sender).getAccountNumber(), 
                bots.get(receiver).getAccountNumber(), remark);
            bots.get(sender).transactions.add(receiverRecord);
            bots.get(receiver).transactions.add(senderRecord);
        }

        

        return senderRecord;
    }

    protected ObservableList<ClientUI> getBots() {
        return bots;
    }

    private static TransactionType generateRandomType() {
        TransactionType ts;
        int p = getRandomNumber(0,1);
        if (p==0) ts = TransactionType.Credit;
        else ts = TransactionType.Debit;
        return ts;
    }

    private static GregorianCalendar nextDate(long fromDate) {
        // https://www.fileformat.info/tip/java/date2millis.htm
        GregorianCalendar date = new GregorianCalendar(); 
        date.setTimeInMillis(fromDate);
        accountCreation += getRandomNumber(604800000, Integer.MAX_VALUE);
        return date;
    }

    protected static int getRandomNumber(int min, int max) {
        Random r = new Random();
        return r.nextInt(max-min+1)+min;
    }

    public ClientUI getBotAt(int index) { return bots.get(index); }
}