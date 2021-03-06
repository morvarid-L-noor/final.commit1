package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.security.SecureRandom;

public class Zombie extends Element implements Actor{
    protected   int power ;
//    public enum type{coneHead , normal , bucketHead}
    private double speed;
    private int attack;

    public enum zombiType{CONEHEAD , NORMAL , BUCKHEAD , FOOTBALL}
    private zombiType zombieType;
//    private int speed;
//    private int attack;
    private boolean canMove = true;

    public Zombie(zombiType zombieType) throws Exception {
        setX(1270);
        int random = new SecureRandom().nextInt(5);
        int y = 120* random + 10 ; // margin = 20 , 136 = height of each row
        setY(y);
        this.zombieType = zombieType;
        setFields();
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
    public boolean arrivesHome(){
        if(this.getX() == 0)return true;
        else return false;
    }
    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public zombiType getZombieType() {
        return zombieType;
    }

    public void setZombieType(zombiType zombieType) throws Exception {
        this.zombieType = zombieType;
    }

    public boolean CanMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }


    public void setDyingGif(){
        if(zombieType == zombieType.NORMAL){
        this.image = new ImageIcon("images\\Gifs\\zombie_normal_dying.gif").getImage();
    }//88888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888 for the rest
    }
    private void setFields() throws Exception {
        if(zombieType == zombieType.NORMAL){

            this.power = 200;
            img = ImageIO.read(new File("images\\zombie1.png"));
            this.image = new ImageIcon("images\\Gifs\\zombie_normal.gif").getImage();

        }else if(zombieType == zombieType.CONEHEAD){

            this.power = 560;
            img = ImageIO.read(new File("images\\zombie2.png"));
            image = new ImageIcon("images\\Gifs\\coneheadzombie.gif").getImage();

        }else if(zombieType == zombieType.BUCKHEAD){

            this.power = 1300;
            img = ImageIO.read(new File("images\\StrongZombie.png"));
            this.image = new ImageIcon("images\\Gifs\\bucketheadzombie.gif").getImage();

        }else throw new Exception(" No type detected !!!!");
    }

    @Override
    public void move() {

    }

    public void setBurntGif(){
        this.image = new ImageIcon("images\\Gifs\\burntZombie.gif").getImage();
    }

    public void setZombieGif() throws Exception{
        //calHealth();
        if (zombieType.equals(zombieType.NORMAL)){
            this.image = new ImageIcon("images\\Gifs\\zombie_normal.gif").getImage();
        } else if (zombieType.equals(zombieType.CONEHEAD)) {
            if (health >= 200) {
                image = new ImageIcon("images\\Gifs\\coneheadzombie.gif").getImage();
            } else {
                this.image = new ImageIcon("images\\Gifs\\zombie_normal.gif").getImage();
            }
        } else if (zombieType.equals(zombieType.CONEHEAD)) {
            if (health >= 200) {
                this.image = new ImageIcon("images\\Gifs\\bucketheadzombie.gif").getImage();
            } else {
                this.image = new ImageIcon("images\\Gifs\\zombie_normal.gif").getImage();
            }
        }else if (zombieType.equals(zombieType.FOOTBALL)) {
            this.image = new ImageIcon("images\\Gifs\\zombie_football.gif").getImage();
        }else throw new Exception(" No type detected !!!!");
    }

//    protected void setFields() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
//        this.sound = new Sound("zombie.wav");
//        if (game.equals(gameType.NORMAL)) {
//            if (zombieType.equals(type.NORMAL)) {
//                img = ImageIO.read(new File("images\\zombie1.png"));
//                setHealth(200);
//                this.speed = 4;
//                this.attack = 5;
//            } else if (zombieType.equals(type.CONEHEAD)) {
//                img = ImageIO.read(new File("images\\zombie2.png"));
//                setHealth(560);
//                this.speed = 3.5;
//                this.attack = 10;
//            } else if (zombieType.equals(type.BUCKHEAD)) {
//                img = ImageIO.read(new File("images\\StrongZombie.png"));
//                setHealth(1300);
//                this.speed = 3.5;
//                this.attack = 20;
//            } else if (zombieType.equals(type.FOOTBALL)) {
//                img = ImageIO.read(new File("images\\football.png"));
//                setHealth(1500);
//                this.speed = 3;
//                this.attack = 30;
//            }
//        }
//    }


    public void setDyingZombieGif() throws Exception{
        if(zombieType.equals(zombieType.FOOTBALL)){
            image = new ImageIcon("images\\Gifs\\zombie_football.gif").getImage();
        }else {
            image = new ImageIcon("images\\Gifs\\zombie_normal_dying.gif").getImage();
        }
    }



}
