package ca.loushunt.battlemusic.command;

import ca.loushunt.battlemusic.BattleMusic;
import ca.loushunt.battlemusic.battle.BattleManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BattleMusicToggleCMD implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String name;
        if(args.length == 0 || !sender.hasPermission("battlemusic.disable.other")) {
            if (sender instanceof Player)
                name = ((Player) sender).getName();
            else
                return false;
        }else
            name = args[0];


        ArrayList<String> disablePlayer = new ArrayList<>();
        if(BattleMusic.getBattleMusicInstance().getConfig().contains("disable-player"))
            disablePlayer = new ArrayList<>(BattleMusic.getBattleMusicInstance().getConfig().getStringList("disable-player"));

        if(label.endsWith("-enable")){
            if(disablePlayer.contains(name)){
                disablePlayer.remove(name);

                BattleMusic.getBattleMusicInstance().getConfig().set("disable-player",disablePlayer);
                BattleMusic.getBattleMusicInstance().saveConfig();
            }

            sender.sendMessage(ChatColor.GREEN+"BattleMusic "+ChatColor.WHITE+"has been enable for "+ChatColor.GREEN+name);
        }else if(label.endsWith("-disable")){
            if(Bukkit.getServer().getPlayer(name) != null)
                if(BattleManager.isPlayerFighting(Bukkit.getServer().getPlayer(name)))
                    BattleManager.stopBattle(Bukkit.getServer().getPlayer(name));

            if(!disablePlayer.contains(name)){
                disablePlayer.add(name);

                BattleMusic.getBattleMusicInstance().getConfig().set("disable-player",disablePlayer);
                BattleMusic.getBattleMusicInstance().saveConfig();
            }

            sender.sendMessage(ChatColor.GREEN+"BattleMusic "+ChatColor.WHITE+"has been disable for "+ChatColor.GREEN+name);

        }
        return true;
    }
}
