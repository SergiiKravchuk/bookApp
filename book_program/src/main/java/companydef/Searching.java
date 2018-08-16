package companydef;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;

import static java.nio.file.FileVisitResult.CONTINUE;

/**
 * Created by 1 on 16.08.2018.
 */
public class Searching extends SimpleFileVisitor<Path> {
    private List<Path> listOfFiles = new LinkedList<>();

    private int numOfMatches = 0;
    private PathMatcher matcher;
    
    Searching (){
        matcher = FileSystems.getDefault().getPathMatcher("glob:*.{txt,html}");
    }

    private void search(Path file){
        Path name = file.getFileName();
        if(name != null && matcher.matches(name)){
            numOfMatches++;
//            System.out.println("File found " + file);
//            add found files in list
            listOfFiles.add(file);
        }

    }

    public void count(){
        System.out.println("Number of matches " + numOfMatches);
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        search(file);
        return CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        search(dir);
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        System.err.println("Access denied in: " + file);
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
}
