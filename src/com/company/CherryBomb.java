package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
public class CherryBomb extends Plant {

    public CherryBomb(int x , int y) throws IOException {
        setX(x);
        setY(y);
//        setRow((y-130)/120);
//        setColumn((x-40)/100);
        setCost(150);
        setHealth(70);
        img = ImageIO.read(new File("images\\cherry.png"));
        image = new ImageIcon("images\\Gifs\\newCherryBomb.gif").getImage(); // gif baraye terekidan
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
