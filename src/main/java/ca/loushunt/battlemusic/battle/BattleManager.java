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

    public static Battle createBattle(Player player, Entity entity){
        Battle battle = new Battle(player, new ArrayList<>(Arrays.asList(entity)), Music.getMusicFromEntity(entity));
        BattleManager.battleList.put(player, battle);
        return battle;
    }

    public static Battle createBattle(Player player, Entity entity, Music music){
        Battle battle = new Battle(player,new ArrayList<>(Arrays.asList(entity)),music);
        BattleManager.battleList.put(player,battle);

        return battle;
    }

    public static Battle createBattle(Player player, ArrayList<Entity> entities){
        Battle battle = new Battle(player,entities,Music.getMusicFromEntity(entities.get(0)));
        BattleManager.battleList.put(player,battle);
        return battle;
    }

    public static Battle createBattle(Player player, ArrayList<Entity> entities, Music music){
        Battle battle = new Battle(player,entities,music);
        BattleManager.battleList.put(player,battle);

        return battle;
    }

    public static Battle getBattle(Player player){
        return BattleManager.battleList.get(player);
    }

    public static boolean isPlayerFighting(Player player){
        return BattleManager.battleList.containsKey(player);
    }

    public static void removeBattle(Player player){
        BattleManager.getBattle(player).getRunAwayTask().cancel();
        BattleManager.battleList.remove(player);
    }

    public static void stopBattle(Player player){
        BattleManager.getBattle(player).getMusic().stop(player);
        BattleManager.removeBattle(player);

        //Execute command after fight
        if(BattleMusic.getBattleMusicInstance().getConfig().contains("commands-after-fight"))
            for(String command: BattleMusic.getBattleMusicInstance().getConfig().getStringList("commands-after-fight"))
                Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(),command.replace("{player}",player.getName()));
    }

    public static ArrayList<Battle> getBattles(){
        return new ArrayList<Battle>(BattleManager.battleList.values());
    }
}
