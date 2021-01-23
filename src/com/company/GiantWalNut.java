package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
public class GiantWalNut extends Plant {
    private Image image;
    public GiantWalNut(int x , int y) throws IOException {
       // super(x,y);
        setX(x);
        setY(y);
        this.cost = 50;
        this.power = 150;
        img = ImageIO.read(new File("images\\Gifs\\walnut.png"));
        setFullGif();
    }
    public void setFullGif(){
        image = new ImageIcon("images\\Gifs\\walnut_full_life.gif").getImage();
    }
    public void setHalfGif(){
        image = new ImageIcon("images\\Gifs\\walnut_half_life.gif").getImage();
    }
    public void setDeadGif(){
        image = new ImageIcon("images\\Gifs\\walnut_dead.gif").getImage();
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
