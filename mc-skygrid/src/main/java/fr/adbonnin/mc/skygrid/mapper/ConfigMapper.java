package fr.adbonnin.mc.skygrid.mapper;

import fr.adbonnin.mc.skygrid.model.Config;
import fr.adbonnin.xtra.bukkit.yaml.YamlNode;
import fr.adbonnin.xtra.bukkit.yaml.node.ObjectNode;
import org.bukkit.configuration.ConfigurationSection;

public class ConfigMapper {

    public static final double DEFAULT_BLOCK_GROUP_WEIGHT = 100;

    public static final double DEFAULT_CREATURE_GROUP_WEIGHT = 1;

    public static final int DEFAULT_CHEST_ITEM_COUNT = 1;

    public static final boolean DEFAULT_PREVENT_SAND_FALL_WITH_CACTUS = true;

    public Config map(ConfigurationSection yaml) {
        return map(new ObjectNode(yaml));
    }

    public Config map(YamlNode configNode) {
        final Config config = new Config();

        final YamlNode settingsNode = configNode.path("settings");
        config.setDefaultBlockGroupWeight(settingsNode.asDouble("default-block-group-weight", DEFAULT_BLOCK_GROUP_WEIGHT));
        config.setDefaultCreatureGroupWeight(settingsNode.asDouble("default-creature-group-weight", DEFAULT_CREATURE_GROUP_WEIGHT));
        config.setDefaultChestItemCount(settingsNode.asInt("default-chest-item-count", DEFAULT_CHEST_ITEM_COUNT));
        config.setPreventSandFallWithCactus(settingsNode.asBoolean("prevent-sand-fall-with-cactus", DEFAULT_PREVENT_SAND_FALL_WITH_CACTUS));

        final BlockGroupMapper blockGroupMapper = new BlockGroupMapper(config);
        final ChestItemsMapper chestItemsMapper = new ChestItemsMapper(config);
        final CreatureGroupMapper creatureGroupMapper = new CreatureGroupMapper(config);
        final WorldMapper worldMapper = new WorldMapper(config);

        config.addBlockGroups(blockGroupMapper.mapByName(configNode.path("block-groups")));
        config.addPlants(blockGroupMapper.mapByMaterial(configNode.path("plants")));
        config.addChestItems(chestItemsMapper.mapByName(configNode.path("chest-items")));
        config.addCreatureGroups(creatureGroupMapper.mapByName(configNode.path("creature-groups")));
        config.addWorlds(worldMapper.mapByName(configNode.path("worlds")));
        return config;
    }
}
