package com.mygdx.game.classes;

public class Prize {
    private int score;
    private String imgPath;

    public Prize(int score, String path) {
        this.score = score;
        this.imgPath = path;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
