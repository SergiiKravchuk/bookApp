package companydef;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

/**
 * Created by 1 on 07.08.2018.
 */
public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException{
        List<Path> listOfFiles;
        String choice;
        String userPath;
        Searching searchFiles = new Searching();
        System.out.println("Make full scan? ( type yes/no )");
        choice = scanner.next();
        if(choice.equals("no")){
            System.out.println("Enter the path for search (like c:\\folder\\nextfolder\\)");
            userPath = scanner.next();
            Path startDir = Paths.get(userPath);
            Files.walkFileTree(startDir,searchFiles);
            searchFiles.count();
            listOfFiles = searchFiles.getListOfFiles();
            showList(listOfFiles);


        }else if(choice.equals("yes")){
            File[] drivePath = searchFiles.getComputerInfo();
            Path path;
            for (File value : drivePath){
                path = value.toPath();
                Files.walkFileTree(path, searchFiles);
            }

        }

    }

    private static void showList(List<Path> list){
        for (Path path : list){
            System.out.println(path);
        }
    }
}
