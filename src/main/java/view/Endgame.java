package view;

import controller.EndgameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Game;

import static view.Main.stage;

public class Endgame extends Application {
    public Game game;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(GameMenu.class.getResource("/Fxml/endGame.fxml"));
        Pane pane = fxmlLoader.load();
        Scene scene = new Scene(pane);
        EndgameController endgameController = fxmlLoader.getController();
        endgameController.setImageView(game);
        endgameController.setListBord(game);
        stage.setScene(scene);
        stage.show();
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
