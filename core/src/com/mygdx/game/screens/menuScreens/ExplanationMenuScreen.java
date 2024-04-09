package com.mygdx.game.screens.menuScreens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.RaceQuizGame;

public class ExplanationMenuScreen extends MenuScreen {

    Sprite carSprite, bombSprite, coinSprite, mnactecSprite;

    Texture carTexture, bombTexture, coinTexture, mnactecTexture;

    public ExplanationMenuScreen(RaceQuizGame raceQuizGame) {
        super(raceQuizGame);
    }


    @Override
    public void show() {
        super.show();

        initializingCar();

        initializingBomb();

        initilizingCoin();

        initializingMnactec();

    }

    private void initializingMnactec() {
        mnactecTexture = new Texture("img/mnactec_logo.jpg");
        mnactecSprite = new Sprite(mnactecTexture);
        mnactecSprite.setSize(coinSprite.getWidth(), coinSprite.getHeight());
        mnactecSprite.setPosition(
                BOARD_X + 50,
                coinSprite.getY()-coinSprite.getHeight()- 30
        );
    }

    private void initilizingCoin() {
        coinTexture = new Texture("img/coin.png");
        coinSprite = new Sprite(coinTexture);
        coinSprite.setSize(coinSprite.getWidth()/2,coinSprite.getHeight()/2);
        coinSprite.setPosition(
                BOARD_X + 50,
                bombSprite.getY()-bombSprite.getHeight()- 30
        );
    }

    private void initializingBomb() {
        bombTexture = new Texture("img/bomb.png");
        bombSprite = new Sprite(bombTexture);
        bombSprite.setSize(bombSprite.getWidth()/2,bombSprite.getHeight()/2);
        bombSprite.setPosition(
                BOARD_X + 50,
                SCREEN_HEIGHT/2 - buttonSprite.getHeight()/2);
    }

    private void initializingCar() {
        carTexture = new Texture("img/carSprite.png");
        carSprite = new Sprite(carTexture);
        carSprite.setPosition(
                BOARD_X+BOARD_WIDTH/2+carSprite.getWidth()*2,
                SCREEN_HEIGHT - 450);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        batch.begin();

        carSprite.draw(batch);
        bombSprite.draw(batch);
        coinSprite.draw(batch);
        mnactecSprite.draw(batch);

        showBitMapFonts();


        batch.end();
    }

    private void showBitMapFonts() {

        showingBMFButton();

        showingBMFTittle();

        showingCarExplanation();

        showingBMFBombExplanation();

        showingBMFCoinExplanation();

        showBMFMnactec();
    }

    private void showBMFMnactec() {
        String txt = changingTextDependingLanguage(
                "PREGUNTA: Cógela, acierta y consigue 500 puntos.",
                "PREGUNTA: Agafa-la, encerta i aconsegueix 500 punts.",
                "QUESTION: Pick it up, get it right and get 500 points."
        );
        font.draw(batch,
                txt,
                mnactecSprite.getX() + mnactecSprite.getWidth() + LINE_WIDTH,
                mnactecSprite.getY()+mnactecSprite.getHeight() - 30);
    }

    private void showingBMFCoinExplanation() {
        String txt = changingTextDependingLanguage(
                "MONEDA: Si la coges, ganas 10 puntos.",
                "MONEDA: Si l'agafes, guanyes 10 punts.",
                "COIN: If you catch it, you earn 10 points."
        );
        font.draw(batch,
                txt,
                coinSprite.getX() + coinSprite.getWidth() + LINE_WIDTH,
                coinSprite.getY() + coinSprite.getHeight() - 30);
    }

    private void showingBMFBombExplanation() {
        String txt = changingTextDependingLanguage(
                "BOMBA: Si la tocas, pierdes un intento.",
                "BOMBA: Si la toques, perds un intent.",
                "BOMB: If you touch it, you lose a try."
        );
        font.draw(batch,
                txt,
                bombSprite.getX()+bombSprite.getWidth() + LINE_WIDTH,
                bombSprite.getY()+bombSprite.getHeight() - 30);
    }

    private void showingCarExplanation() {
        String txt = changingTextDependingLanguage(
                "Arrastra el coche con el dedo\n" +
                        "hacia los lados, evitando chocar\n" +
                        "contra las bombas. Recoge todas\n" +
                        "las monedas y preguntas posibles\n" +
                        "para obtener la máxima puntuación.",
                "Arrossegueu el cotxe amb el dit\n" +
                        "cap als costats, evitant xocar\n" +
                        "contra les bombes. Recull totes\n" +
                        "les monedes i preguntes possibles\n" +
                        "per obtenir la màxima puntuació.",
                "Drag the car with your finger\n" +
                        "to the sides, avoiding hitting\n" +
                        "the bombs. Collect all the\n" +
                        "coins and questions possible\n" +
                        "to get the maximum score."
        );
        font.draw(batch,
                txt,
                BOARD_X + 100,
                SCREEN_HEIGHT - 200);
    }

    private void showingBMFTittle() {
        String tittleText = changingTextDependingLanguage(
                "Explicación del juego",
                "Explicació del joc",
                "Game explanation");
        font.draw(batch,
                tittleText,
                BOARD_X,
                SCREEN_HEIGHT - 100,
                BOARD_WIDTH,
                Align.center,
                true);
    }

    private void showingBMFButton() {
        String continueText = changingTextDependingLanguage("Continuar", "Continuar", "Continue");

        blackFont.draw(batch,
                continueText,
                BOARD_X,
                ANSWER_BOX_C_Y + 100 + ANSWER_BOX_HEIGHT / 2 - 10,
                BOARD_WIDTH,
                Align.center,
                true);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        screenY = (int) SCREEN_HEIGHT - screenY;

        exiting(screenX, screenY);

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

            raceQuizGame.setScreen(raceQuizGame.drivingScreen);
        }
    }
}
