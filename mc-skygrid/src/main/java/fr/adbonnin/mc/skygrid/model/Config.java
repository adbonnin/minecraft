package fr.adbonnin.mc.skygrid.model;

import fr.adbonnin.mc.skygrid.model.block.BlockGroup;
import fr.adbonnin.mc.skygrid.model.block.MaterialBlockGroup;
import fr.adbonnin.mc.skygrid.model.chest.ChestItems;
import fr.adbonnin.mc.skygrid.model.creature.CreatureGroup;
import fr.adbonnin.mc.skygrid.model.creature.EntityTypeCreatureGroup;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.Map;

public class Config {

    public static final double DEFAULT_BLOCK_GROUP_WEIGHT = 100;

    public static final double DEFAULT_CREATURE_GROUP_WEIGHT = 1;

    public static final int DEFAULT_CHEST_ITEM_COUNT = 1;

    public static final boolean DEFAULT_PREVENT_SAND_FALL_WITH_CACTUS = true;

    private final Map<String, SkyGridWorld> worlds = new HashMap<>();

    private final Map<String, BlockGroup> blockGroups = new HashMap<>();

    private final Map<String, ChestItems> chestItems = new HashMap<>();

    private final Map<Material, BlockGroup> plants = new HashMap<>();

    private final Map<String, CreatureGroup> creatureGroups = new HashMap<>();

    private double defaultBlockGroupWeight = DEFAULT_BLOCK_GROUP_WEIGHT;

    private double defaultCreatureGroupWeight = DEFAULT_CREATURE_GROUP_WEIGHT;

    private int defaultChestItemCount = DEFAULT_CHEST_ITEM_COUNT;

    private boolean preventSandFallWithCactus = DEFAULT_PREVENT_SAND_FALL_WITH_CACTUS;

    public SkyGridWorld findWorld(String name) {
        return worlds.get(name);
    }

    public void addWorld(String name, SkyGridWorld world) {
        this.worlds.put(name, world);
    }

    public BlockGroup findBlockGroup(String name) {

        final BlockGroup blockGroup = this.blockGroups.get(name);
        if (blockGroup != null) {
            return blockGroup;
        }

        final Material material = findMaterial(name);
        if (material != null) {
            final MaterialBlockGroup materialBlockGroup = new MaterialBlockGroup(material);
            addBlockGroup(name, materialBlockGroup);
            return materialBlockGroup;
        }

        return null;
    }

    public Material findMaterial(String name) {
        return Material.getMaterial(name);
    }

    public EntityType findEntityType(String name) {
        try {
            return EntityType.valueOf(name);
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }

    public void addBlockGroup(String name, BlockGroup blockGroup) {
        this.blockGroups.put(name, blockGroup);
    }

    public ChestItems findChestItems(String name) {
        return chestItems.get(name);
    }

    public void addChestItem(String name, ChestItems chestItems) {
        this.chestItems.put(name, chestItems);
    }

    public BlockGroup findPlants(Material material) {
        return plants.get(material);
    }

    public void addPlants(Material material, BlockGroup plant) {
        this.plants.put(material, plant);
    }

    public CreatureGroup findCreatureGroup(String name) {

        final CreatureGroup creatureGroup = creatureGroups.get(name);
        if (creatureGroup != null) {
            return creatureGroup;
        }

        final EntityType entityType = findEntityType(name);
        if (entityType != null) {
            final EntityTypeCreatureGroup entityTypeCreatureGroup = new EntityTypeCreatureGroup(entityType);
            addCreatureGroup(name, entityTypeCreatureGroup);
            return entityTypeCreatureGroup;
        }

        return null;
    }

    public void addCreatureGroup(String name, CreatureGroup creatureGroup) {
        this.creatureGroups.put(name, creatureGroup);
    }

    public double getDefaultBlockGroupWeight() {
        return defaultBlockGroupWeight;
    }

    public void setDefaultBlockGroupWeight(double defaultBlockGroupWeight) {
        this.defaultBlockGroupWeight = defaultBlockGroupWeight;
    }

    public double getDefaultCreatureGroupWeight() {
        return defaultCreatureGroupWeight;
    }

    public void setDefaultCreatureGroupWeight(double defaultCreatureGroupWeight) {
        this.defaultCreatureGroupWeight = defaultCreatureGroupWeight;
    }

    public int getDefaultChestItemCount() {
        return defaultChestItemCount;
    }

    public void setDefaultChestItemCount(int defaultChestItemCount) {
        this.defaultChestItemCount = defaultChestItemCount;
    }

    public boolean isPreventSandFallWithCactus() {
        return preventSandFallWithCactus;
    }

    public void setPreventSandFallWithCactus(boolean preventSandFallWithCactus) {
        this.preventSandFallWithCactus = preventSandFallWithCactus;
    }
}
