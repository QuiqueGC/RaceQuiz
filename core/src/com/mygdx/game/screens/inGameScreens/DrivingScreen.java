package com.mygdx.game.screens.inGameScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.classes.ElementCreationTimer;
import com.mygdx.game.RaceQuizGame;
import com.mygdx.game.classes.Player;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Timer;

public class DrivingScreen extends InGameScreen {

    Thread roadLinesThreat;
    ArrayList<Sprite> bombsList;
    ArrayList<Sprite> bombsToDelete;
    Texture bombTexture;
    ArrayList<Sprite> coinsList;
    ArrayList<Sprite> coinsToDelete;
    Texture coinTexture;
    ArrayList<Sprite> mnactecList;
    ArrayList<Sprite> mnactecToDelete;
    Texture mnactecTexture;
    Timer timer;

    public DrivingScreen(RaceQuizGame raceQuizGame) {
        super(raceQuizGame);

    }

    @Override
    public void show() {
        super.show();


        bombsList = new ArrayList<>();
        bombsToDelete = new ArrayList<>();
        bombTexture = new Texture("img/bomb.png");

        coinsList = new ArrayList<>();
        coinsToDelete = new ArrayList<>();
        coinTexture = new Texture("img/coin.png");

        mnactecList = new ArrayList<>();
        mnactecToDelete = new ArrayList<>();
        mnactecTexture = new Texture("img/mnactec_logo.jpg");


        roadLinesThreat = new Thread(roadLines);
        roadLinesThreat.start();


        timer = new Timer();
        timer.schedule(new ElementCreationTimer(bombsList, coinsList, mnactecList ,BOARD_X, SCREEN_HEIGHT),0,500);

    }


    @Override
    public void render(float delta) {
        super.render(delta);


        batch.begin();

        showingAndMovingObstacles();

        player.getSprite().draw(batch);

        batch.end();

    }


    /**
     * Dibuja y mueve los obstáculos que van apareciendo
     * y añade o resta puntos al jugador en base a las colisiones
     */
    private void showingAndMovingObstacles() {

        try{

            movingBombs();

        }catch (ConcurrentModificationException e){

            System.out.println("Fallo con bombas");
        }
        try{

            movingCoins();

        }catch (ConcurrentModificationException e){

            System.out.println("Fallo con monedas");
        }
        try{

            movingMnactec();

        }catch (ConcurrentModificationException e){

            System.out.println("Fallo con mnactec");
        }

            bombsList.removeAll(bombsToDelete);
            bombsToDelete.clear();

            coinsList.removeAll(coinsToDelete);
            coinsToDelete.clear();

    }

    private void movingMnactec() {

        for (Sprite obstacleSprite :
                mnactecList) {
            obstacleSprite.setTexture(mnactecTexture);
            obstacleSprite.draw(batch);

            obstacleSprite.setY(obstacleSprite.getY() - player.getSpeed() * 10 * Gdx.graphics.getDeltaTime());

            if (obstacleSprite.getBoundingRectangle().overlaps(player.getSprite().getBoundingRectangle())) {

                /*if (countDownTimer.isScheduled()){

                    countDownTimer.cancel();
                }*/

                raceQuizGame.setScreen(raceQuizGame.questionScreen);

                obstacleSprite.setX(SCREEN_WIDTH);
                mnactecToDelete.add(obstacleSprite);

            } else if (obstacleSprite.getY() < 0) {
                mnactecToDelete.add(obstacleSprite);
            }
        }
    }

    private void movingCoins() {

        for (Sprite obstacleSprite :
                coinsList) {
            obstacleSprite.setTexture(coinTexture);
            obstacleSprite.draw(batch);

            obstacleSprite.setY(obstacleSprite.getY() - player.getSpeed() * 10 * Gdx.graphics.getDeltaTime());

            if (obstacleSprite.getBoundingRectangle().overlaps(player.getSprite().getBoundingRectangle())) {

                player.setScore(player.getScore()+10);

                obstacleSprite.setX(SCREEN_WIDTH);
                coinsToDelete.add(obstacleSprite);

            } else if (obstacleSprite.getY() < 0) {
                coinsToDelete.add(obstacleSprite);
            }
        }
    }


    /**
     * Mueve las bombas y chequea si impactan con el jugador
     */
    private void movingBombs() {

        for (Sprite obstacleSprite :
                bombsList) {
            obstacleSprite.setTexture(bombTexture);
            obstacleSprite.draw(batch);

            obstacleSprite.setY(obstacleSprite.getY() - player.getSpeed() * 10 * Gdx.graphics.getDeltaTime());

            if (obstacleSprite.getBoundingRectangle().overlaps(player.getSprite().getBoundingRectangle())) {

                /*if (countDownTimer.isScheduled()){

                    countDownTimer.cancel();
                }*/

                player.setTries(player.getTries()-1);
                raceQuizGame.setScreen(raceQuizGame.explosionScreen);

                obstacleSprite.setX(SCREEN_WIDTH);
                bombsToDelete.add(obstacleSprite);

            } else if (obstacleSprite.getY() < 0) {
                bombsToDelete.add(obstacleSprite);
            }
        }
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {


        /*if (countDownTimer.isScheduled()){

            countDownTimer.cancel();
        }*/

        screenY = (int) SCREEN_HEIGHT - screenY;

        checkingIfIsTouchingCar(screenX, screenY);

        exiting(screenX,screenY);

        return false;
    }


    /**
     * Chequea si el jugador está tocando el coche
     *
     * @param screenX La posición en el eje X que toca el jugador
     * @param screenY La posición en el eje Y que toca el jugador
     */
    private void checkingIfIsTouchingCar(int screenX, int screenY) {
        boolean isTouchingCar = screenX >= player.getSprite().getX()
                && screenX <= player.getSprite().getX() + player.getSprite().getWidth()
                && screenY >= player.getSprite().getY()
                && screenY <= player.getSprite().getY() + player.getSprite().getHeight();

        if (isTouchingCar) {
            xTouched = screenX;
            yTouched = screenY;
        }
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        /*if (countDownTimer.isScheduled()){

            countDownTimer.cancel();
        }*/

        if (xTouched != -1 && yTouched != -1) {

            screenY = (int) SCREEN_HEIGHT - screenY;

            checkPositionAndMove(screenX, screenY);
        }

        return false;
    }


    /**
     * Chequea que la posición a donde se pretende arrastrar
     * el sprite está dentro de los límites de la carretera
     *
     * @param screenX La posición en el eje X donde está tocando el usuario
     * @param screenY La posición en el eje Y donde está tocando el usuario
     */
    private void checkPositionAndMove(int screenX, int screenY) {
        int xMovement = xTouched - screenX;
        boolean isInTheRoad = player.getSprite().getX() - xMovement >= BOARD_X
                        && player.getSprite().getX() + player.getSprite().getWidth() - xMovement <= BOARD_X + BOARD_WIDTH;


        if (isInTheRoad) {


            player.getSprite().setX(player.getSprite().getX() - xMovement);

        }

        xTouched = screenX;
        yTouched = screenY;
    }
}
