package model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Euklidischer Algorithmus");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
        EuklidAlgo a = new EuklidAlgo(65,24);
        System.out.println( "Der GGT ist : "+ a.calcGgt());
    }
}