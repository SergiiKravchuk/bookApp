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
import java.util.LinkedList;
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
        List<Path> listOfNames = new LinkedList<>();
        final Searcher searcher = new Searcher(Arrays.asList("txt", "html"));
        String startPath = "src\\test\\resources";
        System.out.println(Paths.get(startPath));

        listOfFiles = searcher.search(Paths.get(startPath));
        for(Path tmp: listOfFiles){
            listOfNames.add(tmp.getFileName());
        }

        List<Path> expFiles = new ArrayList<>();

        expFiles.add(Paths.get("files viewmodel.txt"));
        expFiles.add(Paths.get("text.txt"));
        expFiles.add(Paths.get("ex 4.txt"));
        expFiles.add(Paths.get("testHT.html"));
        expFiles.add(Paths.get("AvaS.txt"));
        expFiles.add(Paths.get("test.txt"));
        
        Assert.assertTrue(listOfNames.containsAll(expFiles));

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

    }

    @Test(expected = FileNotFoundException.class)
    public void pathExistTest()throws IOException{
        List<Path> listOfFiles;
        final Searcher searcher = new Searcher(Arrays.asList("txt", "html"));
        String startDir = "e:\\Fotos\\";

        listOfFiles = searcher.search(Paths.get(startDir));

        Assert.assertNull(listOfFiles);
    }


}
