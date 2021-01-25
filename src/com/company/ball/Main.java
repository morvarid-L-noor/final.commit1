package com.company.ball;

import com.company.ball.FirstScreen;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        // write your code here
//        Sound sound = new Sound("background.wav");
//        sound.play();

        //new FirstScreen();
        new PauseMenu();
    }
}

