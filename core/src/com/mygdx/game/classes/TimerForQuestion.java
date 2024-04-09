package com.mygdx.game.classes;

import com.mygdx.game.RaceQuizGame;

import java.util.TimerTask;

public class TimerForQuestion extends TimerTask {

    private int questionTiming;
    public TimerForQuestion(int questionTiming){

        this.questionTiming = questionTiming;
    }
    @Override
    public void run() {

        questionTiming--;
    }

    public int getQuestionTiming() {
        return questionTiming;
    }

    public void setQuestionTiming(int questionTiming) {
        this.questionTiming = questionTiming;
    }
}
