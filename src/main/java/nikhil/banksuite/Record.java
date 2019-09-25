package nikhil.banksuite;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class holds a DataModel for the Records. 
 * Record(int transactionID, double transactionAmount, Date transactionDate,
 *        boolean isCredit, int fromID, int toID, String remark)
 */

public class Record {
    private final SimpleIntegerProperty transactionID;
    private final SimpleDoubleProperty transactionAmount;
    private final SimpleObjectProperty<GregorianCalendar> transactionDate;
    private final SimpleObjectProperty<TransactionType> transactionType;
    private final SimpleIntegerProperty fromID;
    private final SimpleIntegerProperty toID;
    private final SimpleStringProperty remark;


    public Record(int transactionID, double transactionAmount, GregorianCalendar transactionDate, TransactionType transactionType,
                  int fromID, int toID, String remark) {
        this.transactionID = new SimpleIntegerProperty(transactionID);
        this.transactionAmount = new SimpleDoubleProperty(transactionAmount);
        this.transactionDate = new SimpleObjectProperty<GregorianCalendar>(transactionDate);
        this.fromID = new SimpleIntegerProperty(fromID);
        this.toID = new SimpleIntegerProperty(toID);
        this.remark = new SimpleStringProperty(remark);
        this.transactionType = new SimpleObjectProperty<TransactionType>(transactionType);
    }

    public Record(Record senderRecord) {
        this.transactionID = new SimpleIntegerProperty(senderRecord.getTransactionID());
        this.transactionAmount = new SimpleDoubleProperty(senderRecord.getTransactionAmount());
        this.transactionDate = new SimpleObjectProperty<GregorianCalendar>(senderRecord.getDate());
        this.fromID = new SimpleIntegerProperty(senderRecord.getFromID());
        this.toID = new SimpleIntegerProperty(senderRecord.getToID());
        this.remark = new SimpleStringProperty(senderRecord.getRemark());
        this.transactionType = new SimpleObjectProperty<TransactionType>(senderRecord.getTransactionType());
	}

	public int getTransactionID() {
        return transactionID.get();
    }

    public void setTransactionID(int transactionID) {
        this.transactionID.set(transactionID);
    }

    public IntegerProperty transactionIDProperty() {
        return transactionID;
    }

    public double getTransactionAmount() {
        return transactionAmount.get();
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount.set(transactionAmount);
    }

    public DoubleProperty transactionAmountProperty() {
        return transactionAmount;
    }

    public String getTransactionDate() {
        SimpleDateFormat date = new SimpleDateFormat("EEEE, dd MMM YYYY HH:mm:ss z");
        return date.format(this.transactionDate.get().getTime());
    }

    public GregorianCalendar getDate() {
        return this.transactionDate.get();
    }

    public void setTransactionDate(GregorianCalendar date) {
        this.transactionDate.set(date);
    }

    public ObjectProperty<GregorianCalendar> transactionDateProperty() {
        return this.transactionDate;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType.set(transactionType);
    }

    public TransactionType getTransactionType() {
        return this.transactionType.get();
    }

    public ObjectProperty<TransactionType> transactionTypeProperty() {
        return transactionType;
    }

    public int getFromID() {
        return this.fromID.get();
    }

    public void setFromID(int fromID) {
        this.fromID.set(fromID);
    }

    public IntegerProperty fromIDProperty() {
        return fromID;
    }

    public int getToID() {
        return this.toID.get();
    }

    public void setToID(int toID) {
        this.toID.set(toID);
    }

    public IntegerProperty toIDProperty() {
        return toID;
    }

    public String getRemark() {
        return remark.get();
    }

    public void setRemark(String remark) {
        this.remark.set(remark);
    }

    public StringProperty remarkProperty() { 
        return this.remark; 
    }
}
