package com.mygdx.game.classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

/**
 * Clase para crear las líneas de la carretera y darles movimiento
 */
public class RoadLines implements Runnable {

    private ArrayList<Sprite> roadLinesList;
    private boolean driving;
    private Texture lineImg = new Texture("img/line.png");
    private static final float LINE_HEIGHT = 200;
    private final float SCREEN_HEIGHT;
    private final Player player;



    public RoadLines(float BOARD_X, float BOARD_WIDTH, float SCREEN_HEIGHT, Player player) {


        this.SCREEN_HEIGHT = SCREEN_HEIGHT;
        this.player = player;
        this.driving = true;
        this.roadLinesList = new ArrayList<>();
        roadLinesCreation(BOARD_X+BOARD_WIDTH/4);
        roadLinesCreation(BOARD_X+BOARD_WIDTH/4*2);
        roadLinesCreation(BOARD_X+BOARD_WIDTH/4*3);
    }

    @Override
    public void run() {
        do{

            for (Sprite sp :
                    roadLinesList) {

                if(sp.getY() + LINE_HEIGHT >0)
                {

                    sp.setY(sp.getY() - player.getSpeed());

                }else {sp.setY(SCREEN_HEIGHT);}
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }while(driving);

    }

    /**
     * Crea, posiciona y añade a la lista cada una de las líneas
     * @param xPosition La posición en el eje X en el que irán las líneas
     */
    private void roadLinesCreation(float xPosition) {
        Sprite lineSprite;

        for (float i = SCREEN_HEIGHT; i >= 0; i -= SCREEN_HEIGHT/5) {
            lineSprite = new Sprite(lineImg);
            lineSprite.setY(i);
            lineSprite.setX(xPosition);
            roadLinesList.add(lineSprite);
        }
    }
    public boolean isDriving() {
        return driving;
    }

    public void setDriving(boolean driving) {
        this.driving = driving;
    }

    public ArrayList<Sprite> getRoadLinesList() {
        return roadLinesList;
    }

    public void setRoadLinesList(ArrayList<Sprite> roadLinesList) {
        this.roadLinesList = roadLinesList;
    }

    public Texture getLineImg() {
        return lineImg;
    }

    public void setLineImg(Texture lineImg) {
        this.lineImg = lineImg;
    }
}
