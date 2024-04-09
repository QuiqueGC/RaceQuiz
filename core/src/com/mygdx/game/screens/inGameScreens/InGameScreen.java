package com.mygdx.game.screens.inGameScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.RaceQuizGame;
import com.mygdx.game.classes.RoadLines;
import com.mygdx.game.screens.AbstractScreen;

public abstract class InGameScreen extends AbstractScreen {


    protected Thread counterThreat;
    protected RoadLines roadLines;
    BitmapFont redFont;
    protected Sprite scoreSquareSprite;
    protected Texture scoreSquareTexture;


    public InGameScreen(RaceQuizGame raceQuizGame) {
        super(raceQuizGame);
    }


    @Override
    public void show() {
        super.show();

        redFont = new BitmapFont(Gdx.files.internal("font/SanFran.fnt"),
                Gdx.files.internal("font/SanFran.png"), false);
        redFont.setColor(MAGIC_RED_COLOR);
        redFont.getData().setScale(fontSize);

        player.getSprite().setX(SCREEN_WIDTH / 2f - player.getSprite().getWidth() / 2);
        player.getSprite().setY(50);

        roadLines = new RoadLines(BOARD_X, BOARD_WIDTH, SCREEN_HEIGHT, player);

        initializeScoreSquareSprite();

        startingTimer();
    }

    private void initializeScoreSquareSprite() {
        scoreSquareTexture = new Texture("img/scoreSquare.png");
        scoreSquareSprite = new Sprite(scoreSquareTexture);
        scoreSquareSprite.setSize(SCREEN_WIDTH - (BOARD_X + BOARD_WIDTH) - LINE_WIDTH * 2, SCREEN_HEIGHT / 2);
        scoreSquareSprite.setPosition(BOARD_X + BOARD_WIDTH + LINE_WIDTH, SCREEN_HEIGHT -scoreSquareSprite.getHeight() - LINE_WIDTH);

    }


    private void startingTimer() {

        if (raceQuizGame.isStartingGame()) {

            initializeCountDown();
            raceQuizGame.setStartingGame(false);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        showingRoad();

        batch.begin();

        scoreSquareSprite.draw(batch);

        showingScore();

        showingTime();

        showingTries();

        checkingIfGameOver();

        batch.end();
    }

    protected void checkingIfGameOver() {

        if (player.getTries() <= 0) {
            raceQuizGame.setScreen(raceQuizGame.gameOverScreen);


            /*if (countDownTimer.isScheduled()){

                countDownTimer.cancel();
            }*/

        }
    }


    /**
     * Inicia el contador en caso de que llegue a cero
     */
    private void initializeCountDown() {

        counterThreat = new Thread(this);
        counterThreat.start();
    }


    /**
     * Dibuja la carretera con un ShapeRenderer y las líneas de la misma a partir
     * del objeto creado de la clase RoadLines
     */
    private void showingRoad() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.5529f, 0.5804f, 0.5529f, 1);
        shapeRenderer.rect(BOARD_X, 0, BOARD_WIDTH, SCREEN_HEIGHT);


        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.rect(BOARD_X, 0, LINE_WIDTH, SCREEN_HEIGHT);
        shapeRenderer.rect(BOARD_X + BOARD_WIDTH - LINE_WIDTH, 0, 20, SCREEN_HEIGHT);

        shapeRenderer.end();

        batch.begin();

        for (Sprite sp : roadLines.getRoadLinesList()) {

            Texture textureLine = new Texture("img/line.png");
            sp.setTexture(textureLine);
            sp.draw(batch);
        }

        batch.end();
    }


    private void showingTries() {
        String triesText = changingTextDependingLanguage(
                "Intentos:",
                "Intents:",
                "Tries:");


        redFont.draw(batch,
                triesText,
                scoreSquareSprite.getX(),
                ANSWER_BOX_HEIGHT * 4,
                scoreSquareSprite.getWidth(),
                Align.center,
                true);

        blackFont.draw(batch,
                String.valueOf(player.getTries()),
                scoreSquareSprite.getX(),
                ANSWER_BOX_HEIGHT * 4 - LINE_WIDTH*3,
                scoreSquareSprite.getWidth(),
                Align.center,
                true);
    }

    /**
     * Escribe el texto referente a la puntuación
     */
    private void showingScore() {
        String scoreText = changingTextDependingLanguage(
                "Puntos:",
                "Punts:",
                "Score:");

        redFont.draw(batch,
                scoreText,
                scoreSquareSprite.getX(),
                ANSWER_BOX_HEIGHT * 5,
                scoreSquareSprite.getWidth(),
                Align.center,
                true);

        blackFont.draw(batch,
                String.valueOf(player.getScore()),
                scoreSquareSprite.getX(),
                ANSWER_BOX_HEIGHT * 5 - LINE_WIDTH*3,
                scoreSquareSprite.getWidth(),
                Align.center,
                true);
    }

    /**
     * Escribe el texto referente al contador de tiempo
     */
    private void showingTime() {
        String timeText = changingTextDependingLanguage(
                "Tiempo:",
                "Temps:",
                "Time:");

        redFont.draw(batch,
                timeText,
                scoreSquareSprite.getX(),
                SCREEN_HEIGHT - redFont.getScaleY() - LINE_WIDTH*2,
                scoreSquareSprite.getWidth(),
                Align.center,
                true);

        blackFont.draw(batch,
                player.getTimePlayed(),
                scoreSquareSprite.getX(),
                SCREEN_HEIGHT - redFont.getScaleY() - LINE_WIDTH*4,
                scoreSquareSprite.getWidth(),
                Align.center,
                true);
    }


    @Override
    public void run() {

        countDown();

    }

    /**
     * La cuenta atrás del temporizador
     */
    private void countDown() {

        do {

            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {

                System.out.println(e);
            }

            player.oneMoreSecond();

        } while (player.getTries() > 0);
    }

}
