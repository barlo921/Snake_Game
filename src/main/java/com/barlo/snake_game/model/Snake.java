package com.barlo.snake_game.model;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Snake {

    private final static int START_POSITION = 200;
    private final static int SIZE_OF_HEAD = 20;

    private Rectangle head;
    private ArrayList<Rectangle> tail;

    public Snake() {

        head = new Rectangle(START_POSITION, START_POSITION, SIZE_OF_HEAD, SIZE_OF_HEAD);
        head.setFill(Color.RED);

        tail = new ArrayList<>();
        tail.add(new Rectangle(START_POSITION + SIZE_OF_HEAD, START_POSITION, SIZE_OF_HEAD, SIZE_OF_HEAD));

    }

    public Rectangle getHead() {
        return head;
    }

    public ArrayList<Rectangle> getTail() {
        return tail;
    }

    public int getSizeOfHead() {
       return SIZE_OF_HEAD;
    }
}
