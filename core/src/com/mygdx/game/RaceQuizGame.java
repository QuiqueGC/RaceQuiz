package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.classes.JSONManager;
import com.mygdx.game.classes.Player;
import com.mygdx.game.screens.inGameScreens.DrivingScreen;
import com.mygdx.game.screens.inGameScreens.ExplosionScreen;
import com.mygdx.game.screens.inGameScreens.QuestionScreen;
import com.mygdx.game.screens.menuScreens.DifficultyMenuScreen;
import com.mygdx.game.screens.menuScreens.ExplanationMenuScreen;
import com.mygdx.game.screens.menuScreens.GameOverScreen;
import com.mygdx.game.screens.menuScreens.LanguageMenuScreen;

import java.io.File;

/**
 * Clase principal del juego donde se inicializan todas las pantallas
 */
public class RaceQuizGame extends Game {

    private SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    private Player player;
    public DrivingScreen drivingScreen;
    public QuestionScreen questionScreen;
    public LanguageMenuScreen languageMenuScreen;
    public DifficultyMenuScreen difficultyMenuScreen;
    public ExplanationMenuScreen explanationMenuScreen;
    public ExplosionScreen explosionScreen;
    public GameOverScreen gameOverScreen;
    private boolean startingGame;
    private int language;
    private int difficulty;

    @Override
    public void create() {
        batch = new SpriteBatch();
        player = new Player();
        shapeRenderer = new ShapeRenderer();
        startingGame = true;
        drivingScreen = new DrivingScreen(this);
        questionScreen = new QuestionScreen(this);
        languageMenuScreen = new LanguageMenuScreen(this);
        difficultyMenuScreen = new DifficultyMenuScreen(this);
        explanationMenuScreen = new ExplanationMenuScreen(this);
        explosionScreen = new ExplosionScreen(this);
        gameOverScreen = new GameOverScreen(this);
        language = 1;
        difficulty = 1;


        setScreen(languageMenuScreen);
    }

    public SpriteBatch getBatch() {
        return batch;
    }


    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void dispose() {
        super.dispose();

    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public void setShapeRenderer(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isStartingGame() {
        return startingGame;
    }

    public void setStartingGame(boolean startingGame) {
        this.startingGame = startingGame;
    }
}