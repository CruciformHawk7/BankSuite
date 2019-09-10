package nikhil.banksuite;

import java.util.Date;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

class ClientUI extends Client {
    GridPane homeScreen;

    ClientUI(String name, int accountNumber, double balance, String password) {
        super(name, accountNumber, balance, password);
        fillUp();
    }

    ClientUI(Client cl) {
        this.setAccountNumber(cl.getAccountNumber());
        this.setName(cl.getName());
        this.transactions = cl.transactions;
        fillUp();
    }

    private void attachRecordTable(TableView<Record> recordsTable) {
        TableColumn<Record, Integer> idCol = new TableColumn<>("Trans. ID");
        idCol.setMinWidth(80);
        idCol.setCellValueFactory(new PropertyValueFactory<Record, Integer>("TransactionID"));

        TableColumn<Record, Double> amtCol = new TableColumn<>("Trans. Amount");
        amtCol.setMinWidth(150);
        amtCol.setCellValueFactory(new PropertyValueFactory<Record, Double>("TransactionAmount"));

        TableColumn<Record, String> typeCol = new TableColumn<>("Type");
        typeCol.setMinWidth(180);
        typeCol.setCellValueFactory(new PropertyValueFactory<Record, String>("Credit"));

        TableColumn<Record, Date> dateCol = new TableColumn<>("Date");
        dateCol.setMinWidth(200);
        dateCol.setCellValueFactory(new PropertyValueFactory<Record, Date>("TransactionDate"));
        
        recordsTable.getColumns().addAll(idCol, dateCol, amtCol, typeCol);
    }

    private void fillUp() {
        Label nameLabel = new Label("Name: " + this.getName());
        Label accountLabel = new Label("Account ID: " + this.getAccountNumber());
        Label balanceLabel = new Label("Balance: Rs. " + this.getBalance());
        TableView<Record> table = new TableView<>();
        attachRecordTable(table);
        table.setItems(super.transactions);
        table.setTableMenuButtonVisible(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        homeScreen = new GridPane();
        homeScreen.add(accountLabel, 1, 1);
        GridPane.setHalignment(accountLabel, HPos.CENTER);
        homeScreen.add(nameLabel, 2, 1);
        GridPane.setHalignment(nameLabel, HPos.CENTER);
        homeScreen.add(balanceLabel, 3, 1);
        GridPane.setHalignment(balanceLabel, HPos.CENTER);
        homeScreen.add(table, 1, 2, 3, 1);
        RowConstraints r1 = new RowConstraints();
        r1.setPercentHeight(10);
        RowConstraints r2 = new RowConstraints();
        r2.setPercentHeight(85);
        RowConstraints topPadding = new RowConstraints();
        topPadding.setPercentHeight(2.5);
        RowConstraints bottomPadding = new RowConstraints();
        bottomPadding.setPercentHeight(2.5);
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(32);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(32);
        ColumnConstraints c3 = new ColumnConstraints();
        c3.setPercentWidth(31);
        ColumnConstraints leftPadding = new ColumnConstraints();
        leftPadding.setPercentWidth(2.5);
        ColumnConstraints rightPadding = new ColumnConstraints();
        rightPadding.setPercentWidth(2.5);
        homeScreen.getRowConstraints().addAll(topPadding, r1, r2, bottomPadding);
        homeScreen.getColumnConstraints().addAll(leftPadding, c1, c2, c3, rightPadding);
    }
}