package ca.loushunt.battlemusic.battle;

import ca.loushunt.battlemusic.BattleMusic;
import ca.loushunt.battlemusic.music.Music;
import ca.loushunt.battlemusic.task.RunAwayTask;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class Battle {
    private Player player;
    private ArrayList<Entity> entities;
    private Music music;
    private RunAwayTask runAwayTask;

    /**
     * Create Battle Object
     * @param player The player
     * @param entities The entity list
     * @param music The music to be play
     */
    protected Battle(Player player, ArrayList<Entity> entities, Music music){
        this.player = player;
        this.entities = entities;
        this.music = music;
        music.play(player);

        int runawayTime = BattleMusic.getBattleMusicInstance().getConfig().getInt("run-away-time");

        this.runAwayTask = new RunAwayTask(this);
        this.runAwayTask.runTaskLater(BattleMusic.getBattleMusicInstance(), runawayTime*20);
    }

    /**
     * Add a entity to the fight
     * @param entity
     */
    public void addEntity(Entity entity){
        entities.add(entity);
    }

    /**
     * Check if player is fighting with a entity
     * @param entity The entity
     * @return If the player is fighting with the entity
     */
    public boolean containsEntity(Entity entity){
        return entities.contains(entity);
    }

    /**
     * Remove entity from a fight. If it was the last entity, the music will be stop
     * @param entity The entity
     */
    public void removeEntity(Entity entity){
        this.entities.remove(entity);
        if(this.entities.size() == 0)
            BattleManager.stopBattle(player);
    }

    public void resetRunAwayTask(){
        int runawayTime = BattleMusic.getBattleMusicInstance().getConfig().getInt("run-away-time");

        runAwayTask.cancel();
        this.runAwayTask = new RunAwayTask(this);
        this.runAwayTask.runTaskLater(BattleMusic.getBattleMusicInstance(), runawayTime*20);
    }

    public Music getMusic() {
        return music;
    }

    public Player getPlayer() {
        return player;
    }
}
