package com.barlo.snake_game.view;


import com.barlo.snake_game.controll.FoodController;
import com.barlo.snake_game.controll.JSONController;
import com.barlo.snake_game.controll.SnakeController;
import com.barlo.snake_game.controll.SnakeFXController;
import com.barlo.snake_game.exceptions.AlreadyOccupiedException;
import com.barlo.snake_game.exceptions.MoveOutOfBoundsException;
import com.barlo.snake_game.model.Score;
import com.barlo.snake_game.model.Snake;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


public class GameLoop extends AnimationTimer{

    private static long SNAKE_SPEED = 400_000_000;

    private Snake snake;

    SnakeController snakeController;
    SnakeFXController snakeFXController;
    FoodController foodController;
    Score score;

    private long update = 0;

    public static KeyCode nextDirection = KeyCode.LEFT;
    private static KeyCode currentDirection = KeyCode.LEFT;

    @FXML
    private Pane root;

    @FXML
    private Text scoreField;

    @FXML
    private Button startGameButton;

    public GameLoop(final Pane root, final Text scoreField, final Button startGameButton, final SnakeFXController snakeFXController, final Snake snake) {

        this.root = root;
        this.snake = snake;
        this.scoreField = scoreField;
        this.startGameButton = startGameButton;
        this.snakeFXController = snakeFXController;

        snakeController = new SnakeController(snake);
        foodController = new FoodController(snake, 400);
        score = new Score();

    }

    @Override
    public void handle(long now) {

        if (now - update >= SNAKE_SPEED) {

            try {
                checkDirection();
            } catch (AlreadyOccupiedException | MoveOutOfBoundsException e) {

                setEndGameText();
                JSONController jsonController = new JSONController();
                jsonController.setNewTopScore(score.getScore());

                startNewGameAtEndGame();

                this.stop();
            }

            if (snakeController.eat(foodController.getFood())) {

                foodController.setNewCoordinates();
                root.getChildren().add(snakeController.grow());

                score.setScore(score.getScore()+10);
                changeSpeedDifficulty();

                scoreField.setText("Score: " + score.getScore());
            }

            update = now;
        }

    }


    public static void setNextDirection(final KeyCode newKeyCode) {
        nextDirection = newKeyCode;
    }

    public void addFood() {
        root.getChildren().add(foodController.getFood());
    }

    private void checkDirection() throws AlreadyOccupiedException, MoveOutOfBoundsException {
        if((currentDirection == KeyCode.LEFT && nextDirection == KeyCode.RIGHT) || (currentDirection == KeyCode.RIGHT && nextDirection == KeyCode.LEFT)) {
            snakeController.moveSnake(currentDirection);
        } else if ((currentDirection == KeyCode.UP && nextDirection == KeyCode.DOWN) || (currentDirection == KeyCode.DOWN && nextDirection == KeyCode.UP)) {
            snakeController.moveSnake((currentDirection));
        } else {
            currentDirection = nextDirection;
            snakeController.moveSnake(currentDirection);
        }
    }

    private void setEndGameText() {
        Text endGame = new Text("GAME OVER");
        endGame.setFill(Color.RED);

        endGame.setX(160);
        endGame.setY(200);

        endGame.setScaleX(3);
        endGame.setScaleY(3);

        root.getChildren().add(endGame);

    }

    private void changeSpeedDifficulty() {
        if (score.getScore() == 50) {
            SNAKE_SPEED = 300_000_000;
        } else if (score.getScore() == 100) {
            SNAKE_SPEED = 200_000_000;
        } else if (score.getScore() == 200) {
            SNAKE_SPEED = 100_000_000;
        } else if (score.getScore() % 50 == 0) {
            SNAKE_SPEED -= 10_000_000;
        }
    }

    private void startNewGameAtEndGame () {

        Button newGameButton = new Button();

        newGameButton.setLayoutY(130);
        newGameButton.setLayoutX(130);
        newGameButton.setText("Start New Game");
        newGameButton.setOnAction((event) -> {
            root.getChildren().clear();
            try {
                snakeFXController.startGame();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        root.getChildren().add(newGameButton);

    }

}
