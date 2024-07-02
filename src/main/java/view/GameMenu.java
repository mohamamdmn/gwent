package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Card;
import model.Game;
import model.Tabel;
import model.User;

public class GameMenu extends Application {
    public GameMenuController controller;
    public Game game;

    @Override
    public void start(Stage stage ) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(GameMenu.class.getResource("/Fxml/game.fxml"));
        Pane pane = fxmlLoader.load();
        Scene scene = new Scene(pane);
        this.controller = fxmlLoader.getController();
//        initialize(game.getCurrentUser(),this);
//        initialize(game.getRivalUser(),this);
        controller.setGame(game,this,stage);
        controller.start();
        stage.setScene(scene);
        stage.show();
    }
    public GameMenuController getController(){
        return controller;
    }
    public void  setGame(Game game){
        this.game = game;
    }
    public Game getGame(){
        return game;
    }
//    public  void initialize(User user, GameMenu gameMenu){
//        for(Card card : user.getTabel().getCards()){
//            card.initialize(Tabel.getWeadthCard(),78.4);
//        }
//    }
}
