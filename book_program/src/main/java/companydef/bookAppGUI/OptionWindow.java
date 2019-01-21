package companydef.bookAppGUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

/**
 * Created by 1 on 29.12.2018.
 */
public class OptionWindow {

    static TextSetting textSetting;
    static PageSetting pageSetting;

    private String defaultFont;
    private int defaultSize;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ColorPicker cbox_backColor;

    @FXML
    private ColorPicker cbox_textColor;

    @FXML
    private ComboBox<String> cbox_fontType;

    @FXML
    private ComboBox<String> cbox_fontSize;

    @FXML
    private ComboBox<String> cbox_textSpacing;

    @FXML
    private TextField field_paddUp;

    @FXML
    private TextField field_paddRight;

    @FXML
    private TextField field_paddDown;

    @FXML
    private TextField field_paddLeft;

    @FXML
    private Button btn_cancel;

    @FXML
    private Button btn_apply;

    @FXML
    private ImageView image_right;

    @FXML
    private ImageView image_up;

    @FXML
    private ImageView image_down;

    @FXML
    private ImageView image_left;



    @FXML
    void initialize() {

        setDefaultTextSetting(ReaderWindow.font, ReaderWindow.size);

        ObservableList<String> sizeList = FXCollections.observableArrayList("8", "10", "12", "14", "16", "18", "20", "22", "24", "26", "28", "30", "32", "36");
        ObservableList<String> fontsList = FXCollections.observableArrayList("Arial", "Times New Roman", "Calibri");
        ObservableList<String> spacingList = FXCollections.observableArrayList("10","15","20","25");

        cbox_fontSize.setItems(sizeList);
        cbox_fontType.setItems(fontsList);
        cbox_textSpacing.setItems(spacingList);

        cbox_fontType.setEditable(true);
        cbox_fontSize.setEditable(true);
        cbox_textSpacing.setEditable(true);

        initUserSettings();

        btn_apply.setOnAction(event -> {

            String font = cbox_fontType.getValue();
            String size = cbox_fontSize.getValue();
            String background = cbox_backColor.getValue().toString();
            String textColor = cbox_textColor.getValue().toString();
            String[] textPadding = {field_paddUp.getText(), field_paddRight.getText(), field_paddDown.getText(), field_paddLeft.getText()};

            textSetting = new TextSetting(font, size, textColor);
            pageSetting = new PageSetting(background, textPadding);

            applySetting();

//            ReaderWindow.textArea.setFont(new Font(font, Double.valueOf(size)));
//            ReaderWindow.textArea.setStyle("-fx-background-color: #" + background.substring(2, 8) + ";"
//                    + "-fx-text-fill: #" + textColor.substring(2, 8) + ";"
//                    + " -fx-padding: " + field_paddUp.getText() + " " + field_paddRight.getText() + " " + field_paddDown.getText() + " " + field_paddLeft.getText() + ";");
//            Region content = (Region) ReaderWindow.textArea.lookup(".content");
//            content.setStyle("-fx-background-color: #" + background.substring(2, 8) + ";");
        });


    }

    public void setDefaultTextSetting(String defaultFont, int defaultSize){
        System.out.println("font " + defaultFont + " " + defaultSize);
        this.defaultFont = defaultFont;
        this.defaultSize = defaultSize;
    }

    private void initUserSettings(){

        if (textSetting != null) {

            System.out.println("textsett not null");
            cbox_fontType.setValue(textSetting.textFont);
            cbox_fontSize.setValue(textSetting.textSize);
            Color color = Color.web(textSetting.textColor);
            cbox_textColor.setValue(color);
        }
        else if (pageSetting != null){

            System.out.println("pagesett not null");
            field_paddUp.setText(pageSetting.textPadding[0]);
            field_paddRight.setText(pageSetting.textPadding[1]);
            field_paddDown.setText(pageSetting.textPadding[2]);
            field_paddLeft.setText(pageSetting.textPadding[3]);
            Color color = Color.web(pageSetting.backgroundColor);
            cbox_backColor.setValue(color);
        }
        else{

            System.out.println("else deffont "+ defaultFont + " " + defaultSize );
            cbox_textColor.setValue(Color.BLACK);
            cbox_fontType.setValue(defaultFont);
            cbox_fontSize.setValue(String.valueOf(defaultSize));
            primarySetting(defaultFont, defaultSize);
        }
    }

    public static void applySetting(){

        ReaderWindow.textArea.setFont(new Font(textSetting.textFont, Double.valueOf(textSetting.textSize)));
        ReaderWindow.textArea.setStyle("-fx-background-color: #" + pageSetting.backgroundColor.substring(2, 8) + ";"
                + "-fx-text-fill: #" + textSetting.textColor.substring(2, 8) + ";"
                + " -fx-padding: " + pageSetting.textPadding[0] + " " + pageSetting.textPadding[1] + " " + pageSetting.textPadding[2] + " " + pageSetting.textPadding[3] + ";");
        Region content = (Region) ReaderWindow.textArea.lookup(".content");
        content.setStyle("-fx-background-color: #" + pageSetting.backgroundColor.substring(2, 8) + ";");
    }

    private void initImages(){

        try {

            ClassLoader classLoader = getClass().getClassLoader();
            URL resource_imageUp = classLoader.getResource("images/cubeUp1.png");
            URL resource_imageDown = classLoader.getResource("images/cubeDown1.png");
            URL resource_imageRight = classLoader.getResource("images/cubeRight1.png");
            URL resource_imageLeft = classLoader.getResource("images/cubeLeft1.png");

            Path path = Paths.get(resource_imageUp.toURI());
            System.out.println(path);
            Image tmpImageUp = new Image(path.toString());
            path = Paths.get(resource_imageDown.toURI());
            System.out.println(path);
            Image tmpImageDown = new Image(path.toString());
            path = Paths.get(resource_imageRight.toURI());
            System.out.println(path);
            Image tmpImageRight = new Image(path.toString());
            path = Paths.get(resource_imageLeft.toURI());
            System.out.println(path);
            Image tmpImageLeft = new Image(path.toString());

            image_up.setImage(tmpImageUp);
            image_down.setImage(tmpImageDown);
            image_right.setImage(tmpImageRight);
            image_left.setImage(tmpImageLeft);
        }catch (Exception ex){
            System.out.println("Error with images " + ex.getMessage());
        }
    }

    private void primarySetting(String font, int size){

        ReaderWindow.textArea.setFont(new Font(font, size));
    }

}
