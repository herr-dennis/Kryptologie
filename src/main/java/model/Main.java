package model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.math.BigInteger;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 800);
        stage.setTitle("Euklidischer Algorithmus");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    public static void main(String[] args) {
        launch();


        int a = 252, b = 282, rechteSeite = 42;

        EuklidAlgoErweiterter algo = new EuklidAlgoErweiterter(a, b);
        int[] result = null;
        try {
            result = algo.calculateEuklidAlgoErweiterter(rechteSeite);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        int gcd = result[0]; // GGT von a und b
        int x = result[1];
        int y = result[2];

            System.out.println("Lösung: x = " + x + ", y = " + y);

    }


}