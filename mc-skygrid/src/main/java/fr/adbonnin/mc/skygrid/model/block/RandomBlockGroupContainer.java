package fr.adbonnin.mc.skygrid.model.block;

import fr.adbonnin.xtra.collect.RandomCollection;
import org.bukkit.Material;

import java.util.Random;

public class RandomBlockGroupContainer implements BlockGroupContainer {

    private final RandomCollection<BlockGroup> blocks = new RandomCollection<>();

    @Override
    public Material getRandomBlock(Random random) {
        return blocks.get(random).getRandomBlock(random);
    }

    @Override
    public void addBlockGroup(double weight, BlockGroup block) {
        blocks.add(weight, block);
    }
}
