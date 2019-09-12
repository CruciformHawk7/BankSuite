package nikhil.banksuite;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

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

    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleIntegerProperty age;
    private final SimpleObjectProperty<GregorianCalendar> dateOfBirth;
    
    @NotNull
    @Min(2) @Max(15)
    private final SimpleIntegerProperty accountNumber;

    private final SimpleDoubleProperty balance;
    private final SimpleStringProperty password;

    ObservableList<Record> transactions;

    Client(String name, String firstName, String lastName, 
           GregorianCalendar dateOfBirth, int accountNumber, double balance, String password) {
        this.name = new SimpleStringProperty(name);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.dateOfBirth = new SimpleObjectProperty<GregorianCalendar>(dateOfBirth);
        this.accountNumber = new SimpleIntegerProperty(accountNumber);
        this.balance = new SimpleDoubleProperty(balance);
        this.password = new SimpleStringProperty(password);
        long ye = this.dateOfBirth.get().getTimeInMillis();
        long today = new GregorianCalendar().getTimeInMillis();
        long result = ye-today;
        GregorianCalendar calAge = new GregorianCalendar();
        calAge.setTimeInMillis(result);
        this.age = new SimpleIntegerProperty(calAge.get(GregorianCalendar.YEAR));
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
    
    public void setName(String name){
        this.name.set(name);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getFirstName() {
        return this.firstName.get();
    }

    public StringProperty firstNameProperty() {
        return this.firstName;
    }

    public String getLastName() {
        return this.firstName.get();
    }

    public StringProperty lastNameProperty() {
        return this.firstName;
    }

    public int getAge() {
        return this.age.get();
    }

    public IntegerProperty ageProperty() {
        return this.age;
    }

    public void setDateOfBirth(GregorianCalendar dateOfBirth) {
        this.dateOfBirth.set(dateOfBirth);
    }

    public GregorianCalendar getDateOfBirth() {
        return this.dateOfBirth.get();
    }

    public String textDateOfBirth() {
        SimpleDateFormat date = new SimpleDateFormat("EEEE, dd MMM YYYY HH:mm:ss z");
        return date.format(this.dateOfBirth.get().getTime());
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