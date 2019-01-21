package companydef.bookAppGUI;

/**
 * Created by 1 on 29.12.2018.
 */

import companydef.SearchStarter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileSearcher implements ShelfFiller{

    private List<CheckBox> checkBoxList = new ArrayList<>();
    private ObservableList<Path> files;
    String tmp = "";
    String file;

    @FXML
    private BorderPane searchPane;

    @FXML
    private ListView<Path> filesView;

    @FXML
    private TextField selectedExtension;

    @FXML
    private ListView<?> fileExtensionList;

    @FXML
    private ToggleButton btn_enableAll;

    @FXML
    private Button btn_search;

    @FXML
    private ComboBox<CheckBox> fileExtension;

    @FXML
    private CheckBox cb_txt;

    @FXML
    private CheckBox cb_pdf;

    @FXML
    private CheckBox cb_doc;

    @FXML
    private TextField field_startPath;

    @FXML
    private Button btn_startPath;

    @FXML
    private Button btn_addToLibrary;

    @FXML
    private Button btn_test;

    @FXML
    void initialize() {

        List<String> listOfExtension1 = Arrays.asList("txt", "pdf", "doc");//need to extend
        for (String ext : listOfExtension1){
            checkBoxList.add(new CheckBox(ext));
        }
        List<String> listOfExtension = new ArrayList<>();
        initializeCBList();

        ObservableList<CheckBox> list = FXCollections.observableList(checkBoxList);

        fileExtension.setItems(list);

        fileExtension.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("new val " + newValue);
        });

        filesView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        filesView.setOnMouseClicked(event -> {

            if (event.getClickCount() == 2){
                files = filesView.getSelectionModel().getSelectedItems();
                String selectedFiles = "";
                for (Path filePath : files){
                    selectedFiles += filePath.toString();
                }

                selectedExtension.setText(selectedFiles);

                System.out.println("bookssize " + MainMenu.books.size());

            }
        });

        btn_test.setOnAction(event -> {

                files = filesView.getSelectionModel().getSelectedItems();
                String selectedFiles = "";
                for (Path filePath : files){
                    selectedFiles += filePath.toString();
                }
                selectedExtension.setText(selectedFiles);

                System.out.println("bookssize " + MainMenu.books.size());
        });

        btn_addToLibrary.setOnAction(event -> {

            try {

                for (Path filePath : files) {
                    String file = filePath.toString();
                    ImageView imageView = fillShelf(file, MainMenu.booksPanel);
                    setOnAction(imageView, MainMenu.primaryStage);
                    MainMenu.books.add(file);
                }
            }catch (NullPointerException ex){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("File name field is empty");
                alert.setHeaderText(null);
                alert.setContentText("Please fill the file name field");
                alert.showAndWait();
            }

        });

        btn_search.setOnAction(event -> {

            System.out.println("test");
            listOfExtension.clear();
            getSelectedExtension(listOfExtension);

            try {

                SearchStarter searcher = new SearchStarter(field_startPath.getText(), listOfExtension);
                searcher.startSearch();

                for (Path p : searcher.getListOfFiles()){
                    System.out.println(p);
                }

                ObservableList<Path> tmp = FXCollections.observableArrayList(searcher.getListOfFiles());
                filesView.setItems(tmp);

            }catch (IOException ex){
                System.out.println("Error " + ex.getMessage());

            }
        });

        btn_startPath.setOnAction(event -> field_startPath.setText(StartPathChooser.startPathChoose(field_startPath.getScene().getWindow())));

        btn_enableAll.setOnAction(event -> {

            for (CheckBox checkBox : checkBoxList){
                if (btn_enableAll.isSelected()) checkBox.setSelected(true);
                else checkBox.setSelected(false);
            }
        });

    }

    public String getFile() {
        return file;
    }

    private void isChecked(){

        for (CheckBox checkBox : checkBoxList){
            if (checkBox.isSelected()) System.out.println("selected " + checkBox.getText());
        }
    }

    private void initializeCBList(){

        checkBoxList.add(cb_doc);
        checkBoxList.add(cb_txt);
        checkBoxList.add(cb_pdf);
    }

    private void getSelectedExtension(List<String> listOfExtension){

        for (CheckBox cb : checkBoxList){
            if (cb.isSelected()) {
                listOfExtension.add(cb.getText().substring(1));
            }

        }
    }

}