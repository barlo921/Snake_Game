package com.barlo.snake_game.controll;


import com.barlo.snake_game.model.Snake;
import com.barlo.snake_game.view.GameLoop;
import com.barlo.snake_game.view.StartTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;


public class SnakeFXController implements Initializable {

    @FXML
    private Pane root;

    @FXML
    private Button startGameButton;

    @FXML
    private Text scoreField;

    @FXML
    private Text counter;

    @FXML
    private Pane topScoresPane;

    @FXML
    private Text top1;

    @FXML
    private Text top2;

    @FXML
    private Text top3;

    @Override
    public void initialize(URL location, ResourceBundle resource) {

        JSONController jsonController = new JSONController();

        top1.setText(jsonController.returnScore(0));
        top2.setText(jsonController.returnScore(1));
        top3.setText(jsonController.returnScore(2));

    }

    @FXML
    public void startGame() throws InterruptedException {

        maskUsedObjects(topScoresPane);
        maskUsedObjects(startGameButton);

        scoreField.setOpacity(100);

        Snake snake = new Snake();

        root.getChildren().add(snake.getHead());
        root.getChildren().add(snake.getTail().get(0));

        GameLoop gameLoop = new GameLoop(root, scoreField, startGameButton, this, snake);
        StartTimer startTimer = new StartTimer(counter, gameLoop);

        startTimer.startTimer();


    }

    private <T extends Node> void maskUsedObjects(T obj) {
        obj.setDisable(true);
        obj.setOpacity(0);
    }

}