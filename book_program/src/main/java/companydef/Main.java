package companydef;

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
public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException{
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

        List<Path> listOfFiles = new ArrayList<>();

        Searcher searcher = new Searcher(exList);
//        searcher.setErrorHandler((filePath, fileExc) -> {
//           System.out.println("Access denied: " + filePath);
////           user choice about file
//           return FileVisitResult.CONTINUE;
//                });
        searcher.setErrorHandler(new FileSearcherErrorHandler() {
            @Override
            public FileVisitResult handle(Path file, IOException exc) {
//                .....
                System.out.println("new hand method");
                return FileVisitResult.CONTINUE;
            }
        });
        listOfFiles = searcher.preparation(Paths.get(startPath));



    }
}
