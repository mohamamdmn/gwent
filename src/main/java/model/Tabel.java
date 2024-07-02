package model;

import enums.Faction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Random;

public class Tabel {
    private ObservableList<Card> deck = FXCollections.observableArrayList();
    private ObservableList<Card> discardPile = FXCollections.observableArrayList();
    private static double spacing;
    private static double weadthCard = 60;
    private ObservableList<Card> closeRow = FXCollections.observableArrayList();
    private ObservableList<Card> siegeRow = FXCollections.observableArrayList();
    private ObservableList<Card> rangedRow = FXCollections.observableArrayList();
    private ObservableList<ObservableList<Card>> allRow = FXCollections.observableArrayList();
    private ObservableList<Card> hand = FXCollections.observableArrayList();
    private Faction faction;
    private Card leaderCard;
    private int hp;
    private boolean pass;

    public Tabel(ObservableList<Card> remainingCards,Card leaderCard,Faction faction) {
        hp = 2;
        pass = false;
        this.deck = remainingCards;
        spacing = 0.5;
        allRow.add(closeRow);
        allRow.add(rangedRow);
        allRow.add(siegeRow);
        this.leaderCard = leaderCard;
        this.faction = faction;
    }

    public ObservableList<Card> getDeck() {
        return deck;
    }

    public ObservableList<ObservableList<Card>> getAllRow() {
        return allRow;
    }

    public static double getSpacing() {
        return spacing;
    }

    public static double getWeadthCard() {
        return weadthCard;
    }


    public ObservableList<Card> getHand() {
        return hand;
    }

    public void setHand(ObservableList<Card> hand) {
        this.hand = hand;
    }


    public ObservableList<Card> getDiscardPile() {
        return discardPile;
    }

    public void addToDiscardPile(Card card) {
        discardPile.add(card);
    }

    public Card getCardFromDiscardPileWithUrl(String path) {
        for (Card card : discardPile) {
            if (card.getPath().equals(path)) return card;
        }
        return null;
    }

    public ObservableList<Card> getAppropriateList(Card card) {
        switch (card.getType()) {
            case "close" -> {
                return closeRow;
            }
            case "ranged" -> {
                return rangedRow;
            }
            case "siege" -> {
                return siegeRow;
            }
        }
        return null;
    }


    public void removeFromDiscardPile(Card card) {
        discardPile.remove(card);
    }
    public void decreaseHp(){
        hp --;

    }
    public int getHp(){
        return hp;
    }

    public void addCardToAppropriateArray(Card card) {
        switch (card.getType()) {
            case "close" -> closeRow.add(card);
            case "ranged" -> rangedRow.add(card);
            case "siege" -> siegeRow.add(card);
        }
    }

    public void setRandomDeckFromAllCard() {
        Random rand = new Random();
        ArrayList<Integer> indexes = new ArrayList<>();

        while (indexes.size() < 10) {
            int randomIndex = rand.nextInt(deck.size());
            if (!indexes.contains(randomIndex)) {
                indexes.add(randomIndex);
            }
        }
        for (Integer index : indexes) {
            hand.add(deck.get(index).clone());

        }
        for (Card card : hand) {
            removeCardFromDeck(card);
        }

    }

    public ObservableList<Card> getSiegeRow() {
        return siegeRow;
    }

    public void addToHand(Card card) {
        hand.add(card);
    }
    public void removeCardFromHand(Card card){
        hand.removeIf(card1 -> card1.getPath().equals(card.getPath()));
    }
    public void removeCardFromDeck(Card card){
        deck.removeIf(card1 -> card1.getPath().equals(card.getPath()));
    }

    public void removeFromAppropriateArray(Card card) {
        switch (card.getType()) {
            case "close" -> deleteCard(closeRow, card);
            case "ranged" -> deleteCard(rangedRow, card);
            case "siege" -> deleteCard(siegeRow, card);
        }
    }

    public void deleteCard(ObservableList<Card> array, Card card) {
        for (Card card1 : array) {
            if (card1.getPath().equals(card.getPath())) {
                array.remove(card1);
                return;
            }
        }
    }


    public String getFaction() {
        return faction.toString();
    }

    public void setCloseRow(ObservableList<Card> closeRow) {
        this.closeRow = closeRow;
    }

    public ObservableList<Card> getRangedRow() {
        return rangedRow;
    }
    public boolean getPass(){
        return pass;
    }
    public void setPass(boolean condition){
        pass = condition;
    }
}