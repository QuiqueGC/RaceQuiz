package com.mygdx.game.screens.inGameScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.RaceQuizGame;
import com.mygdx.game.classes.JSONManager;
import com.mygdx.game.classes.Question;
import com.mygdx.game.classes.TimerForQuestion;

import java.util.ArrayList;
import java.util.Timer;

public class QuestionScreen extends InGameScreen {

    boolean chosenAnswer;

    ArrayList<String> shuffledAnswers;

    ArrayList<Question> questionsList;
    ArrayList<Sprite> boxesList;
    Question question;
    int questionTiming, limitTime;

    Sprite boxSprite;
    Texture questionBoxTexture, answerBoxTexture, rightAnswerTex, wrongAnswerTex;

    BitmapFont timerQuestionFont;

    Timer timer;

    TimerForQuestion timerForQuestion;

    public QuestionScreen(RaceQuizGame raceQuizGame) {
        super(raceQuizGame);
        questionsList = new ArrayList<>();

    }

    @Override
    public void show() {
        super.show();

        timerQuestionFont = new BitmapFont(Gdx.files.internal("font/SanFran.fnt"),
                Gdx.files.internal("font/SanFran.png"), false);


        questionTiming = 20;
        limitTime = 0;

        chosenAnswer = false;

        preparingGameToQuestion();

        initializingBoxes();

        timer = new Timer();
        timerForQuestion = new TimerForQuestion(questionTiming);
        timer.schedule(timerForQuestion, 0, 1000);

    }

    /**
     * Inicializa y posiciona los sprites de las cajas donde irán las preguntas
     */
    private void initializingBoxes() {
        boxesList = new ArrayList<>();
        questionBoxTexture = new Texture("img/QuestionBox.png");
        answerBoxTexture = new Texture("img/AnswerBox.png");
        rightAnswerTex = new Texture("img/rightAnswerBox.png");
        wrongAnswerTex = new Texture("img/QuestionBox.png");
        boxSprite = new Sprite(questionBoxTexture);

        for (int i = 0; i < 4; i++) {

            if (i == 0){
                boxSprite = new Sprite(questionBoxTexture);
                boxSprite.setSize(BOARD_WIDTH, boxSprite.getHeight()*2);

            }else{

                boxSprite = new Sprite(answerBoxTexture);
                boxSprite.setSize(BOARD_WIDTH, boxSprite.getHeight());
            }

            boxesList.add(boxSprite);
        }

        boxesList.get(0).setPosition(
                BOARD_X,
                QUESTION_BOX_Y);
        boxesList.get(1).setPosition(BOARD_X, ANSWER_BOX_A_Y);
        boxesList.get(2).setPosition(BOARD_X, ANSWER_BOX_B_Y);
        boxesList.get(3).setPosition(BOARD_X, ANSWER_BOX_C_Y);
    }


    @Override
    public void render(float delta) {
        super.render(delta);

        questionTiming = timerForQuestion.getQuestionTiming();

        batch.begin();

        showingQuestionAndAnswers();

        showingQuestionTimer();

        batch.end();

        finishingTimer();

    }

    private void showingQuestionTimer() {

        timerQuestionFont.getData().setScale(6);

        changingColorOfTimer();

        timerQuestionFont.draw(batch,
                String.valueOf(questionTiming),
                BOARD_X,
                ANSWER_BOX_HEIGHT * 5 - LINE_WIDTH * 3,
                BOARD_WIDTH,
                Align.center,
                true);
    }

    private void changingColorOfTimer() {

        if (questionTiming > 10) {

            timerQuestionFont.setColor(MAGIC_GREEN_COLOR);

        } else {

            timerQuestionFont.setColor(MAGIC_RED_COLOR);
        }
    }

    private void finishingTimer() {
        if (questionTiming <= limitTime) {

            if (!chosenAnswer) {
                player.setTries(player.getTries() - 1);

                /*if (countDownTimer.isScheduled()){

                    countDownTimer.cancel();
                }*/
            }

            raceQuizGame.setScreen(raceQuizGame.drivingScreen);
            player.setSpeed(player.getSpeed() + 10);
        }
    }


    /**
     * Escribe la pregunta y las respuestas que aparecen
     */
    private void showingQuestionAndAnswers() {

        drawingBoxes();

        writeQuestion();

        writeAnswer(shuffledAnswers.get(0), ANSWER_BOX_A_Y);

        writeAnswer(shuffledAnswers.get(1), ANSWER_BOX_B_Y);

        writeAnswer(shuffledAnswers.get(2), ANSWER_BOX_C_Y);
    }

