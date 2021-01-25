package com.company.ball;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;


public class LoadGameFrame {
    private JFrame f;
    private JPanel p;
    GameState gameState ;
    private JList<File> gameList;
    public LoadGameFrame() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        super();

        File[] files = GameFile.getFilesInDirectory();
        gameList = new JList<>(files);
        gameList.setBackground(Color.GRAY);
        Border border = BorderFactory.createLineBorder(Color.green, 2);
        gameList.setBorder(border);
        gameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        gameList.setVisibleRowCount(-1);
        gameList.setMaximumSize(new Dimension(130, 100));
        //gameList.setFixedCellWidth(130);
        //gameList.setCellRenderer(new MyCellRenderer());
        gameList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = gameList.locationToIndex(e.getPoint());
                    System.out.println("Item " + index + " is clicked...");
                    GameModel content = GameFile.objectFileReader(files[index]);
                    openExistingGame(content);
                }
            }
        });


       /* String dir = "C:\\Users\\Movarid\\Desktop\\finalProject\\games";
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
        });*/

    }

    /**
     * @param content of game
     */
    public void openExistingGame(GameModel content) {

        //************************************************************************************************************
    }
    public  GameState getState(){
        return gameState;
    }
}
