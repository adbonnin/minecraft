package fr.adbonnin.mc.skygrid.model;

import fr.adbonnin.mc.skygrid.LocationFunction;
import fr.adbonnin.mc.skygrid.XtraSkyGrid;
import fr.adbonnin.mc.skygrid.model.block.BlockGroup;
import fr.adbonnin.mc.skygrid.model.block.RandomBlockGroup;
import fr.adbonnin.mc.skygrid.model.chest.ChestItem;
import fr.adbonnin.mc.skygrid.model.chest.ChestItems;
import fr.adbonnin.mc.skygrid.model.chest.ChestQuantity;
import fr.adbonnin.mc.skygrid.model.creature.CreatureGroup;
import fr.adbonnin.mc.skygrid.model.creature.RandomCreatureGroup;
import fr.adbonnin.xtra.collect.RandomCollection;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class SkyGridWorld {

    public static final int DEFAULT_HEIGHT = 128;

    private int height = DEFAULT_HEIGHT;

    private final RandomBlockGroup blockGroups = new RandomBlockGroup();

    private final RandomCollection<ChestItems> chestItems  = new RandomCollection<>();

    private final RandomCollection<ChestQuantity> chestQuantities = new RandomCollection<>();

    private final RandomCreatureGroup creatureGroup = new RandomCreatureGroup();

    public void setHeight(int height) {
        this.height = height;
    }

    public Material getRandomBlock(Random random) {
        return blockGroups.getRandomBlock(random);
    }

    public void addBlockGroups(Iterator<? extends Map.Entry<? extends BlockGroup, ? extends Number>> blockGroups) {
        this.blockGroups.addBlockGroups(blockGroups);
    }

    public ChestItem getRandomChestItem(Random random) {
        final ChestItems chestItem = chestItems.get(random);
        return chestItem == null ? null : chestItem.getRandomChestItem(random);
    }

    public void addChestItems(Iterator<? extends Map.Entry<? extends ChestItems, ? extends Number>> chestItems) {
        this.chestItems.addAll(chestItems);
    }

    public EntityType getRandomCreature(Random random) {
        return creatureGroup.getRandomCreature(random);
    }

    public void addCreatureGroups(Iterator<? extends Map.Entry<? extends CreatureGroup, ? extends Number>> creatureGroups) {
        creatureGroup.addCreatureGroups(creatureGroups);
    }

    public ChestQuantity getRandomChestQuantity(Random random) {
        return chestQuantities.get(random);
    }

    public void addChestQuantities(Iterator<? extends Map.Entry<? extends ChestQuantity, ? extends Number>> quantities) {
        this.chestQuantities.addAll(quantities);
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
