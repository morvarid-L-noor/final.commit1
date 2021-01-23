package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
public class Pea extends Plant {
    protected ArrayList<Bullet> bullets = new ArrayList<>();
    public enum peaType{ peaShooter , snowPea}
    private static peaType peaType;
    public Pea(int x , int y , peaType peaType) throws IOException {
        setX(x);
        setY(y);
        this.peaType = peaType;
        setFields();
    }
    private void setFields() throws IOException {
        if(peaType == peaType.peaShooter){
            this.cost = 100;
            this.power = 70;
            //img = ImageIO.read(new File("images\\freezepea.png"));   888888888888888888888888888888888888888888888
        }else if(peaType == peaType.snowPea){
            this.cost = 175;
            this.power = 100;
            img = ImageIO.read(new File("images\\freezepea.png"));
        }
    }
    public ArrayList<Bullet> getBullets(){
        return bullets;
    }

    public void setDyingpea(){
        if(peaType == peaType.peaShooter){
            image = new ImageIcon("images\\Gifs\\pea_shooter_dying.gif").getImage();
        }else
            image = new ImageIcon("images\\Gifs\\sun_flower_dying.gif").getImage(); //88888888888888888888888888888888

    }

    public void setpea(){
        if(peaType == peaType.peaShooter){
            image = new ImageIcon("images\\Gifs\\pea_shooter.gif").getImage();
        }else image = new ImageIcon("images\\Gifs\\freezepeashooter.gif").getImage();

    }
    public void shoot() throws Exception { //88888888888888888888888888888888888888888888 tagheer bede
        if(peaType == peaType.peaShooter) {
            Bullet b = new Bullet(this.getX() + 50, this.getY() + 4, Bullet.bulletType.normal);
//            b.x = x;
//            b.y = y;
            bullets.add(b);
        }else  if(peaType == peaType.peaShooter){
            Bullet b = new Bullet(this.getX() + 50, this.getY() + 4, Bullet.bulletType.frozen);
            bullets.add(b);
        }
    }

}
