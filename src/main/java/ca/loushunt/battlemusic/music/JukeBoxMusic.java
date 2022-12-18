package ca.loushunt.battlemusic.music;

import net.mcjukebox.plugin.bukkit.api.JukeboxAPI;
import net.mcjukebox.plugin.bukkit.api.ResourceType;
import net.mcjukebox.plugin.bukkit.api.models.Media;
import org.bukkit.entity.Player;


public class JukeBoxMusic extends Music{

    private int volume, fade;

    /**
     * Create a new JukeBoxMusic
     * @param sound The sound
     * @param volume The volume
     * @param fade The fade time
     */
    public JukeBoxMusic(String sound,int volume, int fade) {
        super(MusicType.MCJUKEBOX, sound);
        this.volume = volume;
        this.fade = fade;
    }

    /**
     * Play the JukeBoxMusic to a Player
     * @param player The player to play the music
     */
    @Override
    public void play(Player player) {
        Media media = new Media(ResourceType.MUSIC, getSound());
        media.setFadeDuration(fade);
        media.setVolume(volume);
        JukeboxAPI.play(player, media);
    }

    /**
     * Stop the JukeBoxMusic to a Player
     * @param player The player to stop the music
     */
    @Override
    public void stop(Player player) {
        JukeboxAPI.stopMusic(player, "default", fade);
    }

    /**
     * Get the volume
     * @return The volume
     */
    public int getVolume() {
        return volume;
    }

    /**
     * Get the fade time
     * @return The fade time
     */
    public int getFade() {
        return fade;
    }

    /**
     * Set the Fade time
     * @param fade The new fade
     */
    public void setFade(int fade) {
        this.fade = fade;
    }
}
