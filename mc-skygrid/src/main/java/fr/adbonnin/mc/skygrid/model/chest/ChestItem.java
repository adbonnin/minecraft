package fr.adbonnin.mc.skygrid.model.chest;

import fr.adbonnin.mc.skygrid.model.block.BlockGroup;
import org.bukkit.Material;

import java.util.Random;

import static java.util.Objects.requireNonNull;

public class ChestItem implements BlockGroup {

    private final BlockGroup blockGroup;

    private final int count;

    public ChestItem(BlockGroup blockGroup, int count) {
        this.blockGroup = requireNonNull(blockGroup);
        this.count = count;
    }

    @Override
    public Material getRandomBlock(Random random) {
        return blockGroup.getRandomBlock(random);
    }

    public int getRandomCount(Random random) {
        return count <= 1 ? 1 : random.nextInt(count) + 1;
    }
}
