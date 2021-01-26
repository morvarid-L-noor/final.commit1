package com.company.ball;
// for using fps :  for attack   --->  health - (attack)/fps     ,   move ---> width/(fps * numberOfSec)
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
    private int scores , numberOfWinings , numberOfGameOvers;
    public boolean gameOver , start , stopManu , endOfGame;
    private String playerName;
    private  int playersSuns;
    public enum gameType{HARD , NORMAL}
    private  gameType type;
    private boolean mute;
    private Element[][] elements;
    private boolean[] machineInRow ;
    private boolean[] zombieInRow ;
    private boolean mousePress;
    private int mouseX, mouseY;
    private MouseHandler mouseHandler;
    private ArrayList<Zombie> zombies = new ArrayList<>();
    private ArrayList<Machine> machines = new ArrayList<>();
    private ArrayList<Sun> suns = new ArrayList<>();
    private  int waveNum ;
    private String selectedCard ;
    private  long gameStartTime;
    private  long lastPurchaseSunflower = 0;
    private  long lastPurchasePeaShooter = 0;
    private  long lastPurchaseGiantWalnut = 0;
    private  long lastPurchaseCherryBomb = 0 ;
    private  long lastPurchaseFrozenPeaShooter = 0;
    private  Iterator<Zombie> Iter = zombies.iterator();
    private  boolean toPlace = false;   // if we should place purchased plant some where

    public GameState() throws IOException {
        elements = new Plant[5][9];
        zombieInRow = new boolean[5];
        machineInRow = new boolean[5];
        for(int i = 0 ; i < 5 ; i++ ){
            machineInRow[i] = true;
            zombieInRow[i] = false;
            machines.add(new Machine(i+1));
        }
        numberOfGameOvers =0;
        numberOfWinings = 0;
        scores = 0;
        locX = 100;
        locY = 100;
        diam = 32;
        gameOver = false;
        stopManu = false;
        start = false;
        mute = false;
        selectedCard = "-";
        //
        //
        mousePress = false;
        mouseX = 0;
        mouseY = 0;
        type = gameType.NORMAL;  // defualt type is normal
        //
        mouseHandler = new MouseHandler();
        elements = new Element[5][9];
        playersSuns = 150;
        gameStartTime = System.currentTimeMillis();
    }
    /**
     * The method which updates the game state.
     */
    public void update() throws Exception {

        checkGameOver();
        checkFinish();
        updateZombieInRow();
        //stop   888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888
        if(!stopManu){
            produseFallingSun();
            moveSuns();
            catchFallingSuns(); // 88888888888888888888888888888888888888888 kamel nashode
            stopOrNotStopAll(false);
            produceZombies();
            shootPea();
            attack();
            handleplants();
            removeMachine();
            removeDeadZombies();
            removeDeadPlants();
            handlePurchases();
            explosion();
            // 281 to 357 ro naneveshtam

            mouseX = 0;
            mouseY = 0;
        }else {
            stopOrNotStopAll(true);
            removeMachine();
            removeDeadZombies();
            removeDeadPlants();
        }
        setMachines();
        setResultOfGame();
    }

    private void setMachines() throws IOException {
        for(int i = 1 ; i < 5 ; i++){
            machines.add(new Machine(i));
        }
    }

    public  void  checkGameOver(){
//        System.out.print("1");
        for(Zombie z : zombies){
            System.out.print("2");
            if(z.arrivesHome() == true && !machineInRow[z.getRow()]){
                gameOver = true;
            }else if(z.arrivesHome() == true && machineInRow[z.getRow()]){
                machineInRow[z.getRow()] = false;
                for(Zombie zombie : zombies){
                    if(zombie.getRow() == z.getRow())zombie.setAlive(false);
                }
                z.setAlive(false);
                for(Machine m : machines){
                    if(m.getRow() == z.getRow()){
                        m.moveRight(8);
                    }
                }
                machineInRow[z.getRow()] = false;
            }
        }
    }

    public void checkFinish(){
//        System.out.print("3");
        if (System.currentTimeMillis() - gameStartTime >= 530000 && !gameOver) { //888888888888888888888888888888888888888888888888888
//                removeAllBullet();
//                removeAllZombies();
//                removeAllPlants();
            endOfGame = true;
        }
    }

    private void updateZombieInRow(){
//        System.out.print("4");
        int[] zombieInRowCounter = new int[5] ;
        for (int i = 0; i < 5; i++) {
            zombieInRowCounter[i] = 0;
        }

        for(Zombie z : zombies ){
            for(int i = 0 ; i <5 ; i++){
                if(i == z.getRow()-1){
                    zombieInRowCounter[i]++;
                }
            }
        }

        for (int i = 0; i < 5; i++) {
            if(zombieInRowCounter[i] == 0){
                zombieInRow[i]=false;
            }
        }
    }

    public void removeDeadZombies(){
//        System.out.print("5");
        while (Iter.hasNext()) {
            Zombie z = Iter.next();
            if (!z.isAlive()) {
                Iter.remove();
            }
            if(z.getHealth() <= 40)z.setDyingGif();
    }
    }
    private void attack(){
//        System.out.print("6");
        while (Iter.hasNext()) {
            Zombie z = Iter.next();
            z.moveLeft(z.getSpeed());

            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 9; j++) {
                    if (elements[i][j] != null) {
                        if (elements[i][j] instanceof Pea) {
                            //Move the Bullets
                            Iterator<Bullet> bullIter =((Pea)elements[i][j]).getBullets().iterator();
                            while (bullIter.hasNext()) {
                                Bullet b = bullIter.next();
                                if (b.getX() - 200 == z.getX() - 150 && b.getY() - 4 == z.getY() + 40) {

                                    z.setHealth(z.getHealth() - b.getDamage());

                                    if (z.getHealth() == 0) {
                                        z.setAlive(false);
                                    }
                                    //bullIter.remove();
                                }
                            }
                        }

                        if (z.getX() == elements[i][j].getX() && z.getY() + 40 == elements[i][j].getY()) {
                            z.setStoped(true);
                            z.setAttackTime(System.currentTimeMillis());
                            if((System.currentTimeMillis() - z.getAttackTime()) % 1000 == 0 ) { //8888888888888888888888888888888888888888888888888 shack daram
                                if (elements[i][j].getHealth() != 0) {
                                    int newH = elements[i][j].getHealth() - z.getAttack();
                                    elements[i][j].setHealth(newH);
                                }
                            }
                        }
                        if (elements[i][j].getHealth() == 0) {
                            z.setStoped(false);
                        }

                    }
                }
            }
        }
    }

    public void handleplants() throws IOException {
//        System.out.print("7");
        long producingSunPeriod = ( type == gameType.NORMAL? 20000 : 25000);
        long lastingTimeForSun = 10000;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                if (elements[i][j] != null) {
                    if (elements[i][j] instanceof Sunflower) {
                        if ((System.currentTimeMillis() - elements[i][j].getStartTime()) %  producingSunPeriod == 0) {
                            if( ((Sunflower)elements[i][j]).getSun() == null){
                                ((Sunflower)elements[i][j]).setSun(new Sun(elements[i][j].getX() + 20, elements[i][j].getY() - 20));
                                elements[i][j].setStartTime(System.currentTimeMillis());
                            }else {
                                if( ((Sunflower)elements[i][j]).getSun().getStartTime() - System.currentTimeMillis() > lastingTimeForSun){
                                    ((Sunflower)elements[i][j]).setSun(null);
                                }
                            }

                            if (Math.pow(mouseX - (elements[i][j].getX() ), 2)//8888888888888888888888888888888888888  shack
                                    + Math.pow(mouseY - (elements[i][j].getY() ), 2) < 400) {
                                playersSuns += ((Sunflower)elements[i][j]).getSun().getValue();
                                ((Sunflower)elements[i][j]).setSun(null);
                            }

                        }
                    }else if(elements[i][j] instanceof Pea){ //888888888888888888888888888888888888888888888888888888888888888888888888888 dobare tegheer bede
                        Iterator<Bullet> Iter =((Pea)elements[i][j]).getBullets().iterator();
                        while (Iter.hasNext()) {
                            Bullet b = Iter.next();
                            if (zombieInRow[b.getRow()]){
                                b.moveRight(4);
                                if (b.endOfFeild()) {
                                    Iter.remove();
                                }
                            }else Iter.remove();
                        }

                    }
                }
            }
        }

    }

    private void produseFallingSun() throws IOException { // 88888888888888888888888888888888888888 write catching
       // System.out.print("8");
        long produsePeriod = (type == gameType.NORMAL ? 250 : 300); // 8888888888888888888888888888888888 wrong time
        int random = new SecureRandom().nextInt(990) + 10 ;
        if((System.currentTimeMillis() - gameStartTime) % produsePeriod == 0 ){
           // System.out.print("9");
            suns.add(new Sun(random , 130));
        }
    }

    private void moveSuns(){
        for(Sun s : suns){
           // System.out.print("10");
            s.movedown(3);
        }
    }

    private void catchFallingSuns(){

        Iterator<Sun> sIter =suns.iterator();
        while (sIter.hasNext()) {
            Sun s = sIter.next();
            if (Math.pow(mouseX - (s.getX()), 2) + Math.pow(mouseY - (s.getY()), 2) < 40000) {
                playersSuns += 25;
                System.out.print("0");
                sIter.remove(); // 88888888888888888888888888888888888888888888888888888888888 check deleting with iterator ( shack !!!)
            }
        }
    }

    public  void  randomZombie() throws Exception {
        int random = new SecureRandom().nextInt(4);
        if(random == 0) {
            zombies.add(new Zombie(Zombie.zombiType.NORMAL,type));
        }else  if(random == 1) {
            zombies.add(new Zombie(Zombie.zombiType.CONEHEAD , type));
        }else  if(random == 2) {
            zombies.add(new Zombie(Zombie.zombiType.BUCKHEAD , type));
        }else  if(random == 3) {
            zombies.add(new Zombie(Zombie.zombiType.FOOTBALL , type));
        }
    }

    public void produceZombies() throws Exception {
        if((System.currentTimeMillis() - gameStartTime) > 5000 &&  (System.currentTimeMillis() - gameStartTime) < 20000 ){
            if((System.currentTimeMillis() - gameStartTime) % 3000 == 0) {
                randomZombie();
            }

        }else if((System.currentTimeMillis() - gameStartTime) >= 20000 && (System.currentTimeMillis() - gameStartTime) < 38000 ) {
            if((System.currentTimeMillis() - gameStartTime) % 3000 == 0) {
                randomZombie();
                randomZombie();
            }

        }else if((System.currentTimeMillis() - gameStartTime) >=  38000 && (System.currentTimeMillis() - gameStartTime) < 53000 ) {
            if((System.currentTimeMillis() - gameStartTime) % 2500 == 0) {
                randomZombie();
                randomZombie();
            }

        }

        for(Zombie z : zombies){
            z.setZombieGif();
        }
    }

    private void removeMachine(){
        Iterator<Machine> Iter0 = machines.iterator();
        while (Iter0.hasNext()) {
            Machine m = Iter0.next();
            if (m.endOfFeild()) {
                Iter0.remove();
            }
        }
    }

    private void shootPea() throws Exception {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                if (elements[i][j] != null  && elements[i][j] instanceof Pea) {
                    if(zombieInRow[i]){
                        if ((System.currentTimeMillis() - gameStartTime)% 1000 == 0 ) {
                            ((Pea)elements[i][j]).shoot();
                        }
                    }else {
                        Iterator<Bullet> bullIter = ((Pea)elements[i][j]).getBullets().iterator();
                        while (bullIter.hasNext()) {
                            Bullet b = bullIter.next();
                            bullIter.remove();
                        }
                    }
                }
            }
        }
    }

    private void removeDeadPlants(){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                if (elements[i][j] != null) {
                    if (elements[i][j].getHealth() == 0) {
                        elements[i][j] = null;
                    }
                }
            }
        }
    }

    private void handlePurchases() throws IOException {

        if(toPlace){
             System.out.print("7");
            locX = ( mouseX - 40 )/ 100;
            locY = ( mouseY - 130 )/ 130;
            locX = Math.max(locX, 0);
            locX = Math.min(locX, 8);
            locY = Math.max(locY, 0);
            locY = Math.min(locY, 4);

            if(selectedCard.equals("sunflower")){
                 System.out.print("8");
                elements[locX][locY] = new Sunflower(40 + locX * 100 , 130  + locY * 120);
                selectedCard = "-";

            }else if(selectedCard.equals("giantWallNut")){
                elements[locX][locY]  = new GiantWalNut(40 + locX * 100 , 130  + locY * 120);
                selectedCard = "-";

            }else if(selectedCard.equals("peaShooter")){

                elements[locX][locY] = new Pea(40 + locX * 100 , 130  + locY * 120 , Pea.peaType.PEA);
                selectedCard = "-";

            }else if(selectedCard.equals("snowPea")){

                elements[locX][locY] = new Pea(40 + locX * 100 , 130  + locY * 120 , Pea.peaType.FREEZEPEA);
                selectedCard = "-";

            }else if(selectedCard.equals("cherryBomb")){

                elements[locX][locY] = new CherryBomb(40 + locX * 100 , 130  + locY * 120 );
                selectedCard = "-";

            }
            elements[locX][locY].setRow(locX);
            elements[locX][locY].setColumn(locY);
           // selectedCard = null;
            toPlace = false;

        }
    }


    private void setResultOfGame(){
        if(gameOver){
            numberOfGameOvers +=1;
            scores -=(type == gameType.NORMAL ? 1 : 3);
        }
        else if(endOfGame){
            numberOfWinings +=1;
            scores +=(type == gameType.NORMAL ? 3 : 10);
        }

    }

    private void explosion(){
        // cherry bomb explosion
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                if (elements[i][j] != null) {
                    if (elements[i][j] instanceof CherryBomb) {
                        if ( System.currentTimeMillis() - elements[i][j].getStartTime() >=  1000) {
                            for(Zombie z : zombies){
                                if (( Math.pow(z.getX() - elements[i][j].getX(),2 )+ Math.pow(z.getY() - elements[i][j].getY(),2)) < 90000 ) {
                                    z.setBurntGif();
                                    z.setAlive(false);
                                }
                            }
                        }
                        elements[i][j] = null;
                    }

                }

            }

        }

    }

    public void stopOrNotStopAll(boolean tf){
        // when tf is true all of them are stoped and vice versa when tf is false
        for(Zombie z : zombies){
            z.setStoped(tf);
        }
        for(Machine m : machines){
            m.setStoped(tf);
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                if (elements[i][j] != null  && elements[i][j] instanceof Pea){
                        Iterator<Bullet> Iter =((Pea)elements[i][j]).getBullets().iterator();
                        while (Iter.hasNext()) {
                            Bullet b = Iter.next();
                            b.setStoped(tf);
                        }
                }
            }
        }
    }



    public MouseListener getMouseListener() {
        return mouseHandler;
    }
    public MouseMotionListener getMouseMotionListener() {
        return mouseHandler;
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
                     System.out.print("5");
                    selectedCard = "sunflower";
                    playersSuns -= 50;
                    lastPurchaseSunflower = System.currentTimeMillis();
                }else if(mouseX > 200 && mouseX < 280){
                    selectedCard = "giantWallNut";
                    playersSuns -= 50;
                    lastPurchaseGiantWalnut = System.currentTimeMillis();
                }else if(mouseX > 290 && mouseX < 370){
                    selectedCard = "peaShooter";
                    playersSuns -= 100;
                    lastPurchasePeaShooter = System.currentTimeMillis();
                }else if(mouseX > 380 && mouseX < 460){
                    selectedCard = "snowPea";
                    playersSuns -= 175;
                    lastPurchaseFrozenPeaShooter = System.currentTimeMillis();
                }else if(mouseX > 470 && mouseX < 550){
                    selectedCard = "cherryBomb";
                    playersSuns -= 150;
                    lastPurchaseCherryBomb = System.currentTimeMillis();
                }

            }
            if(mouseY > 27 && mouseY < 56){
                if(mouseX > 860 && mouseX < 1000){
                    stopManu = true;
                    mute = true;
                }
            }

            if (! selectedCard.contains("-")) {
                if (locX > 40 && locX < 940 && locY > 130 && locY < 725) {
                    System.out.print("6");
                    toPlace = true;
                }
//            mousePress = true;

            }
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
    public void addToFile(){
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

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
        addToFile();
    }
    public GameState.gameType getType() {
        return type;
    }

    public void setType(GameState.gameType type) {
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

    public ArrayList<Machine> getMachines(){
        return machines;
    }
    public ArrayList<Sun> getSuns(){
        return suns;
    }

    public void addElement(int row , int column, Element element){
        elements[row][column] = element;
    }

    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    public int getNumberOfWinings() {
        return numberOfWinings;
    }

    public void setNumberOfWinings(int numberOfWinings) {
        this.numberOfWinings = numberOfWinings;
    }

    public int getNumberOfGameOvers() {
        return numberOfGameOvers;
    }

    public void setNumberOfGameOvers(int numberOfGameOvers) {
        this.numberOfGameOvers = numberOfGameOvers;
    }



}

