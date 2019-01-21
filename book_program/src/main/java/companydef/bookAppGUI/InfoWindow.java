package companydef.bookAppGUI;

/**
 * Created by 1 on 17.12.2018.
 */

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class InfoWindow {

    public static List<BookInfo> bookInfoList = new ArrayList<>();
    private Image image = new Image(MainMenu.pathNoImage);

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane infoPane;

    @FXML
    private GridPane authorInfoPane;

    @FXML
    private TextField field_authorName;

    @FXML
    private TextField field_collectionName;

    @FXML
    private Label label_authorName;

    @FXML
    private Label label_collectionName;

    @FXML
    private GridPane collectionInfoPane;

    @FXML
    private Label label_category;

    @FXML
    private TextField field_category;

    @FXML
    private Button btn_save;

    @FXML
    private Button btn_cancel;

    @FXML
    private TextField field_imagePath;

    @FXML
    private Button btn_openFile;

    @FXML
    private Button btn_apply;

    @FXML
    private ImageView imagePreview;

    @FXML
    void initialize(){

        imagePreview.setImage(image);
        checkAvailable();

        btn_save.setOnAction(event -> {

            BookInfo info = new BookInfo(field_authorName.getText(), field_collectionName.getText(), field_category.getText());
            bookInfoList.add(MainMenu.selectedIndex, info);
            VBox tmpBox = (VBox)MainMenu.booksPanel.getChildren().get(MainMenu.selectedIndex);
            ImageView imageView = (ImageView)tmpBox.getChildren().get(0);
            imageView.setImage(image);
            Label label = (Label) tmpBox.getChildren().get(1);
            System.out.println("Info window " + label.getText());
        });

        btn_cancel.setOnAction(event -> {

            Stage stage = (Stage)btn_cancel.getScene().getWindow();
            stage.close();
        });

        btn_openFile.setOnAction(event ->
                field_imagePath.setText(BookFileChooser.fileChoose(btn_openFile.getScene().getWindow()).toURI().toString()));

        btn_apply.setOnAction(event -> {

            image = new Image(field_imagePath.getText());
            imagePreview.setImage(image);

        });
    }

    private void checkAvailable(){

        if (MainMenu.selectedIndex < bookInfoList.size()) {

            BookInfo info = bookInfoList.get(MainMenu.selectedIndex);
            field_authorName.setText(info.author);
            field_collectionName.setText(info.collection);
            field_category.setText(info.category);
        }
        else {

            field_authorName.setText("No information given");
            field_collectionName.setText("No information given");
            field_category.setText("No information given");
        }
    }

}

