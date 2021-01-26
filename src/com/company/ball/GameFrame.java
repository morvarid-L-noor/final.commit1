package com.company.ball;
/*** In The Name of Allah ***/
//package game.sample.ball;

import com.company.*;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The window on which the rendering is performed.
 * This example uses the modern BufferStrategy approach for double-buffering,
 * actually it performs triple-buffering!
 * For more information on BufferStrategy check out:
 *    http://docs.oracle.com/javase/tutorial/extra/fullscreen/bufferstrategy.html
 *    http://docs.oracle.com/javase/8/docs/api/java/awt/image/BufferStrategy.html
 *
 * @author Seyed Mohammad Ghaffarian
 */
public class GameFrame extends JFrame {

    public static final int GAME_HEIGHT = 730;                  // 720p game resolution
    public static final int GAME_WIDTH = 1005;  // wide aspect ratio

    //uncomment all /*...*/ in the class for using Tank icon instead of a simple circle
	/*private BufferedImage image;*/
    private BufferedImage backGround;
    private BufferedImage sunflowerON;
    private BufferedImage sunflowerOff;
    private BufferedImage peaShooterOn;
    private BufferedImage peaShooterOff;
    private BufferedImage freezeShooterOn;
    private BufferedImage freezeShooterOff;
    private BufferedImage wallnutOn;
    private BufferedImage wallnutOff;
    private BufferedImage cherryOn;
    private BufferedImage cherryOff;

    private long lastRender;
    private ArrayList<Float> fpsHistory;

    private BufferStrategy bufferStrategy;

    public GameFrame(String title) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        super(title);
        setResizable(false);
        setSize(GAME_WIDTH, GAME_HEIGHT);
        lastRender = -1;
        fpsHistory = new ArrayList<>(100);
//        Sound sound = new Sound("background.wav");sound.play();
//        JLabel nameL = new JLabel("Please enter your name : ");add(nameL);// player ba username password
//        JTextField name = new JTextField();add(name);
//        JButton startButton = new JButton("Let's play ");add(startButton);
//        startButton.addActionListener(e -> {
//            //88888888888888888888888888888888888888888888888
//            this.setVisible(false);
//        });
//        backGround = ImageIO.read(new File("images\\first_screen.jpg"));

