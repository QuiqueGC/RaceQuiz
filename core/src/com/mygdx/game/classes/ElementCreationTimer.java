package com.mygdx.game.classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.TimerTask;

public class ElementCreationTimer extends TimerTask {

    ArrayList<Sprite> bombsList;
    ArrayList<Sprite> coinsList;

    ArrayList<Sprite> mnactecList;

    float BOARD_X, SCREEN_HEIGHT;



    public ElementCreationTimer(ArrayList<Sprite> bombsList,ArrayList<Sprite> coinsList,ArrayList<Sprite> mnactecList,float BOARD_X, float SCREEN_HEIGHT) {

        this.bombsList = bombsList;
        this.coinsList = coinsList;
        this.mnactecList = mnactecList;
        this.BOARD_X = BOARD_X;
        this.SCREEN_HEIGHT = SCREEN_HEIGHT;
    }

    @Override
    public void run() {

        creatingObstacles();
    }


    /**
     * Dibuja los obstáculos que aparecen
     */
    private void creatingObstacles() {
        Sprite obstacleSprite;
        Texture obstacleTexture = new Texture("img/mnactec_logo.jpg");
        ArrayList<Integer> listOfPositions = new ArrayList<>();

        int quantityObstacles = (int) (Math.random() * (4 - 1) + 1);

        for (int i = 0; i < quantityObstacles; i++) {

            obstacleSprite = new Sprite(obstacleTexture);

            obstacleSprite.setY(SCREEN_HEIGHT);

            choosingRandomPositionX(obstacleSprite, listOfPositions);

            choosingListToPut(obstacleSprite);

        }


    }

    private void choosingListToPut(Sprite obstacleSprite) {

        int obstacleType = (int) (Math.random() * (101));


        if(obstacleType < 15){

            obstacleSprite.setSize(240,240);
            bombsList.add(obstacleSprite);

        }else if (obstacleType <= 40){
            obstacleSprite.setX(obstacleSprite.getX()+30);
            obstacleSprite.setSize(168,168);
            mnactecList.add(obstacleSprite);
        }else{

            obstacleSprite.setX(obstacleSprite.getX()+70);
            obstacleSprite.setSize(90, 90);
            coinsList.add(obstacleSprite);
        }
    }

    /**
     * Selecciona aleatoriamente dónde aparecerá el obstáculo
     *
     * @param obstacleSprite El sprite perteneciente al obstáculo en cuestión
     */
    private void choosingRandomPositionX(Sprite obstacleSprite, ArrayList<Integer> listOfPositions) {
        int obstaclesPositionX;

        obstaclesPositionX = checkingIfPositionEmpty(listOfPositions);

        switch (obstaclesPositionX) {
            case 1:
                obstacleSprite.setX(BOARD_X + BOARD_X / 2 - obstacleSprite.getWidth() / 2);
                break;
            case 2:
                obstacleSprite.setX(BOARD_X * 2 + BOARD_X / 2 - obstacleSprite.getWidth() / 2);
                break;
            case 3:
                obstacleSprite.setX(BOARD_X * 3 + BOARD_X / 2 - obstacleSprite.getWidth() / 2);
                break;
            case 4:
                obstacleSprite.setX(BOARD_X * 4 + BOARD_X / 2 - obstacleSprite.getWidth() / 2);
                break;
        }
    }

    /**
     * Asigna una posición que no esté ocupada al obstáculo
     * @param listOfPositions la lista de posiciones ocupadas
     * @return la posición no ocupada
     */
    private int checkingIfPositionEmpty(ArrayList<Integer> listOfPositions) {
        boolean haveToRepeat;
        int obstaclesPositionX;

        do {
            haveToRepeat = false;
            obstaclesPositionX = (int) (Math.random() * (5 - 1) + 1);

            if (listOfPositions.contains(obstaclesPositionX)) {

                haveToRepeat = true;
            }

        } while (haveToRepeat);

        listOfPositions.add(obstaclesPositionX);

        return obstaclesPositionX;
    }


}
