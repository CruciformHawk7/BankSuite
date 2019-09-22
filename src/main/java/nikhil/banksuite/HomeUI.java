package nikhil.banksuite;

import java.util.GregorianCalendar;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

class HomeUI extends Home {

    HomeUI() {
        super();
    }

    public Stage stageGenerator() {
        GridPane homeScreen = new GridPane();
        homeScreen.getStylesheets().add("fonts.css");
        homeScreen.getStylesheets().add("Theme.css");

        ToggleButton theme = new ToggleButton("Light");
        Button transact = new Button("Transact");
        Label welcome = new Label("Transactions");        
        welcome.setId("mainLabel");

        Stage t = new Stage();
        ObservableList<Record> allRecords = FXCollections.observableArrayList();
        TableView<Record> table = new TableView<>();

        for (int i = 0; i < 50; i++) {
            var p = super.nextTransaction();
            if (p == null)
                continue;
            allRecords.add(p);
        }

        attachRecordTable(table);
        table.setItems(allRecords);

        RowConstraints topPadding = new RowConstraints();
        topPadding.setPercentHeight(2);
        RowConstraints heading = new RowConstraints();
        heading.setPercentHeight(6);
        RowConstraints body = new RowConstraints();
        body.setPercentHeight(90);
        RowConstraints bottomPadding = new RowConstraints();
        bottomPadding.setPercentHeight(2);

        ColumnConstraints leftPadding = new ColumnConstraints();
        leftPadding.setPercentWidth(2);
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(32);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(32);
        ColumnConstraints c3 = new ColumnConstraints();
        c3.setPercentWidth(32);
        ColumnConstraints rightPadding = new ColumnConstraints();
        rightPadding.setPercentWidth(2);

        homeScreen.getRowConstraints().addAll(topPadding, heading, body, bottomPadding);
        homeScreen.getColumnConstraints().addAll(leftPadding, c1, c2, c3, rightPadding);

        t.getIcons().add(new Image("bank.png"));

        homeScreen.add(welcome, 1, 1);
        GridPane.setHalignment(theme, HPos.RIGHT);
        GridPane.setValignment(theme, VPos.CENTER);
        GridPane.setHalignment(transact, HPos.CENTER);
        homeScreen.add(theme, 3, 1);
        homeScreen.add(transact, 2, 1);
        homeScreen.add(table, 1, 2, 3, 1);
        
        theme.setOnAction(e3 -> {
            if (theme.getText() == "Light") {
                //switch to light
                theme.setText("Dark");
                homeScreen.getStylesheets().clear();
                homeScreen.getStylesheets().add("LightTheme.css");
                t.getIcons().clear();
                t.getIcons().add(new Image("bank2.png"));
            } else {
                theme.setText("Light");
                homeScreen.getStylesheets().clear();
                homeScreen.getStylesheets().add("Theme.css");
                t.getIcons().clear();
                t.getIcons().add(new Image("bank.png"));
            }
        });

        transact.setOnAction(e4 -> {
            var p = super.nextTransaction();
            while (p==null) p = super.nextTransaction();
            allRecords.add(p);
        });

        table.setRowFactory(row -> {
            TableRow<Record> record = new TableRow<>();
            record.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2) {
                    int bot = record.getItem().getFromID();
                    ClientUI cl = super.getBotAt(bot);
                    if (theme.getText()=="Dark") passwordInput(cl, false);
                    else passwordInput(cl, true);
                }
            });
            return record;
        });

        t.setScene(new Scene(homeScreen, 800, 600));
        return t;
    }

    private void attachRecordTable(TableView<Record> recordsTable) {
        TableColumn<Record, Integer> idCol = new TableColumn<>("ID");
        idCol.setMinWidth(40);
        idCol.setCellValueFactory(new PropertyValueFactory<Record, Integer>("TransactionID"));
        recordsTable.getColumns().add(idCol);

        TableColumn<Record, GregorianCalendar> dateCol = new TableColumn<>("Date");
        dateCol.setMinWidth(200);
        dateCol.setCellValueFactory(new PropertyValueFactory<Record, GregorianCalendar>("TransactionDate"));
        recordsTable.getColumns().add(dateCol);

        TableColumn<Record, Double> amtCol = new TableColumn<>("Amount");
        amtCol.setMinWidth(120);
        amtCol.setCellValueFactory(new PropertyValueFactory<Record, Double>("TransactionAmount"));
        recordsTable.getColumns().add(amtCol);

        TableColumn<Record, String> typeCol = new TableColumn<>("Type");
        typeCol.setMinWidth(80);
        typeCol.setCellValueFactory(new PropertyValueFactory<Record, String>("TransactionType"));
        recordsTable.getColumns().add(typeCol);

        TableColumn<Record, Integer> fromCol = new TableColumn<>("Origin ID");
        fromCol.setMinWidth(40);
        fromCol.setCellValueFactory(new PropertyValueFactory<Record, Integer>("FromID"));
        recordsTable.getColumns().add(fromCol);

        TableColumn<Record, Integer> toCol = new TableColumn<>("To ID");
        toCol.setMinWidth(40);
        toCol.setCellValueFactory(new PropertyValueFactory<Record, Integer>("ToID"));
        recordsTable.getColumns().add(toCol);

        TableColumn<Record, String> remCol = new TableColumn<>("Remarks");
        remCol.setMinWidth(130);
        remCol.setCellValueFactory(new PropertyValueFactory<Record, String>("remark"));
        recordsTable.getColumns().add(remCol);     
    }

    private void passwordInput(ClientUI cl, boolean isDark) {
        Dialog<String> dl = new Dialog<String>();
        dl.setTitle("Password");
        dl.setHeaderText("Welcome, " + cl.getFirstName() + " " + cl.getLastName());
        dl.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dl.initStyle(StageStyle.UTILITY);

        PasswordField pw = new PasswordField();
        StackPane inner = new StackPane();
        inner.setAlignment(Pos.CENTER_LEFT);
        inner.getChildren().addAll(new Label("Enter your password: "), pw);
        dl.getDialogPane().setContent(inner);

        dl.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return pw.getText();
            } else {
                dl.close();
            }
            return null;
        });

        Optional<String> op = dl.showAndWait(); 
        op.ifPresent(e2 -> { 
            boolean ist = cl.verifyPassword(e2);
            if (ist) cl.generateClientStage(isDark).show();
            else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Password");
                alert.setHeaderText("Invalid Password");
                alert.setContentText("The password provided is incorrect, please check again");
                alert.showAndWait();
            }
        });
    }
}