	/*	try{
			image = ImageIO.read(new File("Icon.png"));
		}
		catch(IOException e){
			System.out.println(e);
		}*/
	putCards();
    }

    public void putCards(){
        try {
            backGround = ImageIO.read(new File("mainBG.png"));
            sunflowerON = ImageIO.read(new File("sunflowerBut.png"));
            sunflowerOff = ImageIO.read(new File("sunflowerbutD.png"));

            peaShooterOn = ImageIO.read(new File("peaB.png"));
            peaShooterOff = ImageIO.read(new File("peaBd.png"));

            freezeShooterOn = ImageIO.read(new File("icePeaB.png"));
            freezeShooterOff = ImageIO.read(new File("SnowButD.png"));

            wallnutOn = ImageIO.read(new File("wal.png"));
            wallnutOff = ImageIO.read(new File("walD.png"));

            cherryOn = ImageIO.read(new File("cherryBut.png"));
            cherryOff = ImageIO.read(new File("cherryButD.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * This must be called once after the JFrame is shown:
     *    frame.setVisible(true);
     * and before any rendering is started.
     */
    public void initBufferStrategy() {
        // Triple-buffering
        createBufferStrategy(3);
        bufferStrategy = getBufferStrategy();
    }


    /**
     * Game rendering with triple-buffering using BufferStrategy.
     */
    public void render(GameState state) {
        // Render single frame
        do {
            // The following loop ensures that the contents of the drawing buffer
            // are consistent in case the underlying surface was recreated
            do {
                // Get a new graphics context every time through the loop
                // to make sure the strategy is validated
                Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
                try {
                    doRendering(graphics, state);
                } finally {
                    // Dispose the graphics
                    graphics.dispose();
                }
                // Repeat the rendering if the drawing buffer contents were restored
            } while (bufferStrategy.contentsRestored());

            // Display the buffer
            bufferStrategy.show();
            // Tell the system to do the drawing NOW;
            // otherwise it can take a few extra ms and will feel jerky!
            Toolkit.getDefaultToolkit().sync();

            // Repeat the rendering if the drawing buffer was lost
        } while (bufferStrategy.contentsLost());
    }

    /**
     * Rendering all game elements based on the game state.
     */
    private void doRendering(Graphics2D g2d, GameState state) {

		g2d.drawImage(backGround,null, 0, 27);
        g2d.drawImage(sunflowerON, null, 112, 37);

        if(  state.getLastPurchaseGiantWalnut() ==0 ||  state.getLastPurchasePeaShooter() ==0
                ||  state.getLastPurchaseFrozenPeaShooter() ==0||  state.getLastPurchaseCherryBomb() ==0
                ||  state.getLastPurchaseSunflower() ==0 ||  state.getLastPurchaseCherryBomb() ==0) {
            g2d.drawImage(sunflowerON, null, 112, 37);
            g2d.drawImage(wallnutOn, null, 202, 37);
            g2d.drawImage(peaShooterOn, null, 292, 37);
            g2d.drawImage(freezeShooterOn, null, 382, 37);
            g2d.drawImage(cherryOn, null, 472, 37);
        }
        if (state.getPlayersSuns() >= 50 &&
                ( System.currentTimeMillis() - state.getLastPurchaseSunflower() > 7500 )) {
            g2d.drawImage(sunflowerON, null, 112, 37);
        } else {
            g2d.drawImage(sunflowerOff, null, 112, 37);
        }

        if (state.getPlayersSuns() >= 50 &&
                ( System.currentTimeMillis() - state.getLastPurchaseGiantWalnut() > 30000 )) {
            g2d.drawImage(wallnutOn, null, 202, 37);
        } else {
            g2d.drawImage(wallnutOff, null, 202, 37);
        }

        if (state.getPlayersSuns() >= 100 &&
                ( System.currentTimeMillis() - state.getLastPurchasePeaShooter() > 7500 )) {
            g2d.drawImage(peaShooterOn, null, 292, 37);
        } else {
            g2d.drawImage(peaShooterOff, null, 292, 37);
        }
//88888888888888888888888888888888888888888888888888888888888888888 state = wave ro naneveshtam 8888888888888888888888888888888888888888888888888
        if(state.getType() == GameState.gameType.NORMAL){
            if (state.getPlayersSuns() >= 175 &&
                    ( System.currentTimeMillis() - state.getLastPurchaseFrozenPeaShooter() > 7500 )) {
                g2d.drawImage(freezeShooterOn, null, 382, 37);
            } else {
                g2d.drawImage(freezeShooterOff, null, 382, 37);
            }

            if (state.getPlayersSuns() >= 150 &&
                    ( System.currentTimeMillis() - state.getLastPurchaseCherryBomb() > 30000 )) {
                g2d.drawImage(cherryOn, null, 472, 37);
            } else {
                g2d.drawImage(cherryOff, null, 472, 37);
            }
        }else if(state.getType() == GameState.gameType.HARD){
            if (state.getPlayersSuns() >= 175 &&
                    ( System.currentTimeMillis() - state.getLastPurchaseFrozenPeaShooter() > 30000 )) {
                g2d.drawImage(freezeShooterOn, null, 382, 37);
            } else {
                g2d.drawImage(freezeShooterOff, null, 382, 37);
            }

            if (state.getPlayersSuns() >= 150 &&
                    ( System.currentTimeMillis() - state.getLastPurchaseCherryBomb() > 45000 )) {
                g2d.drawImage(cherryOn, null, 472, 37);
            } else {
                g2d.drawImage(cherryOff, null, 472, 37);
            }
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                if (state.getElements()[i][j] != null) {//888888888888888888888888888888888888888888888888888888888888888888888888888
                    g2d.drawImage(state.getElements()[i][j].getImg(), state.getElements()[i][j].getX(),
                            state.getElements()[i][j].getY() , null);
                    if (state.getElements()[i][j] instanceof Sunflower ) {
                        if(((Sunflower)state.getElements()[i][j]).getSun() != null){
                            g2d.drawImage(((Sunflower)state.getElements()[i][j]).getSun().getImg(), state.getElements()[i][j].getX() + 20,
                                    state.getElements()[i][j].getY() - 20 , null);
                        }
                    }
                }
            }
        }

        for(Zombie z : state.getZombies()){
            g2d.drawImage(z.getImg(), z.getX(), z.getY(), null);
        }
        for(Sun s : state.getSuns()){
            g2d.drawImage(s.getImg(), s.getX(), s.getY(), null);
        }

        for(Machine m : state.getMachines()){
            g2d.drawImage(m.getImg(), m.getX(), m.getY(), null);
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                if (state.getElements()[i][j] != null && state.getElements()[i][j] instanceof Pea) {
                    for (Bullet bullet :((Pea)state.getElements()[i][j]).getBullets()) {
                        g2d.drawImage(bullet.getImg(),  bullet.getX(), bullet.getY() ,null);
                    }
                }
            }
        }

//        long currentRender = System.currentTimeMillis();
//        if (lastRender > 0) {
//            fpsHistory.add(1000.0f / (currentRender - lastRender));
//            if (fpsHistory.size() > 100) {
//                fpsHistory.remove(0); // remove oldest
//            }
//            float avg = 0.0f;
//            for (float fps : fpsHistory) {
//                avg += fps;
//            }
//            avg /= fpsHistory.size();
//            //String str = String.format("Average FPS = %.1f , Last Interval = %d ms",
//                 //   avg, (currentRender - lastRender));
//            g2d.setColor(Color.CYAN);
//            g2d.setFont(g2d.getFont().deriveFont(18.0f));
//           // int strWidth = g2d.getFontMetrics().stringWidth(str);
//          //  int strHeight = g2d.getFontMetrics().getHeight();
//            //g2d.drawString(startButton, (GAME_WIDTH - startButton.getWidth()) / 2, startButton.getHeight() +50);
//        }
//        lastRender = currentRender;
        // Print user guide
//        System.out.print("2");
//        String userGuide
//                = "Press ENTER to start the game  or  "
//                + "Press ESCAPE to end the game.";
        g2d.setFont(g2d.getFont().deriveFont(18.0f));
//        g2d.drawString(userGuide, 320, GAME_HEIGHT - 80);
        g2d.setColor(Color.BLACK);
        String totalSun = Integer.toString(state.getPlayersSuns());
        int strWidth0 = g2d.getFontMetrics().stringWidth(totalSun);
        int strHeight = g2d.getFontMetrics().getHeight();
        g2d.drawString(totalSun, (20 + ((70 - strWidth0) / 2)), 110 + strHeight/2);


        // Draw GAME OVER
        if (state.gameOver) {
            String str = "GAME OVER";
            System.out.print("GAME OVER");
            g2d.setColor(Color.WHITE);
            g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(64.0f));
            int strWidth = g2d.getFontMetrics().stringWidth(str);
            g2d.drawString(str, (GAME_WIDTH - strWidth) / 2, GAME_HEIGHT / 2);
        }
        if (state.start) {
            this.setVisible(true);
            System.out.print("start");
           //88888888888888888888888888888888888888888888888888888888888888888888
        }
        if(state.endOfGame){
            String str = "END OF GAME";
            int strWidth = g2d.getFontMetrics().stringWidth(str);
            g2d.drawString(str, (GAME_WIDTH - strWidth) / 2, GAME_HEIGHT / 2);
        }
    }

}
