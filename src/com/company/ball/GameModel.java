package com.company.ball;

import java.util.ArrayList;

public class GameModel {
    private ArrayList<GameState> gameStates;
    private String userName;

    public GameModel (String username) {
        this.gameStates = new ArrayList<>();
        this.userName = username;
    }

    @Override
    public String toString() {
        return
                "Admin :" +
                        "username='" + userName + '\'' +
                        ", games='" + gameStates + '\'';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<GameState> getGameStates() {
        return gameStates;
    }

    public void addGame(GameState gameState){
        if (!gameStates.contains(gameState)){
            gameStates.add(gameState);
        }
    }
}
