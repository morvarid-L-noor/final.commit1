package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;
public class Element {
    protected BufferedImage img;
    protected Image image;
    protected int health;
    protected int x;
    protected int y;
    protected  long startTime;
    private boolean alive;
    protected int row;
    protected int column;

    Element(){}
    public void moveRight(int speed) {
        x = x + speed;
    }
    public void moveLeft(int speed) {
        x = x - speed ;
    }
    public int getX() {
        return x;
    }

    public Image getImage(){
        return image;
    }

    public void setX(int x) {
        this.x = x;
    }
    public int getRow() {
        return row;
    }

    public void setRow(int x) {
        this.row = x;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int x) {
        this.column = x;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int x) {
        this.health = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long s) {
        this.startTime = s;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }





}
