package ca.loushunt.battlemusic.music;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class RPMusic extends Music{
    public RPMusic(String sound) {
        super(MusicType.RESSOURCEPACK, sound);
    }

    @Override
    public void play(Player player) {
        try{
            player.playSound(player.getLocation(), Sound.valueOf(getSound()),100.0f,1.0f);
        }catch(Exception e){
            player.playSound(player.getLocation(), getSound(), 100.0f, 1.0f);
        }
    }

    @Override
    public void stop(Player player) {
        try{
            player.stopSound(Sound.valueOf(getSound()));
        }catch(Exception exception){
            player.stopSound(getSound());
        }
    }
}
