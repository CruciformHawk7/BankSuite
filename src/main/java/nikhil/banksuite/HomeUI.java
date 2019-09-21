package nikhil.banksuite;

import java.util.GregorianCalendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

class HomeUI extends Home {

    HomeUI() {
        super();
    }

    public Stage stageGenerator() {
        GridPane homeScreen = new GridPane();
        homeScreen.getStylesheets().add("fonts.css");
        homeScreen.getStylesheets().add("Theme.css");
        homeScreen.setId("homeScreen");
        ObservableList<Record> allRecords = FXCollections.observableArrayList();
        TableView<Record> table = new TableView<>();
        for (int i = 0; i<50; i++) { 
            var p = super.nextTransaction();
            if (p==null) continue;
            allRecords.add(p); 
        }
        attachRecordTable(table);
        table.setItems(allRecords);
        homeScreen.add(table,0,0);        
        Stage t = new Stage();
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

        TableColumn<Record, Integer> fromCol = new TableColumn<> ("Origin ID");
        fromCol.setMinWidth(40);
        fromCol.setCellValueFactory(new PropertyValueFactory<Record, Integer>("FromID"));
        recordsTable.getColumns().add(fromCol);

        TableColumn<Record, Integer> toCol = new TableColumn<> ("To ID");
        fromCol.setMinWidth(40);
        fromCol.setCellValueFactory(new PropertyValueFactory<Record, Integer>("ToID"));
        recordsTable.getColumns().add(toCol);

        TableColumn<Record, String> remCol = new TableColumn<>("Remarks");
        remCol.setMinWidth(130);
        remCol.setCellValueFactory(new PropertyValueFactory<Record, String>("remark"));
        recordsTable.getColumns().add(remCol);        
    }
}