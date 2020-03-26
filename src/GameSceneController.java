import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class GameSceneController {

    private int sensitivity = ConstantSettings.settingsValues[2];
	
	@FXML private ImageView asteroid1;
    @FXML private ImageView asteroid2;
    @FXML private ImageView asteroid3;
    @FXML private ImageView asteroid4;
    @FXML private ImageView asteroid5;
    @FXML private ImageView asteroid6;
    @FXML private ImageView asteroid7;
    @FXML private ImageView asteroid8;
    private ImageView[] asteroids = new ImageView[8];

    private String selectedMusic = Main.selectedMusic;

    private int score = 0;
    private int life = 3;

    private int asteroidSpeed=2;

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

            if (gunMeter.getHeight() <= 116){
                gunMeter.setHeight(gunMeter.getHeight() + 4);
            }
			

            collisionDetection();

            lifeMechanics();

            score += 1;
            scoreLabel.setText(Integer.toString(score));
        }
    };

    @FXML private ImageView spaceship;

    @FXML private ImageView starBackground, dustBackground, spaceBackground;

    @FXML private ImageView hpBar1, hpBar2, hpBar3;

    @FXML private Rectangle gunMeter;

    @FXML private ImageView laserBeam;


    @FXML
    public void initialize() {
		
		asteroids[0]=asteroid1;
        asteroids[1]=asteroid2;
        asteroids[2]=asteroid3;
        asteroids[3]=asteroid4;
        asteroids[4]=asteroid5;
        asteroids[5]=asteroid6;
        asteroids[6]=asteroid7;
        asteroids[7]=asteroid8;
        for(ImageView i: asteroids) {
            i.setLayoutY(Math.random()*(-900)-100);
            i.setLayoutX(590);
            i.setTranslateX(Math.random()*(585+645-1)-645);
        }
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

    private void collisionDetection(){
        //asteroid movement, asteroid collision detection
        for(int i=0; i<8; i++) {
            ImageView asteroid=asteroids[i];
            //lopsided asteroid collision
            if(i==0 || i==4) {
                if (asteroid.getLayoutY() >= 850
                        //left edge of of asteroid
                        || (asteroid.getLayoutY() >= spaceship.getLayoutY() - 110
                        && asteroid.getLayoutY() <= spaceship.getLayoutY() + 15
                        && asteroid.getTranslateX() >= spaceship.getTranslateX() - 85
                        && asteroid.getTranslateX() <= spaceship.getTranslateX() - 28)
                        //right edge of of asteroid
                        || (asteroid.getLayoutY() >= spaceship.getLayoutY() - 145
                        && asteroid.getLayoutY() <= spaceship.getLayoutY() + 30
                        && asteroid.getTranslateX() >= spaceship.getTranslateX() + 28
                        && asteroid.getTranslateX() <= spaceship.getTranslateX() + 85)
                        //closer to center of asteroid
                        || (asteroid.getLayoutY() >= spaceship.getLayoutY() - 135
                        && asteroid.getLayoutY() <= spaceship.getLayoutY() + 30
                        && asteroid.getTranslateX() >= spaceship.getTranslateX() - 28
                        && asteroid.getTranslateX() <= spaceship.getTranslateX() + 28)) {
                    life--;
                    asteroid.setLayoutY(Math.random() * (-900) - 100);
                    asteroid.setTranslateX(Math.random() * (585 + 645 - 1) - 645);
                }
                else {
                    asteroid.setLayoutY(asteroid.getLayoutY() + asteroidSpeed);
                }
            }
            //regular-shaped (rounder) asteroid collision
            else {
                if (asteroid.getLayoutY() >= 850
                        //left edge of of asteroid
                        || (asteroid.getLayoutY() >= spaceship.getLayoutY() - 125
                        && asteroid.getLayoutY() <= spaceship.getLayoutY() + 30
                        && asteroid.getTranslateX() >= spaceship.getTranslateX() - 85
                        && asteroid.getTranslateX() <= spaceship.getTranslateX() - 40)
                        //right edge of of asteroid
                        || (asteroid.getLayoutY() >= spaceship.getLayoutY() - 125
                        && asteroid.getLayoutY() <= spaceship.getLayoutY() + 30
                        && asteroid.getTranslateX() >= spaceship.getTranslateX() + 40
                        && asteroid.getTranslateX() <= spaceship.getTranslateX() + 85)
                        //closer to center of asteroid
                        || (asteroid.getLayoutY() >= spaceship.getLayoutY() - 145
                        && asteroid.getLayoutY() <= spaceship.getLayoutY() + 30
                        && asteroid.getTranslateX() >= spaceship.getTranslateX() - 40
                        && asteroid.getTranslateX() <= spaceship.getTranslateX() + 40)) {
                    life--;
                    asteroid.setLayoutY(Math.random() * (-900) - 100);
                    asteroid.setTranslateX(Math.random() * (585 + 645 - 1) - 645);
                }
                else {
                    asteroid.setLayoutY(asteroid.getLayoutY() + asteroidSpeed);
                }
            }
        }// end for loop
    }

    private void hitDetection(){
        laserBeam.setTranslateX(spaceship.getTranslateX());
        laserBeam.setTranslateY(spaceship.getTranslateY());
        laserBeam.setOpacity(1);

        //asteroid movement, asteroid collision detection
        for(int i=0; i<8; i++) {
            ImageView asteroid=asteroids[i];
            //lopsided asteroid collision
            if(i==0 || i==4) {
                if (asteroid.getLayoutY() >= 850
                        //left edge of of asteroid
                        || (
                        asteroid.getTranslateX() >= laserBeam.getTranslateX() - 85
                        && asteroid.getTranslateX() <= laserBeam.getTranslateX() - 28)
                        //right edge of of asteroid
                        || (
                        asteroid.getTranslateX() >= laserBeam.getTranslateX() + 28
                        && asteroid.getTranslateX() <= laserBeam.getTranslateX() + 85)
                        //closer to center of asteroid
                        || (asteroid.getTranslateX() >= laserBeam.getTranslateX() - 28
                        && asteroid.getTranslateX() <= laserBeam.getTranslateX() + 28)) {
                    asteroid.setLayoutY(Math.random() * (-900) - 100);
                    asteroid.setTranslateX(Math.random() * (585 + 645 - 1) - 645);
                }
                else {
                    asteroid.setLayoutY(asteroid.getLayoutY() + asteroidSpeed);
                }
            }
            //regular-shaped (rounder) asteroid collision
            else {
                if (asteroid.getLayoutY() >= 850
                        //left edge of of asteroid
                        || (asteroid.getTranslateX() >= laserBeam.getTranslateX() - 85
                        && asteroid.getTranslateX() <= laserBeam.getTranslateX() - 40)
                        //right edge of of asteroid
                        || (asteroid.getTranslateX() >= laserBeam.getTranslateX() + 40
                        && asteroid.getTranslateX() <= laserBeam.getTranslateX() + 85)
                        //closer to center of asteroid
                        || (asteroid.getTranslateX() >= laserBeam.getTranslateX() - 40
                        && asteroid.getTranslateX() <= laserBeam.getTranslateX() + 40)) {
                    asteroid.setLayoutY(Math.random() * (-900) - 100);
                    asteroid.setTranslateX(Math.random() * (585 + 645 - 1) - 645);
                }
                else {
                    asteroid.setLayoutY(asteroid.getLayoutY() + asteroidSpeed);
                }
            }
        }// end for loop

        FadeTransition laserDecay = new FadeTransition(Duration.millis(300),laserBeam);
        laserDecay.setInterpolator(Interpolator.EASE_IN);
        laserDecay.setToValue(0);
        laserDecay.play();

        ScaleTransition laserShrink = new ScaleTransition(Duration.millis(300),laserBeam);
        laserShrink.setInterpolator(Interpolator.EASE_IN);
        laserShrink.setToX(0);

        ScaleTransition laserGrow = new ScaleTransition(Duration.millis(0),laserBeam);
        laserGrow.setToX(1);

        ParallelTransition laser = new ParallelTransition(laserShrink,laserGrow);
        laser.play();
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

        if (keyEvent.getCode() == KeyCode.Z){
            hitDetection();
            if (gunMeter.getHeight() > 70) {
                gunMeter.setHeight(gunMeter.getHeight() - 70);
            }
            else{
                gunMeter.setHeight(0);
            }
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
