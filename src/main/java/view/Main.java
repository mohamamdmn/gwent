package view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Card;
import model.Game;
import model.Tabel;
import model.User;

import java.util.ArrayList;
import java.util.Objects;

import static javafx.application.Application.launch;

public class Main extends Application {
    public static Stage stage;


    public static Tabel makeCardForUser(GameMenu gameMenu) {
        ObservableList<Card> cards = FXCollections.observableArrayList();
        Card card;
        for (int i = 1; i <= 20; i++) {
            if (i % 3 == 0) {
                card = new Card("salam",GameMenu.class.getResource("/Image/card/" + i + ".jpg").toExternalForm(), "close", false, 20, GameMenu.class.getResource("/Image/dc/" + i + ".jpg").toExternalForm(), "spy");
            } else if (i % 3 == 1) {
                card = new Card("salam",GameMenu.class.getResource("/Image/card/" + i + ".jpg").toExternalForm(), "ranged", false, 20, GameMenu.class.getResource("/Image/dc/" + i + ".jpg").toExternalForm(), "muster");
            } else
                card = new Card("salam",GameMenu.class.getResource("/Image/card/" + i + ".jpg").toExternalForm(), "siege", false, 20, GameMenu.class.getResource("/Image/dc/" + i + ".jpg").toExternalForm(), "" +
                        "");


            cards.add(card);
        }

        Tabel tabel = new Tabel(cards,);
        return tabel;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        GameMenu gameMenu = new GameMenu();
        User currentUser = new User();
        User rivalUser = new User();
        currentUser.setTabel(makeCardForUser(gameMenu));
        currentUser.getTabel().setRandomDeckFromAllCard();
        rivalUser.setTabel(makeCardForUser(gameMenu));
        rivalUser.getTabel().setRandomDeckFromAllCard();

        Game game = new Game(currentUser, rivalUser);
        gameMenu.setGame(game);
        gameMenu.start(stage);
    }
    public static Stage getStage(){
        return stage;
    }
}