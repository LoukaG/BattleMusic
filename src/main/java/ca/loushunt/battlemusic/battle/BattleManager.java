package ca.loushunt.battlemusic.battle;

import ca.loushunt.battlemusic.BattleMusic;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class BattleManager {
    private static HashMap<Player,Battle> battleList;

    static{
        battleList = new HashMap<>();
    }

    public static Battle createBattle(Player player, Entity entity){
        Battle battle = new Battle(player, entity);
        BattleManager.battleList.put(player, battle);

        return battle;
    }

    public static Battle createBattle(Player player, ArrayList<Entity> entities){
        Battle battle = new Battle(player,entities);
        BattleManager.battleList.put(player,battle);

        return battle;
    }
}
