package companydef.bookAppGUI;

import javafx.scene.control.Alert;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

/**
 * Created by 1 on 18.01.2019.
 */
public class ConfigFileCreator {

    static void writeConfigFile(){
        System.out.println("Write config");
        try(BufferedWriter writer = new BufferedWriter
                (new OutputStreamWriter
                        (new FileOutputStream("configT.txt"), Charset.forName("UTF-8").newEncoder()))) {


            writer.write("Text settings:\n");
            writer.write("Font: " + OptionWindow.textSetting.textFont + "\n");
            writer.write("Font size: " + OptionWindow.textSetting.textSize + "\n");
            writer.write("Text color: " + OptionWindow.textSetting.textColor + "\n");

            writer.write("Page settings:\n");
            writer.write("Background color: " + OptionWindow.pageSetting.backgroundColor + "\n");
            writer.write("Text padding: ");
            for (String str : OptionWindow.pageSetting.textPadding)
                writer.write(str + " ");
            writer.write("\n");

        }catch (IOException ex){
            System.out.println("Error" + ex.getMessage());
        }catch (NullPointerException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Problem with saving");
            alert.setHeaderText(null);
            alert.setContentText("Nothing to save in config");
            alert.showAndWait();
        }
    }

    static void writeLibraryFile(){

        try(BufferedWriter writer = new BufferedWriter
                (new OutputStreamWriter
                        (new FileOutputStream("library.txt"), Charset.forName("UTF-8").newEncoder()))) {

            writer.write("Books:\n");
            for (String book : MainMenu.books)
                writer.write(book + "\n");

            writer.write("Information about books:\n");
            for (BookInfo info : InfoWindow.bookInfoList){
                writer.write("Author: " + info.author + "\n");
                writer.write("Collection: " + info.collection + "\n");
                writer.write("Category: " + info.category + "\n");
            }

        }catch (IOException ex){

        }
    }
}
