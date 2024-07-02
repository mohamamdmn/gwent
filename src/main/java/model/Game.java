package model;

import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Game {
    private User currentUser;
    private User rivalUser;
    private int round;
    private ArrayList<Integer> myPoints = new ArrayList<>();
    private ArrayList<Integer> rivalPoints = new ArrayList<>();

    public Game(User current, User rivalUser) {
        this.currentUser = current;
        this.rivalUser = rivalUser;
        this.round = 1;
    }

    public void substitute() {
        User temp = currentUser;
        currentUser = rivalUser;
        rivalUser = temp;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public User getRivalUser() {
        return rivalUser;
    }

    public boolean nextRound() {
        if (round + 1 <= 3) {
            round++;
            return true;
        }
        return false;
    }
    public void setPoints(int myPont,int rivalPoint){
        myPoints.add(myPont);
        rivalPoints.add(rivalPoint);
    }
    public ArrayList<Integer>getMyPoints(){
        return myPoints;
    }
    public ArrayList<Integer> getRivalPoints(){
        return rivalPoints;
    }


}
