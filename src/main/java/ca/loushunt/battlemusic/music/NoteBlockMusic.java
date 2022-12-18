package ca.loushunt.battlemusic.music;

import com.xxmicloxx.NoteBlockAPI.model.RepeatMode;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;

public class NoteBlockMusic extends Music{

    private HashMap<Player, RadioSongPlayer> radioSongPlayerList;
    private Song song;

    /**
     * Create a new NoteBlockMusic
     * @param sound The sound
     * @param folderPath The folder path
     */
    public NoteBlockMusic(String sound, String folderPath) {
        super(MusicType.NOTEBLOCK, sound);
        File soundFile = new File(folderPath+"\\music\\"+sound);
        if(!soundFile.exists())
            Bukkit.getLogger().warning("[BattleMusic] Failed to load NoteBlockAPI Music "+getSound());
        this.song = NBSDecoder.parse(soundFile);
        this.radioSongPlayerList = new HashMap<Player,RadioSongPlayer>();
    }

    /**
     * Play the NoteBlockMusic music
     * @param player The player to play the music
     */
    @Override
    public void play(Player player) {
        RadioSongPlayer radioSongPlayer = new RadioSongPlayer(song);
        radioSongPlayer.addPlayer(player);
        radioSongPlayer.setRepeatMode(RepeatMode.ALL);
        radioSongPlayer.setPlaying(true);
        radioSongPlayerList.put(player, radioSongPlayer);
    }

    /**
     * Stop the NoteBlockMusic music
     * @param player The player to stop the music
     */
    @Override
    public void stop(Player player) {
        radioSongPlayerList.get(player).destroy();
        radioSongPlayerList.remove(player);
    }
}
