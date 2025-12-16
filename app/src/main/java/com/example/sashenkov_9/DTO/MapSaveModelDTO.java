package com.example.sashenkov_9.DTO;

import java.io.Serializable;

public class MapSaveModelDTO implements Serializable {

    private int id;
    private int mapId;
    private String mapName;
    private int gamesSum;
    private int wins;
    private int loses;
    private int openedTiles;
    private int openedNumberTiles;
    private int openedBlankTiles;
    private int flagsSum;
    private int flagsOnBombs;
    private int timeSpentSum;
    private String lastGameData;
    private int lastGameTime;

    public MapSaveModelDTO() {
    }

    public MapSaveModelDTO(
            int id,
            int mapId,
            String mapName,
            int gamesSum,
            int wins,
            int loses,
            int openedTiles,
            int openedNumberTiles,
            int openedBlankTiles,
            int flagsSum,
            int flagsOnBombs,
            int timeSpentSum,
            String lastGameData,
            int lastGameTime
    ) {
        this.id = id;
        this.mapId = mapId;
        this.mapName = mapName;
        this.gamesSum = gamesSum;
        this.wins = wins;
        this.loses = loses;
        this.openedTiles = openedTiles;
        this.openedNumberTiles = openedNumberTiles;
        this.openedBlankTiles = openedBlankTiles;
        this.flagsSum = flagsSum;
        this.flagsOnBombs = flagsOnBombs;
        this.timeSpentSum = timeSpentSum;
        this.lastGameData = lastGameData;
        this.lastGameTime = lastGameTime;
    }

    public int getId() { return id; }
    public int getMapId() { return mapId; }
    public String getMapName() { return mapName; }
    public int getGamesSum() { return gamesSum; }
    public int getWins() { return wins; }
    public int getLoses() { return loses; }
    public int getOpenedTiles() { return openedTiles; }
    public int getOpenedNumberTiles() { return openedNumberTiles; }
    public int getOpenedBlankTiles() { return openedBlankTiles; }
    public int getFlagsSum() { return flagsSum; }
    public int getFlagsOnBombs() { return flagsOnBombs; }
    public int getTimeSpentSum() { return timeSpentSum; }
    public String getLastGameData() { return lastGameData; }
    public int getLastGameTime() { return lastGameTime; }

    public void setId(int id) { this.id = id; }
    public void setMapId(int mapId) { this.mapId = mapId; }
    public void setMapName(String mapName) { this.mapName = mapName; }
    public void setGamesSum(int gamesSum) { this.gamesSum = gamesSum; }
    public void setWins(int wins) { this.wins = wins; }
    public void setLoses(int loses) { this.loses = loses; }
    public void setOpenedTiles(int openedTiles) { this.openedTiles = openedTiles; }
    public void setOpenedNumberTiles(int openedNumberTiles) { this.openedNumberTiles = openedNumberTiles; }
    public void setOpenedBlankTiles(int openedBlankTiles) { this.openedBlankTiles = openedBlankTiles; }
    public void setFlagsSum(int flagsSum) { this.flagsSum = flagsSum; }
    public void setFlagsOnBombs(int flagsOnBombs) { this.flagsOnBombs = flagsOnBombs; }
    public void setTimeSpentSum(int timeSpentSum) { this.timeSpentSum = timeSpentSum; }
    public void setLastGameData(String lastGameData) { this.lastGameData = lastGameData; }
    public void setLastGameTime(int lastGameTime) { this.lastGameTime = lastGameTime; }
}
