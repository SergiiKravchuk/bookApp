package companydef;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by 1 on 07.08.2018.
 */
public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        System.out.println("SearcherNIO");
        List<String> exList = new ArrayList<>();
        System.out.println("Enter the path of start directory" +
                           " (like c:\\folder\\nextFolder)");
        String startPath = scanner.next();
        String ex = "";
        System.out.println("Enter the extends of files");
        while (!ex.equals("ok")){
            ex = scanner.next();
            if(!ex.equals("ok")) {
                exList.add(ex);
            }
        }
//        String[] exList = new String[5];
        new Searcher(Paths.get(startPath), exList);

        System.out.println("HEre the bad files" );
        Searcher.show();

    }
}
