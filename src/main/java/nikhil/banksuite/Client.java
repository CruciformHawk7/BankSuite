package nikhil.banksuite;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Client {
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty accountNumber;
    private final SimpleDoubleProperty balance;
    private final SimpleStringProperty password;

    ObservableList<Record> transactions;

    Client(String name, int accountNumber, double balance, String password) {
        this.name = new SimpleStringProperty(name);
        this.accountNumber = new SimpleIntegerProperty(accountNumber);
        this.balance = new SimpleDoubleProperty(balance);
        this.password = new SimpleStringProperty(password);
        transactions = FXCollections.observableArrayList();
    }

    Client() {
        this.accountNumber = new SimpleIntegerProperty(0);
        this.name = new SimpleStringProperty("name");
        this.password = new SimpleStringProperty("password");
        this.transactions = FXCollections.observableArrayList();
        this.balance = new SimpleDoubleProperty(0);
    }

    public void setName(String name){
        this.name.set(name);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public int getAccountNumber() {
        return accountNumber.get();
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber.set(accountNumber);
    }

    public IntegerProperty accountNumberProperty() {
        return accountNumber;
    }

    public double getBalance() {
        return balance.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }
}