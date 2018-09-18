package companydef;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;

/**
 * Created by 1 on 04.09.2018.
 */
public interface FileSearcherErrorHandler {
    FileVisitResult handle(Path file, IOException exc);
}
