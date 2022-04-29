package ca.loushunt.battlemusic;

import org.bstats.bukkit.Metrics;
import org.bukkit.entity.Bat;
import org.bukkit.plugin.java.JavaPlugin;

public final class BattleMusic extends JavaPlugin {

    private static boolean hasJukebox, hasNoteBlockAPI;

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
}
