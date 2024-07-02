package model;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import view.GameMenu;

public class Card extends Group {
    private String path;
    private String type;
    private double x;
    private double y;
    private boolean isHero;
    private int power;
    private ImageView imageView;
    private Circle circlePointView;
    private Circle circleType;
    private Circle circleAbility;
    private Text powerText;
    private String pathDc;
    private String ability;
    private String name;


    public Card(String name, String path, String type, boolean isHero, int power, String pathDC, String ability) {
        this.name = name;
        this.path = path;
        this.type = type;
        this.power = power;
        this.isHero = isHero;
        this.pathDc = pathDC;
        this.ability = ability;
        initialize(Tabel.getWeadthCard(), 78.4);

    }

    public void initialize(double x, double y) {
        imageView = new ImageView(new Image(path));
        imageView.setFitWidth(x);
        imageView.setFitHeight(y);

        circlePointView = new Circle(15);
        if (!(type.equals("weather") || type.equals("spell"))) {
            if (!isHero)
                circlePointView.setFill(new ImagePattern(new Image(GameMenu.class.getResource("/gwentImages/img/icons/power_normal.png").toExternalForm())));
            else
                circlePointView.setFill(new ImagePattern(new Image(GameMenu.class.getResource("/gwentImages/img/icons/power_hero.png").toExternalForm())));
        } else {
            circlePointView.setFill(new ImagePattern(new Image(GameMenu.class.getResource("/gwentImages/img/icons/power_normal.png").toExternalForm())));
        }

        powerText = new Text();
        powerText.setText(String.valueOf(power));
        powerText.setFont(Font.font("Arial", FontWeight.BOLD, 15));

        circleType = new Circle(10);
        if (!(type.equals("spell") || type.equals("leader") || type.equals("weather"))) {
            circleType.setFill(new ImagePattern(new Image(GameMenu.class.getResource("/gwentImages/img/icons/card_row_" + type + ".png").toExternalForm())));
        }

        circleAbility = new Circle(15);
        if (!(ability.equals("none") || ability.equals("transformers"))) {
            circleAbility.setFill(new ImagePattern(new Image(GameMenu.class.getResource("/gwentImages/img/icons/card_ability_" + ability + ".png").toExternalForm())));
        }


        this.getChildren().add(imageView);
        this.getChildren().add(circlePointView);
        this.getChildren().add(circleType);
        this.getChildren().add(circleAbility);
        this.getChildren().add(powerText);

        circlePointView.setCenterY(circlePointView.getCenterY() + 10);
        circlePointView.setCenterX(circlePointView.getCenterX() + 13);

        circleType.setCenterY(circleType.getCenterY() + imageView.getFitHeight() - circleType.getRadius());
        circleType.setCenterX(circleType.getCenterX() + imageView.getFitWidth() - circleType.getRadius());

        circleAbility.setCenterX(circleAbility.getCenterX() + imageView.getFitWidth() - circleType.getRadius() - circleAbility.getRadius());
        circleAbility.setCenterY(circleAbility.getCenterY() + imageView.getFitHeight() - circleType.getRadius() - circleAbility.getRadius());


        powerText.setX(circlePointView.getCenterX() - 10);
        powerText.setY(circlePointView.getCenterY() + 2);

    }

    public String getPath() {
        return path;
    }

    public String getType() {
        return type;
    }

    public String getPathDc() {
        return pathDc;
    }

    public ImageView getimageView() {
        return imageView;
    }

    public Text getPowerText() {
        return powerText;
    }

    public Circle getTypeCircle() {
        return circleType;
    }

    public Card clone() {
        Card card = new Card(this.name,this.path, this.type, this.isHero, this.power, this.pathDc, this.ability);
        return card;
    }

    public void setX(double x) {
        this.x = x;
    }

    public int getPower() {
        return power;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean getIsLeader() {
        return this.isHero;
    }

    public String getAbility() {
        return ability;
    }

    public void setType(String type) {
        this.type = type;
    }

}
