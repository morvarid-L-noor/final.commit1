package com.company.ball;

import com.company.Sound;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
public class FirstScreen {
    private JFrame f;
    protected GameState gameState = new GameState();
    JButton startButton;
    Sound sound;
    public FirstScreen() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        f = new JFrame();
        sound = new Sound("menu.wav");
        f.setTitle("First Screen");
        f.setContentPane(new JLabel(new ImageIcon("images\\first_screen.jpg")));
        f.setLayout(new FlowLayout());
        f.setLayout(new BorderLayout());

            JMenuBar mb = new JMenuBar();
            JMenu fileMenu = new JMenu("Menu");
            JMenuItem newGame = new JMenuItem("New Game");
            JMenuItem loadGame = new JMenuItem("Load Game");
            JMenuItem ranking = new JMenuItem("Ranking"); //8888888888888888888888888888888888888888888888
            JMenuItem setting = new JMenuItem("Setting");
            JMenuItem exitItem = new JMenuItem("Quit");
            fileMenu.setBounds(0, 0, 100, 150);
            fileMenu.add(newGame);
            fileMenu.add(loadGame);
            fileMenu.add(ranking);
            fileMenu.add(setting);
            fileMenu.add(exitItem);
            mb.add(fileMenu);
            newGame.addActionListener(e -> {
                try {
                    new NewGameFrame(gameState);
//                    NewGameFrame newGameFrame = new NewGameFrame(gameState);
//                    this.gameState = newGameFrame.getState();
                } catch (UnsupportedAudioFileException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (LineUnavailableException e1) {
                    e1.printStackTrace();
                }
            });
            loadGame.addActionListener(e -> {
                LoadGameFrame loadGameFrame = null;
                try {
                    loadGameFrame = new LoadGameFrame(gameState);
                } catch (UnsupportedAudioFileException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (LineUnavailableException e1) {
                    e1.printStackTrace();
                }
                this.gameState = loadGameFrame.getState();
            });
            setting.addActionListener(e -> {
                SettingFrame settingFrame = null;
                try {
                    settingFrame = new SettingFrame(gameState);
                } catch (UnsupportedAudioFileException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (LineUnavailableException e1) {
                    e1.printStackTrace();
                }
                this.gameState = settingFrame.getState();

            });
            exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.ALT_MASK));
            exitItem.addActionListener(e -> {
                f.dispose();
                // System.exit(0);//8888888888888888888888888888888888888888888888888888888888888888888 save game
            });
            f.setJMenuBar(mb);
            f.setSize(1000, 800);
            f.setLayout(null);
            f.setVisible(true);
            f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            //-------------------------------------------------------------------------------------------------------------------------------
            f.setFont(new Font("Arial", Font.PLAIN, 18));
        }

    public GameState getGameState() {
        return gameState;
    }

    public  void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
