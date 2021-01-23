package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Machine extends  Element{
    private Image image;
    Machine(int row) throws IOException {
        setX(15);
        setY(136 * row + 20 );
        img = ImageIO.read(new File("images\\Lawn_Mower.png"));//lawn_mower
        image = new ImageIcon("images\\lawn_mower.gif").getImage();
    }
}
