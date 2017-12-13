package com.worldciv.dungeons;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.worldciv.parties.Party;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.worldciv.the60th.Main.fileSystem;
import static com.worldciv.the60th.Main.getDungeonManager;
import static com.worldciv.the60th.Main.getWorldGuard;
import static com.worldciv.utility.utilityMultimaps.partyid;
import static com.worldciv.utility.utilityStrings.worldciv;

public class Dungeon {

    public String getPartyID() {
        return PartyID;
    }

    public String getDungeonID() {
        return DungeonID;
    }

    public String getDifficulty() {
        return difficulty;
    }

    String PartyID;
    String DungeonID;
    String difficulty;


    public Dungeon(String party_id, String dungeon_id, String difficulty){
        PartyID = party_id;
        DungeonID = dungeon_id;
        this.difficulty = difficulty;

                //spawn all party members to dungeon_id player spawn.
    }

    public void teleportToDungeon(){

        for(String players_in_party : getPlayers(getPartyID())){

            Player player = Bukkit.getPlayer(players_in_party);

            if(fileSystem.getPlayerSpawn(this.getDungeonID()) == null){
player.sendMessage(worldciv + ChatColor.GRAY + " There is no dungeon teleport entry.");
return;
            }

            player.teleport(fileSystem.getPlayerSpawn(this.getDungeonID()));

        }

       // for (String partyids : partyid.)

    }

    public void teleportOutOfDungeon(){

        for(String players_in_party : getPlayers(getPartyID())){

            Player player = Bukkit.getPlayer(players_in_party);

            if(fileSystem.getPlayerEndSpawn(this.getDungeonID()) == null){
                player.sendMessage(worldciv + ChatColor.GRAY + " There is no dungeon teleport exit.");
                return;
            }

            player.teleport(fileSystem.getPlayerEndSpawn(this.getDungeonID()));

        }

        // for (String partyids : partyid.)

    }

    public List<String> getPlayers(String party_id){

        Multimap<String, String> inversepartyid = Multimaps.invertFrom(partyid, ArrayListMultimap.<String, String>create()); //Converts key - value to value - key

        Collection<String> collectionplayers = inversepartyid.get(party_id); //this returns all the players in the party in a collection
        String stringplayers = collectionplayers.toString();

        stringplayers = stringplayers.replace("[", "");
        stringplayers = stringplayers.replace("]", "");

        List<String> arrayplayers = Arrays.asList(stringplayers.split(", "));

        return arrayplayers;

    }

}
