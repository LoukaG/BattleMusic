package ca.loushunt.battlemusic.command;

import ca.loushunt.battlemusic.BattleMusic;
import ca.loushunt.battlemusic.music.Music;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BattleMusicCMD implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender.hasPermission("battlemusic.command")) {
            switch (args.length) {
                case 0:
                    sender.sendMessage(ChatColor.GREEN + "BattleMusic" + ChatColor.WHITE + " version " + ChatColor.GREEN + BattleMusic.getBattleMusicInstance().getDescription().getVersion()
                            + ChatColor.WHITE + " by " + ChatColor.GREEN + "LouShunt");

                    return true;
                case 1:
                    switch (args[0].toLowerCase()) {
                        case "reload":
                            BattleMusic.getBattleMusicInstance().reloadConfig();
                            Music.loadMusicList();
                            sender.sendMessage(ChatColor.GREEN + "BattleMusic " + ChatColor.WHITE + "has been " + ChatColor.GREEN + "reloaded");
                            return true;
                        case "version":
                            sender.sendMessage(ChatColor.GREEN + "BattleMusic" + ChatColor.WHITE + " version " + ChatColor.GREEN + BattleMusic.getBattleMusicInstance().getDescription().getVersion()
                                    + ChatColor.WHITE + " by " + ChatColor.GREEN + "LouShunt");
                            return true;
                        case "remove":
                        case "add":
                            sender.sendMessage(ChatColor.GREEN + "/battlemusic " + args[0] + " " + ChatColor.WHITE + ChatColor.BOLD + "<mobs/noteblock:sound/mcjukebox:sound/sound> " + ChatColor.RESET + ChatColor.ITALIC + "[<name/sound>] [<sound>]");
                            return true;
                        case "show":
                            if(BattleMusic.getBattleMusicInstance().getConfig().contains("music.sound")){
                                String message = "The list of "+ChatColor.GREEN+"default sound"+ChatColor.WHITE+" :\n";

                                for(String sound: BattleMusic.getBattleMusicInstance().getConfig().getStringList("music.sound")){
                                    if(sound.startsWith("mcjukebox:"))
                                        message+=ChatColor.GOLD+"-";
                                    else if(sound.startsWith("noteblock:"))
                                        message+=ChatColor.AQUA+"-";
                                    else
                                        message+=ChatColor.BLUE+"-";

                                    message+=sound+"\n";
                                }

                                sender.sendMessage(message);
                            }else
                                sender.sendMessage(ChatColor.RED+"There is no default sound");
                            return true;
                    }
                case 2:
                    if(args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove")) {
                        ArrayList<String> soundList = BattleMusic.getBattleMusicInstance().getConfig().contains("music.sound") ?
                                new ArrayList<>(BattleMusic.getBattleMusicInstance().getConfig().getStringList("music.sound")) :
                                new ArrayList<>();

                        String soundType = args[1].startsWith("noteblock:") ? "NoteBlockAPI" : (args[1].startsWith("mcjukebox") ? "MCJukebox" : "Ressource Pack");
                        if(args[0].equalsIgnoreCase("add")){
                            soundList.add(args[1]);
                            sender.sendMessage("'" + ChatColor.GREEN + args[1] + ChatColor.WHITE + "' has been added to " + ChatColor.GREEN + "default music" + ChatColor.WHITE + " has a " + ChatColor.GREEN + soundType + ChatColor.WHITE + " sound");
                        }else{
                            soundList.remove(args[1]);
                            sender.sendMessage("'" + ChatColor.GREEN + args[1] + ChatColor.WHITE + "' has been removed from " + ChatColor.GREEN + "default music");
                        }
                        if(soundList.size() > 0)
                            BattleMusic.getBattleMusicInstance().getConfig().set("music.sound", soundList);
                        else
                            BattleMusic.getBattleMusicInstance().getConfig().set("music.sound",null);
                        BattleMusic.getBattleMusicInstance().saveConfig();
                        BattleMusic.getBattleMusicInstance().reloadConfig();
                        Music.loadMusicList();

                        return true;
                    }else if(args[0].equalsIgnoreCase("show")){
                        if(BattleMusic.getBattleMusicInstance().getConfig().contains("music."+args[1].toLowerCase()+".sound")){
                            String message = "The list of sound for "+ChatColor.GREEN+args[1]+ChatColor.WHITE+" :\n";

                            for(String sound: BattleMusic.getBattleMusicInstance().getConfig().getStringList("music."+args[1].toLowerCase()+".sound")){
                                if(sound.startsWith("mcjukebox:"))
                                    message+=ChatColor.GOLD+"-";
                                else if(sound.startsWith("noteblock:"))
                                    message+=ChatColor.AQUA+"-";
                                else
                                    message+=ChatColor.BLUE+"-";

                                message+=sound+"\n";
                            }

                            sender.sendMessage(message);
                        }else
                            sender.sendMessage(ChatColor.RED+"There is no sound for "+args[1]);

                        return true;
                    }
                case 3:
                    if(args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove")) {
                        try{
                            EntityType.valueOf(args[1].toUpperCase());
                        }catch(IllegalArgumentException ex){
                            sender.sendMessage(ChatColor.RED+args[1]+" is not a valid entity name");
                            return true;
                        }
                        ArrayList<String> soundList = BattleMusic.getBattleMusicInstance().getConfig().contains("music."+args[1].toLowerCase()+".sound") ?
                                new ArrayList<>(BattleMusic.getBattleMusicInstance().getConfig().getStringList("music."+args[1].toLowerCase()+".sound")) :
                                new ArrayList<>();
                        String soundType = args[2].startsWith("noteblock:") ? "NoteBlockAPI" : (args[1].startsWith("mcjukebox") ? "MCJukebox" : "Ressource Pack");
                        if(args[0].equalsIgnoreCase("add")){
                            soundList.add(args[2]);
                            sender.sendMessage("'" + ChatColor.GREEN + args[2] + ChatColor.WHITE + "' has been added to " + ChatColor.GREEN + args[1] + ChatColor.WHITE + " has a " + ChatColor.GREEN + soundType + ChatColor.WHITE + " sound");
                        }else{
                            soundList.remove(args[2]);
                            sender.sendMessage("'" + ChatColor.GREEN + args[2] + ChatColor.WHITE + "' has been removed from " + ChatColor.GREEN + args[1]);
                        }

                        if(soundList.size() > 0)
                            BattleMusic.getBattleMusicInstance().getConfig().set("music."+args[1].toLowerCase()+".sound", soundList);
                        else
                            BattleMusic.getBattleMusicInstance().getConfig().set("music."+args[1].toLowerCase(),null);
                        BattleMusic.getBattleMusicInstance().saveConfig();
                        BattleMusic.getBattleMusicInstance().reloadConfig();
                        Music.loadMusicList();


                        return true;
                    }else if(args[0].equalsIgnoreCase("show")){
                        if(BattleMusic.getBattleMusicInstance().getConfig().contains("music."+args[1].toLowerCase()+"."+args[2]+".sound")){
                            String message = "The list of sound for "+ChatColor.GREEN+args[1]+ChatColor.WHITE+" named "+ChatColor.GREEN+args[2]+":\n";

                            for(String sound: BattleMusic.getBattleMusicInstance().getConfig().getStringList("music."+args[1].toLowerCase()+"."+args[2]+".sound")){
                                if(sound.startsWith("mcjukebox:"))
                                    message+=ChatColor.GOLD+"-";
                                else if(sound.startsWith("noteblock:"))
                                    message+=ChatColor.AQUA+"-";
                                else
                                    message+=ChatColor.BLUE+"-";

                                message+=sound+"\n";
                            }

                            sender.sendMessage(message);
                        }else
                            sender.sendMessage(ChatColor.RED+"There is no sound for "+args[1]+" named "+args[2]);

                        return true;
                    }
                case 4:
                    if(args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove")) {
                        try{
                            EntityType.valueOf(args[1].toUpperCase());
                        }catch(IllegalArgumentException ex){
                            sender.sendMessage(ChatColor.RED+args[1]+" is not a valid entity name");
                            return true;
                        }
                        ArrayList<String> soundList = BattleMusic.getBattleMusicInstance().getConfig().contains("music."+args[1].toLowerCase()+"."+args[2]+".sound") ?
                                new ArrayList<>(BattleMusic.getBattleMusicInstance().getConfig().getStringList("music."+args[1].toLowerCase()+"."+args[2]+".sound")) :
                                new ArrayList<>();
                        String soundType = args[3].startsWith("noteblock:") ? "NoteBlockAPI" : (args[1].startsWith("mcjukebox") ? "MCJukebox" : "Ressource Pack");
                        if(args[0].equalsIgnoreCase("add")){
                            soundList.add(args[3]);
                            sender.sendMessage("'" + ChatColor.GREEN + args[3] + ChatColor.WHITE + "' has been added to " + ChatColor.GREEN + args[1] + ChatColor.WHITE + " named "+ChatColor.GREEN+args[2]+ChatColor.WHITE+" has a " + ChatColor.GREEN + soundType + ChatColor.WHITE + " sound");
                        }else{
                            soundList.remove(args[3]);
                            sender.sendMessage("'" + ChatColor.GREEN + args[3] + ChatColor.WHITE + "' has been removed from " + ChatColor.GREEN + args[1] + ChatColor.WHITE + " named "+ChatColor.GREEN+args[2]);
                        }

                        if(soundList.size() > 0)
                            BattleMusic.getBattleMusicInstance().getConfig().set("music."+args[1].toLowerCase()+"."+args[2]+".sound", soundList);
                        else
                            BattleMusic.getBattleMusicInstance().getConfig().set("music."+args[1].toLowerCase()+"."+args[2],null);
                        BattleMusic.getBattleMusicInstance().saveConfig();
                        BattleMusic.getBattleMusicInstance().reloadConfig();
                        Music.loadMusicList();

                        return true;
                    }
            }
        }
        return false;
    }
}
