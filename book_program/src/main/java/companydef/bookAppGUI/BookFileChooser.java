package companydef.bookAppGUI;

import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

/**
 * Created by 1 on 02.01.2019.
 */
public class BookFileChooser {

    public static File fileChoose(Window window){
        String file = " ";
        File selectedFile = new File("");
        try {

            FileChooser chooser = new FileChooser();
            chooser.setTitle("Open Resource File");
            selectedFile = chooser.showOpenDialog(window);
            System.out.println("File " + selectedFile);
            file = selectedFile.toURI().toString();
            System.out.println("Given file " + file);

        }catch (NullPointerException ex){
            System.out.println(ex.getMessage());
        }

        return selectedFile;
    }
}
