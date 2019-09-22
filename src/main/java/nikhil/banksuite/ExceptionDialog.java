package nikhil.banksuite;

import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

class ExceptionDialog {

    String title;
    String headerText;
    String contentText;
    Exception e;

    ExceptionDialog(Exception e) {
        this.title = e.getClass().getName();
        this.headerText = e.getCause().toString();
        this.contentText = e.getMessage();
        this.e = e;
    }

    ExceptionDialog(String title, Exception e) {
        this.title = title;
        this.headerText = e.getCause().toString();
        this.contentText = e.getMessage();
        this.e = e;
    }

    ExceptionDialog(String title, String headerText, Exception e) {
        this.title = title;
        this.headerText = headerText;
        this.contentText = e.getMessage();
        this.e = e;
    }

    public void show() {
        Alert box = new Alert(AlertType.ERROR);
        box.setTitle(title);
        box.setHeaderText(headerText);
        box.setContentText(contentText);
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String fullTrace = sw.toString();

        TextArea stackTrace = new TextArea(fullTrace);
        GridPane gp = new GridPane();
        RowConstraints r1 = new RowConstraints();
        r1.setMaxHeight(14);
        RowConstraints r2 = new RowConstraints();

        ColumnConstraints column = new ColumnConstraints();

        gp.getRowConstraints().addAll(r1,r2);
        gp.getColumnConstraints().add(column);

        gp.add(new Label("Stacktrace:"), 0, 0);
        gp.add(stackTrace, 0, 1);

        box.getDialogPane().setExpandableContent(gp);
        box.showAndWait();
    }
}