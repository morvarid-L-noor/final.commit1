package com.company.ball;
/*** In The Name of Allah ***/
//package game.sample.ball;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class Main {

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
//        ThreadPool.init();
//            FirstScreen firstScreen = new FirstScreen();
////        // After the player clicks 'PLAY' ...
//        EventQueue.invokeLater(() -> {
//            GameFrame frame = null;
//            try {
//                frame = new GameFrame(" Plants vs Zombies ");
//            } catch (UnsupportedAudioFileException | IOException e) {
//                e.printStackTrace();
//            } catch (LineUnavailableException e) {
//                e.printStackTrace();
//            }
//            frame.setLocationRelativeTo(null); // put frame at center of screen
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setVisible(true);
//            frame.initBufferStrategy();
//            // Create and execute the game-loop
//            GameLoop game = new GameLoop(frame);
//            game.init();
//            ThreadPool.execute(game);
//            // and the game starts ...
//        });

        ThreadPool.init();

        // Show the game menu ...

        // After the player clicks 'PLAY' ...
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameFrame frame = null;
                try {
                    frame = new GameFrame("Simple Ball !");
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                }
                frame.setLocationRelativeTo(null); // put frame at center of screen
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.initBufferStrategy();
                // Create and execute the game-loop
                GameLoop game = new GameLoop(frame);
                game.init();
                ThreadPool.execute(game);
                // and the game starts ...
            }
        });

    }
}
