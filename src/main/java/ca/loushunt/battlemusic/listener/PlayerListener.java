package ca.loushunt.battlemusic.listener;

import ca.loushunt.battlemusic.BattleMusic;
import ca.loushunt.battlemusic.battle.Battle;
import ca.loushunt.battlemusic.battle.BattleManager;
import org.bukkit.GameMode;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof LivingEntity && event.getEntity() instanceof LivingEntity)
            if(((LivingEntity)event.getEntity()).getHealth()-event.getDamage() > 0)
                if(event.getEntity() instanceof Player || event.getDamager() instanceof Player){
                    Player player = (Player) (event.getEntity() instanceof Player? event.getEntity(): event.getDamager());
                    Entity entity = event.getEntity() instanceof Player? event.getDamager(): event.getEntity();
                    if(!(entity instanceof Player) || !BattleMusic.getBattleMusicInstance().getConfig().getBoolean("ignore-playervsplayer"))
                        if(!BattleMusic.getBattleMusicInstance().getConfig().getStringList("disable-music").contains(entity.getType().toString().toLowerCase()))
                            if(entity instanceof Monster || (entity instanceof Player && !BattleMusic.getBattleMusicInstance().getConfig().getBoolean("ignore-playervsplayer")))
                                if(!(BattleMusic.getBattleMusicInstance().getConfig().getBoolean("ignore-creative") && player.getGameMode() == GameMode.CREATIVE))
                                    if(BattleManager.isPlayerFighting(player)) {
                                        if (!BattleManager.getBattle(player).containsEntity(entity))
                                            BattleManager.getBattle(player).addEntity(entity);
                                        BattleManager.getBattle(player).resetRunAwayTask();
                                    }else
                                        BattleManager.createBattle(player, entity);
                }



    }

    @EventHandler
    public void onTargetChange(EntityTargetLivingEntityEvent event){
        if(event.getEntity() instanceof LivingEntity && event.getEntity() instanceof Monster)
            if(event.getTarget() instanceof Player){
                Player player = (Player) event.getTarget();
                Entity entity = event.getEntity();
                if(!BattleMusic.getBattleMusicInstance().getConfig().getStringList("disable-music").contains(entity.getType().toString().toLowerCase()))
                    if(!(BattleMusic.getBattleMusicInstance().getConfig().getBoolean("ignore-creative") && player.getGameMode() == GameMode.CREATIVE))
                        if(BattleManager.isPlayerFighting(player)) {
                            if (!BattleManager.getBattle(player).containsEntity(entity))
                                BattleManager.getBattle(player).addEntity(entity);
                        }else
                            BattleManager.createBattle(player, entity);
            }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        if(BattleManager.isPlayerFighting(event.getPlayer()))
            BattleManager.stopBattle(event.getPlayer());
    }

    @EventHandler
    public void onGameModeChange(PlayerGameModeChangeEvent event){
        if(BattleManager.isPlayerFighting(event.getPlayer()) && event.getNewGameMode() == GameMode.CREATIVE && BattleMusic.getBattleMusicInstance().getConfig().getBoolean("ignore-creative"))
            BattleManager.stopBattle(event.getPlayer());
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){
        for(Battle battle: BattleManager.getBattles())
            if(battle.containsEntity(event.getEntity()))
                battle.removeEntity(event.getEntity());

    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event){
        for(Battle battle: BattleManager.getBattles())
            if(battle.containsEntity(event.getEntity()))
                battle.removeEntity(event.getEntity());
    }


}
