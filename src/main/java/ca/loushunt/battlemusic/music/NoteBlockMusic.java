package ca.loushunt.battlemusic.music;

import com.xxmicloxx.NoteBlockAPI.model.RepeatMode;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;

public class NoteBlockMusic extends Music{

    private RadioSongPlayer radioSongPlayer;
    private File soundFile;

    public NoteBlockMusic(String sound, Player player, String folderPath) {
        super(MusicType.NOTEBLOCK, sound, player);
        this.soundFile = new File(folderPath+"/music",sound);
    }

    @Override
    public void play() {
        if(this.soundFile.exists()){
            Song song = NBSDecoder.parse(soundFile);
            radioSongPlayer = new RadioSongPlayer(song);
            radioSongPlayer.addPlayer(getPlayer());
            radioSongPlayer.setRepeatMode(RepeatMode.ALL);
            radioSongPlayer.setPlaying(true);
        }else
            Bukkit.getLogger().info("Failed to load NoteBlockAPI Music "+getSound());
    }

    @Override
    public void stop() {
        radioSongPlayer.destroy();
    }
}
