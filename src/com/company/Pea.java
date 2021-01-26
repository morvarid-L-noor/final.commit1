package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
public class Pea extends Plant {
    protected ArrayList<Bullet> bullets = new ArrayList<>();
    public enum peaType{ PEA , FREEZEPEA}
    private static peaType peaType;
    private int damage;
    public Pea(int x , int y , peaType peaType) throws IOException {
        setX(x);
        setY(y);
//        setRow((y-130)/120);
//        setColumn((x-40)/100);
        this.peaType = peaType;
        setFields();
    }
    @Override
    protected void setFields() throws IOException {
        if(peaType == peaType.PEA){
            this.cost = 100;
            this.power = 70;
            this.damage = 30;
            //img = ImageIO.read(new File("images\\freezepea.png"));   888888888888888888888888888888888888888888888
        }else if(peaType == peaType.FREEZEPEA){
            this.cost = 175;
            this.power = 100;
            this.damage = 35;
            img = ImageIO.read(new File("images\\freezepea.png"));
        }
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public ArrayList<Bullet> getBullets(){
        return bullets;
    }

    public void setDyingpea(){
        if(peaType == peaType.PEA){
            image = new ImageIcon("images\\Gifs\\pea_shooter_dying.gif").getImage();
        }else
            image = new ImageIcon("images\\Gifs\\sun_flower_dying.gif").getImage(); //88888888888888888888888888888888

    }

    public void setpea(){
        if(peaType == peaType.PEA){
            image = new ImageIcon("images\\Gifs\\pea_shooter.gif").getImage();
        }else image = new ImageIcon("images\\Gifs\\freezepeashooter.gif").getImage();

    }
    public void shoot() throws Exception { //88888888888888888888888888888888888888888888 tagheer bede
        if(peaType == peaType.PEA) {
            Bullet b = new Bullet(this.getX() + 50, this.getY() + 4, Bullet.bulletType.normal);
            bullets.add(b);
        }else{
            Bullet b = new Bullet(this.getX() + 50, this.getY() + 4, Bullet.bulletType.frozen);
            bullets.add(b);
        }
    }

}
