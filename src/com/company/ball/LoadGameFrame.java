package com.company.ball;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;

/**
 * Created by Movarid on 1/22/2021.
 */
public class LoadGameFrame {
    private JFrame f;
    private JPanel p;
    GameState gameState ;
    public LoadGameFrame(GameState gameState1) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        super();
        gameState = gameState1;
        String dir = "C:\\Users\\Movarid\\Desktop\\finalProject\\games";
        f = new JFrame();
        p = new JPanel();
        f.setSize(200,200);
        f.setLocation(400,400);
        JLabel nameL = new JLabel("Please enter your name : ");p.add(nameL);// player ba username password
        JTextField name = new JTextField();p.add(name);
        JButton loadGameButton = new JButton("Load game ");p.add(loadGameButton);
        f.add(p);
        loadGameButton.addActionListener((ActionEvent e) -> {
            File[] list = new File(dir).listFiles();
//            GameState gameState = new GameState();
            File returnedFile = null ;
            assert list != null;
            boolean sent = false;
            for(File file : list){
                if(file.getName().contains(name.getText())){
                    sent = true;
                   returnedFile = file;
                }
            }
            if(sent == false) try {
                throw new FileNotFoundException(" Class load game : file not found error !!!!");

            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }

            try(FileInputStream fileInputStream = new FileInputStream(dir+File.separator+returnedFile.getName())) {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                gameState =  (GameState) objectInputStream.readObject();
            } catch (ClassNotFoundException | IOException e2) {
                e2.printStackTrace();

            }
        });

    }
    public  GameState getState(){
        return gameState;
    }
}
