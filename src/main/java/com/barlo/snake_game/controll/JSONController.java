package com.barlo.snake_game.controll;


import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import java.io.*;

public class JSONController {

    private static final String JSON_SOURCE_FILE = "src/main/resources/ScoreDB.json";

    private int[] topScores = new int[3];


    public JSONController() {

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(JSON_SOURCE_FILE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        JsonReader jsonReader = Json.createReader(inputStream);

        JsonObject jsonObject = jsonReader.readObject();

        jsonReader.close();

        for (int i=0; i<topScores.length; i++) {
            topScores[i] = jsonObject.getJsonObject("top" + (i+1)).getInt("score");
        }

    }

    public String returnScore(final int topNumber) {
        return String.valueOf(topScores[topNumber]);
    }

    public int[] getTopScores() {
        return topScores;
    }

    public void setNewTopScore(final int score) {

        int newTopScorePlace = -1;

        for (int i=topScores.length-1; i>=0; i--) {
            if (score > topScores[i]) newTopScorePlace = i;
        }

        if (newTopScorePlace != -1) {
            OutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(JSON_SOURCE_FILE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            if (newTopScorePlace != 2) {
                if (newTopScorePlace == 0) {
                    int [] oldTopScores = topScores;
                    System.arraycopy(oldTopScores, 0, topScores, 1, 2);
                    topScores[newTopScorePlace] = score;
                }
                if (newTopScorePlace == 1) {
                    int [] oldTopScores = topScores;
                    System.arraycopy(oldTopScores, 1, topScores, 2, 1);
                    topScores[newTopScorePlace] = score;
                }
            } else {
                topScores[newTopScorePlace] = score;
            }

            JsonWriter jsonWriter = Json.createWriter(outputStream);

            JsonObject value = Json.createObjectBuilder().
                    add("top1", Json.createObjectBuilder().add("score", topScores[0])).
                    add("top2", Json.createObjectBuilder().add("score", topScores[1])).
                    add("top3", Json.createObjectBuilder().add("score", topScores[2])).
                    build();

            jsonWriter.writeObject(value);
        }

    }
}
