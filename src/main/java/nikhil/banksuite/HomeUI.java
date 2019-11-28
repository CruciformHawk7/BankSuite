package nikhil.banksuite;

import java.time.ZoneId;
import java.util.GregorianCalendar;
import java.util.Optional;

import javax.swing.GroupLayout.Alignment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

class HomeUI extends Home {

    static Record incoming;
    ObservableList<Record> allTransactions;
    private static boolean debugMode = true;

    HomeUI() {
        super();
        incoming = null;
    }

    public void updateList(ListView<String> list) {
        for (var ty: bots) {
            list.getItems().add(Integer.toString(ty.getAccountNumber()) 
                + "\t" + ty.getFirstName() + " " + ty.getLastName());
        }
    }

    public Stage loginStage() {
        GridPane login = new GridPane();
        login.getStylesheets().add("fonts.css");
        login.getStylesheets().add("Theme.css");
        Stage out = new Stage();

        Task<ObservableList<Record>> transactions = new Task<ObservableList<Record>>() {
            @Override public ObservableList<Record> call() {
                return doTransactions();
            }
        };

        new Thread(transactions).start();

        Label uname;
        uname = new Label("Login!");
        ListView<String> users;
        uname.setId("mainLabel");
        Button lo = new Button("Login");
        Button signup = new Button("Sign up");
        ToggleButton theme = new ToggleButton("Dark");

        login.getStylesheets().clear();
        login.getStylesheets().add("LightTheme.css");
        out.getIcons().clear();
        out.getIcons().add(new Image("bank2.png"));

        theme.setOnAction(e -> {
            if (theme.getText().equals("Light")) {
                theme.setText("Dark");
                login.getStylesheets().clear();
                login.getStylesheets().add("LightTheme.css");
                out.getIcons().clear();
                out.getIcons().add(new Image("bank2.png"));
            } else {
                theme.setText("Light");
                login.getStylesheets().clear();
                login.getStylesheets().add("Theme.css");
                out.getIcons().clear();
                out.getIcons().add(new Image("bank.png"));
            }            
        });

        TextField username = new TextField();
        username.setText("UserID");

        if (debugMode) {
            users = new ListView<String>();
            updateList(users);
            login.add(users, 1, 4, 2, 1);
        }

        lo.setOnAction(e -> {
            if (username.getText().toLowerCase().equals("admin")) {
                out.hide();
                if (theme.getText().equals("Light"))
                    this.stageGenerator(false).show();
                else 
                    this.stageGenerator(true).show();
            } else {
                int bot = Integer.parseInt(username.getText());
                try {
                    ClientUI cl = super.getBotAt(getBotId(bot));
                    if (theme.getText()=="Dark") passwordInput(cl, false);
                    else passwordInput(cl, true);
                } catch (IndexOutOfBoundsException ex) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("User not found");
                    alert.setHeaderText("The username was not found");
                    alert.setContentText("If you are a new user, try signing up!");

                    alert.showAndWait();
                }
            }
        });

        signup.setOnAction((e) -> {
            ((theme.getText().equals("Dark")) ? this.signUp(false):this.signUp(true)).show();
        });

        RowConstraints topPad = new RowConstraints();
        topPad.setPercentHeight(3);
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(25);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(35);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(35);
        RowConstraints row4 = new RowConstraints();
        row4.setPercentHeight(0);
        RowConstraints bottom = new RowConstraints();
        bottom.setPercentHeight(2);

        ColumnConstraints leftPad = new ColumnConstraints();
        leftPad.setPercentWidth(2);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(48);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(48);
        ColumnConstraints rightPad = new ColumnConstraints();
        rightPad.setPercentWidth(2);

        if (debugMode) {
            row1.setPercentHeight(10);
            row2.setPercentHeight(10);
            row3.setPercentHeight(10);
            row4.setPercentHeight(65);
            out.setScene(new Scene(login, 600, 600));
        } else {
            out.setScene(new Scene(login, 600, 200));
        }

        GridPane.setHalignment(theme, HPos.RIGHT);
        GridPane.setValignment(theme, VPos.TOP);

        login.getRowConstraints().addAll(topPad, row1, row2, row3, row4, bottom);
        login.getColumnConstraints().addAll(leftPad, column1, column2, rightPad);
        
        login.add(uname,1,1);
        login.add(username,1, 2, 2, 1);
        login.add(lo, 1, 3, 2, 1);
        login.add(theme, 2, 1);

        try {
            allTransactions = transactions.get();
        } catch (Exception e) {
            new ExceptionDialog(e).show();
            System.exit(1);
        }

