package fr.adbonnin.mc.skygrid.model;

import fr.adbonnin.mc.skygrid.LocationFunction;
import fr.adbonnin.mc.skygrid.XtraSkyGrid;
import fr.adbonnin.mc.skygrid.model.block.BlockGroup;
import fr.adbonnin.mc.skygrid.model.block.BlockGroupContainer;
import fr.adbonnin.mc.skygrid.model.chest.ChestItem;
import fr.adbonnin.mc.skygrid.model.chest.ChestItems;
import fr.adbonnin.mc.skygrid.model.chest.ChestQuantity;
import fr.adbonnin.mc.skygrid.model.creature.CreatureGroup;
import fr.adbonnin.mc.skygrid.model.creature.RandomCreatureGroup;
import fr.adbonnin.xtra.collect.RandomCollection;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.Random;

public class SkyGridWorld implements BlockGroupContainer, CreatureGroup {

    private int height;

    private final RandomCollection<BlockGroup> blockGroups = new RandomCollection<>();

    private final RandomCollection<ChestItems> chestItems  = new RandomCollection<>();

    private final RandomCollection<ChestQuantity> chestQuantities = new RandomCollection<>();

    private final RandomCreatureGroup randomCreatureGroup = new RandomCreatureGroup();

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public Material getRandomBlock(Random random) {
        final BlockGroup blockGroup = blockGroups.get(random);
        return blockGroup == null ? null : blockGroup.getRandomBlock(random);
    }

    @Override
    public void addBlockGroup(double weight, BlockGroup block) {
        this.blockGroups.add(weight, block);
    }

    public ChestItem getRandomChestItem(Random random) {
        final ChestItems chestItem = chestItems.get(random);
        return chestItem == null ? null : chestItem.getRandomChestItem(random);
    }

    public void addChestItems(double weight, ChestItems chestItems) {
        this.chestItems.add(weight, chestItems);
    }

    @Override
    public EntityType getRandomCreature(Random random) {
        return randomCreatureGroup.getRandomCreature(random);
    }

    @Override
    public void addCreatureGroup(double weight, CreatureGroup creatureGroup) {
        randomCreatureGroup.addCreatureGroup(weight, creatureGroup);
    }

    public ChestQuantity getRandomChestQuantity(Random random) {
        return chestQuantities.get(random);
    }

    public void addChestQuantity(double weight, ChestQuantity chestQuantity) {
        this.chestQuantities.add(weight, chestQuantity);
    }

    public int getHighestBlockY() {
        final int heightIndex = height - 1;
        final int rest = heightIndex % XtraSkyGrid.BLOCK_SPACE;
        return heightIndex - rest;
    }

    public void chunkIteration(LocationFunction locationFunction) {
        XtraSkyGrid.chunkIteration(getHighestBlockY() + 1, locationFunction);
    }
}
