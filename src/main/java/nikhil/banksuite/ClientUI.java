package nikhil.banksuite;

import java.util.Date;
import java.util.GregorianCalendar;

import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

class ClientUI extends Client {

    String labelColor = "#ffffff";
    String backColour = "#323232";

    ClientUI(String name, String firstName, String lastName, 
            GregorianCalendar dateOfBirth, int accountNumber, double balance, String password) {
        super(name, firstName, lastName, dateOfBirth, accountNumber, balance, password);
    }

    public Stage generateClientStage(boolean isDark) {
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
        ToggleButton theme = new ToggleButton("Light");
        theme.setId("buttons");

        accountLabel.setVisible(false);
        ageLabel.setVisible(false);
        logOutBtn.setVisible(false);

        nameLabel.setId("mainLabel");
        accountLabel.setId("subLabel");
        balanceLabel.setId("mainLabel");
        ageLabel.setId("subLabel");
        moreInfoBtn.setId("buttons");
        logOutBtn.setId("buttons");

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
        
        homeScreen.add(table, 1, 3, 3, 1);

        RowConstraints r1 = new RowConstraints();
        r1.setPercentHeight(10);
        RowConstraints r2 = new RowConstraints();
        r2.setPercentHeight(87);
        RowConstraints topPadding = new RowConstraints();
        topPadding.setPercentHeight(0.5);
        RowConstraints bottomPadding = new RowConstraints();
        bottomPadding.setPercentHeight(2.5);
        RowConstraints expandColumn = new RowConstraints();
        expandColumn.setPercentHeight(0);
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


}