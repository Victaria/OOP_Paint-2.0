package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
         FXMLLoader loader = new FXMLLoader(Main.class.getResource("sample.fxml"));
        primaryStage.setTitle("Paint");
        primaryStage.setScene(new Scene(root, 1200, 750));
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
