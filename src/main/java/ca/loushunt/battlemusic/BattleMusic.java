package ca.loushunt.battlemusic;

import ca.loushunt.battlemusic.command.BattleMusicCMD;
import ca.loushunt.battlemusic.command.BattleMusicCMDCompleter;
import ca.loushunt.battlemusic.listener.PlayerListener;
import ca.loushunt.battlemusic.music.Music;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class BattleMusic extends JavaPlugin {

    private static boolean hasJukebox, hasNoteBlockAPI;
    private static BattleMusic battleMusic;

    static{
        BattleMusic.hasNoteBlockAPI = BattleMusic.hasJukebox = false;
    }

    @Override
    public void onEnable() {
        //Check if MCJukebox is enable on the server
        BattleMusic.hasJukebox = getServer().getPluginManager().getPlugin("MCJukebox") != null;
        if(!BattleMusic.hasJukebox)
            getServer().getLogger().info("Failed to load MCJukebox.");

        //Check if NoteBlockAPI is enable on the server
        BattleMusic.hasNoteBlockAPI = getServer().getPluginManager().getPlugin("NoteBlockAPI") != null;
        if(!BattleMusic.hasNoteBlockAPI)
            getServer().getLogger().info("Failed to load NoteBlockAPI.");

        //Enable BStats. To see the stats go to https://bstats.org/plugin/bukkit/BattleMusic/11061
        Metrics metrics = new Metrics(this, 11061);


        saveDefaultConfig();

        //Create music folder
        File musicFolder = new File(getDataFolder()+"/music/");
        if(!musicFolder.exists())
            musicFolder.mkdir();

        BattleMusic.battleMusic = this;

        Music.loadMusicList();

        //Register listener
        getServer().getPluginManager().registerEvents( new PlayerListener(),this);

        //Register command
        getCommand("battlemusic").setExecutor(new BattleMusicCMD());
        getCommand("battlemusic").setTabCompleter(new BattleMusicCMDCompleter());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static boolean hasJukebox() {
        return hasJukebox;
    }

    public static boolean hasNoteBlockAPI() {
        return hasNoteBlockAPI;
    }

    public static BattleMusic getBattleMusicInstance(){
        return BattleMusic.battleMusic;
    }

}
