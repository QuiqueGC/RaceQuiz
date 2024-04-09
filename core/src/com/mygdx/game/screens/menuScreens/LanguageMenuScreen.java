package com.mygdx.game.screens.menuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.RaceQuizGame;

import java.util.ArrayList;

public class LanguageMenuScreen extends MenuScreen {

    Sprite boxSprite;
    ArrayList<Sprite> boxesList;
    Texture boxTexture, chosenBoxTexture;


    public LanguageMenuScreen(RaceQuizGame raceQuizGame) {
        super(raceQuizGame);
    }

    @Override
    public void show() {
        super.show();

        boxesList = new ArrayList<>();
        boxTexture = new Texture("img/QuestionBox.png");
        chosenBoxTexture = new Texture("img/rightAnswerBox.png");

        initializeBoxes();
    }


    private void initializeBoxes() {

        for (int i = 0; i < 3; i++) {
            boxSprite = new Sprite(boxTexture);
            boxSprite.setSize(BOARD_WIDTH / 2, 100);
            boxesList.add(boxSprite);
        }

        boxesList.get(0).setTexture(chosenBoxTexture);
        boxesList.get(0).setPosition(
                BOARD_X + BOARD_WIDTH / 2 - boxesList.get(0).getWidth() / 2,
                SCREEN_HEIGHT / 4 * 3 - 150+100);

        boxesList.get(1).setPosition(
                BOARD_X + BOARD_WIDTH / 2 - boxesList.get(0).getWidth() / 2,
                ANSWER_BOX_A_Y + 125+100);

        boxesList.get(2).setPosition(
                BOARD_X + BOARD_WIDTH / 2 - boxesList.get(0).getWidth() / 2,
                ANSWER_BOX_B_Y + 150+100);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        batch.begin();

        showBoxes();

        writtingtexts();

        batch.end();
    }

    protected void writtingtexts() {
        String continueText = changingTextDependingLanguage("Continuar", "Continuar", "Continue");
        String languageText = changingTextDependingLanguage("Selección de idioma", "Selecció d'idioma", "Language selection");


        writtingText("Castellano", SCREEN_HEIGHT / 4 * 3 - 150, font);
        writtingText("Català", ANSWER_BOX_A_Y + 125, font);
        writtingText("English", ANSWER_BOX_B_Y + 150, font);
        writtingText(continueText, ANSWER_BOX_C_Y, blackFont);
        writtingText(languageText, SCREEN_HEIGHT / 4 * 3 - 25, font);
    }

    protected void writtingText(String txt, float boxY, BitmapFont font) {
        font.draw(batch,
                txt,
                BOARD_X,
                boxY + ANSWER_BOX_HEIGHT / 2 - 10 +100,
                BOARD_WIDTH,
                Align.center,
                true);
    }

    private void showBoxes() {

        for (Sprite boxSprite :
                boxesList) {
            boxSprite.draw(batch);
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        screenY = (int) SCREEN_HEIGHT - screenY;

        exiting(screenX, screenY);

        pickingLanguage(screenX, screenY, boxesList.get(0), 1);
        pickingLanguage(screenX, screenY, boxesList.get(1), 2);
        pickingLanguage(screenX, screenY, boxesList.get(2), 3);

        touchingContinueButton(screenX, screenY);

        return false;
    }

    private void touchingContinueButton(int screenX, int screenY) {
        boolean touchedContinue = screenX > buttonSprite.getX()
                && screenX < buttonSprite.getX() + buttonSprite.getWidth()
                && screenY > buttonSprite.getY()
                && screenY < buttonSprite.getY() + buttonSprite.getHeight();

        if(touchedContinue){

            if (countDownTimer.isScheduled()){

                countDownTimer.cancel();
            }

            raceQuizGame.setScreen(raceQuizGame.difficultyMenuScreen);
        }
    }

    private void pickingLanguage(int screenX, int screenY, Sprite boxSprite, int language) {
        boolean pickedLanguage = screenX > boxSprite.getX()
                && screenX < boxSprite.getX() + boxSprite.getWidth()
                && screenY > boxSprite.getY()
                && screenY < boxSprite.getY() + boxSprite.getHeight();

        if (pickedLanguage) {

            for (Sprite sprite :
                    boxesList) {
                sprite.setTexture(boxTexture);
            }

            boxSprite.setTexture(chosenBoxTexture);
            raceQuizGame.setLanguage(language);
        }
    }
}
