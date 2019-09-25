package nikhil.banksuite;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

class ClientUI extends Client {

    String labelColor = "#ffffff";
    String backColour = "#323232";

    ObservableList<Integer> accounts;

    ClientUI(String name, String firstName, String lastName, 
            GregorianCalendar dateOfBirth, int accountNumber, double balance, String password) {
        super(name, firstName, lastName, dateOfBirth, accountNumber, balance, password);
    }

    public void setAccounts(ArrayList<Integer> accounts) {
        this.accounts = FXCollections.observableArrayList(accounts);
    }

    public Stage generateClientStage(boolean isDark, ObservableList<ClientUI> bots, HomeUI caller) {
        Stage out = new Stage();
        GridPane homeScreen = new GridPane();
        homeScreen.setId("homeScreen");
        homeScreen.getStylesheets().add("fonts.css");

        if (isDark) { 
            homeScreen.getStylesheets().add("Theme.css");
            out.getIcons().add(new Image("bank.png"));
        }
        else { 
            homeScreen.getStylesheets().add("LightTheme.css"); 
            out.getIcons().add(new Image("bank2.png"));
        }

        Label nameLabel = new Label("Welcome, " + this.getName());
        Label accountLabel = new Label("Account ID: " + this.getAccountNumber());
        Label balanceLabel = new Label("Balance: ₹" + this.getBalance());
        Label ageLabel = new Label("Born " + this.textDateOfBirth());
        Button moreInfoBtn = new Button("More ▼");
        Button logOutBtn = new Button("Log Out");
        Button sendMoney = new Button("+ Send Money");
        ToggleButton theme = new ToggleButton("Light");
        theme.setId("buttons");

        accountLabel.setVisible(false);
        ageLabel.setVisible(false);
        logOutBtn.setVisible(false);

        nameLabel.setId("mainLabel");
        accountLabel.setId("subLabel");
        balanceLabel.setId("mainLabel");
        ageLabel.setId("subLabel");

        TableView<Record> table = new TableView<>();
        attachRecordTable(table);
        table.setItems(super.transactions);
        table.setTableMenuButtonVisible(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        homeScreen.add(nameLabel, 1, 1);
        GridPane.setHalignment(nameLabel, HPos.CENTER);
        homeScreen.add(balanceLabel, 2, 1);
        GridPane.setHalignment(balanceLabel, HPos.CENTER);
        homeScreen.add(moreInfoBtn, 3, 1);
        GridPane.setHalignment(moreInfoBtn, HPos.RIGHT);

        homeScreen.add(accountLabel, 1, 2);
        GridPane.setHalignment(accountLabel, HPos.CENTER);
        homeScreen.add(ageLabel, 2, 2);
        GridPane.setHalignment(ageLabel, HPos.CENTER);
        homeScreen.add(logOutBtn, 3, 2);
        GridPane.setHalignment(logOutBtn, HPos.RIGHT);
        homeScreen.add(sendMoney, 2, 5);
        GridPane.setValignment(sendMoney, VPos.BOTTOM);
        GridPane.setHalignment(sendMoney, HPos.CENTER);
        
        homeScreen.add(table, 1, 3, 3, 1);

        RowConstraints r1 = new RowConstraints();
        r1.setPercentHeight(10);
        RowConstraints r2 = new RowConstraints();
        r2.setPercentHeight(77);
        RowConstraints topPadding = new RowConstraints();
        topPadding.setPercentHeight(0.5);
        RowConstraints bottomPadding = new RowConstraints();
        bottomPadding.setPercentHeight(2.5);
        RowConstraints expandColumn = new RowConstraints();
        expandColumn.setPercentHeight(0);
        RowConstraints tools = new RowConstraints();
        tools.setPercentHeight(10);

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

        moreInfoBtn.setOnAction((e) -> {
            if (!accountLabel.isVisible()) {
                accountLabel.setVisible(true);
                ageLabel.setVisible(true);
                logOutBtn.setVisible(true);
                expandColumn.setPercentHeight(8);
                r2.setPercentHeight(r2.getPercentHeight()-8);
                moreInfoBtn.setText("Less ▲");
            } else {
                accountLabel.setVisible(false);
                ageLabel.setVisible(false);
                logOutBtn.setVisible(false);
                expandColumn.setPercentHeight(0);
                r2.setPercentHeight(r2.getPercentHeight()+8);
                moreInfoBtn.setText("More ▼");
            }
        });

        sendMoney.setOnAction(e -> {
            sendMoneyDialog(isDark, bots, caller).show();
        });

        homeScreen.getRowConstraints().addAll(topPadding, r1, expandColumn, r2, bottomPadding);
        homeScreen.getColumnConstraints().addAll(leftPadding, c1, c2, c3, rightPadding);

        Scene scene = new Scene(homeScreen, 800, 600);
        out.setScene(scene);
        out.setTitle(getName());
        
        logOutBtn.setOnAction((e) -> {
            out.close();
        });

        out.getIcons().add(new Image("bank.png")); 

        return out;
    }

    private void attachRecordTable(TableView<Record> recordsTable) {
        TableColumn<Record, Integer> idCol = new TableColumn<>("ID");
        idCol.setMinWidth(40);
        idCol.setCellValueFactory(new PropertyValueFactory<Record, Integer>("TransactionID"));
        recordsTable.getColumns().add(idCol);

        TableColumn<Record, Date> dateCol = new TableColumn<>("Date");
        dateCol.setMinWidth(200);
        dateCol.setCellValueFactory(new PropertyValueFactory<Record, Date>("TransactionDate"));
        recordsTable.getColumns().add(dateCol);

        TableColumn<Record, Double> amtCol = new TableColumn<>("Amount");
        amtCol.setMinWidth(120);
        amtCol.setCellValueFactory(new PropertyValueFactory<Record, Double>("TransactionAmount"));
        recordsTable.getColumns().add(amtCol);

        TableColumn<Record, String> typeCol = new TableColumn<>("Type");
        typeCol.setMinWidth(80);
        typeCol.setCellValueFactory(new PropertyValueFactory<Record, String>("TransactionType"));
        recordsTable.getColumns().add(typeCol);

        TableColumn<Record, Integer> fromCol = new TableColumn<> ("Origin ID");
        fromCol.setMinWidth(40);
        fromCol.setCellValueFactory(new PropertyValueFactory<Record, Integer>("FromID"));
        recordsTable.getColumns().add(fromCol);

        TableColumn<Record, String> remCol = new TableColumn<>("Remarks");
        remCol.setMinWidth(130);
        remCol.setCellValueFactory(new PropertyValueFactory<Record, String>("remark"));
        recordsTable.getColumns().add(remCol);        
    }

    public Stage sendMoneyDialog(boolean isDark, ObservableList<ClientUI> bots, HomeUI caller) {
        Stage m = new Stage();
        m.initStyle(StageStyle.UTILITY);
        GridPane gd = new GridPane();
        m.setScene(new Scene(gd, 350, 200));
        m.setTitle("Transaction");

        if (isDark) gd.getStylesheets().add("Theme.css");
        else gd.getStylesheets().add("LightTheme.css");
        m.getIcons().add(new Image("bank.png"));
        int sep = 20;

        ColumnConstraints padding  = new ColumnConstraints();
        padding.setPercentWidth(5);
        ColumnConstraints contents = new ColumnConstraints();
        contents.setPercentWidth(90);

        RowConstraints first = new RowConstraints();
        first.setPercentHeight(sep);
        RowConstraints second = new RowConstraints();
        second.setPercentHeight(sep);
        RowConstraints third = new RowConstraints();
        third.setPercentHeight(sep);
        RowConstraints fourth = new RowConstraints();
        fourth.setPercentHeight(sep);
        RowConstraints fifth = new RowConstraints();
        fifth.setPercentHeight(sep);

        gd.getRowConstraints().addAll(first, second, third, fourth, fifth);
        gd.getColumnConstraints().addAll(padding, contents, padding);

        Label pick = new Label("Pick an account: ");
        pick.setId("subLabel");
        gd.add(pick, 1, 0);
        GridPane.setValignment(pick, VPos.CENTER);

        ComboBox<Integer> accPicker = new ComboBox<Integer>(accounts);
        gd.add(accPicker, 1, 1);
        GridPane.setValignment(accPicker, VPos.TOP);

        Label enter = new Label("Transaction Amount(Balance: ₹" + this.getBalance() + "):");
        enter.setId("subLabel");
        gd.add(enter, 1, 2);
        GridPane.setValignment(enter, VPos.CENTER);

        TextField amt = new TextField();
        gd.add(amt, 1, 3);
        GridPane.setValignment(amt, VPos.TOP);

        HBox options = new HBox();
        Button Ok = new Button("Send");
        Ok.setStyle("-fx-margin: 0 10 0 0");
        
        Button Cancel = new Button("Cancel");
        Region spacer = new Region();
        spacer.setPrefWidth(20);
        options.getChildren().addAll(Ok, spacer, Cancel);
        gd.add(options, 1, 4);
        options.setStyle("-fx-padding: 0 0 0 180");
        GridPane.setValignment(options, VPos.CENTER);
        GridPane.setHalignment(options, HPos.RIGHT);

        Ok.setOnAction(e -> {
            double am = Double.parseDouble(amt.getText());
            if (am<this.getBalance()) {
                String remark = "VIA NET BANKING";
                ClientUI sender = this;
                ClientUI receiver = null;
                for (var bot : bots) {
                    if (bot.getAccountNumber() == accPicker.getValue()) {
                        receiver = bot;
                        break;
                    }
                }
                if (receiver!=null) {
                    sender.removeBalance(am);
                    receiver.addBalance(am);
                    Record senderRecord = new Record(Home.transactionCount, am, new GregorianCalendar(), 
                        TransactionType.Debit, sender.getAccountNumber(), receiver.getAccountNumber(), remark);
                    Record receiverRecord = new Record(Home.transactionCount++, am, new GregorianCalendar(), 
                        TransactionType.Credit, sender.getAccountNumber(), 
                        receiver.getAccountNumber(), remark);
                    sender.transactions.add(senderRecord);
                    receiver.transactions.add(receiverRecord);
                    caller.allTransactions.add(senderRecord);
                    m.close();
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Failed");
                    alert.setHeaderText("User doesnt exist!");
                    alert.setContentText("Please check the account number and try again.");
                    alert.showAndWait();
                }
                
            }
        });

        amt.setOnKeyPressed((e3) -> {
            if (e3.getCode().equals(KeyCode.ENTER)) {
                Ok.fire();
            }
        });

        Cancel.setOnAction(e2 -> {
            m.close();
        });

        return m;
    }
}