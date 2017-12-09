package com.worldciv.dungeons;

import java.util.ArrayList;

public class DungeonManager {
    private final int MAX_DUNGEON_AMOUNT = 5;
    private int dungeonsInUse = 0;
    public static final Dungeon[] dungeonsList = new Dungeon[5];

    public boolean addDungeonToTracker(Dungeon dungeon){
        if(dungeonsInUse < 5) return false;

        dungeonsList[dungeonsInUse] = dungeon;
        dungeonsInUse += 1;
        return true;
    }
    public DungeonManager(){

    }
}
