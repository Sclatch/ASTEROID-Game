import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class GameSceneController {

    //Move to constant settings later
    private int sensitivity = 5;
    private int score = 0;
    @FXML private Label scoreLabel;
    private boolean moveLeft = false, moveRight = false;
    private AnimationTimer animationTimer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            if(moveLeft){
                spaceship.setTranslateX(spaceship.getTranslateX() - sensitivity);
            }
            else if (moveRight){
                spaceship.setTranslateX(spaceship.getTranslateX() + sensitivity);
            }
            score += 1;
            scoreLabel.setText(Integer.toString(score));
        }
    };

    @FXML private ImageView spaceship;

    @FXML
    public void initialize() {
        animationTimer.start();
    }

    @FXML
    private void isKeyPressed(KeyEvent keyEvent){
        if (keyEvent.getCode() == KeyCode.LEFT) {
            moveLeft = true;
            moveRight = false;
//            //System.out.println(spaceship.getTranslateX());//DEBUG
            keyEvent.consume();
        }
        else if (keyEvent.getCode() == KeyCode.RIGHT) {
            moveRight = true;
            moveLeft = false;
//            //System.out.println(spaceship.getTranslateX());//DEBUG
            keyEvent.consume();
        }
        else if (keyEvent.getCode() == KeyCode.ESCAPE) {
            animationTimer.stop();
            moveRight = false;
            moveLeft = false;
            FadeTransition fadeTransition = new Intro().fadeOut(((Scene)keyEvent.getSource()).getRoot());
            fadeTransition.play();
            fadeTransition.setOnFinished(actionEvent -> {
                Parent titleScene = null;
                try {
                    titleScene = FXMLLoader.load(getClass().getResource("Title.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
                titleScene.setOpacity(0);
                Stage stage = (Stage) ((Scene)keyEvent.getSource()).getWindow();
                stage.setScene(new Scene(titleScene, Color.BLACK));
                FadeTransition fadeTransition2 = new Intro().fadeIn(titleScene);
                fadeTransition2.play();
                stage.show();
            });
        }
    }
    @FXML
    private void isKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.RIGHT) {
            moveLeft = false;
            moveRight = false;
//            //System.out.println(spaceship.getTranslateX());//DEBUG
            keyEvent.consume();
        }
    }
}
