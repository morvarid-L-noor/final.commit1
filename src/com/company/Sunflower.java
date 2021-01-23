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
        this.cost = 50;
        this.power = 50;
        img = ImageIO.read(new File("images\\sunflower.png"));
        setDarkSunflower();
        startTime = System.currentTimeMillis();
        setStartTime(System.currentTimeMillis());

//        this.sound = new Sound("pacman_eatghost.wav");
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
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
