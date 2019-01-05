package fr.adbonnin.mc.skygrid.model.block;

import fr.adbonnin.xtra.collect.RandomCollection;
import org.bukkit.Material;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class RandomBlockGroup implements BlockGroup {

    private final RandomCollection<BlockGroup> blocks = new RandomCollection<>();

    @Override
    public Material getRandomBlock(Random random) {
        return blocks.get(random).getRandomBlock(random);
    }

    public void addBlockGroups(Iterator<? extends Map.Entry<? extends BlockGroup, ? extends Number>> blockGroups) {
        blocks.addAll(blockGroups);
    }
}
