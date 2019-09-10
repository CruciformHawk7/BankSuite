package nikhil.banksuite;

import java.time.LocalDate;
import java.util.Date;

import javafx.beans.property.*;

/**
 * This class holds a DataModel for the Records. 
 * Record(int transactionID, double transactionAmount, Date transactionDate,
 *        boolean isCredit, int fromID, int toID, String remark)
 */

public class Record {
    private final SimpleIntegerProperty transactionID;
    private final SimpleDoubleProperty transactionAmount;
    private final SimpleObjectProperty<LocalDate> transactionDate;
    private final SimpleObjectProperty<TransactionType> transactionType;
    private final SimpleIntegerProperty fromID;
    private final SimpleIntegerProperty toID;
    private final SimpleStringProperty remark;


    public Record(int transactionID, double transactionAmount, LocalDate transactionDate, TransactionType transactionType,
                  int fromID, int toID, String remark) {
        this.transactionID = new SimpleIntegerProperty(transactionID);
        this.transactionAmount = new SimpleDoubleProperty(transactionAmount);
        this.transactionDate = new SimpleObjectProperty<LocalDate>(transactionDate);
        this.fromID = new SimpleIntegerProperty(fromID);
        this.toID = new SimpleIntegerProperty(toID);
        this.remark = new SimpleStringProperty(remark);
        this.transactionType = new SimpleObjectProperty<TransactionType>(transactionType);
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

    public LocalDate getTransactionDate() {
        return this.transactionDate.get();
    }

    public void setTransactionDate(LocalDate date) {
        this.transactionDate.set(date);
    }

    public ObjectProperty<LocalDate> transactionDateProperty() {
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
