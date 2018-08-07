package companydef;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by 1 on 07.08.2018.
 */
public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        SearchingFiles search = new SearchingFiles();

        String[] extList = {".txt", ".html", ".pdf"};


        System.out.println("Make full scan? ( type yes/no )");
        String scanChoice = scanner.next();
        if (scanChoice.equals("no")){
//            String path = "e:\\Games\\testFor\\";
            String path;
            System.out.println("Enter the path like c:\\folder\\nextfolder\\");
            path = scanner.next();
            File str_dir = new File(path);
            search.listOfFiles(str_dir, extList);


        }else if(scanChoice.equals("yes")){
            search.getComputerInfo();
            search.fullSearching(extList);
        }



    }
}
