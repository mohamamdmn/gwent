package controller;

import enums.CardInfo;
import enums.Faction;
import enums.Path;
import enums.Type;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Card;
import model.Game;
import model.Tabel;
import model.User;
import view.GameMenu;

import java.util.ArrayList;
import java.util.Arrays;

public class PregameController {
    private Stage mainStage;
    private Label totalCardsLabel = new Label();
    private Label unitCardLabel = new Label();
    private Label specialCardsLabel = new Label();
    private Label totalUnitStrength = new Label();
    private Label heroCards = new Label();
    private User currentUser = new User();
    private User rivalUser = new User();
    private VBox middVBox;
    private Pane root;
    private Faction currentFaction = Faction.Nilfgaars;
    GridPane collectionGrid;
    GridPane deckGrid;
    private Button startGame = new Button("start game");
    private ArrayList<ImageView> cardCollection = new ArrayList<>();
    private ArrayList<ImageView> deck = new ArrayList<>();
    private ArrayList<ImageView> factions;
    private Button changeFaction;
    private Image cursorImage = new Image(PregameController.class.getResource("/gwentImages/img/icons/cursor.png").toExternalForm());
    private ImageView scoiaTeal = new ImageView(new Image(PregameController.class.getResource("/gwentImages/img/lg/faction_scoiatael.jpg").toExternalForm()));
    private ImageView realsm = new ImageView(new Image(PregameController.class.getResource("/gwentImages/img/lg/faction_realms.jpg").toExternalForm()));
    private ImageView nilfgaard = new ImageView(new Image(PregameController.class.getResource("/gwentImages/img/lg/faction_nilfgaard.jpg").toExternalForm()));
    private ImageView monsters = new ImageView(new Image(PregameController.class.getResource("/gwentImages/img/lg/faction_monsters.jpg").toExternalForm()));
    private ImageView skellige = new ImageView(new Image(PregameController.class.getResource("/gwentImages/img/lg/faction_skellige.jpg").toExternalForm()));
    private ImageView leaderCardView = new ImageView();

    public PregameController(Pane root, Stage stage) {
        mainStage = stage;
        this.root = root;
        root.setCursor(new ImageCursor(cursorImage));
        collectionGrid = gridPaneExtract(root);
        deckGrid = gridPane2ex(root);
        changeFaction = changeFactionExtract();
        middVBox = middVBoxExtractor();
        leaderChoose();
        leaderCardView.setFitWidth(130);
        leaderCardView.setFitHeight(195);
        leaderCardView.setOnMouseClicked(event -> changeLeader());
        middVBoxInitialize();
        factions = new ArrayList<>(Arrays.asList(scoiaTeal, realsm, nilfgaard, monsters, skellige));
        setFactionsClickEvent();
        changeFaction.setText("Change Faction");
        changeFaction.setOnMouseClicked(event -> showFactions());
        collectionGrid.setVgap(10);
        deckGrid.setVgap(10);
        cardCollectionInitialize();
        collectionFiller();
    }

    private void changeLeader() {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);

        hBox.setSpacing(10);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.RED);
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        for (Path path : Path.values()) {
            if (path.getCard().getFaction().equals(currentFaction) && path.getCard().getType().equals(Type.Leader)) {
                ImageView imageView = new ImageView(new Image(path.getPath().toExternalForm()));
                imageView.setFitHeight(210);
                imageView.setFitWidth(140);
                imageView.hoverProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        imageView.setEffect(dropShadow);
                    } else {
                        imageView.setEffect(null);
                    }
                });
                imageView.setOnMouseClicked(event -> {
                    leaderCardView.setImage(imageView.getImage());
                    root.getChildren().remove(root.getChildren().size() - 1);
                });
                hBox.getChildren().add(imageView);
            }
        }
        root.getChildren().add(hBox);
        hBox.setLayoutX((root.getWidth() - hBox.getChildren().size() * 140) / 2);
        hBox.setLayoutY(root.getHeight() / 2 - 70);
