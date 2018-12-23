package fr.adbonnin.mc.skygrid;

import fr.adbonnin.mc.skygrid.model.Config;
import fr.adbonnin.mc.skygrid.model.SkyGridWorld;
import fr.adbonnin.mc.skygrid.model.block.BlockGroup;
import fr.adbonnin.mc.skygrid.model.chest.ChestItem;
import fr.adbonnin.mc.skygrid.model.chest.ChestQuantity;
import fr.adbonnin.xtra.bukkit.logging.BukkitLogger;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static fr.adbonnin.mc.skygrid.XtraSkyGrid.blockToString;
import static fr.adbonnin.mc.skygrid.XtraSkyGrid.chunkToString;
import static java.util.Objects.requireNonNull;

public class SkyGridBlockPopulator extends BlockPopulator {

    private final SkyGridWorld skyGridWorld;

    private final BukkitLogger log;

    private final Config config;

    public SkyGridBlockPopulator(SkyGridWorld skyGridWorld, SkyGrid plugin) {
        this.skyGridWorld = requireNonNull(skyGridWorld);
        this.log = new BukkitLogger(plugin, SkyGridBlockPopulator.class);
        this.config = plugin.getSkyGridConfig();
    }

    @Override
    public void populate(World world, final Random random, final Chunk chunk) {
        skyGridWorld.chunkIteration(new LocationFunction() {
            @Override
            public void apply(int x, int y, int z) {
                final Block block = chunk.getBlock(x, y, z);
                populate(block, random);
            }
        });
    }

    public void populate(Block block, Random random) {

        if (block == null) {
            return;
        }

        final Material type = block.getType();

        if (Material.CHEST.equals(type)) {
            populateChest(block, random);
        }
        else if (Material.SPAWNER.equals(type)) {
            populateSpawner(block, random);
        }
        else {
            final BlockGroup plants = config.findPlants(type);
            if (plants != null) {
                populatePlant(block, plants, random);
            }
        }
    }

    public void populateChest(Block block, Random random) {
        final Chest chest = (Chest) block.getState();

        final ChestQuantity chestQuantity = skyGridWorld.getRandomChestQuantity(random);
        if (chestQuantity == null) {
            return;
        }

        final Map<String, Integer> result = new HashMap<>();

        final int quantity = chestQuantity.getRandomCount(random);
        final ItemStack[] items = new ItemStack[quantity];

        for (int i = 0; i < quantity; i++) {

            final ChestItem chestItem = skyGridWorld.getRandomChestItem(random);
            if (chestItem == null) {
                continue;
            }

            final Material material = chestItem.getRandomBlock(random);
            final int count = chestItem.getRandomCount(random);
            items[i] = new ItemStack(material, count);

            final String materialName = material.name();
            final Integer total = result.get(materialName);
            result.put(materialName, (total == null ? 0 : total) + count);
        }

        chest.getInventory().addItem(items);
        log.debug("Chest has been populated; " +
                "chunk: " + chunkToString(block.getChunk()) + ", " +
                "block: " + blockToString(block) + ", " +
                "with: " + result);
    }

    public void populateSpawner(Block block, Random random) {
        final CreatureSpawner spawner = (CreatureSpawner) block.getState();

        final EntityType creature = skyGridWorld.getRandomCreature(random);
        if (creature == null) {
            return;
        }

        spawner.setSpawnedType(creature);
        log.debug("Creature spawner has been populated; " +
                "chunk: " + chunkToString(block.getChunk()) + ", " +
                "block: " + blockToString(block) + ", " +
                "with: " + creature.name());
    }

    public void populatePlant(Block block, BlockGroup plants, Random random) {

        final Material plant = plants.getRandomBlock(random);
        if (plant == null || Material.AIR.equals(plant)) {
            return;
        }

        final Block up = block.getRelative(BlockFace.UP);
        if (!up.isEmpty()) {
            return;
        }

        up.setType(plant, false);
        log.debug("Block has been planted; " +
                "chunk: " + chunkToString(block.getChunk()) + ", " +
                "block: " + blockToString(block) + ", " +
                "plant: " + plant.name());
    }
}
