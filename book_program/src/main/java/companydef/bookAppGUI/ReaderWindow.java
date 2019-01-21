package companydef.bookAppGUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Created by 1 on 30.12.2018.
 */
public class ReaderWindow {

    private String pathNoImage = "/leftarrow.png";
    public static TextArea textArea = new TextArea();
    static String font = "Times New Roman";
    static int size = 16;


    @FXML
    private BorderPane mainPane;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Button btn_back;

    @FXML
    private MenuItem item_background;

    @FXML
    private MenuItem item_font;

    @FXML
    private Menu btn_options;

    @FXML
    void initialize() {

        mainPane.setCenter(textArea);

        textArea.setWrapText(true);
        textArea.setEditable(false);

        ImageView imageView = new ImageView(pathNoImage);
        imageView.setFitHeight(25);
        imageView.setFitWidth(25);
        btn_back.setGraphic(imageView);

        btn_back.setOnAction(event -> returnToMainScreen());

        item_background.setOnAction(event -> optionWindow(font, size));

        fileReaderSelect(MainMenu.books.get(MainMenu.selectedIndex));

    }

    private void fileReaderSelect(String file){

        String fileExtension = file.substring(file.length() - 3);
        System.out.println("srt"  + fileExtension);

        switch(fileExtension){
            case "doc":{
                docReader(file);
                break;
            }
            case "docx":{
                docxReader(file);
                break;
            }
            case "txt":{
                readFile(file);
                break;
            }
            default:{
                alertExtension();
                break;
            }
        }
    }

    public static void alertExtension(){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Unknown file extension");
        alert.setHeaderText(null);
        alert.setContentText("Common file extension cant be read by program.");
        alert.showAndWait();
    }


    public void readFile(String file){

        String bookText = "";
        try {

            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
            Scanner scanner = new Scanner(br);
            while (scanner.hasNext()){
                bookText += scanner.nextLine() + "\n";
            }
            textArea.setText(bookText);
            setDefaultSetting();


        }catch (FileNotFoundException ex){
            System.out.println(ex.getMessage());
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void returnToMainScreen(){

        try{

            Parent root = FXMLLoader.load(getClass().getResource("../../../resources/fxml/mainScreenV1.01.fxml"));
            MainMenu.primaryStage.setScene(new Scene(root, 800, 300));
        }catch (Exception ex){
            System.out.println("in reader window");
        }
    }

    private void optionWindow(String font, int size){

        try{

            Stage options = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../resources/fxml/optionWindow.fxml"));
            Parent root = loader.load();
            options.setScene(new Scene(root, 600, 400));
            options.setResizable(false);
            options.show();
        }catch (Exception ex){

        }
    }

    private void setDefaultSetting(){

        if (OptionWindow.textSetting != null && OptionWindow.pageSetting != null)
            OptionWindow.applySetting();
        else
            textArea.setFont(new Font(font, size));
    }

    private void docReader(String fileName){

        try {
            File file = new File(fileName);
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());

            HWPFDocument doc = new HWPFDocument(fis);

            WordExtractor we = new WordExtractor(doc);

            String[] paragraphs = we.getParagraphText();
            String fileText = "";

            System.out.println("Total no of paragraph "+paragraphs.length);
            for (String para : paragraphs) {
                fileText += para.toString();
            }
            textArea.setText(fileText);
            setDefaultSetting();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void docxReader(String fileName){

        try {
            File file = new File(fileName);
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());

            XWPFDocument document = new XWPFDocument(fis);

            List<XWPFParagraph> paragraphs = document.getParagraphs();

            System.out.println("Total no of paragraph "+paragraphs.size());
            for (XWPFParagraph para : paragraphs) {
                System.out.println(para.getText());
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