//        root.setOnMouseClicked(event -> root.getChildren().remove(hBox));

    }

    private void leaderChoose() {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.RED);
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        for (Path path : Path.values()) {
            if (path.getCard().getFaction().equals(currentFaction) && path.getCard().getType().equals(Type.Leader)) {
                leaderCardView.setImage(new Image(path.getPath().toExternalForm()));
                leaderCardView.setFitWidth(130);
                leaderCardView.setFitHeight(195);
                leaderCardView.hoverProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        leaderCardView.setEffect(dropShadow);
                    } else {
                        leaderCardView.setEffect(null);
                    }
                });
                break;
            }
        }

    }

    private void middVBoxInitialize() {
        Path path = Path.Monsters_arachas_behemoth;
        System.out.println(path.getPath());
        middVBox.setSpacing(10);
        middVBox.getChildren().add(new Label("Leader"));
        middVBox.getChildren().add(leaderCardView);
        middVBox.getChildren().add(new Label("Total cards in deck"));
        middVBox.getChildren().add(new Label("Number of unit cards"));
        middVBox.getChildren().add(new Label("Special cards"));
        middVBox.getChildren().add(new Label("Total Unit Card Strength"));
        middVBox.getChildren().add(new Label("Hero Cards"));
        middVBox.getChildren().add(startGame);
        startGame.setOnMouseClicked(event -> startGame());

    }

    private void startGame() {
        ObservableList<Card> cardDeck = FXCollections.observableArrayList();
        CardInfo cardInfo = null;
        for (ImageView imageView : deck) {
            for (Path iterator : Path.values()) {
                if (imageView.getImage().getUrl().equals(iterator.getPath().toExternalForm())) {
                    cardInfo = iterator.getCard();
                    cardDeck.add(new Card(iterator.toString().toLowerCase(), imageView.getImage().getUrl(), cardInfo.getType().toString(), cardInfo.isHero(), cardInfo.getPower(), iterator.getPath2().toExternalForm(), cardInfo.getAbility().toString()));
                    break;
                }
            }
        }
        Card leaderCard = null;
        for (Path iterator : Path.values()) {
            if (leaderCardView.getImage().getUrl().equals(iterator.getPath().toExternalForm())) {
                cardInfo = iterator.getCard();
                leaderCard = new Card(iterator.toString().toLowerCase(), leaderCardView.getImage().getUrl(), cardInfo.getType().toString(), cardInfo.isHero(), cardInfo.getPower(), iterator.getPath2().toExternalForm(), cardInfo.getAbility().toString());
                break;
            }
        }
        currentUser.setTabel(new Tabel(cardDeck, leaderCard, currentFaction));
        currentUser.getTabel().setRandomDeckFromAllCard();


        //must be deleted
        ObservableList<Card> rivalDeck = FXCollections.observableArrayList();
        for (Card card : cardDeck) {
            rivalDeck.add(card);
        }
        rivalUser.setTabel(new Tabel(rivalDeck, leaderCard.clone(), currentFaction));
        rivalUser.getTabel().setRandomDeckFromAllCard();
        ////
        GameMenu gameMenu = new GameMenu();
        Game game = new Game(currentUser, rivalUser);
        gameMenu.setGame(game);
        try {
            gameMenu.start(mainStage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private VBox middVBoxExtractor() {
        VBox v = (VBox) root.getChildren().get(0);
        HBox h = (HBox) v.getChildren().get(3);
        return (VBox) h.getChildren().get(1);
    }

    private void setFactionsClickEvent() {
        for (ImageView imageView : factions) {
            imageView.setOnMouseClicked(event -> turnFaction(imageView));
            imageView.hoverProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    DropShadow dropShadow = new DropShadow();
                    dropShadow.setColor(Color.RED);
                    dropShadow.setRadius(5.0);
                    dropShadow.setOffsetX(3.0);
                    dropShadow.setOffsetY(3.0);
                    imageView.setEffect(dropShadow);
                } else {

                    imageView.setEffect(null);
                }
            });
        }
    }

    private void turnFaction(ImageView imageView) {
        String path = imageView.getImage().getUrl();
        if (path.contains("tael")) currentFaction = Faction.ScosiaTeal;
        if (path.contains("monst")) currentFaction = Faction.Monstres;
        if (path.contains("nilfgaard")) currentFaction = Faction.Nilfgaars;
        if (path.contains("realms")) currentFaction = Faction.NorthernRealms;
        if (path.contains("skellige")) currentFaction = Faction.Skelige;
        root.getChildren().remove(root.getChildren().get(root.getChildren().size() - 1));
        collectionGrid.getChildren().clear();
        deckGrid.getChildren().clear();
        deck.clear();
        deckGrid.getChildren().clear();
        cardCollectionInitialize();
        collectionFiller();
        leaderChoose();
    }

    private void showFactions() {
        VBox vBox = new VBox();
        HBox hBox1 = new HBox();
        HBox hBox2 = new HBox();
        vBox.setSpacing(15);
        vBox.setAlignment(Pos.TOP_CENTER);
        System.out.println(vBox.getWidth());
        vBox.setLayoutX(root.getWidth() / 2 - 500);
        hBox1.setSpacing(10);
        hBox2.setSpacing(165);
        hBox2.setAlignment(Pos.CENTER);
        Label scoiaTealLabel = new Label("scoiaTeal");
        Label monstersLabel = new Label("monsters");
        Label nilfgaardLabel = new Label("nilfgaard");
        Label realmsLabel = new Label("realms");
        Label skelligeLabel = new Label("skellige");
        for (ImageView imageView : factions) {
            hBox1.getChildren().add(imageView);
            String path = imageView.getImage().getUrl();
            if (path.contains("tael")) hBox2.getChildren().add(scoiaTealLabel);
            if (path.contains("monst")) hBox2.getChildren().add(monstersLabel);
            if (path.contains("nilfgaard")) hBox2.getChildren().add(nilfgaardLabel);
            if (path.contains("realms")) hBox2.getChildren().add(realmsLabel);
            if (path.contains("skellige")) hBox2.getChildren().add(skelligeLabel);

        }
        vBox.getChildren().addAll(hBox2, hBox1);

        root.getChildren().add(vBox);
        root.setOnMouseClicked(event -> root.getChildren().remove(vBox));
    }


    private Button changeFactionExtract() {

        return (Button) ((HBox) ((VBox) root.getChildren().get(0)).getChildren().get(2)).getChildren().get(1);
    }

    private GridPane gridPane2ex(Pane pane) {
        VBox vBox = (VBox) pane.getChildren().get(0);
        HBox h = (HBox) vBox.getChildren().get(3);
        ScrollPane s = (ScrollPane) h.getChildren().get(2);
        GridPane g = (GridPane) s.getContent();
        return g;
    }

    private GridPane gridPaneExtract(Pane pane) {
        VBox vBox = (VBox) pane.getChildren().get(0);
        HBox h = (HBox) vBox.getChildren().get(3);
        ScrollPane s = (ScrollPane) h.getChildren().get(0);
        GridPane g = (GridPane) s.getContent();
        return g;
    }

    private void collectionFiller() {
        collectionGrid.getChildren().clear();
        for (int i = 0; i < cardCollection.size() / 3 + 1; i++) {
            for (int j = 0; j < 3; j++) {
                if (3 * i + j >= cardCollection.size()) break;
                cardCollection.get(3 * i + j).setFitWidth(150);
                cardCollection.get(3 * i + j).setFitHeight(225);
                collectionGrid.add(cardCollection.get(3 * i + j), j, i + 1);
            }
        }
    }

    private void deckFiller() {
        deckGrid.getChildren().clear();
        for (int i = 0; i < deck.size() / 3 + 1; i++) {
            for (int j = 0; j < 3; j++) {
                if (3 * i + j >= deck.size()) break;
                deck.get(3 * i + j).setFitWidth(150);
                deck.get(3 * i + j).setFitHeight(225);
                deckGrid.add(deck.get(3 * i + j), j, i);
            }
        }
    }

    private void cardCollectionInitialize() {
        cardCollection.clear();
        Image cardImage;
        for (Path path : Path.values()) {
            if ((path.getCard().getFaction().equals(currentFaction) || path.getCard().getFaction().equals(Faction.All)) && !path.getCard().getType().equals(Type.Leader)) {
                for (int i = 0; i < path.getCard().getCount() - numOfRepeatDetector(path) + 1; i++) {
                    cardImage = new Image(path.getPath().toExternalForm());
                    cardCollection.add(new ImageView(cardImage));
                }
            }
        }
        for (ImageView imageView : cardCollection) {
            imageView.hoverProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    DropShadow dropShadow = new DropShadow();
                    dropShadow.setColor(Color.RED);
                    dropShadow.setRadius(5.0);
                    dropShadow.setOffsetX(3.0);
                    dropShadow.setOffsetY(3.0);
                    imageView.setEffect(dropShadow);
                } else {

                    imageView.setEffect(null);
                }
            });
            imageView.setOnMouseClicked(event -> selectClick(imageView));
        }
    }

    private int numOfRepeatDetector(Path path) {
        int maxCount = path.getCard().getCount();
        int numOfPictures = 0;
        for (Path iterator : Path.values()) {
            if (iterator.getCard().equals(path.getCard())) numOfPictures++;
        }
        return numOfPictures;
    }

    private void selectClick(ImageView imageView) {
        cardCollection.remove(imageView);
        collectionFiller();
        deck.add(imageView);
        imageView.setOnMouseClicked(event -> deSelectClick(imageView));
        deckFiller();
    }

    private void deSelectClick(ImageView imageView) {
        deck.remove(imageView);
        deckFiller();
        cardCollection.add(imageView);
        imageView.setOnMouseClicked(event -> selectClick(imageView));
        collectionFiller();
    }

    private Path imageToUrl(String url) {
        Path result = null;
        for (Path path : Path.values()) {
            if (path.getPath().toExternalForm().equals(url)) {
                result = path;
            }
        }
        return result;
    }

}

