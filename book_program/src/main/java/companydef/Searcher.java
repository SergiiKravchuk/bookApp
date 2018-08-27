package companydef;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.nio.file.FileVisitResult.CONTINUE;

//e:\Games\testFor\
public class Searcher extends SimpleFileVisitor<Path>{
    private List<Path> listOfFiles = new LinkedList<>();
    private static Map<Path, IOException> accessError = new HashMap<>();

    private int numOfMatches = 0;
    private PathMatcher matcher;
    private FileSearcherErrorHandler handler;

    Searcher (Path startDir, List<String> exList)throws IOException{

        String fileExtend = getPattern(exList);
        matcher = FileSystems.getDefault().getPathMatcher("glob:" + fileExtend);

        Files.walkFileTree(startDir, this);

    }


    private void search(Path file){
        Path name = file.getFileName();
        if(name != null && matcher.matches(name)){
            numOfMatches++;
//            System.out.println("File found " + file);
//            add found files in list
            System.out.println("Found: " + file);
            listOfFiles.add(file);
        }
    }

    private String getPattern(List<String> exList){
        String pattern = "*.{";
        for (String str : exList){

            if (str.equals(exList.get(exList.size()-1))){
                pattern += str;
            }else{
                pattern += str + ",";
            }
        }
        pattern += "}";
        System.out.println(pattern);
        return pattern;
    }

    public void count(){
        System.out.println("Number of matches " + numOfMatches);
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        search(file);
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
//        adding to map

        handler = (filePath, fileExc) -> {
            System.out.println(filePath);
            accessError.put(filePath, fileExc);
        };

        return CONTINUE;
    }



    public List<Path> getListOfFiles() {
        return listOfFiles;
    }

    public File[] getComputerInfo(){
        File[] drivePath;
        drivePath = File.listRoots();

        System.out.println("Drives: ");

        for (File path : drivePath){
            System.out.println(path);
        }
        return drivePath;
    }

    interface FileSearcherErrorHandler{
        void handle(Path file, IOException exc);
    }

//    test
    public static void show(){
        for (Path p : accessError.keySet()){
            System.out.println(p);
        }
    }
}