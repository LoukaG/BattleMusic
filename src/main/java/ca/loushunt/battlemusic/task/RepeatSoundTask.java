package ca.loushunt.battlemusic.task;

import ca.loushunt.battlemusic.battle.Battle;
import ca.loushunt.battlemusic.battle.BattleManager;
import org.bukkit.scheduler.BukkitRunnable;

public class RepeatSoundTask extends BukkitRunnable {
    private Battle battle;

    /**
     * Create a new RepeatSoundTask
     * @param battle The battle
     */
    public RepeatSoundTask(Battle battle){
        this.battle = battle;
    }

    @Override
    public void run() {
        if(battle.isFinish())
           this.cancel();
        else
            battle.getMusic().play(battle.getPlayer());

    }
}
