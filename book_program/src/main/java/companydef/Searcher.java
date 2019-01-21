package companydef;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

import static java.nio.file.FileVisitResult.CONTINUE;


public class Searcher {

    static Logger logger = LoggerFactory.getLogger(SearchStarter.class);
    private PathMatcher matcher;

    private FileSearcherErrorHandler errorHandler = new DefaultFileSearcherErrorHandler();

    Searcher(List<String> exList) {
        String fileExtend = getPattern(exList);

        matcher = FileSystems.getDefault().getPathMatcher(fileExtend);
    }

    public List<Path> search(Path startDir)throws IOException{
//        adds
            if (Files.exists(startDir)) {
                SimpleHandler visitor = new SimpleHandler(startDir, matcher, errorHandler);
                visitor.search();

                return visitor.getListOfFiles();
            }else {
                throw new FileNotFoundException();

            }
    }

    protected String getPattern(List<String> exList) {
        String pattern = "glob:*.{";

        pattern += String.join(",", exList);

        pattern += "}";
        System.out.println(pattern);
        return pattern;
    }

    void setErrorHandler(FileSearcherErrorHandler errorHandler) {

        this.errorHandler = Objects.requireNonNull(errorHandler, "Handler must be not null");
    }

    private class SimpleHandler extends SimpleFileVisitor<Path> {

        private PathMatcher matcher;
        private Path startDir;

        private FileSearcherErrorHandler errorHandler;

        private List<Path> listOfFiles = new ArrayList<>();

        SimpleHandler(Path startDir, PathMatcher matcher, FileSearcherErrorHandler errorHandler) {
            this.matcher = matcher;
            this.startDir = startDir;

            this.errorHandler = errorHandler;
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
//                System.out.println("Found: " + file);
                logger.info("Found: " + file );

                listOfFiles.add(file);
            }
            return CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) {
            return errorHandler.handle(file, exc);
        }


    }
}

