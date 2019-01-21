package companydef.bookAppGUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MainMenu implements ShelfFiller{


    ClassLoader classLoader = getClass().getClassLoader();
    public static String pathNoImage = "/image/noImage1.png";
    public static Image imgNoImage;

    static List<String> books = new ArrayList<>();
    static ContextMenu contextMenu = new ContextMenu();
    static HBox booksPanel = new HBox();
    static boolean start = true;

    static Stage primaryStage;
    public static int selectedIndex;


    @FXML
    private ScrollPane bookPanel;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu menu_file;

    @FXML
    private MenuItem item_open;

    @FXML
    private MenuItem item_exit;

    @FXML
    private MenuItem item_search;

    @FXML
    private MenuItem item_save;

    @FXML
    private Menu menu_option;

    @FXML
    private MenuItem item_visuals;

    @FXML
    private Menu menu_help;

    @FXML
    private MenuItem item_about;

    @FXML
    private Label choose;

    @FXML
    void initialize() {

        pathNoImage = classLoader.getResource("noImage1.png").getPath();
        System.out.println("image " + pathNoImage);
        imgNoImage = new Image(pathNoImage);

        choose.setText("No book is choosen");
        initializeMenuBar();

        if (start) {
            readConfigFile();
            readLibraryFile();
            setValues();
            initializeContextMenu();
            start = false;
        }

        bookPanel.setContent(booksPanel);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void initializeMenuBar(){

        item_open.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        item_save.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        item_search.setAccelerator(KeyCombination.keyCombination("Ctrl+F"));
        item_exit.setAccelerator(KeyCombination.keyCombination("Ctrl+E"));

        item_open.setOnAction(event -> {

            choose.setText("open from menu bar");
            String file = BookFileChooser.fileChoose(primaryStage).toString();
            if (isEnable(file.substring(file.length() - 3))) {
                if (!file.isEmpty()) {
                    books.add(file);
                    InfoWindow.bookInfoList.add(new BookInfo("No information"));
                    ImageView imageView = fillShelf(file, MainMenu.booksPanel);
                    setOnAction(imageView, MainMenu.primaryStage);
                }
            }
            else ReaderWindow.alertExtension();
        });

        item_save.setOnAction(event -> {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Save confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Save all changes");
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> {
                        if (!books.isEmpty()) {
                            ConfigFileCreator.writeConfigFile();
                            ConfigFileCreator.writeLibraryFile();
                        }
            });
        });

        item_search.setOnAction(event -> {

            choose.setText("search from menu bar");
            searchFiles();
        });

        item_exit.setOnAction(event -> {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Do you want to exit?");
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> System.exit(0));
        });

    }

    private void initializeContextMenu(){

        MenuItem info = new MenuItem("Information");
        MenuItem open = new MenuItem("Open");
        MenuItem delete = new MenuItem("Delete");

        SeparatorMenuItem separator = new SeparatorMenuItem();

        contextMenu.getItems().addAll(open, separator, info, delete);

        open.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        info.setAccelerator(new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN));

        open.setOnAction(event -> {

            choose.setText("Open is chosen");
            readScene(primaryStage);
        });

        info.setOnAction(event -> {

            choose.setText("Info is chosen");
            try {
                bookInfo();
            } catch (Exception ex) {
                System.out.println("error: " + ex.getMessage());
            }
        });

        delete.setOnAction(event -> {

            books.remove(selectedIndex);
            booksPanel.getChildren().remove(selectedIndex);
            InfoWindow.bookInfoList.remove(selectedIndex);
        });
    }

    private boolean isEnable(String extension){

        List<String> extensions = Arrays.asList("doc", "docx", "txt");
        if (extensions.contains(extension))
            return true;
        else
            return false;
    }

    private void readConfigFile(){

        boolean flag = true;
        try {

            FileInputStream fis = new FileInputStream("configT.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
            Scanner scanner = new Scanner(br);
            while (scanner.hasNext()){

                String tmp = scanner.nextLine();
                if (tmp.equals("Text setting:")){
                    flag = true;
                    continue;
                }else if (tmp.equals("Page setting:")){
                    flag = false;
                    continue;
                }

                if (flag){
                    OptionWindow.textSetting.textFont = getValue(tmp);
                    OptionWindow.textSetting.textSize = getValue(scanner.nextLine());
                    OptionWindow.textSetting.textColor = getValue(scanner.nextLine());
                }
                else if (!flag) {
                    OptionWindow.pageSetting.backgroundColor = getValue(tmp);
                    String tmpPadding = getValue(scanner.nextLine());
                    OptionWindow.pageSetting.textPadding = toArray(tmpPadding);

                }
            }
            br.close();

        }catch (IOException ex){

            System.out.println("Error with reading config " + ex.getMessage());
        }
    }

    private void readLibraryFile(){

        boolean flag = true;
        try {

            FileInputStream fis = new FileInputStream("library.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
            Scanner scanner = new Scanner(br);
            while (scanner.hasNext()){

                String tmp = scanner.nextLine();
                if (tmp.equals("Books:")) {
                    flag = true;
                    continue;
                }
                else if (tmp.equals("Information about books:")){
                    flag = false;
                    continue;
                }

                if (flag) books.add(tmp);
                else{
                    String tmpColl = scanner.nextLine();
                    String tmpCateg = scanner.nextLine();
                    BookInfo info = new BookInfo(tmp, tmpCateg, tmpColl);
                    InfoWindow.bookInfoList.add(info);
                }


            }
            br.close();
        }catch (IOException ex){

        }
    }

    private String getValue(String value){

        for (int i = 0; i < value.length(); i++){
            if (value.charAt(i) == ':') {
                value = value.substring(++i, value.length() - 1);
                break;
            }
        }
        return value;
    }

    private String[] toArray(String value){

        String[] padding = new String[4];
        int index = 0;
        int pointer = 0;

        for (int i = 0; i < value.length(); i++){
            if (value.charAt(i) == ' ') {
                padding[index] = value.substring(pointer, i);
                pointer = i+1;
                index++;
            }
        }

        return padding;
    }

    private void setValues(){

        for (String val : books){
            ImageView imageView = fillShelf(val, booksPanel);
            setOnAction(imageView, primaryStage);
        }
    }

    private void bookInfo() throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("../../../resources/fxml/infoWindow.fxml"));
        Stage info = new Stage();
        info.setTitle("Book info");
        info.setScene(new Scene(root, 600, 400));
        info.setResizable(false);

        info.show();
    }

    private void searchFiles(){

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../resources/fxml/fileSearcher.fxml"));
            Parent root = loader.load();
            Stage search = new Stage();
            search.setTitle("Search Files");
            search.setScene(new Scene(root, 600, 400));
            search.setResizable(false);
            FileSearcher f = loader.getController();
            System.out.println("received file" + f.getFile());
            search.show();

        }catch (Exception ex){

        }

    }


}
