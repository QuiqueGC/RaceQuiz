package com.mygdx.game.classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Guarda los datos del jugador
 */
public class Player implements Comparable{

    private String name;
    private int score;
    private int tries;
    private int speed;
    private int secondsPlayed;
    private int minutsPlayed;

    private String timePlayed;

    private Sprite sprite;

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public Player() {

        Texture texture = new Texture("img/carSprite.png");
        sprite = new Sprite(texture);

        this.score = 0;
        this.tries = 3;
        this.speed = 30;
        this.secondsPlayed = 0;
        this.minutsPlayed = 0;
        this.timePlayed = "0"+this.minutsPlayed + ":" +"0"+ this.secondsPlayed;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTries() {
        return tries;
    }

    public void setTries(int tries) {
        this.tries = tries;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public int getSecondsPlayed() {
        return secondsPlayed;
    }

    public void setSecondsPlayed(int timeCounter) {
        this.secondsPlayed = timeCounter;
    }

    public int getMinutsPlayed() {
        return minutsPlayed;
    }

    public void setMinutsPlayed(int minutsPlayed) {
        this.minutsPlayed = minutsPlayed;
    }

    public String getTimePlayed() {
        return timePlayed;
    }

    public void setTimePlayed(String timePlayed) {
        this.timePlayed = timePlayed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void restartingPlayer(){
        this.score = 0;
        this.tries = 3;
        this.speed = 30;
        this.secondsPlayed = 0;
        this.minutsPlayed = 0;
        this.timePlayed = "0"+this.minutsPlayed + ":" + "0"+this.secondsPlayed;
    }

    public void loosingScore(int score){

        this.score -= score;

        if (this.score < 0){

            this.score = 0;
        }
    }

    public void oneMoreSecond(){
        String secondsString = secondsPlayed<10?"0"+secondsPlayed:String.valueOf(secondsPlayed);


        this.secondsPlayed += 1;

        if(this.secondsPlayed == 60){
            this.minutsPlayed += 1;
            this.secondsPlayed = 0;
        }

        this.timePlayed = "0" + this.minutsPlayed + ":" + secondsString;
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }


    @Override
    public int compareTo(Object o) {
        int value;

        if (o instanceof Player) {


                value = ((Player) o).score - this.score;

                if (value == 0) {

                    value = ((Player) o).secondsPlayed - this.secondsPlayed;

                }

                return value;


        } else {
            throw new ClassCastException();
        }
    }

    /**
     * texto para introducir en el fichero
     * @return String con la línea de texto
     */
    public String toFile() {

        return this.name+";"+this.score+";"+this.secondsPlayed;
    }


}
