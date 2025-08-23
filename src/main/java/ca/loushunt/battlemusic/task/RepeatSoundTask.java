package ca.loushunt.battlemusic.task;

import ca.loushunt.battlemusic.battle.Battle;
import org.bukkit.scheduler.BukkitRunnable;

public class RepeatSoundTask extends BukkitRunnable {
    private Battle battle;

    public RepeatSoundTask(Battle battle) {
        this.battle = battle;
    }

    @Override
    public void run() {
        if (this.battle.isFinish()) {
            cancel();
        } else {
            this.battle.getMusic().stop(this.battle.getPlayer());
            this.battle.getMusic().play(this.battle.getPlayer());
        }
    }
}
