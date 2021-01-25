package com.company.ball;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RankingFrame {
    private JFrame rankingFrame;
    private JPanel rankingPanel;
    private GameState gameState;

    public RankingFrame() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        rankingFrame = new JFrame();
        rankingFrame.setBackground(Color.white);
        //sound = new Sound("game_end.wav");

        rankingPanel = new JPanel(new BorderLayout(10, 10));
        rankingPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        rankingPanel.setBackground(Color.black);

        JPanel rPannel = new JPanel(new GridLayout(2, 3, 5, 5));
        rPannel.setPreferredSize(new Dimension(100, 200));
        //f.setLocation(400,400);
        //JPanel newMenuPannel = new JPanel(new GridLayout(3,1, 5, 5));
        //newMenuPannel.setBorder(new EmptyBorder(10, 10, 10, 10));
        BufferedImage rankImg = ImageIO.read(new File("images\\images.jpg"));
        JLabel ranklable = new JLabel(new ImageIcon(rankImg));
        ranklable.setHorizontalAlignment(SwingConstants.CENTER);
        ranklable.setOpaque(true);

        JButton winButton = new JButton("Wins");
        winButton.setBackground(Color.yellow);

        JButton lossButton = new JButton("Losses");
        lossButton.setBackground(Color.lightGray);

        JButton typeButton = new JButton("Game Type");
        typeButton.setBackground(Color.orange);

        JTextField winField = new JTextField();
        winField.setEditable(false);

        JTextField lossField = new JTextField();
        lossField.setEditable(false);

        JTextField typeField = new JTextField();
        typeField.setEditable(false);
       /* //BufferedImage newImg = ImageIO.read(new File("number of wins"));
        JLabel winlable = new JLabel("number of wins");
        winlable.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel winlable = new JLabel("number of wins");
        winlable.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel winlable = new JLabel("number of wins");
        winlable.setHorizontalAlignment(SwingConstants.CENTER);*/


        rPannel.add(winButton);
        rPannel.add(lossButton);
        rPannel.add(typeButton);
        rPannel.add(typeButton);
        rPannel.add(winField);
        rPannel.add(lossField);
        rPannel.add(typeField);

        rankingPanel.add(ranklable, BorderLayout.NORTH);
        rankingPanel.add(rPannel, BorderLayout.CENTER);
        //settingPanel.add(levelPannel, BorderLayout.SOUTH);

        rankingFrame.add(rankingPanel);
        rankingFrame.setSize(400, 400);
        rankingFrame.setVisible(true);
        rankingFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);



    }
    public  GameState getState(){
        return gameState;
    }
}
