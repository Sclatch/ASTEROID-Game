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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class GameSceneController {


    //Move to constant settings later
    private int sensitivity = 15;

    private String selectedMusic = Main.selectedMusic;

    private int score = 0;
    private int life = 3;

    @FXML private Label scoreLabel;
    private boolean moveLeft = false, moveRight = false;
    private int moveSlow = 0;

    private AnimationTimer animationTimer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            if(moveLeft && !moveRight){
                if(spaceship.getTranslateX() > -640) {
                    spaceship.setTranslateX(spaceship.getTranslateX() - sensitivity + (moveSlow * 10));
                }
                else if(spaceship.getTranslateX() == -640){
                    spaceship.setTranslateX(spaceship.getTranslateX() - 5);
                }
            }

            if (moveRight && !moveLeft){
                if(spaceship.getTranslateX() < 580) {
                    spaceship.setTranslateX(spaceship.getTranslateX() + sensitivity - (moveSlow * 10));
                }
                else if (spaceship.getTranslateX() == 580){
                    spaceship.setTranslateX(spaceship.getTranslateX() + 5);
                }
            }

            lifeMechanics();

            score += 1;
            scoreLabel.setText(Integer.toString(score));
        }
    };

    @FXML private ImageView spaceship;

    @FXML private ImageView starBackground, dustBackground, spaceBackground;

    @FXML private ImageView hpBar1, hpBar2, hpBar3;


    @FXML
    public void initialize() {
        //game animations
        animationTimer.start();

        //Background scrolling
        parallaxBackground();

        //music
        TitleController.resetMusic();
        Main.music.stop();
        Main.music.setSoundFile(selectedMusic);
        Main.music.openSoundFile();
        Main.music.play();
    }

    private void parallaxBackground(){
        TranslateTransition translateTransitionStar = new TranslateTransition(Duration.millis(700), starBackground);
        translateTransitionStar.setFromY(-1600);
        translateTransitionStar.setToY(0);
        translateTransitionStar.setInterpolator(Interpolator.LINEAR);

        TranslateTransition translateTransitionDust = new TranslateTransition(Duration.millis(8000), dustBackground);
        translateTransitionDust.setFromY(0);
        translateTransitionDust.setToY(-1600);
        translateTransitionDust.setInterpolator(Interpolator.LINEAR);

        TranslateTransition translateTransitionSpace = new TranslateTransition(Duration.millis(20000), spaceBackground);
        translateTransitionSpace.setFromY(-1600);
        translateTransitionSpace.setToY(0);
        translateTransitionSpace.setInterpolator(Interpolator.LINEAR);

        TranslateTransition translateTransitionStar2 = new TranslateTransition(Duration.millis(0), starBackground);
        translateTransitionStar2.setFromY(0);
        translateTransitionStar2.setToY(-1600);

        TranslateTransition translateTransitionDust2 = new TranslateTransition(Duration.millis(0), dustBackground);
        translateTransitionDust.setFromY(-1600);
        translateTransitionDust.setToY(0);

        TranslateTransition translateTransitionSpace2 = new TranslateTransition(Duration.millis(0), spaceBackground);
        translateTransitionSpace2.setFromY(0);
        translateTransitionSpace2.setToY(-1600);

        ParallelTransition parallelTransitionStar = new ParallelTransition(translateTransitionStar, translateTransitionStar2);
        parallelTransitionStar.setCycleCount(Animation.INDEFINITE);
        parallelTransitionStar.play();

        ParallelTransition parallelTransitionDust = new ParallelTransition(translateTransitionDust, translateTransitionDust2);
        parallelTransitionDust.setCycleCount(Animation.INDEFINITE);
        parallelTransitionDust.play();

        ParallelTransition parallelTransitionSpace = new ParallelTransition(translateTransitionSpace, translateTransitionSpace2);
        parallelTransitionSpace.setCycleCount(Animation.INDEFINITE);
        parallelTransitionSpace.play();
    }

    private void lifeMechanics(){
        if (life == 3){
            hpBar1.setOpacity(100);
            hpBar2.setOpacity(100);
            hpBar3.setOpacity(100);
        }

        else if(life == 2){
            hpBar1.setOpacity(100);
            hpBar2.setOpacity(100);
            hpBar3.setOpacity(0);
        }

        else if(life == 1){
            hpBar1.setOpacity(100);
            hpBar2.setOpacity(0);
            hpBar3.setOpacity(0);
        }

        else if(life == 0){
            hpBar1.setOpacity(0);
            hpBar2.setOpacity(0);
            hpBar3.setOpacity(0);
        }
    }

    @FXML
    private void isKeyPressed(KeyEvent keyEvent){
        if (keyEvent.getCode() == KeyCode.LEFT) {
            moveLeft = true;
//            //System.out.println(spaceship.getTranslateX());//DEBUG
            keyEvent.consume();
        }
        if (keyEvent.getCode() == KeyCode.RIGHT) {
            moveRight = true;
//            //System.out.println(spaceship.getTranslateX());//DEBUG
            keyEvent.consume();
        }

        if (keyEvent.getCode() == KeyCode.SHIFT){
            moveSlow = 1;
            keyEvent.consume();
        }

        //THIS IS FOR DEBUGGING THE LIFE
        if (keyEvent.getCode() == KeyCode.C){
            life -= 1;
            keyEvent.consume();
        }

        if (keyEvent.getCode() == KeyCode.V){
            life += 1;
            keyEvent.consume();
        }
        //-------------------------------

        if (keyEvent.getCode() == KeyCode.ESCAPE) {
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

        if (keyEvent.getCode() == KeyCode.LEFT){
            moveLeft = false;
            keyEvent.consume();
        }

        if (keyEvent.getCode() == KeyCode.RIGHT){
            moveRight = false;
            keyEvent.consume();
        }

        if(keyEvent.getCode() == KeyCode.SHIFT){
            moveSlow = 0;
            keyEvent.consume();
        }
    }
}
