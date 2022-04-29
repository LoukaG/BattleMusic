package ca.loushunt.battlemusic.battle;

import ca.loushunt.battlemusic.music.Music;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class Battle {
    private Player player;
    private ArrayList<Entity> entities;
    private Music music;

    /**
     * Create Battle Object with one Entity
     * @param player The player
     * @param entity The first entity
     */
    protected Battle(Player player, Entity entity){
        this.player = player;
        this.entities = new ArrayList<>(Arrays.asList(entity));
    }

    /**
     * Create Battle Object with multiple entities
     * @param player The player
     * @param entities The list of entities
     */
    protected Battle(Player player, ArrayList<Entity> entities){
        this.player = player;
        this.entities = entities;
    }

    /**
     * Add a entity to the fight
     * @param entity
     */
    public void addEntity(Entity entity){
        entities.add(entity);
    }

    /**
     * Check if player is fighting with a entity
     * @param entity The entity
     * @return If the player is fighting with the entity
     */
    public boolean containsEntity(Entity entity){
        return entities.contains(entity);
    }

    /**
     * Remove entity from a fight. If it was the last entity, the music will be stop
     * @param entity The entity
     */
    public void removeEntity(Entity entity){
        this.entities.remove(entity);
    }
}
