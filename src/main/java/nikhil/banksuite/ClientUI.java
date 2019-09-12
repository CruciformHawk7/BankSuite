package nikhil.banksuite;

import java.util.Date;
import java.util.GregorianCalendar;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

class ClientUI extends Client {

    String labelColor = "#ffffff";
    String backColour = "#323232";

    ClientUI(String name, String firstName, String lastName, 
            GregorianCalendar dateOfBirth, int accountNumber, double balance, String password) {
        super(name, firstName, lastName, dateOfBirth, accountNumber, balance, password);
    }

    public Stage generateClientStage() {
        Stage out = new Stage();
        Scene scene = new Scene(fillUp(), 800, 600, Color.web("#212121"));
        out.setScene(scene);
        out.setTitle(getName());
        return out;
    }

    private GridPane fillUp() {
        GridPane homeScreen = new GridPane();
        Label nameLabel = new Label("Welcome, " + this.getName());
        Label accountLabel = new Label("Account ID: " + this.getAccountNumber());
        Label balanceLabel = new Label("Balance: ₹" + this.getBalance());
        Label ageLabel = new Label("Born " + this.textDateOfBirth());
        Button moreInfoBtn = new Button("More ▼");

        accountLabel.setVisible(false);
        ageLabel.setVisible(false);

        nameLabel.setFont(new Font(16));
        nameLabel.setTextFill(Color.web("#ffffff"));
        accountLabel.setFont(new Font(12));
        accountLabel.setTextFill(Color.web("#ffffff"));
        balanceLabel.setFont(new Font(16));
        balanceLabel.setTextFill(Color.web("#ffffff"));
        ageLabel.setFont(new Font(12));
        ageLabel.setTextFill(Color.web("#ffffff"));

        TableView<Record> table = new TableView<>();
        table.setStyle("-fx-cell-color: #424242");
        attachRecordTable(table);
        table.setItems(super.transactions);
        table.setTableMenuButtonVisible(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        homeScreen = new GridPane();

        homeScreen.getStylesheets().add("Dark.css");
        
        homeScreen.add(nameLabel, 1, 1);
        GridPane.setHalignment(nameLabel, HPos.CENTER);
        homeScreen.add(balanceLabel, 2, 1);
        GridPane.setHalignment(balanceLabel, HPos.CENTER);
        homeScreen.add(moreInfoBtn, 3, 1);
        GridPane.setHalignment(moreInfoBtn, HPos.RIGHT);

        homeScreen.add(ageLabel, 2, 2);
        GridPane.setHalignment(ageLabel, HPos.CENTER);
        homeScreen.add(accountLabel, 1, 2);
        GridPane.setHalignment(accountLabel, HPos.CENTER);
        
        
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

        moreInfoBtn.setOnAction((event) -> {
            if (!accountLabel.isVisible()) {
                accountLabel.setVisible(true);
                ageLabel.setVisible(true);
                expandColumn.setPercentHeight(8);
                r2.setPercentHeight(r2.getPercentHeight()-8);
                moreInfoBtn.setText("Less ▲");
            } else {
                accountLabel.setVisible(false);
                ageLabel.setVisible(false);
                expandColumn.setPercentHeight(0);
                r2.setPercentHeight(r2.getPercentHeight()+8);
                moreInfoBtn.setText("More ▼");
            }
        });

        homeScreen.getRowConstraints().addAll(topPadding, r1, expandColumn, r2, bottomPadding);
        homeScreen.getColumnConstraints().addAll(leftPadding, c1, c2, c3, rightPadding);

        return homeScreen;
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