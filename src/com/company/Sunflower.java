package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
public class Sunflower extends Plant {
    private Image image;
    private Sun sun = null ;
    public Sunflower(int x, int y ) throws IOException {
        setAlive(true);
        setX(x);
        setY(y);
//        setColumn((x-40)/100);
//        setRow((y-130)/120);
        setCost(50);
        setHealth(50);
        img = ImageIO.read(new File("images\\sunflower.png"));
        setDarkSunflower();
        setStartTime(System.currentTimeMillis());
//        this.sound = new Sound("pacman_eatghost.wav");
    }


    public void setDyingSunflower(){
        image = new ImageIcon("images\\Gifs\\sun_flower_dying.gif").getImage();
    }
    public void setLightSunflower(){
        image = new ImageIcon("images\\Gifs\\sun_flower.gif").getImage();
    }
    public void setDarkSunflower(){
        image = new ImageIcon("images\\Gifs\\sunflower.gif").getImage();
    }

    public Sun getSun() {
        return sun;
    }

    public void setSun(Sun sun) {
        this.sun = sun;
    }
}
