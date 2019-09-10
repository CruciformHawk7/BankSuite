package nikhil.banksuite;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class Client {
    @Pattern(message="Invalid Name ${validatedValue}", regexp="^[A-Za-z_0-9]+$")
    @NotNull @NotBlank @NotEmpty
    @Size(max=15, min=8)
    private final SimpleStringProperty name;
    
    @NotNull
    @Min(2) @Max(15)
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

    public boolean verifyPassword(String password) {
        return this.password.get().equals(password);
    }

    @Override
    public String toString() {
        String out;
        out="Name: " + this.name;
        out+="\nAccount Number: " + this.accountNumber;
        out+="\nBalance: " + balance;
        out+="\nTransactions:\n TID\t\tTran Amt\t\tDate\t\tType";
        for (Record p : transactions) {
            out+="\n" + p.getTransactionID() + "\t\t";
            out+=p.getTransactionAmount() + "\t\t"; 
            out+=p.getTransactionDate()+ "\t\t" + p.getTransactionType();
        }
        return out;
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

    public void setBalance(double balance) {
        this.balance.set(balance);
    }

    public DoubleProperty balanceProperty() {
        return this.balance;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }
}