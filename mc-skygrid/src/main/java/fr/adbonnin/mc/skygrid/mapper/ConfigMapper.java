package fr.adbonnin.mc.skygrid.mapper;

import fr.adbonnin.mc.skygrid.model.Config;
import fr.adbonnin.mc.skygrid.model.SkyGridWorld;
import fr.adbonnin.mc.skygrid.model.block.BlockGroup;
import fr.adbonnin.mc.skygrid.model.chest.ChestItems;
import fr.adbonnin.mc.skygrid.model.creature.CreatureGroup;
import fr.adbonnin.xtra.bukkit.yaml.ObjectNode;
import fr.adbonnin.xtra.bukkit.yaml.YamlNode;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Iterator;
import java.util.Map;

import static fr.adbonnin.mc.skygrid.model.Config.*;

public enum ConfigMapper {
    INSTANCE;

    public Config map(ConfigurationSection config) {
        return map(new ObjectNode(config));
    }

    public Config map(YamlNode configNode) {
        final Config config = new Config();
        mapSettings(configNode.get("settings"), config);
        mapBlockGroups(configNode.get("block-groups"), config);
        mapChestItems(configNode.get("chest-items"), config);
        mapPlants(configNode.get("plants"), config);
        mapCreatureGroups(configNode.get("creature-groups"), config);
        mapWorlds(configNode.get("worlds"), config);
        return config;
    }

    private void mapSettings(YamlNode settingsNode, Config config) {
        config.setDefaultBlockGroupWeight(settingsNode.get("default-block-group-weight").asDouble(DEFAULT_BLOCK_GROUP_WEIGHT));
        config.setDefaultCreatureGroupWeight(settingsNode.get("default-creature-group-weight").asDouble(DEFAULT_CREATURE_GROUP_WEIGHT));
        config.setDefaultChestItemCount(settingsNode.get("default-chest-item-count").asInt(DEFAULT_CHEST_ITEM_COUNT));
        config.setPreventSandFallWithCactus(settingsNode.get("prevent-sand-fall-with-cactus").asBoolean(DEFAULT_PREVENT_SAND_FALL_WITH_CACTUS));
    }

    private void mapBlockGroups(YamlNode blockGroupsNode, Config config) {
        final Iterator<Map.Entry<String, YamlNode>> itr = blockGroupsNode.fields();
        while (itr.hasNext()) {
            final Map.Entry<String, YamlNode> next = itr.next();

            final String name = next.getKey();
            final BlockGroup blockGroup = BlockGroupMapper.INSTANCE.map(next.getValue(), config);
            config.addBlockGroup(name, blockGroup);
        }
    }

    private void mapChestItems(YamlNode chestItemsNode, Config config) {
        final Iterator<Map.Entry<String, YamlNode>> itr = chestItemsNode.fields();
        while (itr.hasNext()) {
            final Map.Entry<String, YamlNode> next = itr.next();

            final String name = next.getKey();
            final ChestItems chestItems = ChestItemsMapper.INSTANCE.map(next.getValue(), config);
            config.addChestItem(name, chestItems);
        }
    }

    private void mapPlants(YamlNode plantsNode, Config config) {
        final Iterator<Map.Entry<String, YamlNode>> itr = plantsNode.fields();
        while (itr.hasNext()) {
            final Map.Entry<String, YamlNode> next = itr.next();

            final String name = next.getKey();
            final Material material = config.findMaterial(name);
            if (material == null) {
                throw new IllegalArgumentException("material can't be found; " +
                        "name: " + name);
            }

            final BlockGroup plants = BlockGroupMapper.INSTANCE.map(next.getValue(), config);
            config.addPlants(material, plants);
        }
    }

    private void mapCreatureGroups(YamlNode creatureGroupsNode, Config config) {
        final Iterator<Map.Entry<String, YamlNode>> itr = creatureGroupsNode.fields();
        while (itr.hasNext()) {
            final Map.Entry<String, YamlNode> next = itr.next();

            final String name = next.getKey();
            final CreatureGroup creatureGroup = CreatureGroupMapper.INSTANCE.map(next.getValue(), config);
            config.addCreatureGroup(name, creatureGroup);
        }
    }

    private void mapWorlds(YamlNode worldsNode, Config config) {
        final Iterator<Map.Entry<String, YamlNode>> itr = worldsNode.fields();
        while (itr.hasNext()) {
            final Map.Entry<String, YamlNode> next = itr.next();

            final String name = next.getKey();
            final SkyGridWorld world = WorldMapper.INSTANCE.map(next.getValue(), config);
            config.addWorld(name, world);
        }
    }
}
