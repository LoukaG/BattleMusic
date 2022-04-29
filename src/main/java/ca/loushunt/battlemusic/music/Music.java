package ca.loushunt.battlemusic.music;

import org.bukkit.entity.Player;

public abstract class Music {
    public enum MusicType{
        RESSOURCEPACK,
        NOTEBLOCK,
        MCJUKEBOX;
    }

    private MusicType musicType;
    private String sound;
    private Player player;

    public Music(MusicType musicType, String sound, Player player) {
        this.musicType = musicType;
        this.sound = sound;
        this.player = player;
    }

    public MusicType getMusicType() {
        return musicType;
    }

    public String getSound() {
        return sound;
    }

    public Player getPlayer() {
        return player;
    }

    public abstract void play();
    public abstract void stop();
}
