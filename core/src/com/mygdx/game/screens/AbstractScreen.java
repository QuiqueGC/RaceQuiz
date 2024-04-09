package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.RaceQuizGame;
import com.mygdx.game.classes.Player;

/**
 * Clase (con las características generales de cada pantalla)
 * que implementa Screen y que heredarán cada una de las pantallas
 */
public abstract class AbstractScreen implements Screen, Runnable, InputProcessor {

    protected float SCREEN_WIDTH,
            SCREEN_HEIGHT,
            BOARD_X,
            BOARD_WIDTH,
            LINE_WIDTH,
            ANSWER_BOX_HEIGHT,
            ANSWER_BOX_A_Y,
            ANSWER_BOX_B_Y,
            ANSWER_BOX_C_Y,
            QUESTION_BOX_Y,
            fontSize;

    protected Texture exitTexture = new Texture("img/Continue.png");
    protected Sprite exitSprite = new Sprite(exitTexture);
    protected SpriteBatch batch;
    protected Player player;
    protected ShapeRenderer shapeRenderer;
    protected int xTouched = -1, yTouched = -1;
    protected RaceQuizGame raceQuizGame;
    Sprite backgroundSprite;
    Texture backgroundTexture;
    protected BitmapFont font;
    protected BitmapFont blackFont;

    protected final Color MAGIC_RED_COLOR = new Color(0xa3320bff);
    protected final Color MAGIC_GREEN_COLOR = new Color(0x6BCF8Fff);


    public AbstractScreen(RaceQuizGame raceQuizGame) {
        this.raceQuizGame = raceQuizGame;
        this.batch = raceQuizGame.getBatch();
        this.player = raceQuizGame.getPlayer();
        this.shapeRenderer = raceQuizGame.getShapeRenderer();
    }

    @Override
    public void show() {

        settingGeneralDimensions();

        font = new BitmapFont(Gdx.files.internal("font/SanFran.fnt"),
                Gdx.files.internal("font/SanFran.png"), false);

        font.getData().setScale(fontSize);

        blackFont = new BitmapFont(Gdx.files.internal("font/SanFran.fnt"),
                Gdx.files.internal("font/SanFran.png"), false);

        blackFont.getData().setScale(fontSize);

        blackFont.setColor(Color.BLACK);

        backgroundTexture = new Texture("img/fondoapp.png");
        backgroundSprite = new Sprite(backgroundTexture);
        backgroundSprite.setPosition(SCREEN_WIDTH / 2 - backgroundSprite.getWidth() / 2, SCREEN_HEIGHT / 2 - backgroundSprite.getHeight() / 2);

        Gdx.input.setInputProcessor(this);
    }


    @Override
    public void render(float delta) {

        //System.out.println(countDownTimer.isScheduled());

        ScreenUtils.clear(0, 0, 0, 1);

        batch.begin();

        backgroundSprite.draw(batch);

        showingExit();

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {
    }

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        font.dispose();
    }


    /**
     * Inicializa las variables que contienen valores referentes a dimensiones
     */
    private void settingGeneralDimensions() {
        SCREEN_WIDTH = Gdx.graphics.getWidth();
        SCREEN_HEIGHT = Gdx.graphics.getHeight();


        BOARD_X = SCREEN_WIDTH / 6f;
        BOARD_WIDTH = SCREEN_WIDTH / 6f * 4F;
        LINE_WIDTH = 20;

        ANSWER_BOX_HEIGHT = SCREEN_HEIGHT / 2 / 3 - LINE_WIDTH;
        ANSWER_BOX_A_Y = SCREEN_HEIGHT / 2 / 3 * 2 + LINE_WIDTH;
        ANSWER_BOX_B_Y = SCREEN_HEIGHT / 2 / 3 + LINE_WIDTH;
        ANSWER_BOX_C_Y = LINE_WIDTH;
        QUESTION_BOX_Y = SCREEN_HEIGHT / 2 + LINE_WIDTH * 2 + ANSWER_BOX_HEIGHT * 1.5f;


        fontSize = 1.25f;
    }

    private void showingExit() {
        String exitText = changingTextDependingLanguage("Salir", "Sortir", "Exit");

        exitSprite.setSize(180, 80);
        exitSprite.setPosition(LINE_WIDTH * 3, SCREEN_HEIGHT - exitSprite.getHeight() * 2);
        exitSprite.draw(batch);

        blackFont.draw(batch,
                exitText,
                exitSprite.getX(),
                SCREEN_HEIGHT - font.getScaleY() - LINE_WIDTH * 5,
                exitSprite.getWidth(),
                Align.center,
                true);
    }

    protected String changingTextDependingLanguage(String spanish, String catalan, String english) {

        switch (raceQuizGame.getLanguage()) {
            case 1:
                return spanish;
            case 2:
                return catalan;
            case 3:
                return english;
        }
        return null;
    }

    protected void exiting(int screenX, int screenY) {
        boolean touchingExitButton = screenX > exitSprite.getX()
                && screenX < exitSprite.getX() + exitSprite.getWidth()
                && screenY > exitSprite.getY()
                && screenY < exitSprite.getY() + exitSprite.getHeight();

        if (touchingExitButton) {

            Gdx.app.exit();
        }
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        xTouched = -1;
        yTouched = -1;

        return false;
    }

    @Override
    public void run() {
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