    private void drawingBoxes() {

        for (Sprite boxSprite : boxesList) {

            boxSprite.draw(batch);
        }
    }

    private void writeQuestion() {
        font.draw(batch,
                question.getQuestion(),
                BOARD_X,
                SCREEN_HEIGHT -100,
                BOARD_WIDTH,
                Align.center,
                true);
    }


    /**
     * Escribe la respuesta en su sitio
     *
     * @param answerTxt  El texto que ha de aparecer
     * @param answerBoxY El punto en el que se sitúa en el eje Y
     */
    private void writeAnswer(String answerTxt, float answerBoxY) {

        blackFont.draw(batch,
                answerTxt,
                BOARD_X,
                (answerBoxY) + ANSWER_BOX_HEIGHT / 2,
                BOARD_WIDTH,
                Align.center,
                true);
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        /*if (countDownTimer.isScheduled()){

            countDownTimer.cancel();
        }*/

        screenY = (int) SCREEN_HEIGHT - screenY;

        checkingAnswer(screenX, screenY);

        exiting(screenX, screenY);

        return false;
    }

    /**
     * Chequea que el jugador ha seleccionado una respuesta
     *
     * @param screenX La posición en el eje X que toca el jugador
     * @param screenY La posición en el eje Y que toca el jugador
     */
    private void checkingAnswer(int screenX, int screenY) {


        if (screenX > BOARD_X && screenX < BOARD_X + BOARD_WIDTH && !chosenAnswer) {

            if (screenY > ANSWER_BOX_A_Y
                    && screenY < ANSWER_BOX_A_Y + ANSWER_BOX_HEIGHT) {


                givingScore(shuffledAnswers.get(0), boxesList.get(1));

            } else if (screenY > ANSWER_BOX_B_Y
                    && screenY < ANSWER_BOX_B_Y + ANSWER_BOX_HEIGHT) {


                givingScore(shuffledAnswers.get(1), boxesList.get(2));

            } else if (screenY > ANSWER_BOX_C_Y
                    && screenY < ANSWER_BOX_C_Y + ANSWER_BOX_HEIGHT) {

                givingScore(shuffledAnswers.get(2), boxesList.get(3));

            }
        }
    }

    /**
     * Chequea que si la respuesta escogida es la correcta y otorga
     * o resta puntos en base a eso
     *
     * @param answerTxt El texto que contiene la respuesta seleccionada
     */
    private void givingScore(String answerTxt, Sprite boxSprite) {
        chosenAnswer = true;

        if (answerTxt.equals(question.getRightAnswer())) {

            player.setScore(player.getScore() + 500);
            boxSprite.setTexture(rightAnswerTex);

            //Aquí borramos la pregunta de la lista
            questionsList.remove(question);

        } else {
            player.setTries(player.getTries() - 1);
            boxSprite.setTexture(wrongAnswerTex);

        }

        /*if (countDownTimer.isScheduled()){

            countDownTimer.cancel();
        }*/

        limitTime = questionTiming - 2;

    }


    /**
     * Prepara el juego para la siguiente pregunta
     */
    private void preparingGameToQuestion() {

        shuffledAnswers = new ArrayList<>();
        ArrayList<String> answers = new ArrayList<>();


        choosingRandomQuestion(answers);


        shufflingAnswers(answers);


        roadLines.setDriving(false);
    }

    /**
     * Escoge una pregunta aleatoria de la lista de preguntas
     *
     * @param answers ArrayList de strings con las tres posibles respuestas
     */
    private void choosingRandomQuestion(ArrayList<String> answers) {

        if (questionsList.isEmpty()) {

            questionsList = JSONManager.readingQuestions(raceQuizGame.getLanguage(), raceQuizGame.getDifficulty());
        }

        int randomIndex = (int) (Math.random() * (questionsList.size()));

        question = questionsList.get(randomIndex);

        answers.add(question.getRightAnswer());
        answers.add(question.getWrongAnswer1());
        answers.add(question.getWrongAnswer2());
    }


    /**
     * Desordena las posibles respuestas para mostrarlas
     * en distinto orden por la pantalla
     *
     * @param answers El ArrayList con las respuestas ordenadas
     */
    private void shufflingAnswers(ArrayList<String> answers) {
        int randomIndex;

        do {
            randomIndex = (int) (Math.random() * (answers.size()));
            shuffledAnswers.add(answers.get(randomIndex));
            answers.remove(randomIndex);

        } while (!answers.isEmpty());
    }

}
