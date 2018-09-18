package companydef;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

import static java.nio.file.FileVisitResult.CONTINUE;


public class Searcher {

    private PathMatcher matcher;

    private FileSearcherErrorHandler handler = new DefaultFileSeacherErrorHandler();

    Searcher(List<String> exList) {
        String fileExtend = getPattern(exList);

        matcher = FileSystems.getDefault().getPathMatcher(fileExtend);
    }

    public List<Path> search(Path startDir){
//        adds
        try {
            if (Files.exists(startDir)) {
                SimpleHandler visitor = new SimpleHandler(startDir, matcher, handler);
                visitor.search();

                return visitor.getListOfFiles();
            } /*else {
                return null;
            }*/
        }catch (IOException ex){
            System.out.println(ex);
        }
        return null;
    }

    /*private*/ String getPattern(List<String> exList) {
        String pattern = "glob:*.{";

        pattern += String.join(",", exList);

        pattern += "}";
        System.out.println(pattern);
        return pattern;
    }

    void setErrorHandler(FileSearcherErrorHandler handler) {

        this.handler = Objects.requireNonNull(handler, "Handler must be not null");
    }

    private class SimpleHandler extends SimpleFileVisitor<Path> {

        private PathMatcher matcher;
        private Path startDir;

        private FileSearcherErrorHandler handler;

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

        private boolean isMatch(Path file) {
            Path name = file.getFileName();
            return name != null && matcher.matches(name);
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            if(isMatch(file)){
                System.out.println("Found: " + file);

                listOfFiles.add(file);
            }
            return CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) {
            return handler.handle(file, exc);
        }

    }
}

