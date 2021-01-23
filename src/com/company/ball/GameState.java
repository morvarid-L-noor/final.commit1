package com.company.ball;

import com.company.*;

import java.awt.event.*;
import java.io.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class holds the state of game and all of its elements.
 * This class also handles user inputs, which affect the game state.
 *
 * @author Seyed Mohammad Ghaffarian
 */
public class GameState implements Serializable{

    public int locX, locY, diam;
    public boolean gameOver , start , stopManu , endOfGame;
    private String playerName;
    private  int playersSuns;

    enum type{ hard , normal}
    private  type type;
    private boolean mute;
    private Element[][] elements;
    private boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
    private boolean mousePress;
    private int mouseX, mouseY;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;
    private ArrayList<Zombie> zombies = new ArrayList<>();
    private  int waveNum ;
    private String selectedCard ;
    private  long gameStartTime;
    private  long lastPurchaseSunflower = 0;
    private  long lastPurchasePeaShooter = 0;
    private  long lastPurchaseGiantWalnut = 0;
    private  long lastPurchaseCherryBomb = 0 ;
    private  long lastPurchaseFrozenPeaShooter = 0;
    private  boolean row1Zombie = false;
    private  boolean row2Zombie = false;
    private  boolean row3Zombie = false;
    private  boolean row4Zombie = false;
    private  boolean row5Zombie = false;
    private  boolean toPlace = false;   // if we should place purchased plant some where

    public GameState(){
        locX = 100;
        locY = 100;
        diam = 32;
        gameOver = false;
        stopManu = true;
        start = false;
        selectedCard = null;
        //
        keyUP = false;
        keyDOWN = false;
        keyRIGHT = false;
        keyLEFT = false;
        //
        mousePress = false;
        mouseX = 0;
        mouseY = 0;
        //
        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();
        elements = new Element[5][9];
        playersSuns = 150;
        gameStartTime = System.currentTimeMillis();
    }

