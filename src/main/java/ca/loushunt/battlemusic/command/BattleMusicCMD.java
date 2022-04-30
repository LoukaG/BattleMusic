package ca.loushunt.battlemusic.command;

import ca.loushunt.battlemusic.BattleMusic;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class BattleMusicCMD implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender.hasPermission("battlemusic.command")){
            if(args.length == 0){
                sender.sendMessage(ChatColor.GREEN+"BattleMusic"+ ChatColor.WHITE+" version "+ ChatColor.GREEN+BattleMusic.getBattleMusicInstance().getDescription().getVersion()
                                    +ChatColor.WHITE+" by "+ChatColor.GREEN+"LouShunt");
            }
        }
        return false;
    }
}
