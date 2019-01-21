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
        System.out.println("sec " + getClass().getResourceAsStream("/fxml/mainScreenV1.01.fxml"));

        try {
            InputStream stream = getClass().getResourceAsStream("/fxml/mainScreenV1.01.fxml");
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainScreenV1.01.fxml"));
//            FXMLLoader loader = new FXMLLoader();
//            Parent root = loader.load(stream);

            Parent root = new FXMLLoader().load(stream);

            primaryStage.setTitle("BookApp");
            primaryStage.setScene(new Scene(root, 1200, 800));

//            MainMenu controller = loader.getController();
//            controller.setPrimaryStage(primaryStage);

            primaryStage.show();

        }catch (Exception ex){
            System.out.println("error with GUI starter " + ex.getMessage() + " end" + ex.toString());
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
