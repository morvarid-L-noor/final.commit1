package com.company.ball;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

public class SettingFrame  {
    GameState gameState;
    public SettingFrame(GameState gameState1 ) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        super();
        gameState = gameState1;
        JFrame frame = new JFrame();
        JPanel p = new JPanel();
        JCheckBox checkbox1 = new JCheckBox("Mute");//e.getStateChange()==1?"checked":"unchecked"
        p.add(checkbox1);
        checkbox1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                gameState.setMute((e.getStateChange()==1?true:false));
            }
        });
        JLabel level = new JLabel();level.setText("Normal/Hard :\n");
        p.add(level);
        JComboBox<GameState.type> c1 = new JComboBox<>(GameState.type.values()); p.add(c1);
        JButton saveBtn = new JButton("Save changes");
        p.add(saveBtn);
        saveBtn.addActionListener(e -> {

        });

        frame.add(p);

    }
    public  GameState getState(){
        return gameState;
    }
}
