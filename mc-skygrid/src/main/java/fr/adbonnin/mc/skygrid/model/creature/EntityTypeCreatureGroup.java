package fr.adbonnin.mc.skygrid.model.creature;

import org.bukkit.entity.EntityType;

import java.util.Random;

import static java.util.Objects.requireNonNull;

public class EntityTypeCreatureGroup implements CreatureGroup {

    private final EntityType entityType;

    public EntityTypeCreatureGroup(EntityType entityType) {
        this.entityType = requireNonNull(entityType);
    }

    @Override
    public EntityType getRandomCreature(Random random) {
        return entityType;
    }
}
