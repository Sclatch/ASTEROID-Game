import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class GameSceneController {


    //Move to constant settings later
    private int sensitivity = 10;

    private String selectedMusic = Main.selectedMusic;

    private int score = 0;
    @FXML private Label scoreLabel;
    private boolean moveLeft = false, moveRight = false;

    private AnimationTimer animationTimer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            if(moveLeft){
                if(spaceship.getTranslateX() > -640) {
                    spaceship.setTranslateX(spaceship.getTranslateX() - sensitivity);
                }
            }
            else if (moveRight){
                if(spaceship.getTranslateX() < 580) {
                    spaceship.setTranslateX(spaceship.getTranslateX() + sensitivity);
                }
            }

            score += 1;
            scoreLabel.setText(Integer.toString(score));
        }
    };

    @FXML private ImageView spaceship;

    @FXML private ImageView asteroids1,asteroids2,asteroids3,asteroids4;

    @FXML private ImageView gameBackground;

    @FXML
    public void initialize() {
        //game animations
        animationTimer.start();

        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), gameBackground);
        translateTransition.setFromY(-1600);
        translateTransition.setToY(0);
        translateTransition.setInterpolator(Interpolator.LINEAR);

        TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(0), gameBackground);
        translateTransition2.setFromY(0);
        translateTransition2.setToY(-1600);

        ParallelTransition parallelTransition = new ParallelTransition(translateTransition, translateTransition2);
        parallelTransition.setCycleCount(Animation.INDEFINITE);
        parallelTransition.play();
        //music
        TitleController.resetMusic();
        Main.music.stop();
        Main.music.setSoundFile(selectedMusic);
        Main.music.openSoundFile();
        Main.music.play();
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
