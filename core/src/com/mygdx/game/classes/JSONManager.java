package com.mygdx.game.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.io.File;
import java.util.ArrayList;

public class JSONManager {

    //System.out.println(Gdx.files.getExternalStoragePath());
    //System.out.println(Gdx.files.getLocalStoragePath());

    public static ArrayList<Question> readingQuestions(int language, int difficulty) {
        ArrayList<Question> questionsList = new ArrayList<>();

        JsonValue base = choosingJSONDependingOnLanguage(language, difficulty);


        for (JsonValue value :
                base) {
            Question question = new Question(
                    value.getString("Question"),
                    value.getString("CorrectAnswer"),
                    value.getString("WrongAnswer1"),
                    value.getString("WrongAnswer2"));

            questionsList.add(question);
        }

        return questionsList;
    }

    private static JsonValue choosingJSONDependingOnLanguage(int language, int difficulty) {
        JsonValue base = null;

        switch (language) {
            case 1:
                base = choosingJSONDependingOnDifficulty(difficulty,"json/Esp_easy_questions.json", "json/Esp_normal_questions.json", "json/Esp_hard_questions.json" );
                break;
            case 2:
                base = choosingJSONDependingOnDifficulty(difficulty,"json/Cat_easy_questions.json", "json/Cat_normal_questions.json", "json/Cat_hard_questions.json" );
                break;
            case 3:
                base = choosingJSONDependingOnDifficulty(difficulty,"json/Eng_easy_questions.json", "json/Eng_normal_questions.json", "json/Eng_hard_questions.json" );
                break;
        }
        return base;
    }

    private static JsonValue choosingJSONDependingOnDifficulty(int difficulty, String easyURL, String normalURL, String hardURL) {
        JsonReader json = new JsonReader();
        JsonValue base = null;

        switch (difficulty) {
            case 1:
                base = json.parse(Gdx.files.local(easyURL));
                break;
            case 2:
                base = json.parse(Gdx.files.local(normalURL));
                break;
            case 3:
                base = json.parse(Gdx.files.local(hardURL));
                break;
        }

        return base;

    }


    public static ArrayList<Prize> readingPrizes() {
        ArrayList<Prize> prizesList = new ArrayList<>();
        JsonReader json = new JsonReader();
        JsonValue base = json.parse(Gdx.files.local("json/score.json"));
        for (JsonValue value :
                base) {
            Prize prize = new Prize(
                    value.getInt("Score"),
                    value.getString("Img")
            );
            prizesList.add(prize);

        }
        return prizesList;
    }
}
