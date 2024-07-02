package controller;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import model.Game;

public class EndgameController {

    public ImageView imageView;
    public GridPane listBord;


    public void setListBord(Game game) {
        listBord.add(new Text("Round1"), 1, 0);
        listBord.add(new Text("Round2"), 2, 0);
        listBord.add(new Text("Round3"), 3, 0);


        listBord.add(new Text("Player1"), 0, 1);
        listBord.add(new Text("Player2"), 0, 2);
        listBord.add(new Text(game.getMyPoints().get(0).toString()), 1, 1);
        listBord.add(new Text(game.getRivalPoints().get(0).toString()), 1, 2);
        listBord.add(new Text(game.getMyPoints().get(1).toString()), 2, 1);
        listBord.add(new Text(game.getRivalPoints().get(1).toString()), 2, 2);

        if (game.getMyPoints().size() > 2) {
            listBord.add(new Text(game.getMyPoints().get(2).toString()), 3, 1);
            listBord.add(new Text(game.getRivalPoints().get(2).toString()), 3, 2);
        } else {
            listBord.add(new Text("0"), 3, 1);
            listBord.add(new Text("0"), 3, 2);
        }
    }

    public void setImageView(Game game) {
        int myHp = game.getCurrentUser().getTabel().getHp();
        int rivalHp = game.getCurrentUser().getTabel().getHp();
        if (myHp > rivalHp) {
            imageView.setImage(new Image(Game.class.getResource("/gwentImages/img/icons/end_win.png").toExternalForm()));
        } else {
            imageView.setImage(new Image(Game.class.getResource("/gwentImages/img/icons/end_lose.png").toExternalForm()));
        }
    }

}

