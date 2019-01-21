package companydef.bookAppGUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by 1 on 06.01.2019.
 */
public interface ReadScene {

    default void readScene(Stage primaryStage){

        try {
            Parent root = FXMLLoader.load(getClass().getResource("../../../resources/fxml/readerWindow.fxml"));
            primaryStage.setScene(new Scene(root, 800, 300));
        }catch (Exception ex){
            System.out.println("readscene: " + ex.getMessage());
        }
    }
}
