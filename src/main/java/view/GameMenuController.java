package view;

import controller.GameController;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Card;
import model.Game;
import model.Tabel;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameMenuController {
    public Game game;
    public Stage stage;
    public GameMenu gameMenu;
    public Card clickCard;
    public ArrayList<Text> myPowerGroup = new ArrayList<>();
    public ArrayList<Text> rivalPowerGroup = new ArrayList<>();
    public ArrayList<TilePane> myRowGroup = new ArrayList<>();
    public ArrayList<TilePane> rivalRowGroup = new ArrayList<>();


    public ImageView descriptionCard;
    public TilePane rivalClose;
    public TilePane rivalSiege;
    public TilePane rivalRanged;
    public TilePane mySiege;
    public TilePane myRanged;
    public TilePane myClose;


    public Text rivalSiegePower;
    public Text rivalClosePower;
    public Text rivalRangedPower;
    public Text myClosePower;
    public Text myRangedPower;
    public Text mySiegePower;
    public Text rivalFinalPower;
    public Text myFinalPower;
    public Rectangle passButton;
    public TilePane handTilePane;
    public Button Previous;
    public Button next;
    public ImageView myDiscardPile;
    public ImageView rivalDiscardPile;
    public ImageView rivalDeck;
    public ImageView myDeck;
    public Text numberOfRivalCardText;
    public Text numberOfMyCardText;
    public Text myPass;
    public Text rivalPass;

    public TilePane getHandTilePane() {
        return handTilePane;
    }

    public void setGame(Game game, GameMenu gameMenu, Stage stage) {
        this.game = game;
        this.gameMenu = gameMenu;
        this.stage = stage;
    }


    public void start() {
        handTilePane.getChildren().clear();
        for (int x = game.getCurrentUser().getTabel().getHand().size(); x > 0; x--) {
            Card card = game.getCurrentUser().getTabel().getHand().get(x - 1);
            ///makeHBoxDeckAndCardsWithAbilityForClickOr...Mouse
            handTilePane.getChildren().add(card);
            card.setOnMouseEntered(event -> {
                if (handTilePane.getChildren().contains(card)) {
                    enterMouseOnCard(card);
                }
            });

            card.setOnMouseExited(event -> {
                if (handTilePane.getChildren().contains(card)) {
                    exitMouseOnCard(card);
                }
            });

            card.setOnMouseClicked(event -> {
                try {
                    clickOnCard(card);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

        }

        //sets
        setImageCardInTilePane(handTilePane, false);
        setPowersGroup();
        setTilePaneGroup();


        setPass();


        ///setPowersInAllTime
        setPowersInAllTime();

        //setRivalCard
        setRivalCard();
        setMyCard();
        setDiscardPileImageView("me");
        setDiscardPileImageView("rival");
//        setMyAndRivalDeck();

    }

    private void setPass() {
        passButton.setFill(new ImagePattern(new Image(GameMenu.class.getResource("/Image/icons/pass.png").toExternalForm())));
    }

    public void setMyAndRivalDeck() {
        System.out.println(game.getCurrentUser().getTabel().getFaction());
        myDeck.setImage(new Image(Game.class.getResource("/gwentImages/img/icons/deck_back_" + game.getCurrentUser().getTabel().getFaction() + ".jpg").toExternalForm()));
        numberOfMyCardText.setText(String.valueOf(game.getCurrentUser().getTabel().getDeck().size()));
        numberOfMyCardText.setFont(Font.font("Arial", FontWeight.BOLD, 15));

        rivalDeck.setImage(new Image(Game.class.getResource("/gwentImages/img/icons/deck_back_" + game.getCurrentUser().getTabel().getFaction() + ".jpg").toExternalForm()));
        numberOfRivalCardText.setText(String.valueOf(game.getRivalUser().getTabel().getDeck().size()));
        numberOfRivalCardText.setFont(Font.font("Arial", FontWeight.BOLD, 15));

    }

    public void setDiscardPileImageView(String type) {
        Tabel tabel;
        ImageView imageView;
        Card card = null;
        if (type.equals("me")) {
            tabel = game.getCurrentUser().getTabel();
            imageView = myDiscardPile;
            if (!game.getCurrentUser().getTabel().getDiscardPile().isEmpty())
                card = game.getCurrentUser().getTabel().getDiscardPile().get(0);
        } else {
            tabel = game.getRivalUser().getTabel();
            imageView = rivalDiscardPile;
            if (!game.getRivalUser().getTabel().getDiscardPile().isEmpty())
                card = game.getRivalUser().getTabel().getDiscardPile().get(0);
        }
        if (!tabel.getDiscardPile().isEmpty()) {
            assert card != null;
            imageView.setImage(new Image(card.getPath()));
            imageView.setOnMouseClicked(event -> {
                setDescriptionCard(tabel.getCardFromDiscardPileWithUrl(imageView.getImage().getUrl()));
            });
        }
    }

    public void clickOnBackGround() {
        descriptionCard.setImage(null);
        if (clickCard != null) {
            if (!clickCard.getAbility().equals("spy") && getTilePane(clickCard, "me") != null) {
                getTilePane(clickCard, "me").setBackground(null);
            } else if (clickCard.getAbility().equals("spy") && getTilePane(clickCard, "rival") != null) {
                getTilePane(clickCard, "rival").setBackground(null);
            }

        }
        clickCard = null;
    }

    public Card setClickCard(Card imageView) {
        String url = imageView.getimageView().getImage().getUrl();
        ObservableList<Card> cards = game.getCurrentUser().getTabel().getHand();
        for (Card card : cards) {
            if (card.getPath().equals(url)) return card;
        }
        return null;
    }

    public void setPowersInAllTime() {//andPasses
        Timer timer = new Timer();
        int delay = 1000;
        TimerTask task = new TimerTask() {
            public void run() {
                setRowPower(1); //forMe
                setRowPower(0); //forRival
                setFinalPower(1); //forMe
                setFinalPower(0);//forRival
                setMyAndRivalPass();
            }
        };
        timer.schedule(task, delay, delay);
    }

    public void setMyAndRivalPass() {
        if (game.getCurrentUser().getTabel().getPass()) {
            myPass.setText("Passed");
            myPass.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        } else myPass.setText("");
        if (game.getRivalUser().getTabel().getPass()) {
            rivalPass.setText("Passed");
            rivalPass.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        } else rivalPass.setText("");
    }

    public TilePane getTilePane(Card card, String type) {
        if (type.equals("me")) {
            if (card == null) return null;
            if (card.getType().equals("close")) return myClose;
            else if (card.getType().equals("ranged")) return myRanged;
            else return mySiege;
        } else {
            if (card == null) return null;
            if (card.getType().equals("close")) return rivalClose;
            else if (card.getType().equals("ranged")) return rivalRanged;
            else return rivalSiege;
        }
    }

    public void setPowersGroup() {
        myPowerGroup.clear();
        myPowerGroup.add(myClosePower);
        myPowerGroup.add(myRangedPower);
        myPowerGroup.add(mySiegePower);

        rivalPowerGroup.clear();
        rivalPowerGroup.add(rivalClosePower);
        rivalPowerGroup.add(rivalRangedPower);
        rivalPowerGroup.add(rivalSiegePower);
    }

    public void setTilePaneGroup() {
        myRowGroup.clear();
        myRowGroup.add(myClose);
        myRowGroup.add(myRanged);
        myRowGroup.add(mySiege);

        rivalRowGroup.clear();
        rivalRowGroup.add(rivalClose);
        rivalRowGroup.add(rivalRanged);
        rivalRowGroup.add(rivalSiege);
    }

    public void setRivalCard() {
        for (int index = 0; index < rivalRowGroup.size(); index++) {
            rivalRowGroup.get(index).getChildren().clear();
            for (Card card : game.getRivalUser().getTabel().getAllRow().get(index)) {
                rivalRowGroup.get(index).getChildren().add(card.clone());
            }
            setImageCardInTilePane(rivalRowGroup.get(index), true);
        }
    }

    public void setMyCard() {
        for (int index = 0; index < myRowGroup.size(); index++) {
            myRowGroup.get(index).getChildren().clear();
            for (Card card : game.getCurrentUser().getTabel().getAllRow().get(index)) {
                myRowGroup.get(index).getChildren().add(card.clone());

            }
            setImageCardInTilePane(myRowGroup.get(index), true);
        }
    }


    public void clickOnCard(Card imageViewCard) throws Exception {
        if (clickCard != null) clickOnBackGround();
        setDescriptionCard(imageViewCard);
        clickCard = setClickCard(imageViewCard);
        System.out.println(clickCard.getAbility());
        Image backgroundImage = new Image(GameMenu.class.getResource("/Image/rows/" + clickCard.getType() + ".png").toExternalForm());
        if (!clickCard.getAbility().equals("spy")) {
            getTilePane(clickCard, "me").setBackground(new Background(new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        } else {
            getTilePane(clickCard, "rival").setBackground(new Background(new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        }


        if (!(clickCard.getType().equals("weather") || clickCard.getType().equals("spell"))) {
            GameController.logicSpecialCard(clickCard, this);
        } else {
            /////for Spell And wetherCard
        }
    }

    public void forCommonAndMedicCard(String type) {
        TilePane tilePane = getTilePane(clickCard, "me");
        tilePane.setOnMouseEntered(event -> {
            if (tilePane.equals(getTilePane(clickCard, "me"))) {
                tilePane.setStyle("-fx-border-color: yellow; -fx-border-width: 2px;");
                tilePane.setOnMouseClicked(event1 -> {
                    if (tilePane.equals(getTilePane(clickCard, "me"))) {
                        transitionCard(clickCard, getTilePane(clickCard, "me"), handTilePane, 0);

                    }
                });
            }
        });
        tilePane.setOnMouseExited(event -> {
            if (tilePane.equals(getTilePane(clickCard, "me"))) {
                tilePane.setStyle("");

            }
        });
    }

//    public void forDecoyCard() throws Exception {
//        for (TilePane tilePane : myRowGroup) {
//            for (Node card : tilePane.getChildren()) {
//                if (clickCard != null && clickCard.getAbility().equals("Decoy")) {
//                    card.setOnMouseClicked(event -> {
//                        clickCard.setType(((Card) card).getType());
//                        transitionCard((Card) card, handTilePane, getTilePane(clickCard,"me"), 1);
//                        transitionCard(clickCard, getTilePane(clickCard,"me"), handTilePane, 0);
//                    });
//                }
//            }
//        }
//    }

    public void forMusterCard() {
        TilePane tilePane = getTilePane(clickCard, "me");
        tilePane.setOnMouseEntered(event -> {
            if (tilePane.equals(getTilePane(clickCard, "me"))) {
                tilePane.setStyle("-fx-border-color: yellow; -fx-border-width: 2px;");
                tilePane.setOnMouseClicked(event1 -> {
                    for (Node card : handTilePane.getChildren()) {
                        if (card != clickCard) {
                            if (((Card) card).getPath().equals(clickCard.getPath())) {
                                transitionCard((Card) card, getTilePane((Card) card, "me"), handTilePane, 3);
                            }
                        }
                    }
                    for (Card card : game.getCurrentUser().getTabel().getDeck()) {
                        if (card.getPath().equals(clickCard.getPath())) {
                            game.getCurrentUser().getTabel().removeCardFromDeck(card);
                            game.getCurrentUser().getTabel().addCardToAppropriateArray(card);
                        }
                    }
                    transitionCard(clickCard, getTilePane(clickCard, "me"), handTilePane, 0);
                });
            }
        });
        tilePane.setOnMouseExited(event -> {
            if (tilePane.equals(getTilePane(clickCard, "me"))) {
                tilePane.setStyle("");

            }
        });
    }

    public void forSpyCard() {
        TilePane tilePane = getTilePane(clickCard, "rival");
        tilePane.setOnMouseEntered(event -> {
            if (tilePane.equals(getTilePane(clickCard, "rival"))) {
                tilePane.setStyle("-fx-border-color: yellow; -fx-border-width: 2px;");
                tilePane.setOnMouseClicked(event1 -> {
                    Random rand = new Random();
                    ArrayList<Integer> indexes = new ArrayList<>();

                    while (indexes.size() < 2) {
                        int randomIndex = rand.nextInt(game.getCurrentUser().getTabel().getDeck().size());
                        if (!indexes.contains(randomIndex)) {
                            indexes.add(randomIndex);
                        }
                    }
                    for (int index : indexes) {
                        Card card = game.getCurrentUser().getTabel().getDeck().get(index);
                        game.getCurrentUser().getTabel().addToHand(card);
                        game.getCurrentUser().getTabel().removeCardFromDeck(card);
                    }

                    transitionCard(clickCard, getTilePane(clickCard, "rival"), handTilePane, 4);


                });
            }
        });
        tilePane.setOnMouseExited(event -> {
            if (tilePane.equals(getTilePane(clickCard, "rival"))) {
                tilePane.setStyle("");

            }
        });

    }


    public void enterMouseOnCard(Card imageViewCard) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(200), imageViewCard);
        transition.setToY(-10);
        transition.play();
    }

    public void setDescriptionCard(Card card) {
        descriptionCard.setImage(new Image(card.getPathDc()));
    }

    public void exitMouseOnCard(Card imageViewCard) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(200), imageViewCard);
        transition.setToY(0);
        transition.play();
    }

    public void transitionCard(Card card, Node origin, Node destination, int type) {
        if (type != 4) {
            getTilePane(card, "me").setStyle("");
            getTilePane(card, "me").setBackground(null);
        } else {
            getTilePane(card, "rival").setStyle("");
            getTilePane(card, "rival").setBackground(null);
        }
        descriptionCard.setImage(null);

        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), card);
        transition.setToX(origin.getLayoutX() - destination.getLayoutX());
        transition.setToY(origin.getLayoutY() - destination.getLayoutY());
        transition.play();
        transition.setOnFinished(event -> {

            try {
                finishTransitionCard(getTilePane(card, "me"), type, card);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });
    }

    public void finishTransitionCard(TilePane tilePane, int type, Card card) throws Exception {
        //forTable
        if (type == 0) { //for common card from hand
            game.getCurrentUser().getTabel().removeCardFromHand(card);
            game.getCurrentUser().getTabel().addCardToAppropriateArray(card);
            GameController.changeTurn(this, game);
            clickCard = null;
        } else if (type == 1) {
            game.getCurrentUser().getTabel().removeFromAppropriateArray(card);
            Card cardCopy = card.clone();
            cardCopy.setY(handTilePane.getPrefHeight());
            game.getCurrentUser().getTabel().addToHand(cardCopy);
        } else if (type == 2) {
            game.getCurrentUser().getTabel().removeFromDiscardPile(card);
            game.getCurrentUser().getTabel().addCardToAppropriateArray(card);
            card.setY(tilePane.getPrefHeight() - 13);
            card.setX(Tabel.getWeadthCard());
        } else if (type == 3) {
            game.getCurrentUser().getTabel().removeCardFromHand(card);
            game.getCurrentUser().getTabel().addCardToAppropriateArray(card);
        } else if (type == 4) {///for spy card from hand
            game.getCurrentUser().getTabel().removeCardFromHand(card);
            game.getRivalUser().getTabel().addCardToAppropriateArray(card);
            clickCard = null;
            //changeTurn
            GameController.changeTurn(this, game);
        }

    }


    public void setImageCardInTilePane(TilePane tilePane, boolean type) {
        for (Node card : tilePane.getChildren()) {
            if (card instanceof Card) {
                ((Card) card).setY(tilePane.getPrefHeight());
                ((Card) card).setX(Tabel.getWeadthCard());

                if (type && clickCard == null) {
                    card.setOnMouseClicked(event -> {
                        setDescriptionCard((Card) card);
                    });

                }
            }

        }
    }

    public void setFinalPower(int type) {
        ArrayList<Text> powerGroup;
        Text finalPowerText;
        if (type == 1) {
            powerGroup = myPowerGroup;
            finalPowerText = myFinalPower;
        } else {
            powerGroup = rivalPowerGroup;
            finalPowerText = rivalFinalPower;
        }
        int finalPower = 0;
        for (Node powerText : powerGroup) {
            if (powerText instanceof Text) {
                finalPower += Integer.parseInt(((Text) powerText).getText());
            }
        }
        finalPowerText.setText(String.valueOf(finalPower));
        finalPowerText.setFont(Font.font("Arial", FontWeight.BOLD, 15));


    }

    public void setRowPower(int type) {
        ArrayList<Text> powerGroup;
        ArrayList<TilePane> rowGroup;
        Tabel tabel;
        if (type == 1) {
            powerGroup = myPowerGroup;
            rowGroup = myRowGroup;
            tabel = game.getCurrentUser().getTabel();

        } else {
            powerGroup = rivalPowerGroup;
            rowGroup = rivalRowGroup;
            tabel = game.getRivalUser().getTabel();
        }
        for (int i = 0; i < rowGroup.size(); i++) {
            TilePane tilePane = rowGroup.get(i);
            Text text = powerGroup.get(i);
            int power = 0;
            for (Node card : tilePane.getChildren()) {
                if (card instanceof Card)
                    power += GameController.getPower((Card) card,tabel);
            }
            text.setText(String.valueOf(power));
            text.setFont(Font.font("Arial", FontWeight.BOLD, 15));

        }
    }

    public void loadAgainGame() throws Exception {
        gameMenu.start(stage);
    }

    public void previousCardInDiscardPile(MouseEvent mouseEvent) {
        int index = game.getCurrentUser().getTabel().getDiscardPile().indexOf(game.getCurrentUser().getTabel().getCardFromDiscardPileWithUrl(myDiscardPile.getImage().getUrl()));
        int size = game.getCurrentUser().getTabel().getDiscardPile().size();
        int previous;
        if (index - 1 >= 0) previous = index - 1;
        else previous = size - 1;
        myDiscardPile.setImage(new Image(game.getCurrentUser().getTabel().getDiscardPile().get(previous).getPath()));
    }

    public void nextCardInDiscardPile(MouseEvent mouseEvent) {
        int index = game.getCurrentUser().getTabel().getDiscardPile().indexOf(game.getCurrentUser().getTabel().getCardFromDiscardPileWithUrl(myDiscardPile.getImage().getUrl()));
        int size = game.getCurrentUser().getTabel().getDiscardPile().size();
        int next;
        if (index + 1 < size) next = index + 1;
        else next = 0;
        myDiscardPile.setImage(new Image(game.getCurrentUser().getTabel().getDiscardPile().get(next).getPath()));
    }

    public void enterPassButton(MouseEvent mouseEvent) throws Exception {
        game.getCurrentUser().getTabel().setPass(true);
    }

    public int getAllPow(String type) {
        if (type.equals("me")) return Integer.parseInt(myFinalPower.getText());
        else return Integer.parseInt(rivalFinalPower.getText());
    }


///two mistakes for decoyCard ->
// card add to deck but location of in deck is not correct
//click


}
