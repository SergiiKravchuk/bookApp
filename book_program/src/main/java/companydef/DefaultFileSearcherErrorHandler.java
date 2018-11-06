package companydef;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;

/**
 * Created by 1 on 11.09.2018.
 */
public class DefaultFileSearcherErrorHandler implements FileSearcherErrorHandler {
    private static Logger logger = LoggerFactory.getLogger(Main.class);
    @Override
    public FileVisitResult handle(Path file, IOException exc) {
//        System.out.println("Access denied: " + file);
        logger.info("Access denied: " + file);
        return FileVisitResult.CONTINUE;
    }
}
