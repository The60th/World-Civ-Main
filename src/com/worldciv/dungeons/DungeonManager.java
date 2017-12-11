package com.worldciv.dungeons;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.worldciv.utility.utilityMultimaps;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

import static com.worldciv.the60th.Main.*;
import static com.worldciv.utility.utilityArrays.dungeonregionlist;
import static com.worldciv.utility.utilityMultimaps.partyid;

public class DungeonManager {

    public static ArrayList<Dungeon> activedungeons = new ArrayList<Dungeon>();

    public void addDungeon(Dungeon dungeon) {

        if(!isDungeon(dungeon.getDungeonID())){
            activedungeons.add(dungeon);
        }
    }

    public boolean isDungeon(String dungeonid){

        for(Dungeon dungeon : activedungeons){
            if(dungeon.getDungeonID().equalsIgnoreCase(dungeonid)){
                return true;
            }
        }

        return false;
    }

    public Dungeon getDungeon(String partyid) {

        for (Dungeon dungeon : activedungeons) {
            if (dungeon.getPartyID().equalsIgnoreCase(partyid)) {
                //dungeon list does have a dungeon with that party id.
                return dungeon;
            } else {
                //dungeon list does not have a dungeon with that party id.
            }
        }

        return null;
    }

    public Dungeon getDungeon(Player player){

        for (Dungeon dungeon : activedungeons) {

            String party_id = partyid.get(player.getName()).toString();
            party_id =party_id.replace("[", "");
            party_id =party_id.replace("]", "");

            if (dungeon.getPartyID().equalsIgnoreCase(party_id)) {
                //dungeon list does have a dungeon with that party id.
                return dungeon;
            } else {
                //dungeon list does not have a dungeon with that party id.
            }
        }

        return null;
    }



    public ArrayList<String> getAllDungeons() {
        for (Object regionname : getWorldGuard().getRegionManager(Bukkit.getWorld("world")).getRegions().keySet().toArray()) { //CHANGE REGION WORLDS //TODO change this world to dungeonworld

            if (getWorldGuard().getRegionManager(Bukkit.getWorld("world")).getRegion(regionname.toString()).getFlag(dungeon_region) == StateFlag.State.ALLOW) {
                if (!dungeonregionlist.contains(regionname.toString())) {
                    dungeonregionlist.add(regionname.toString());
                }
            }
        }

        Collections.sort(dungeonregionlist);

        return dungeonregionlist;

    }

    public HashMap<String, String> getAllActiveDungeons() {

        HashMap<String, String> hashactives = new HashMap<String, String>();



        for (Dungeon dungeon : activedungeons) { //iterate through all ids in the map

                hashactives.put(dungeon.getDungeonID(), dungeon.getPlayers(dungeon.getPartyID()).toString());

            }

        return  hashactives; //returns nameofdungeon and playersinside
    }

    public DungeonManager(){

    }
}
