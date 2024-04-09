package com.mygdx.game.screens.menuScreens;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.RaceQuizGame;

public class DifficultyMenuScreen extends LanguageMenuScreen{


    public DifficultyMenuScreen(RaceQuizGame raceQuizGame) {
        super(raceQuizGame);
    }


    @Override
    protected void writtingtexts() {
        String continueText = changingTextDependingLanguage("Continuar", "Continuar", "Continue");
        String difficultyText = changingTextDependingLanguage("Selección de dificultad", "Selecció de dificultat", "Difficulty selection");
        String easyText = changingTextDependingLanguage("Fácil (8-11 años)","Fàcil (8-11 anys)","Easy (8-11 years old)");
        String normalText = changingTextDependingLanguage("Normal (12-17 años)", "Normal (12-17 anys)", "Normal (12-17 years old)");
        String hardText = changingTextDependingLanguage("Difícil (más de 18 años)", "Difícil (més de 18 anys)", "Hard (more tha 18 years old)");

        writtingText(easyText, SCREEN_HEIGHT / 4 * 3 - 150, font);
        writtingText(normalText, ANSWER_BOX_A_Y + 125, font);
        writtingText(hardText, ANSWER_BOX_B_Y + 150, font);
        writtingText(continueText, ANSWER_BOX_C_Y, blackFont);
        writtingText(difficultyText, SCREEN_HEIGHT / 4 * 3 - 25, font);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        screenY = (int) SCREEN_HEIGHT - screenY;

        exiting(screenX, screenY);

        pickingDifficulty(screenX, screenY, boxesList.get(0), 1);
        pickingDifficulty(screenX, screenY, boxesList.get(1), 2);
        pickingDifficulty(screenX, screenY, boxesList.get(2), 3);

        touchingContinueButton(screenX, screenY);

        return false;
    }

    private void pickingDifficulty(int screenX, int screenY, Sprite boxSprite, int difficulty) {
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
            raceQuizGame.setDifficulty(difficulty);
        }
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

            raceQuizGame.setScreen(raceQuizGame.explanationMenuScreen);
        }
    }
}
