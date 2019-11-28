package nikhil.banksuite;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * A simple GUI Java application that demonstrates a sample bank 
 * With accounts that can be withdrawn from and transferred to
 * run: mvn clean javafx:run
 * build jar: mvn clean package
 * 
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        try {
            stage = new HomeUI().loginStage();
            stage.show();
        } catch (Exception e) {
            new ExceptionDialog("Error occured" ,e).show();
        }
    }

    public static void secondMain(String[] args) {
        launch();
    }

}