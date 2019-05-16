package com.barlo.snake_game.controll;


import com.barlo.snake_game.exceptions.AlreadyOccupiedException;
import com.barlo.snake_game.exceptions.MoveOutOfBoundsException;
import com.barlo.snake_game.model.Snake;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Iterator;

public class SnakeController {

    private final char directionX = 'x';
    private final char directionY = 'y';

    private Snake snake;

    private int stepForward;
    private int stepBackward;

    private double lastXPosition;
    private double lastYPosition;

    public SnakeController(final Snake snake) {

        this.snake = snake;

        this.stepForward = snake.getSizeOfHead();
        this.stepBackward = - snake.getSizeOfHead();

    }

    public void moveSnake(final KeyCode keyCode) throws AlreadyOccupiedException, MoveOutOfBoundsException {

        switch (keyCode) {

            case UP:
                move(directionY, stepBackward);
                break;
            case DOWN:
                move(directionY, stepForward);
                break;
            case LEFT:
                move(directionX, stepBackward);
                break;
            case RIGHT:
                move(directionX, stepForward);
                break;
        }

    }

    public boolean eat(final Rectangle food) {

        if (snake.getHead().getX() == food.getX() && snake.getHead().getY() == food.getY()) {
            return true;
        }

        return false;
    }

    public Rectangle grow() {

        Rectangle lastTail = new Rectangle(lastXPosition, lastYPosition, snake.getSizeOfHead(), snake.getSizeOfHead());

        snake.getTail().add(lastTail);

        return lastTail;
    }


    private void move(final char direction, final int step) throws AlreadyOccupiedException, MoveOutOfBoundsException {

        Rectangle head = snake.getHead();
        ArrayList<Rectangle> tail = snake.getTail();

        double startXPosition = head.getX();
        double startYPosition = head.getY();

        switch (direction) {

            case 'x':

                endGame(startXPosition+step, startYPosition);

                head.setX(head.getX()+step);

                shiftTail(startXPosition, startYPosition, tail);

                break;


            case 'y':

                endGame(startXPosition, startYPosition+step);

                head.setY(head.getY()+step);


                shiftTail(startXPosition, startYPosition, tail);

                break;

        }

    }

    private void shiftTail(final double startXPosition, final double startYPosition, final ArrayList<Rectangle> tail) {

        double translateX = startXPosition;
        double translateY = startYPosition;

        for (int i=0; i<tail.size(); i++) {

            double tempTranslateX = tail.get(i).getX();
            double tempTranslateY = tail.get(i).getY();

            if (i == tail.size()-1) {
                storeLastPosition(tempTranslateX, tempTranslateY);
            }

            tail.get(i).setX(translateX);
            tail.get(i).setY(translateY);

            translateX = tempTranslateX;
            translateY = tempTranslateY;

        }

    }

    private void storeLastPosition(final double lastX, final double lastY) {
        lastXPosition = lastX;
        lastYPosition = lastY;
    }

    private void endGame(final double translateX, final double translateY) throws AlreadyOccupiedException, MoveOutOfBoundsException {

        Iterator<Rectangle> it = snake.getTail().iterator();

        while (it.hasNext()) {

            Rectangle tail = it.next();

            if (translateX == tail.getX() && translateY == tail.getY()) {
                throw new AlreadyOccupiedException();
            }

        }

        if(translateX < 0 || translateX >= 400 || translateY < 0 || translateY >= 400) {
            throw new MoveOutOfBoundsException();
        }

    }

}
