package com.mygdx.game.screens.menuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.RaceQuizGame;
import com.mygdx.game.classes.JSONManager;
import com.mygdx.game.classes.Prize;

import java.util.ArrayList;

public class GameOverScreen extends MenuScreen{

    Sprite imgSprite;
    Texture imgTexture;
    ArrayList<Prize> prizesList;
    BitmapFont gameOverFont;
    BitmapFont redFont;


    public GameOverScreen(RaceQuizGame raceQuizGame) {
        super(raceQuizGame);
    }


    @Override
    public void show() {
        super.show();

        prizesList = JSONManager.readingPrizes();

        selectingPrize();

        initializingPrizeSprite();

        gameOverFont = new BitmapFont(Gdx.files.internal("font/SanFran.fnt"),
                Gdx.files.internal("font/SanFran.png"), false);


        redFont = new BitmapFont(Gdx.files.internal("font/SanFran.fnt"),
                Gdx.files.internal("font/SanFran.png"), false);
        redFont.setColor(MAGIC_RED_COLOR);

    }

    private void initializingPrizeSprite() {
        imgSprite = new Sprite(imgTexture);
        imgSprite.setSize(BOARD_WIDTH/4, BOARD_WIDTH/4);
        imgSprite.setPosition(SCREEN_WIDTH/2, SCREEN_HEIGHT/2 - imgSprite.getHeight()/2);
    }

    private void selectingPrize() {
        boolean prizeChosen = false;
        for (Prize prize :
                prizesList) {

            if (player.getScore() <= prize.getScore() &&!prizeChosen){
                FileHandle file = Gdx.files.local(prize.getImgPath());
                imgTexture = new Texture(file);
                prizeChosen = true;
            }
        }

        if(!prizeChosen){

            imgTexture = new Texture("img/mnactec_logo.jpg");
        }
    }


    @Override
    public void render(float delta) {
        super.render(delta);

        batch.begin();

        imgSprite.draw(batch);
        showButtonText();

        showingGameOver();
        showFinalScore();
        showFinalTime();
        batch.end();

    }

    private void showFinalTime() {
        String txt = changingTextDependingLanguage(
                "Tiempo jugado:\n",
                "Temps jugat:\n",
                "Played time:\n");


        redFont.draw(batch,
                txt,
                BOARD_X + BOARD_WIDTH / 2 - buttonSprite.getWidth() / 2 - LINE_WIDTH * 5,
                ANSWER_BOX_A_Y + 125 + ANSWER_BOX_HEIGHT / 2 - 80);

        font.draw(batch,
                player.getTimePlayed(),
                BOARD_X + BOARD_WIDTH / 2 - buttonSprite.getWidth() / 2 - LINE_WIDTH * 5,
                ANSWER_BOX_A_Y + 125 + ANSWER_BOX_HEIGHT / 2 - 140);
    }

    private void showFinalScore() {
        String txt = changingTextDependingLanguage(
                "Puntuación final:",
                "Puntuació final:",
                "Final score:");

        redFont.draw(batch,
                txt,
                BOARD_X + BOARD_WIDTH / 2 - buttonSprite.getWidth() / 2 - LINE_WIDTH * 5,
                SCREEN_HEIGHT / 4 * 3 - 150 + ANSWER_BOX_HEIGHT / 2 - 80);

        font.draw(batch,
                String.valueOf(player.getScore()),
                BOARD_X + BOARD_WIDTH / 2 - buttonSprite.getWidth() / 2 - LINE_WIDTH * 5,
                SCREEN_HEIGHT / 4 * 3 - 150 + ANSWER_BOX_HEIGHT / 2 - 140);
    }

    private void showingGameOver() {
        String txt = changingTextDependingLanguage(
                "Juego Terminado",
                "Joc acabat",
                "Game Over"
        );

        gameOverFont.getData().setScale(3);
        gameOverFont.setColor(MAGIC_RED_COLOR);
        gameOverFont.draw(batch,
                txt,
                BOARD_X,
                SCREEN_HEIGHT / 4 * 3 - 25 + ANSWER_BOX_HEIGHT / 2 - 10,
                BOARD_WIDTH,
                Align.center,
                true);
    }

    private void showButtonText() {
        String txt = changingTextDependingLanguage(
                "Terminar",
                "Acabar",
                "Finish");

        blackFont.draw(batch,
                txt,
                BOARD_X,
                ANSWER_BOX_C_Y + 100 + ANSWER_BOX_HEIGHT / 2 - 10,
                BOARD_WIDTH,
                Align.center,
                true);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        screenY = (int) SCREEN_HEIGHT - screenY;


        exiting(screenX,screenY);

        touchingFinishButton(screenX, screenY);

        return false;
    }

    private void touchingFinishButton(int screenX, int screenY) {
        boolean touchedButton = screenX > buttonSprite.getX()
                && screenX < buttonSprite.getX() + buttonSprite.getWidth()
                && screenY > buttonSprite.getY()
                && screenY < buttonSprite.getY() + buttonSprite.getHeight();

        if(touchedButton){

            /*if (countDownTimer.isScheduled()){

                countDownTimer.cancel();
            }

            player.restartingPlayer();
            raceQuizGame.setStartingGame(true);
            raceQuizGame.setScreen(raceQuizGame.drivingScreen);*/


            exiting(screenX,screenY);
        }

    }

    @Override
    public void dispose() {
        super.dispose();
        gameOverFont.dispose();
        redFont.dispose();
    }
}
