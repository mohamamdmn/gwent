package view.animation;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class TransitionCard extends Transition {
    private HBox distinitionHbox;
    private ImageView imageViewOrigin;
    private HBox deck;
    public double duration = 100;
    private double vx;
    private double vy;

    public TransitionCard(ImageView imageView, HBox hBox,HBox deck){
        imageViewOrigin = imageView;
        distinitionHbox = hBox;
        this.deck = deck;
        this.setCycleDuration(Duration.millis(duration));
        this.setCycleCount(-1);
        vx = hBox.getLayoutX() - deck.getLayoutX() / 20;
        vy = hBox.getLayoutY() - deck.getLayoutY() /20;
    }
    @Override
    protected void interpolate(double v) {
       double x = imageViewOrigin.getX() + vx;
       double y = imageViewOrigin.getY() + vy;
       if(deck.getBoundsInParent().intersects(distinitionHbox.getBoundsInParent())){
           this.stop();
       }else {
           imageViewOrigin.setX(x);
           imageViewOrigin.setY(y);
       }

    }
}
