package com.company.ball;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;

public class NewGameFrame  {
    GameState gameState;
    private JFrame f;
    private JPanel p;
    public NewGameFrame(GameState gameState1 ) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        super();
        gameState = gameState1;
        f = new JFrame();
        p = new JPanel();
        f.setSize(200,200);
        f.setLocation(400,400);
        JLabel nameL = new JLabel("Please enter your name : ");p.add(nameL);// player ba username password
        JTextField name = new JTextField();p.add(name);
        JButton newGameButton = new JButton("New Game ");p.add(newGameButton);
        f.add(p);
        newGameButton.addActionListener(e -> {
            gameState.setPlayerName(name.getText());

        });

    }
    public  GameState getState(){
        return gameState;
    }
}
