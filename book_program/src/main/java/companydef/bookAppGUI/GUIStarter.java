package companydef.bookAppGUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.InputStream;

/**
 * Created by 1 on 20.01.2019.
 */
public class GUIStarter extends Application{

    @Override
    public void start(Stage primaryStage){
        System.out.println("smth " + getClass().getResource("/fxml/mainScreenV1.01.fxml"));
        System.out.println("smth " + getClass().getResource("mainScreenV1.01.fxml"));
        System.out.println("sec " + getClass().getResourceAsStream("/fxml/mainScreenV1.01.fxml"));

        try {
//            InputStream stream = getClass().getClassLoader().getResourceAsStream("/fxml/mainScreenV1.01.fxml");
//            Parent root = new FXMLLoader().load(stream);


            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("/fxml/mainScreenV1.01.fxml"));
            Parent root = loader.load();

            primaryStage.setTitle("BookApp");
            primaryStage.setScene(new Scene(root, 1200, 800));

            primaryStage.show();

        }catch (Exception ex){
            System.out.println("error with GUI starter " + ex.getMessage() + " end " + ex.toString());
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
