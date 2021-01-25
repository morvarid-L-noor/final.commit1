package com.company.ball;

import com.company.Sound;

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
    private BufferedImage bg;
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

    private JMenuBar pauseMenu;
    private JMenuItem resume;
    private JMenuItem save;
    private JMenuItem exitToMenu;

    public GameFrame(String title) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        super(title);
        setResizable(false);
        setVisible(true);
        setSize(GAME_WIDTH, GAME_HEIGHT);
        lastRender = -1;
        fpsHistory = new ArrayList<>(100);
        Sound sound = new Sound("background.wav");
        sound.play();
        this.backGround = ImageIO.read(new File("images\\mainBG.png"));

        this.pauseMenu = new JMenuBar();
        pauseMenu.setName("Pause Menu");
        this.resume = new JMenuItem("Resume Game");
        this.save = new JMenuItem("Save Game");
        this.exitToMenu = new JMenuItem("Exit to Main Menu");
        pauseMenu.add(resume);
        pauseMenu.add(save);
        pauseMenu.add(exitToMenu);
        exitToMenu.addActionListener(e -> {
            //FirstScreen firstscreen = new FirstScreen();
        });
    }

    public void putCards(){
        try {

            ////////////////if bayad bzri vasashoon
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
        // Draw background
        // g2d.setColor(Color.GRAY);
        g2d.drawImage(bg, null, 0, 27);
        // g2d.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        // Draw ball
        //g2d.setColor(Color.BLACK);   //88888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888
        // g2d.fillOval(state.locX, state.locY, state.diam, state.diam);

        /*		g2d.drawImage(image,state.locX,state.locY,null);*/


        // Print FPS info
        long currentRender = System.currentTimeMillis();
        if (lastRender > 0) {
            fpsHistory.add(1000.0f / (currentRender - lastRender));
            if (fpsHistory.size() > 100) {
                fpsHistory.remove(0); // remove oldest
            }
            float avg = 0.0f;
            for (float fps : fpsHistory) {
                avg += fps;
            }
            avg /= fpsHistory.size();
            String str ="X = "+ state.getMouseX()+" Y = "+ state.getMouseY();
            g2d.setColor(Color.CYAN);
            g2d.setFont(g2d.getFont().deriveFont(18.0f));
            int strWidth = g2d.getFontMetrics().stringWidth(str);
            int strHeight = g2d.getFontMetrics().getHeight();
            g2d.drawString(str, (GAME_WIDTH - strWidth) / 2, strHeight+50);
        }
        lastRender = currentRender;
        // Print user guide
        String userGuide
                = "Use the MOUSE or ARROW KEYS to move the BALL. "
                + "Press ESCAPE to end the game.";
        g2d.setFont(g2d.getFont().deriveFont(18.0f));
        g2d.drawString(userGuide, 10, GAME_HEIGHT - 10);
        // Draw GAME OVER
        if (state.gameOver) {
            String str = "GAME OVER";
            g2d.setColor(Color.WHITE);
            g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(64.0f));
            int strWidth = g2d.getFontMetrics().stringWidth(str);
            g2d.drawString(str, (GAME_WIDTH - strWidth) / 2, GAME_HEIGHT / 2);
        }
    }

}
