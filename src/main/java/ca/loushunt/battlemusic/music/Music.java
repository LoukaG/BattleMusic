package ca.loushunt.battlemusic.music;

import ca.loushunt.battlemusic.BattleMusic;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public abstract class Music {

    public enum MusicType{
        RESSOURCEPACK,
        NOTEBLOCK,
        MCJUKEBOX;
    }

    private static HashMap<String, ArrayList<Music>> musicList;

    static{
        musicList = new HashMap<>();
    }

    private MusicType musicType;
    private String sound;

    // O B J E C T

    /**
     * Create a music object
     * @param musicType Type of music
     * @param sound The sound of the music
     */
    public Music(MusicType musicType, String sound) {
        this.musicType = musicType;
        this.sound = sound;
    }

    /**
     * Get Music Type (Ressource pack, NoteBlock or MCJukebox)
     * @return The music type
     */
    public MusicType getMusicType() {
        return musicType;
    }

    /**
     * Get the sound string
     * @return The sound string
     */
    public String getSound() {
        return sound;
    }

    /**
     * Play the music to a player
     * @param player The player to play the music
     */
    public abstract void play(Player player);

    /**
     * Stop the music of a player
     * @param player The player to stop the music
     */
    public abstract void stop(Player player);


    public String toString() {
        return "Music{" +
                "musicType=" + musicType +
                ", sound='" + sound + '\'' +
                '}';
    }


    // S T A T I C

    public static void loadMusicList(){
        try{
            loadSubsectionSound("music");
        }catch(Exception ex){
            Bukkit.getLogger().throwing("Music","loadMusicList",ex);
            Bukkit.getLogger().warning("[BattleMusic] Could not load music from config.yml. Disabling BattleMusic...");
            BattleMusic.getBattleMusicInstance().getPluginLoader().disablePlugin(BattleMusic.getBattleMusicInstance());
        }
    }

    private static void loadSubsectionSound(String path){
        FileConfiguration config = BattleMusic.getBattleMusicInstance().getConfig();
        for(String subsection: config.getConfigurationSection(path).getKeys(false)){
            if(subsection.equals("sound")){
                ArrayList<Music> musics = new ArrayList<>();
                for(String sound: config.getStringList(path+".sound")){
                    Music music = Music.getMusic(sound);
                    if(music != null)
                        musics.add(music);
                }

                if(musics.size() > 0)
                    musicList.put(path+"."+subsection, musics);
            }else
                Music.loadSubsectionSound(path+"."+subsection);
        }
    }

    /**
     * Get a music to play from a Entity
     * @param entity The entity
     * @return The music to play with this entity
     */
    public static Music getMusicFromEntity(Entity entity){
        String path = "music.sound";
        String type = entity.getType().toString().toLowerCase();

        if(Music.musicList.containsKey("music."+type+".sound"))
            path = "music."+type+".sound";

        if(Music.musicList.containsKey("music."+type+"."+entity.getCustomName()+".sound"))
            path = "music."+type+"."+entity.getCustomName()+".sound";

        if(Music.musicList.get(path) != null)
            return Music.musicList.get(path).get((int)(Math.random()*Music.musicList.get(path).size()));

        return null;
    }

    /**
     * Construct the music object with the sound name
     * @param sound The sound
     * @return The music object
     */
    public static Music getMusic(String sound){

        if(sound.startsWith("mcjukebox:") && BattleMusic.hasJukebox()){
            //MCJukebox music. Start with "mcjukebox:"
            sound = sound.replace("mcjukebox:", "");

            return new JukeBoxMusic(sound,
                    BattleMusic.getBattleMusicInstance().getConfig().getInt("mcjukebox.volume"),
                    BattleMusic.getBattleMusicInstance().getConfig().getInt("mcjukebox.fade"));
        }else if(sound.startsWith("noteblock:") && BattleMusic.hasNoteBlockAPI()){
            //Noteblock music. Start with "noteblock:"
            sound = sound.replace("noteblock:","");
            sound += sound.endsWith(".nbs")?"":".nbs";

            return new NoteBlockMusic(sound, BattleMusic.getBattleMusicInstance().getDataFolder()+"");
        }
        Pattern p = Pattern.compile("[^a-z0-9/._-]");


        if(p.matcher(sound.split(";")[0]).find()){

            Bukkit.getLogger().warning(sound.startsWith("mcjukebox:")?"[BattleMusic] Could not load sound '"+sound+"' from Minecraft sound. You need MCJukebox to use this sound"
                    :(sound.startsWith("noteblock")?"[BattleMusic] Could not load sound '"+sound+"' from Minecraft sound. You need NoteBlockAPI to use this sound"
                    :"[BattleMusic] Could not load sound '"+sound+"' from Minecraft sound."));
            return null;
        }

        //RessourcePack Music
        return new RPMusic(sound);
    }
}
