package com.mygdx.game.screens.menuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.RaceQuizGame;
import com.mygdx.game.screens.AbstractScreen;

public abstract class MenuScreen extends AbstractScreen {

    Sprite centralBoxSprite;
    Texture centralBoxTexture;

    protected Sprite buttonSprite;
    protected Texture buttonTexture;

    protected Timer.Task countDownTimer;



    public MenuScreen(RaceQuizGame raceQuizGame) {
        super(raceQuizGame);
    }

    @Override
    public void show() {
        super.show();

        initializeBigBox();

        initializingButton();

        initializingTimer();

        startCountDownTimer();

    }

    private void initializingTimer() {
        countDownTimer = new Timer.Task() {
            @Override
            public void run() {
                Gdx.app.exit();
            }
        };
    }

    protected void startCountDownTimer() {

        if (!countDownTimer.isScheduled()) {

            Timer.schedule(countDownTimer, 60);
        }
    }


    private void initializeBigBox() {
        centralBoxTexture = new Texture("img/blackBox.png");
        centralBoxSprite = new Sprite(centralBoxTexture);
        centralBoxSprite.setSize(BOARD_WIDTH+160,SCREEN_HEIGHT-50);
        centralBoxSprite.setPosition(
                SCREEN_WIDTH/2 - centralBoxSprite.getWidth()/2,
                SCREEN_HEIGHT/2 - centralBoxSprite.getHeight()/2);

    }

    private void initializingButton() {
        buttonTexture = new Texture("img/Continue.png");
        buttonSprite = new Sprite(buttonTexture);
        buttonSprite.setSize(BOARD_WIDTH / 2, 100);
        buttonSprite.setPosition(
                BOARD_X + BOARD_WIDTH / 2 - buttonSprite.getWidth() / 2,
                ANSWER_BOX_C_Y + 100);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        batch.begin();

        centralBoxSprite.draw(batch);

        buttonSprite.draw(batch);

        batch.end();
    }


}
