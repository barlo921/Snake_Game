package com.barlo.snake_game.controll;

import com.barlo.snake_game.model.Snake;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Iterator;

public class FoodController {

    private Rectangle food;

    private Snake snake;

    private int windowSize;
    private int foodXCoordinate;
    private int foodYCoordinate;

    public FoodController(final Snake snake, final int windowSize) {

        this.snake = snake;
        this.windowSize = windowSize;

        foodCoordinates();

        food = new Rectangle(foodXCoordinate, foodYCoordinate, snake.getSizeOfHead(), snake.getSizeOfHead());
        food.setFill(Color.AQUA);

    }

    public Rectangle getFood() {
        return food;
    }

    public void setNewCoordinates() {
        foodCoordinates();

        food.setX(foodXCoordinate);
        food.setY(foodYCoordinate);
    }

    private void foodCoordinates() {

        boolean newFoodAdded = false;

        while (!newFoodAdded) {

            foodXCoordinate = generateCoordinate();
            foodYCoordinate = generateCoordinate();

            if (checkFoodForFreePlace()) {
                newFoodAdded = true;
            }

        }

    }


    private int generateCoordinate() {

        int coordinate = (int) (Math.random() * windowSize);
        boolean coordinateFound = false;

        while (!coordinateFound) {

            if (!(coordinate % snake.getSizeOfHead() == 0)) {
                coordinate = (int) (Math.random() * windowSize);
            } else {
                coordinateFound = true;
            }

        }

        return coordinate;

    }

    private boolean checkFoodForFreePlace() {

        if (snake.getHead().getX() == foodXCoordinate && snake.getHead().getY() == foodYCoordinate) {
            return false;
        }

        Iterator <Rectangle> it = snake.getTail().iterator();

        while (it.hasNext()) {

            Rectangle tempRectangle = it.next();

            if (tempRectangle.getX() == foodXCoordinate && tempRectangle.getY() == foodYCoordinate) {
                return false;
            }
        }

        return true;

    }

}
