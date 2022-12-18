package ca.loushunt.battlemusic.music;

import ca.loushunt.battlemusic.BattleMusic;
import ca.loushunt.battlemusic.battle.BattleManager;
import ca.loushunt.battlemusic.task.RepeatSoundTask;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class RPMusic extends Music{
    private int duration;
    private RepeatSoundTask repeatSoundTask;

    /**
     * Create a new Ressource Pack Music
     * @param sound The sound string
     */
    public RPMusic(String sound) {
        super(MusicType.RESSOURCEPACK, sound.split(";")[0]);

        int duration = 0;
        if(sound.contains(";"))
            this.duration = Integer.parseInt(sound.split(";")[1]);
    }

    /**
     * Play the Ressource Pack Music to a Player
     * @param player The player
     */
    @Override
    public void play(Player player) {
        try{
            player.playSound(player.getLocation(), Sound.valueOf(getSound()),10000.0f,1.0f);
        }catch(Exception e){
            player.playSound(player.getLocation(), getSound(), 10000.0f, 1.0f);
        }

        if(duration > 0) {
            repeatSoundTask = new RepeatSoundTask(BattleManager.getBattle(player));
            this.repeatSoundTask.runTaskTimer(BattleMusic.getBattleMusicInstance(), duration*20, duration*20);
        }
    }

    /**
     * Stop the Ressource Pack Music to a Player
     * @param player The player to stop the music
     */
    @Override
    public void stop(Player player) {
        try {
            player.stopSound(Sound.valueOf(getSound()));
        } catch (Exception exception) {
            player.stopSound(getSound());
        }

        if(repeatSoundTask != null)
            if(!repeatSoundTask.isCancelled())
                repeatSoundTask.cancel();
    }
}
