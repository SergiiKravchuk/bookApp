package companydef;

import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
    public void foundFilesTest()throws Exception{
        List<Path> listOfFiles;
        List<Path> listOfNames = new ArrayList<>();
        final Searcher searcher = new Searcher(Arrays.asList("txt", "html"));
        ClassLoader classLoader = getClass().getClassLoader();
        final URL resource = classLoader.getResource("");
        Path startPath = Paths.get(resource.toURI());

        listOfFiles = searcher.search(startPath);

        for(Path file: listOfFiles){

            System.out.println("file " + file);
            listOfNames.add(file.getFileName());
        }

        List<Path> expFiles = new ArrayList<>();

        final List<String> strings = Arrays.asList("files viewmodel.txt", "text.txt", "ex 4.txt",
                "testHT.html", "AvaS.txt", "test.txt");

        for (String path : strings){
            expFiles.add(Paths.get(path));
        }
        Assert.assertTrue(listOfNames.containsAll(expFiles));

    }

    public void showItems(List<Path> list){
        for (Path p : list){
            System.out.print(p + " ");
        }
        System.out.println();
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

        String startFile = "add date";

        listOfFiles = searcher.search(Paths.get(startFile));

        Assert.assertNull(listOfFiles);
    }


}
