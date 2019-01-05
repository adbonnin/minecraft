package fr.adbonnin.mc.skygrid.model.creature;

import fr.adbonnin.xtra.collect.RandomCollection;
import org.bukkit.entity.EntityType;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class RandomCreatureGroup implements CreatureGroup {

    private final RandomCollection<CreatureGroup> creatures = new RandomCollection<>();

    @Override
    public EntityType getRandomCreature(Random random) {
        return creatures.get(random).getRandomCreature(random);
    }

    public void addCreatureGroups(Iterator<? extends Map.Entry<? extends CreatureGroup, ? extends Number>> creatureGroups) {
        creatures.addAll(creatureGroups);
    }
}
