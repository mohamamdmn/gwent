package controller;

import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.Card;
import model.Game;
import model.Tabel;
import view.Endgame;
import view.*;


public class GameController {

    public static void logicSpecialCard(Card card, GameMenuController gameMenuController) throws Exception {
        if (card.getAbility().equals("medic")) {
            gameMenuController.forCommonAndMedicCard("medic");
        } else if (card.getAbility().equals("muster")) {
            gameMenuController.forMusterCard();
        } else if (card.getAbility().equals("spy")) {
            gameMenuController.forSpyCard();
        } else {
            gameMenuController.forCommonAndMedicCard("common");
        }
    }

    public static int getPower(Card card, Tabel tabel) {
        int power = card.getPower();
        if (!card.getIsLeader()) {
            ///for weatherCard :: power Must be 1
            ObservableList<Card> row = tabel.getAppropriateList(card);
            if (!card.getAbility().equals("horn")) power = forCommandersHorn(row, power);
            if (!card.getAbility().equals("morale")) power = forMoralBoost(row, power);
            power = forTightBond(row, power, card);
            /////


        }
        return power;
    }

    public static int forCommandersHorn(ObservableList<Card> row, int power) {
        int finalPower = power;
        for (Card card : row) {
            if (card.getAbility().equals("horn")) {
                return finalPower * 2;
            }
        }
        return finalPower;

    }

    public static int forMoralBoost(ObservableList<Card> row, int power) {
        int finalPower = power;
        for (Card card : row) {
            if (card.getAbility().equals("Morale")) {
                finalPower += 1;
            }
        }
        return finalPower;
    }

    public static int forTightBond(ObservableList<Card> row, int power, Card mainCard) {
        int number = 1;
        for (Card card : row) {
            if (card.getAbility().equals("bond")) {
                if (card.getPower() == mainCard.getPower()) number++;
            }
        }
        return power * number;
    }

    public static void changeTurn(GameMenuController gameMenuController, Game game) throws Exception {
        if (game.getRivalUser().getTabel().getPass()) {
            if (!game.getCurrentUser().getTabel().getPass()) {
                gameMenuController.loadAgainGame();
            } else {
                endTurn(gameMenuController, game);
            }
        } else {
            game.substitute();
            gameMenuController.loadAgainGame();
        }
    }

    public static void endTurn(GameMenuController gameMenuController, Game game) throws Exception {
        boolean endGame = false;
        loseOrVin(game, gameMenuController);
        if (game.nextRound()) {
            if (game.getCurrentUser().getTabel().getHp() > 0 && game.getRivalUser().getTabel().getHp() > 0) {
                nextRound(game);
                gameMenuController.loadAgainGame();
            } else endGame = true;
        } else endGame = true;

        if (endGame) {
            Endgame endgame = new Endgame();
            endgame.setGame(game);
            endgame.start(Main.getStage());
        }
    }

    public static void loseOrVin(Game game, GameMenuController gameMenuController) {
        int mypow = gameMenuController.getAllPow("me");
        int rivalPow = gameMenuController.getAllPow("rival");
        game.setPoints(mypow, rivalPow);
        if (mypow < rivalPow) {
            game.getCurrentUser().getTabel().decreaseHp();
        } else {
            game.getRivalUser().getTabel().decreaseHp();
        }
    }

    public static void deleteMyAndRivalRows(Game game) {
        for (ObservableList<Card> row : game.getCurrentUser().getTabel().getAllRow()) {
            for (Card card : row) {
                game.getCurrentUser().getTabel().addToDiscardPile(card);
            }
            row.clear();
        }
        for (ObservableList<Card> row : game.getRivalUser().getTabel().getAllRow()) {
            for (Card card : row) {
                game.getRivalUser().getTabel().addToDiscardPile(card);
            }
            row.clear();
        }
    }

    public static void nextRound(Game game) {
        deleteMyAndRivalRows(game);
        game.getCurrentUser().getTabel().setPass(false);
        game.getRivalUser().getTabel().setPass(false);
    }


}
