package ca.loushunt.battlemusic.battle;

import ca.loushunt.battlemusic.BattleMusic;
import ca.loushunt.battlemusic.music.Music;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class BattleManager {
    private static HashMap<Player,Battle> battleList;

    static{
        battleList = new HashMap<>();
    }

    /**
     * Create a new battle with a entity
     * @param player The player
     * @param entity The entity
     * @return The battle
     */
    public static Battle createBattle(Player player, Entity entity){
        return createBattle(player, new ArrayList<>(Arrays.asList(entity)), Music.getMusicFromEntity(entity));
    }

    /**
     * Create a new battle with a entity and a music
     * @param player The player
     * @param entity The entity
     * @param music The music
     * @return The battle
     */
    public static Battle createBattle(Player player, Entity entity, Music music){
        return createBattle(player, new ArrayList<>(Arrays.asList(entity)), music);
    }

    /**
     * Create a new battle with a list of entity
     * @param player The player
     * @param entities The entity list
     * @return The battle
     */
    public static Battle createBattle(Player player, ArrayList<Entity> entities){
        return createBattle(player, entities, Music.getMusicFromEntity(entities.get(0)));
    }

    /**
     * Create a new battle with a list of entity and a music
     * @param player The player
     * @param entities The entity list
     * @param music The music
     * @return The battle
     */
    public static Battle createBattle(Player player, ArrayList<Entity> entities, Music music){
        Battle battle = new Battle(player,entities,music);
        if(battle.getMusic() != null) {
            BattleManager.battleList.put(player, battle);
            music.play(player);
        }else
            return null;

        return battle;
    }


    /**
     * Get the battle of a player
     * @param player The player
     * @return The battle
     */
    public static Battle getBattle(Player player){
        return BattleManager.battleList.get(player);
    }

    /**
     * Check if a player is currently in a battle
     * @param player The player
     * @return If the player is in a battle
     */
    public static boolean isPlayerFighting(Player player){
        return BattleManager.battleList.containsKey(player);
    }

    /**
     * Remove a player from the battle list
     * @param player The player
     */
    public static void removeBattle(Player player){
        BattleManager.getBattle(player).getRunAwayTask().cancel();
        BattleManager.battleList.remove(player);
    }

    /**
     * Stop the battle from a player
     * @param player The player who is fighting
     */
    public static void stopBattle(Player player){
        final Battle battle = BattleManager.getBattle(player);
        battle.getMusic().stop(player);
        battle.finish();
        BattleManager.removeBattle(player);

        //Execute command after fight
        if(BattleMusic.getBattleMusicInstance().getConfig().contains("commands-after-fight"))
            for(String command: BattleMusic.getBattleMusicInstance().getConfig().getStringList("commands-after-fight"))
                Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(),command.replace("{player}",player.getName()));
    }

    /**
     * Get the list of all the battles
     * @return The list of all the battles
     */
    public static ArrayList<Battle> getBattles(){
        return new ArrayList<Battle>(BattleManager.battleList.values());
    }
}
