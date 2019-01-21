package companydef;

import companydef.bookAppGUI.MainMenu;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * Created by 1 on 07.08.2018.
 */
public class SearchStarter {

    private static List<Path> listOfFiles = new ArrayList<>();
    private static List<String> exList = new ArrayList<>();
    private static String startPath;


    public SearchStarter(String startPath, List<String> exList){
        this.startPath = startPath;
        this.exList = exList;
    }

    public List<Path> getListOfFiles() {
        return listOfFiles;
    }

    public static void main() throws IOException{

        handInput();
        startSearch();
    }

    public static void startSearch()throws IOException{

        Searcher searcher = new Searcher(exList);
        searcher.setErrorHandler((filePath, fileExc) -> {
            System.out.println("Access denied: " + filePath);
//           user choice about file
            return FileVisitResult.CONTINUE;
        });

        listOfFiles = searcher.search(Paths.get(startPath));
    }

    public static void handInput(){

        System.out.println("SearcherNIO");
        System.out.println("Enter the path of start directory" +
                " (like c:\\folder\\nextFolder)");
        final Scanner scanner = new Scanner(System.in);
        startPath = scanner.next();
        String ex = "";
        System.out.println("Enter the extends of files");
        while (!ex.equals("ok")){
            ex = scanner.next();
            if(!ex.equals("ok")) {
                exList.add(ex);
            }
        }
    }
}
