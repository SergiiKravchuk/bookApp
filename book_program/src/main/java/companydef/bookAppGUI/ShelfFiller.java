package companydef.bookAppGUI;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by 1 on 06.01.2019.
 */
public interface ShelfFiller extends ReadScene{

    default ImageView fillShelf(String bookName, HBox booksPanel){

        VBox bookTitle = new VBox();
        ImageView imageView = new ImageView(MainMenu.imgNoImage);
        imageView.setFitHeight(450);
        imageView.setFitWidth(300);

        Label label = new Label(bookName);
        bookTitle.getChildren().add(imageView);
        bookTitle.getChildren().add(label);
        booksPanel.getChildren().add(bookTitle);

        return imageView;
    }

    default void setOnAction(ImageView imageView, Stage primaryStage){

        if(imageView.isMouseTransparent()) System.out.println("yes");
        else System.out.println("no");
        imageView.onMousePressedProperty().set(eventImage -> {

            if (eventImage.getButton() == MouseButton.SECONDARY){
                MainMenu.contextMenu.show(imageView, eventImage.getScreenX(), eventImage.getScreenY());
                MainMenu.selectedIndex = MainMenu.booksPanel.getChildren().indexOf(imageView.getParent());
                System.out.println("selected index " + MainMenu.selectedIndex);
            }
            else if (eventImage.getButton() == MouseButton.PRIMARY) {
                readScene(MainMenu.primaryStage);
            }
        });

    }
}
