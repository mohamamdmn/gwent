package view;

import controller.PregameController;
import enums.Faction;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.border.Border;
import java.net.URL;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class PregameMenu extends Application {
    private PregameController pregameController;
    private Faction currentFaction = Faction.Nilfgaars;//Monstres is example
    private Image factionIcon;
    private Label factionName = new Label();
    @FXML
    private Button changeFactionButton;
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        launch(args);
    }
    @FXML
    public void initialize(){

    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = PregameMenu.class.getResource("/Fxml/Fxml.fxml");
        Pane root = fxmlLoader.load(url);
        Scene scene = new Scene(root);
        pregameController = new PregameController(root, stage);
        root.setStyle("-fx-background-color: black;");
        stage.setScene(scene);
        stage.show();
    }

    private GridPane gridPaneExtract(Pane pane) {
        VBox vBox = (VBox) pane.getChildren().get(0);
        HBox h = (HBox) vBox.getChildren().get(3);
        ScrollPane s = (ScrollPane) h.getChildren().get(0);
        GridPane g = (GridPane) s.getContent();
        return g;
    }
    private GridPane gridPane2ex(Pane pane){
        VBox vBox = (VBox) pane.getChildren().get(0);
        HBox h = (HBox) vBox.getChildren().get(3);
        ScrollPane s = (ScrollPane) h.getChildren().get(2);
        GridPane g = (GridPane) s.getContent();
        return g;
    }

}