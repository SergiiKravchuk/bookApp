package companydef;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;

/**
 * Created by 1 on 11.09.2018.
 */
public class DefaultFileSeacherErrorHandler implements FileSearcherErrorHandler {
    @Override
    public FileVisitResult handle(Path file, IOException exc) {
        System.out.println("Access denied: " + file);
        return FileVisitResult.CONTINUE;
    }
}
