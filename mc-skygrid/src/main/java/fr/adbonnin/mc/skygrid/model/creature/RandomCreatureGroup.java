package fr.adbonnin.mc.skygrid.model.creature;

import fr.adbonnin.xtra.collect.RandomCollection;
import org.bukkit.entity.EntityType;

import java.util.Random;

public class RandomCreatureGroup implements CreatureGroup {

    private final RandomCollection<CreatureGroup> creatures = new RandomCollection<>();

    @Override
    public EntityType getRandomCreature(Random random) {
        return creatures.get(random).getRandomCreature(random);
    }

    public void addCreatureGroup(double weight, CreatureGroup creatureGroup) {
        creatures.add(weight, creatureGroup);
    }
}
