package nikhil.banksuite;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * A simple GUI Java application that demonstrates a sample bank 
 * With accounts that can be withdrawn from and transferred to
 * run: mvn clean javafx:run
 *
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        ClientUI clui = new ClientUI(DemoDataGenerator.generateFakeClient());
        try {
        DemoDataGenerator.writeToFile("test.txt");
        } catch(Exception e) {
            System.exit(0);
        }
        Scene scene = new Scene(clui.homeScreen, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}