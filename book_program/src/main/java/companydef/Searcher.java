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


public class Searcher {

    private PathMatcher matcher;

    Searcher(List<String> exList){
        String fileExtend = getPattern(exList);

        matcher = FileSystems.getDefault().getPathMatcher("glob:" + fileExtend);
    }

    public List<Path> start(Path startDir) throws IOException{
        MyFileVisitor visitor = new MyFileVisitor(startDir, matcher);
        visitor.prepare();
        return visitor.getListOfFiles();
    }

    private String getPattern(List<String> exList){
        String pattern = "*.{";

        pattern += String.join(",", exList);

        pattern += "}";
        System.out.println(pattern);
        return pattern;
    }

}

//MyFileVisitor

class MyFileVisitor extends SimpleFileVisitor<Path>{

    private PathMatcher matcher;
    private Path startDir;

    private FileSearcherErrorHandler handler;
    private int numOfMatches = 0;
    private List<Path> listOfFiles = new LinkedList<>();
    private Map<Path, IOException> accessError = new HashMap<>();

    MyFileVisitor(Path startDir, PathMatcher matcher) {
        this.matcher = matcher;
        this.startDir = startDir;

        handler = (filePath, fileExc) -> {
            System.out.println("Access denied: " + filePath);
            accessError.put(filePath, fileExc);
        };

    }

    public void prepare()throws IOException{
        Files.walkFileTree(startDir, this);
    }
    public List<Path> getListOfFiles(){
        return listOfFiles;
    }
    public Map getAccessError(){
        return accessError;
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

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        search(file);
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        handler.handle(file, exc);

        return CONTINUE;
    }

}

interface FileSearcherErrorHandler{
    void handle(Path file, IOException exc);
}
