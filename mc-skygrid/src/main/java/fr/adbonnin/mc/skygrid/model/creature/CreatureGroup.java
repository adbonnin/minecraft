package fr.adbonnin.mc.skygrid.model.creature;

import org.bukkit.entity.EntityType;

import java.util.Random;

public interface CreatureGroup {

    EntityType getRandomCreature(Random random);

    void addCreatureGroup(double weight, CreatureGroup creatureGroup);
}
