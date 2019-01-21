package companydef.bookAppGUI;

import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

import java.io.File;

/**
 * Created by 1 on 08.01.2019.
 */
public class StartPathChooser {

    public static String startPathChoose(Window window){

        String startPath = "";
        try {

            DirectoryChooser chooser = new DirectoryChooser();
            File startDirectory = chooser.showDialog(window);
            startPath = startDirectory.getAbsolutePath();
        }catch (NullPointerException ex){
            //
        }

        return startPath;
    }
}
