package ca.loushunt.battlemusic.task;

import ca.loushunt.battlemusic.battle.Battle;
import ca.loushunt.battlemusic.battle.BattleManager;
import org.bukkit.entity.Bat;
import org.bukkit.scheduler.BukkitRunnable;

public class RunAwayTask extends BukkitRunnable {

    private Battle battle;

    public RunAwayTask(Battle battle){
        this.battle = battle;
    }

    @Override
    public void run() {
        BattleManager.stopBattle(battle.getPlayer());
    }
}
