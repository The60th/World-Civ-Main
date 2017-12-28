package com.worldciv.utility;

import com.worldciv.dungeons.Dungeon;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public final class utilityArrays {
    public final static ArrayList<Player> togglevision = new ArrayList<Player>();
    public final static ArrayList<Player> togglesocialspy = new ArrayList<Player>();
    public final static ArrayList<Player> toggletimber = new ArrayList<Player>();
    public final static ArrayList<Player> toggletimbermessages = new ArrayList<Player>();

    public final static ArrayList<Player> dummytoggleboard = new ArrayList<Player>();
    public final static ArrayList<Player> toggledisplay = new ArrayList<Player>();
    public final static ArrayList<Player> togglevisionmessage = new ArrayList<Player>();
    public final static ArrayList<Player> togglecensor = new ArrayList<Player>(); //the toggle is inversed from the other toggles

    public static ArrayList<Player> currentlyBlinded = new ArrayList<Player>();
    public static ArrayList<Player> holdingLight = new ArrayList<Player>();
    public final static ArrayList<Player> visionteam = new ArrayList<Player>();

    public static ArrayList<Player> globalMute = new ArrayList<Player>();
    public static ArrayList<Player> townyMute = new ArrayList<Player>();


    public final static ArrayList<Player> setnewsmessage = new ArrayList<Player>();
    public final static ArrayList<Player> notreadylist = new ArrayList<Player>();

    public final static ArrayList<Player> visionregion = new ArrayList<Player>();

    public final static ArrayList<String> dungeonregionlist = new ArrayList();
    public final static ArrayList<Dungeon> alldungeons = new ArrayList(); //forgot what this was, dont think i need this?

    public final static ArrayList<Player> lighttutorial = new ArrayList<Player>();

}
