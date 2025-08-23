package ca.loushunt.battlemusic.battle;

import ca.loushunt.battlemusic.BattleMusic;
import ca.loushunt.battlemusic.music.Music;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class BattleManager {
    private static HashMap<Player, Battle> battleList = new HashMap<>();

    public static Battle createBattle(Player player, Entity entity) {
        return createBattle(player, new ArrayList<>(Arrays.asList(new Entity[]{entity})), Music.getMusicFromEntity(entity));
    }

    public static Battle createBattle(Player player, Entity entity, Music music) {
        return createBattle(player, new ArrayList<>(Arrays.asList(entity)), music);
    }

    public static Battle createBattle(Player player, ArrayList<Entity> entities) {
        return createBattle(player, entities, Music.getMusicFromEntity(entities.get(0)));
    }

    public static Battle createBattle(Player player, ArrayList<Entity> entities, Music music) {
        Battle battle = new Battle(player, entities, music);
        if (battle.getMusic() != null) {
            battleList.put(player, battle);
            music.play(player);
        } else {
            return null;
        }
        return battle;
    }

    public static Battle getBattle(Player player) {
        return battleList.get(player);
    }

    public static boolean isPlayerFighting(Player player) {
        return battleList.containsKey(player);
    }

    public static void removeBattle(Player player) {
        getBattle(player).getRunAwayTask().cancel();
        battleList.remove(player);
    }

    public static void stopBattle(Player player) {
        Battle battle = getBattle(player);
        battle.getMusic().stop(player);
        battle.finish();
        removeBattle(player);
        if (BattleMusic.getBattleMusicInstance().getConfig().contains("commands-after-fight"))
            for (String command : BattleMusic.getBattleMusicInstance().getConfig().getStringList("commands-after-fight"))
                Bukkit.dispatchCommand((CommandSender) Bukkit.getServer().getConsoleSender(), command.replace("{player}", player.getName()));
    }

    public static ArrayList<Battle> getBattles() {
        return new ArrayList<>(battleList.values());
    }
}