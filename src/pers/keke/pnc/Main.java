/**
 *
 * @author Keke
 */

package pers.keke.pnc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("Window.fxml"));
        primaryStage.setTitle("Producer And Consumer");
        primaryStage.setScene(new Scene(root, 600, 450));
        primaryStage.show();

    }

//    Timeline textTimeLine = new Timeline(new KeyFrame(Duration.millis(2500), ae -> doSomething())).play();


    public static void main(String[] args) {

//        WindowController controller = new WindowController();
//        controller.initImage();

        launch(args);

    }

}