        return out;
    }

    Stage signUp(boolean isDark) {
        Stage out = new Stage();
        GridPane screen = new GridPane();
        screen.getStylesheets().addAll("fonts.css", "Theme.css");
        ClientUI newClient = new ClientUI();

        if (!isDark) {
            screen.getStylesheets().clear();
            screen.getStylesheets().addAll("fonts.css", "LightTheme.css");
        }

        Label lblWelcome = new Label("Sign up");
        lblWelcome.setId("mainLabel");

        Label lblAccNo = new Label();
        int acNo = getRandomNumber(0, 200);
        while (accountIds.contains(acNo)) acNo = getRandomNumber(0, 200);
        final int acN = acNo;
        lblAccNo.setText("Account Number: " + Integer.toString(acNo));
        lblAccNo.setId("subLabel");

        TextField txtFirstName = new TextField();
        txtFirstName.setPromptText("First Name");

        TextField txtLastName = new TextField();
        txtLastName.setPromptText("Last Name");

        DatePicker dpDOB = new DatePicker();
        dpDOB.setPromptText("Date of Birth");
        TextField txtBalance = new TextField();
        txtBalance.setPromptText("Balance");
        PasswordField pw = new PasswordField();
        pw.setPromptText("Password");
        PasswordField pwc = new PasswordField();
        pwc.setPromptText("Confirm Password");
        Button submit = new Button("Sign up");
        Button cancel = new Button("Cancel");

        screen.setHgap(5);
        screen.setVgap(5);
        screen.add(lblWelcome, 1, 1);
        screen.add(lblAccNo, 1, 2);
        screen.add(txtFirstName, 1, 3);
        screen.add(txtLastName, 2, 3);
        screen.add(dpDOB, 1, 4);
        screen.add(txtBalance, 2, 4);
        screen.add(pw, 1, 5);
        screen.add(pwc, 2, 5);
        screen.add(cancel, 1, 6);
        screen.add(submit, 2, 6);
        

        RowConstraints topPad = new RowConstraints();
        topPad.setPercentHeight(5);
        RowConstraints r1 = new RowConstraints();
        r1.setPercentHeight(15);
        RowConstraints r2 = new RowConstraints();
        r2.setPercentHeight(15);
        RowConstraints r3 = new RowConstraints();
        r3.setPercentHeight(15);
        RowConstraints r4 = new RowConstraints();
        r4.setPercentHeight(15);
        RowConstraints r5 = new RowConstraints();
        r5.setPercentHeight(15);
        RowConstraints r6 = new RowConstraints();
        r6.setPercentHeight(15);
        RowConstraints bottomPad = new RowConstraints();
        bottomPad.setPercentHeight(5);

        ColumnConstraints left = new ColumnConstraints();
        left.setPercentWidth(5);
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(45);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(45);
        ColumnConstraints right = new ColumnConstraints();
        right.setPercentWidth(5);
        GridPane.setHalignment(submit, HPos.CENTER);
        out.setResizable(false);
        
        screen.getRowConstraints().addAll(topPad, r1, r2, r3, r4, r5, r6, bottomPad);
        screen.getColumnConstraints().addAll(left, c1, c2, right);

        submit.setOnAction((e) -> {
            boolean conditionMatches = false;
            if (pw.getText().equals(pwc.getText())) conditionMatches = true; 
            if (conditionMatches) {
                boolean isName = false;
                for (var bot: bots) 
                    if (bot.getName() == txtFirstName.getText()) isName=true;
                GregorianCalendar dob = GregorianCalendar.from(dpDOB.getValue().atStartOfDay(ZoneId.systemDefault()));
                newClient.setName((isName)?txtFirstName.getText()+txtLastName.getText():txtFirstName.getText());
                newClient.setFirstName(txtFirstName.getText());
                newClient.setLastName(txtLastName.getText()); 
                newClient.setDateOfBirth(dob);
                newClient.setAccountNumber(acN);
                newClient.addBalance(Double.parseDouble(txtBalance.getText())); 
                newClient.setPassword(pw.getText());
                bots.add(newClient);
            }
        });
        
        out.setScene(new Scene(screen, 350, 300));
        out.initStyle(StageStyle.UTILITY);
        
        return out;
    }

    protected ObservableList<Record> doTransactions() {
        ObservableList<Record> transactions = FXCollections.observableArrayList();
        for (int i = 0; i < 50; i++) {
            var p = super.nextTransaction();
            if (p == null)
                continue;
            transactions.add(p);
        }
        return transactions;
    }

    public Stage stageGenerator(boolean colourTheme) {
        GridPane homeScreen = new GridPane();
        homeScreen.getStylesheets().add("fonts.css");
        homeScreen.getStylesheets().add("Theme.css");

        ToggleButton theme = new ToggleButton("Light");
        Button transact = new Button("Transact");
        Label welcome = new Label("Transactions");
        welcome.setId("mainLabel");

        Stage t = new Stage();
        t.setTitle("All Transactions");
        TableView<Record> table = new TableView<>();
        table.setItems(allTransactions);
        attachRecordTable(table);
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

        if (colourTheme) {
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

        transact.setOnAction(e4 -> {
            var p = super.nextTransaction();
            while (p==null) p = super.nextTransaction();
            table.getItems().add(p);
        });

        table.setRowFactory(row -> {
            TableRow<Record> record = new TableRow<>();
            record.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2) {
                    int bot = record.getItem().getFromID();
                    ClientUI cl = super.getBotAt(getBotId(bot));
                    if (theme.getText()=="Dark") passwordInput(cl, false);
                    else passwordInput(cl, true);
                }
            });
            record.setOnKeyPressed((p) -> {
                if (p.getCode().equals(KeyCode.ENTER)) {
                    System.err.println("Row: Enter Pressed");
                    int bot = record.getItem().getFromID();
                    ClientUI cl = super.getBotAt(getBotId(bot));
                    if (theme.getText()=="Dark") passwordInput(cl, false);
                    else passwordInput(cl, true);
                }
            });
            return record;
        });        


        t.setOnCloseRequest((exit) -> {
            System.exit(0);
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
            if (ist) cl.generateClientStage(isDark, bots, this).show();
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