    public  void  checkGameOver(){
        for(Zombie z : zombies){
            if(z.arrivesHome() == true){
                gameOver = true;
            }
        }
    }
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
        addToFile();
    }
    public GameState.type getType() {
        return type;
    }

    public void setType(GameState.type type) {
        this.type = type;
    }

    public boolean isMute() {
        return mute;
    }

    public void setMute(boolean mute) {
        this.mute = mute;
    }

    public Element[][] getElements() {
        return elements;
    }

    public void setElements(Element[][] elements) {
        this.elements = elements;
    }

    public void addElement(int row , int column, Element element){
        elements[row][column] = element;
    }

    public  void  randomZombie() throws Exception {
        int random = new SecureRandom().nextInt(3);
        if(random == 0) {
            zombies.add(new Zombie(Zombie.zombiType.NORMAL));
        }else  if(random == 1) {
            zombies.add(new Zombie(Zombie.zombiType.CONEHEAD));
        }else  if(random == 2) {
            zombies.add(new Zombie(Zombie.zombiType.BUCKHEAD));
        }
    }
    /**
     * The method which updates the game state.
     */
    public void update() throws Exception {
        checkGameOver();
         //stop   888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888
        if(!stopManu){
        if((System.currentTimeMillis() - gameStartTime) > 50000 &&  (System.currentTimeMillis() - gameStartTime) < 200000 ){
            if((System.currentTimeMillis() - gameStartTime) % 30000 == 0) {
                randomZombie();
            }

        }else if((System.currentTimeMillis() - gameStartTime) >= 200000 && (System.currentTimeMillis() - gameStartTime) < 380000 ) {
            if((System.currentTimeMillis() - gameStartTime) % 30000 == 0) {
                randomZombie();
                randomZombie();
            }

        }else if((System.currentTimeMillis() - gameStartTime) >=  380000 && (System.currentTimeMillis() - gameStartTime) < 530000 ) {
            if((System.currentTimeMillis() - gameStartTime) % 25000 == 0) {
                randomZombie();
                randomZombie();
            }

        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                if (elements[i][j] != null) {
                    if (elements[i][j] instanceof Sunflower) {

                        if ((System.currentTimeMillis() - elements[i][j].getStartTime()) > 20000) {
                            if( ((Sunflower)elements[i][j]).getSun() == null){
                                ((Sunflower)elements[i][j]).setSun(new Sun(elements[i][j].getX() + 20, elements[i][j].getY() - 20));
                            elements[i][j].setStartTime(System.currentTimeMillis());
                             }else {
                                if( ((Sunflower)elements[i][j]).getSun().getStartTime() - System.currentTimeMillis() > 10000){
                                    ((Sunflower)elements[i][j]).setSun(null);
                                }
                            }

                            if (Math.pow(mouseX - (elements[i][j].getX() ), 2)//8888888888888888888888888888888888888  shack
                                    + Math.pow(mouseY - (elements[i][j].getY() ), 2) < 225) {
                                playersSuns += ((Sunflower)elements[i][j]).getSun().getValue();
                                ((Sunflower)elements[i][j]).setSun(null);
                            }

                        }
                    }else if(elements[i][j] instanceof Pea){ //888888888888888888888888888888888888888888888888888888888888888888888888888 dobare tegheer bede
                        Iterator<Bullet> Iter =((Pea)elements[i][j]).getBullets().iterator();
                        while (Iter.hasNext()) {
                            Bullet b = Iter.next();
                            b.moveRight(1);
                            if (!b.endOfFeild()) {
                                Iter.remove();
                            }
                        }

                    }
                }
            }
        }
        Iterator<Zombie> Iter = zombies.iterator();
        while (Iter.hasNext()) {
            Zombie z = Iter.next();
            z.moveLeft(1);  // speed ro dorost con 88888888888888888888888888888888888888888888
            if (!z.isAlive()) {
                Iter.remove();
            }
//
//            for (int i = 0; i < 5; i++) {
//                for (int j = 0; j < 9; j++) {
//                    if (plants[i][j] != null) {
//
//                        if (plants[i][j] instanceof Pea) {
//
//                            //Move the Bullets
//                            bullIter = plants[i][j].bulletArr.iterator();
//
//                            while (bullIter.hasNext()) {
//                                Bullet b = bullIter.next();
//
//                                if (b.locX - 200 == z.locX - 150 && b.locY - 4 == z.locY + 40) {
//
//                                    z.life--;
//                                    if (b instanceof SnowBullet) {
//                                        z.counter = 90;
//                                    }
//                                    if (z.life == 0) {
//                                        int diedY = z.y;
//                                        zombieIter.remove();
//                                        zombieInRow[diedY] = false;
//                                        for (Zombie zom : zombieList) {
//                                            if (zom.y == diedY) {
//                                                zombieInRow[diedY] = true;
//                                            }
//                                        }
//                                    }
//                                    bullIter.remove();
//
//                                }
//
//                            }
//
//                        }
//                        plantLose(z, plants[i][j]);
//                        if (plants[i][j].life == 0) {
//                            z.zombieMove = true;
//                        }
//
//                    }
//                }
//            }
        }

        for (int i = 0; i < 5; i++) {

            for (int j = 0; j < 9; j++) {
                if (elements[i][j] != null) {
                    if(i == 0 && row1Zombie){
                        if ((System.currentTimeMillis() - gameStartTime)% 1000 == 0  && elements[i][j] instanceof Pea) {
                            ((Pea)elements[i][j]).shoot();
                        }
                    }else if(i == 1 && row2Zombie){
                        if ((System.currentTimeMillis() - gameStartTime)% 1000 == 0  && elements[i][j] instanceof Pea) {
                            ((Pea)elements[i][j]).shoot();
                        }
                    }else if(i == 2 && row3Zombie){
                        if ((System.currentTimeMillis() - gameStartTime)% 1000 == 0  && elements[i][j] instanceof Pea) {
                            ((Pea)elements[i][j]).shoot();
                        }
                    }else if(i == 3 && row4Zombie){
                        if ((System.currentTimeMillis() - gameStartTime)% 1000 == 0  && elements[i][j] instanceof Pea) {
                            ((Pea)elements[i][j]).shoot();
                        }
                    }else if(i == 4 && row5Zombie){
                        if ((System.currentTimeMillis() - gameStartTime)% 1000 == 0  && elements[i][j] instanceof Pea) {
                            ((Pea)elements[i][j]).shoot();
                        }
                    }
                }
            }
        }

        for(Zombie z : zombies){
            z.setZombieGif();
        }

        // 281 to 357 ro naneveshtam
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                if (elements[i][j] != null) {
                    if (elements[i][j].getHealth() == 0) {
                        elements[i][j] = null;
                    }
                }
            }
        }



        if(toPlace){
            locX = ( mouseX - 40 )/ 100;
            locY = ( mouseY - 130 )/ 130;
            locX = Math.max(locX, 0);
            locX = Math.min(locX, 8);
            locY = Math.max(locY, 0);
            locY = Math.min(locY, 4);

            if(selectedCard.equals("sunflower")){
                elements[locX][locY] = new Sunflower(40 + locX * 100 , 130  + locY * 130);
                playersSuns -= 50;
            }else if(selectedCard.equals("giantWallNut")){
                elements[locX][locY] = new GiantWalNut(40 + locX * 100 , 130  + locY * 130);
                playersSuns -= 50;
            }else if(selectedCard.equals("peaShooter")){
                elements[locX][locY] = new Pea(40 + locX * 100 , 130  + locY * 130 , Pea.peaType.peaShooter);
                playersSuns -= 100;
            }else if(selectedCard.equals("snowPea")){
                elements[locX][locY] = new Pea(40 + locX * 100 , 130  + locY * 130 , Pea.peaType.snowPea);
                playersSuns -= 175;
            }else if(selectedCard.equals("cherryBomb")){
                elements[locX][locY] = new CherryBomb(40 + locX * 100 , 130  + locY * 130 );
                playersSuns -= 150;
            }
            elements[locX][locY].setRow(locX);
            elements[locX][locY].setColumn(locY);
            selectedCard = null;
            toPlace = false;


        }
 // cherry bomb explosion
    for (int i = 0; i < 5; i++) {
        for (int j = 0; j < 9; j++) {
            if (elements[i][j] != null) {
                if (elements[i][j] instanceof CherryBomb) {
                    //((CherryBomb) plants[i][j]).explodeTime++;
                    if ( System.currentTimeMillis() - (elements[i][j]).getStartTime() >=  1000) {
                        Iter = zombies.iterator();
                        while (Iter.hasNext()) {
                            Zombie z = Iter.next();
                            if (( Math.pow(z.getX() - elements[i][j].getX(),2 )+ Math.pow(z.getY() - elements[i][j].getY(),2)) < 90000 ) {
                                    Iter.remove();
                            }
                        }
                        elements[i][j] = null;
                    }

                }
            }
        }
    }

            if (System.currentTimeMillis() - gameStartTime >= 530000) { //888888888888888888888888888888888888888888888888888
//                removeAllBullet();
//                removeAllZombies();
//                removeAllPlants();
                endOfGame = true;
            }
        mouseX = 0;
        mouseY = 0;
    }else {

    }
    }

    public KeyListener getKeyListener() {
        return keyHandler;
    }
    public MouseListener getMouseListener() {
        return mouseHandler;
    }
    public MouseMotionListener getMouseMotionListener() {
        return mouseHandler;
    }

    class KeyHandler extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_UP:
                    keyUP = true;
                    break;
                case KeyEvent.VK_DOWN:
                    keyDOWN = true;
                    break;
                case KeyEvent.VK_LEFT:
                    keyLEFT = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    keyRIGHT = true;
                    break;
                case KeyEvent.VK_ESCAPE:
                    gameOver = true;
                    break;
                case KeyEvent.VK_ENTER:
                    start = true;
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_UP:
                    keyUP = false;
                    break;
                case KeyEvent.VK_DOWN:
                    keyDOWN = false;
                    break;
                case KeyEvent.VK_LEFT:
                    keyLEFT = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    keyRIGHT = false;
                    break;
            }
        }

    }

    /**
     * The mouse handler.
     */
    class MouseHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {

            mouseX = e.getX();
            mouseY = e.getY();
            if(mouseY > 35 && mouseY < 130){
                if(mouseX > 110 && mouseX < 190){
                    selectedCard = "sunflower";
                    lastPurchaseSunflower = System.currentTimeMillis();
                }else if(mouseX > 200 && mouseX < 280){
                    selectedCard = "giantWallNut";
                    lastPurchaseGiantWalnut = System.currentTimeMillis();
                }else if(mouseX > 290 && mouseX < 370){
                    selectedCard = "peaShooter";
                    lastPurchasePeaShooter = System.currentTimeMillis();
                }else if(mouseX > 380 && mouseX < 460){
                    selectedCard = "snowPea";
                    lastPurchaseFrozenPeaShooter = System.currentTimeMillis();
                }else if(mouseX > 470 && mouseX < 550){
                    selectedCard = "cherryBomb";
                    lastPurchaseCherryBomb = System.currentTimeMillis();
                }

            }
            if(mouseY > 27 && mouseY < 56){
                if(mouseX > 860 && mouseX < 1000){
                    stopManu = true;
                }
            }

            if(locX > 40 && locX < 940  && locY > 130 && locY < 725 && selectedCard != null){
                    toPlace = true;
            }
            mousePress = true;


        }
        @Override
        public void mousePressed(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
            mousePress = true;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            mousePress = false;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
        }
    }
    void addToFile(){
        String studentDir = "C:\\Users\\Movarid\\Desktop\\finalProject\\games";
        try(FileOutputStream fileOutputStream = new FileOutputStream(studentDir + File.separator+this.playerName)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Zombie> getZombies(){
        return zombies;
    }

    public long getLastPurchaseSunflower() {
        return lastPurchaseSunflower;
    }

    public void setLastPurchaseSunflower(long lastPurchaseSunflower) {
        this.lastPurchaseSunflower = lastPurchaseSunflower;
    }

    public long getLastPurchasePeaShooter() {
        return lastPurchasePeaShooter;
    }

    public void setLastPurchasePeaShooter(long lastPurchasePeaShooter) {
        this.lastPurchasePeaShooter = lastPurchasePeaShooter;
    }

    public long getLastPurchaseGiantWalnut() {
        return lastPurchaseGiantWalnut;
    }

    public void setLastPurchaseGiantWalnut(long lastPurchaseGiantWalnut) {
        this.lastPurchaseGiantWalnut = lastPurchaseGiantWalnut;
    }

    public long getLastPurchaseCherryBomb() {
        return lastPurchaseCherryBomb;
    }

    public void setLastPurchaseCherryBomb(long lastPurchaseCherryBomb) {
        this.lastPurchaseCherryBomb = lastPurchaseCherryBomb;
    }

    public long getLastPurchaseFrozenPeaShooter() {
        return lastPurchaseFrozenPeaShooter;
    }

    public void setLastPurchaseFrozenPeaShooter(long lastPurchaseFrozenPeaShooter) {
        this.lastPurchaseFrozenPeaShooter = lastPurchaseFrozenPeaShooter;
    }

    public int getPlayersSuns() {
        return playersSuns;
    }

    public void setPlayersSuns(int playersSuns) {
        this.playersSuns = playersSuns;
    }

}

