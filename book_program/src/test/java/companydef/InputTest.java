package companydef;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 1 on 12.09.2018.
 */
public class InputTest {

    @Test
    public void extListTest(){
        final Searcher searcher = new Searcher(new ArrayList<>());
        Assert.assertEquals("glob:*.{txt,html}", searcher.getPattern(Arrays.asList("txt", "html")));
    }

    @Test
    public void foundFilesTest()throws IOException{
        List<Path> listOfFiles;
        final Searcher searcher = new Searcher(Arrays.asList("txt", "html"));
        String startPath = "e:\\Games\\testFor\\";

        listOfFiles = searcher.search(Paths.get(startPath));

        List<Path> expFiles = new ArrayList<>();
//
        expFiles.add(Paths.get("e:\\Games\\testFor\\files viewmodel.txt"));
        expFiles.add(Paths.get("e:\\Games\\testFor\\text.txt"));
        expFiles.add(Paths.get("e:\\Games\\testFor\\second\\ex 4.txt"));
        expFiles.add(Paths.get("e:\\Games\\testFor\\second\\fourth\\testHT.html"));
        expFiles.add(Paths.get("e:\\Games\\testFor\\second\\third\\AvaS.txt"));
        expFiles.add(Paths.get("e:\\Games\\testFor\\second\\third\\test.txt"));

        Assert.assertTrue(listOfFiles.containsAll(expFiles));

    }

//  can be deleted
    @Ignore
    @Test(expected = FileNotFoundException.class)
    public void pathTest()throws IOException{
        List<Path> listOfFiles = new ArrayList<>();
        final Searcher searcher = new Searcher(Arrays.asList("txt", "html"));

        listOfFiles = searcher.search(null);


    }

    @Test(expected = NullPointerException.class)
    public void handlerTest(){
            Searcher searcher = new Searcher(new ArrayList<>());
        searcher.setErrorHandler(null);
//        Assert.assertEquals(null, searcher.setErrorHandler(null));

    }

    @Test(expected = FileNotFoundException.class)
    public void pathExistTest()throws IOException{
        List<Path> listOfFiles;
        final Searcher searcher = new Searcher(Arrays.asList("txt", "html"));
        String startDir = "e:\\Fotos\\";

        listOfFiles = searcher.search(Paths.get(startDir));

//        Assert.assertEquals(null, listOfFiles);
        Assert.assertNull(listOfFiles);
    }


}
