package companydef.bookAppGUI;

/**
 * Created by 1 on 04.01.2019.
 */
public class BookInfo {

    String author;
    String collection;
    String category;

    public BookInfo(String author, String collection, String category){

        this.author = author;
        this.collection = collection;
        this.category = category;

    }

    public BookInfo(String defaultString){

        this.author = defaultString;
        this.collection = defaultString;
        this.category = defaultString;
    }
}
