package companydef;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

import static java.nio.file.FileVisitResult.CONTINUE;


public class Searcher {

    private PathMatcher matcher;
//    ???????????new FileSearcherErrorHandler....
    private FileSearcherErrorHandler handler = new FileSearcherErrorHandler(){};

    Searcher(List<String> exList) {
        String fileExtend = getPattern(exList);

        matcher = FileSystems.getDefault().getPathMatcher("glob:" + fileExtend);
    }

    public List<Path> preparation(Path startDir) throws IOException {
        SimpleHandler visitor = new SimpleHandler(startDir, matcher, handler);
        visitor.search();

        return visitor.getListOfFiles();
    }

    private String getPattern(List<String> exList) {
        String pattern = "*.{";

        pattern += String.join(",", exList);

        pattern += "}";
        System.out.println(pattern);
        return pattern;
    }

    void setErrorHandler(FileSearcherErrorHandler handler) {

        this.handler = Objects.requireNonNull(handler);
    }

    private class SimpleHandler extends SimpleFileVisitor<Path> {

        private PathMatcher matcher;
        private Path startDir;

        private FileSearcherErrorHandler handler;
        private int numOfMatches = 0;
        private List<Path> listOfFiles = new ArrayList<>();

        SimpleHandler(Path startDir, PathMatcher matcher, FileSearcherErrorHandler handler) {
            this.matcher = matcher;
            this.startDir = startDir;

            this.handler = handler;
        }

        public void search() throws IOException {
            Files.walkFileTree(startDir, this);
        }

        public List<Path> getListOfFiles() {
            return listOfFiles;
        }

        private void search(Path file) {
            Path name = file.getFileName();
            if (name != null && matcher.matches(name)) {
                numOfMatches++;
                System.out.println("Found: " + file);
//                add found files in list
                listOfFiles.add(file);
            }
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            search(file);
            return CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) {
            handler.handle(file, exc);

//        return CONTINUE;
            return handler.handle(file, exc);
        }

    }
}

