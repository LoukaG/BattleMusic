package ca.loushunt.battlemusic.music;

import ca.loushunt.battlemusic.BattleMusic;
import ca.loushunt.battlemusic.battle.BattleManager;
import ca.loushunt.battlemusic.task.RepeatSoundTask;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class RPMusic extends Music {
    private int duration;
    private RepeatSoundTask repeatSoundTask;

    public RPMusic(String sound) {
        super(MusicType.RESSOURCEPACK, sound.split(";")[0]);

        this.duration = 0;
        if (sound.contains(";")) {
            this.duration = Integer.parseInt(sound.split(";")[1]);
        }
    }

    @Override
    public void play(Player player) {
        try {
            player.playSound(player.getLocation(), Sound.valueOf(getSound()), 10000.0f, 1.0f);
        } catch (Exception e) {
            player.playSound(player.getLocation(), getSound(), 10000.0f, 1.0f);
        }

        if (this.duration > 0) {
            this.repeatSoundTask = new RepeatSoundTask(BattleManager.getBattle(player));
            this.repeatSoundTask.runTaskTimer((Plugin)BattleMusic.getBattleMusicInstance(), (this.duration * 20L), (this.duration * 20L));
        }
    }

    @Override
    public void stop(Player player) {
        try {
            player.stopSound(Sound.valueOf(getSound()));
        } catch (Exception exception) {
            player.stopSound(getSound());
        }

        if (this.repeatSoundTask != null && !this.repeatSoundTask.isCancelled()) this.repeatSoundTask.cancel();
    }
}
