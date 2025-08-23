package ca.loushunt.battlemusic.battle;

import ca.loushunt.battlemusic.BattleMusic;
import ca.loushunt.battlemusic.music.Music;
import ca.loushunt.battlemusic.task.RunAwayTask;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;

public class Battle {
    private Player player;

    private ArrayList<Entity> entities;

    private Music music;

    private RunAwayTask runAwayTask;

    private boolean isFinish;

    protected Battle(Player player, ArrayList<Entity> entities, Music music) {
        this.player = player;
        this.entities = entities;
        this.music = music;
        this.isFinish = false;
        if (music == null)
            return;
        int runawayTime = BattleMusic.getBattleMusicInstance().getConfig().getInt("run-away-time");
        this.runAwayTask = new RunAwayTask(this);
        this.runAwayTask.runTaskLater((Plugin)BattleMusic.getBattleMusicInstance(), (runawayTime * 20));
    }

    public void addEntity(Entity entity) {
        this.entities.add(entity);
    }

    public boolean containsEntity(Entity entity) {
        return this.entities.contains(entity);
    }

    public void removeEntity(Entity entity) {
        this.entities.remove(entity);
        if (this.entities.size() == 0)
            BattleManager.stopBattle(this.player);
    }

    public void resetRunAwayTask() {
        int runawayTime = BattleMusic.getBattleMusicInstance().getConfig().getInt("run-away-time");
        this.runAwayTask.cancel();
        this.runAwayTask = new RunAwayTask(this);
        this.runAwayTask.runTaskLater((Plugin)BattleMusic.getBattleMusicInstance(), (runawayTime * 20));
    }

    public Music getMusic() {
        return this.music;
    }

    public Player getPlayer() {
        return this.player;
    }

    public RunAwayTask getRunAwayTask() {
        return this.runAwayTask;
    }

    protected void finish() {
        this.isFinish = true;
    }

    public boolean isFinish() {
        return this.isFinish;
    }
}