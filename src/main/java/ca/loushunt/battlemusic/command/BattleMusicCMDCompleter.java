package ca.loushunt.battlemusic.command;

import ca.loushunt.battlemusic.BattleMusic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BattleMusicCMDCompleter implements TabCompleter {
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> suggestion = new ArrayList<>();
        switch(args.length){
            case 1:
                suggestion = new ArrayList<>(Arrays.asList("reload","add","remove","show","version"));
                break;
            case 2:
                if(args[0].equalsIgnoreCase("add"))
                    suggestion = new ArrayList<>(Arrays.asList("skeleton","spider","wither","wither_skeleton","zombie","ghast","enderman","ender_dragon","creeper","noteblock:","mcjukebox:"));
                else if(args[0].equalsIgnoreCase("remove")) {
                    suggestion = BattleMusic.getBattleMusicInstance().getConfig().getStringList("music.sound");
                    suggestion.addAll(Arrays.asList("skeleton", "spider", "wither", "wither_skeleton", "zombie", "ghast", "enderman", "ender_dragon", "creeper"));
                }
                break;
            case 4:
            case 3:
                if(args[0].equalsIgnoreCase("add"))
                    suggestion = new ArrayList<>(Arrays.asList("noteblock:","mcjukebox"));
                else if(args[0].equalsIgnoreCase("remove")){
                    if(args.length == 3)
                        suggestion = BattleMusic.getBattleMusicInstance().getConfig().getStringList("music."+args[1]+".sound");
                    else
                        suggestion = BattleMusic.getBattleMusicInstance().getConfig().getStringList("music."+args[1].toLowerCase()+"."+args[2]+".sound");
                }
                break;

        }

        return suggestion;
    }
}
