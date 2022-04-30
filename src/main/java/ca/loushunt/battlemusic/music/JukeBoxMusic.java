package ca.loushunt.battlemusic.music;

import net.mcjukebox.plugin.bukkit.api.JukeboxAPI;
import net.mcjukebox.plugin.bukkit.api.ResourceType;
import net.mcjukebox.plugin.bukkit.api.models.Media;
import org.bukkit.entity.Player;


public class JukeBoxMusic extends Music{

    private int volume, fade;

    public JukeBoxMusic(String sound,int volume, int fade) {
        super(MusicType.MCJUKEBOX, sound);
        this.volume = volume;
        this.fade = fade;
    }

    @Override
    public void play(Player player) {
        Media media = new Media(ResourceType.MUSIC, getSound());
        media.setFadeDuration(fade);
        media.setVolume(volume);
        JukeboxAPI.play(player, media);
    }

    @Override
    public void stop(Player player) {
        JukeboxAPI.stopMusic(player, "default", fade);
    }

    public int getVolume() {
        return volume;
    }

    public int getFade() {
        return fade;
    }

    public void setFade(int fade) {
        this.fade = fade;
    }
}
