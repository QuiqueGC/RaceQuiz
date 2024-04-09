package com.mygdx.game.screens.inGameScreens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.RaceQuizGame;


public class ExplosionScreen extends InGameScreen{

    Sprite explosionSprite;
    Texture explosionTexture;

    int timeToChange, explosionTiming;


    public ExplosionScreen(RaceQuizGame raceQuizGame) {
        super(raceQuizGame);
    }

    @Override
    public void show() {
        super.show();

        explosionTiming = player.getSecondsPlayed();
        timeToChange = player.getSecondsPlayed() + 1;

        explosionTexture = new Texture("img/explosion.png");
        explosionSprite = new Sprite(explosionTexture);
        explosionSprite.setSize(BOARD_WIDTH, BOARD_WIDTH);
        explosionSprite.setPosition(BOARD_X+BOARD_WIDTH/2 - explosionSprite.getWidth()/2, SCREEN_HEIGHT/2 - explosionSprite.getHeight()/2);

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        explosionTiming = player.getSecondsPlayed();

        batch.begin();
        explosionSprite.draw(batch);
        batch.end();

        if(explosionTiming >= timeToChange){

            /*if (countDownTimer.isScheduled()){

                countDownTimer.cancel();
            }*/
            raceQuizGame.setScreen(raceQuizGame.drivingScreen);
        }
    }

}
