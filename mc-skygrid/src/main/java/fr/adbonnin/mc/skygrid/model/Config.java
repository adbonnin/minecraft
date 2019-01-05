package fr.adbonnin.mc.skygrid.model;

import fr.adbonnin.mc.skygrid.model.block.BlockGroup;
import fr.adbonnin.mc.skygrid.model.block.MaterialBlockGroup;
import fr.adbonnin.mc.skygrid.model.chest.ChestItems;
import fr.adbonnin.mc.skygrid.model.creature.CreatureGroup;
import fr.adbonnin.mc.skygrid.model.creature.EntityTypeCreatureGroup;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static fr.adbonnin.xtra.collect.XtraIterators.putAll;

public class Config {

    private final Map<String, SkyGridWorld> worlds = new HashMap<>();

    private final Map<String, BlockGroup> blockGroups = new HashMap<>();

    private final Map<String, ChestItems> chestItems = new HashMap<>();

    private final Map<Material, BlockGroup> plants = new HashMap<>();

    private final Map<String, CreatureGroup> creatureGroups = new HashMap<>();

    private double defaultBlockGroupWeight;

    private double defaultCreatureGroupWeight;

    private int defaultChestItemCount;

    private boolean preventSandFallWithCactus;

    public SkyGridWorld findWorld(String name) {
        return worlds.get(name);
    }

    public void addWorlds(Map<String, SkyGridWorld> worlds) {
        this.worlds.putAll(worlds);
    }

    public Material findMaterial(String name) {
        return Material.getMaterial(name);
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

    public void addBlockGroup(String name, BlockGroup blockGroup) {
        this.blockGroups.put(name, blockGroup);
    }

    public void addBlockGroups(Iterator<? extends Map.Entry<? extends String, ? extends BlockGroup>> blockGroups) {
        putAll(this.blockGroups, blockGroups);
    }

    public EntityType findEntityType(String name) {
        try {
            return EntityType.valueOf(name);
        }
        catch (IllegalArgumentException e) {
            return null;
        }
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

    public void addCreatureGroups(Iterator<? extends Map.Entry<? extends String, ? extends CreatureGroup>> creatureGroups) {
        putAll(this.creatureGroups, creatureGroups);
    }

    public ChestItems findChestItems(String name) {
        return chestItems.get(name);
    }

    public void addChestItems(Iterator<? extends Map.Entry<? extends String, ? extends ChestItems>> chestItems) {
        putAll(this.chestItems, chestItems);
    }

    public BlockGroup findPlants(Material material) {
        return plants.get(material);
    }

    public void addPlants(Iterator<? extends Map.Entry<Material, BlockGroup>> plants) {
        putAll(this.plants, plants);
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
