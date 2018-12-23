package fr.adbonnin.mc.skygrid.mapper;

import fr.adbonnin.mc.skygrid.model.Config;
import fr.adbonnin.mc.skygrid.model.SkyGridWorld;
import fr.adbonnin.mc.skygrid.model.chest.ChestItems;
import fr.adbonnin.mc.skygrid.model.chest.ChestQuantity;
import fr.adbonnin.xtra.bukkit.yaml.YamlNode;
import fr.adbonnin.xtra.io.ReaderSplitter;

public enum WorldMapper {
    INSTANCE;

    public SkyGridWorld map(YamlNode worldNode, Config config) {
        final SkyGridWorld world = new SkyGridWorld();
        world.setHeight(worldNode.get("height").intValue());
        mapBlockGroups(worldNode.get("block-groups"), world, config);
        mapChestItems(worldNode.get("chest-items"), world, config);
        mapChestQuantities(worldNode.get("chest-quantities"), world);
        mapCreatureSpawners(worldNode.get("creature-spawners"), world, config);
        return world;
    }

    private void mapBlockGroups(YamlNode blockGroupsNode, SkyGridWorld world, Config config) {
        BlockGroupMapper.INSTANCE.mapBlockGroups(blockGroupsNode, world, config);
    }

    @SuppressWarnings("Duplicates")
    private void mapChestItems(YamlNode chestItemsNode, SkyGridWorld world, Config config) {
        for (YamlNode chestItemNode : chestItemsNode) {
            final ReaderSplitter splitter = new ReaderSplitter(chestItemNode.textValue(), ":");

            final String name = splitter.next(null);
            if (name == null) {
                continue;
            }

            final ChestItems chestItems = config.findChestItems(name);
            if (chestItems == null) {
                throw new IllegalArgumentException("chest item can't be found; " +
                        "name: " + name);
            }

            final double weight = splitter.nextAsDouble(config.getDefaultBlockGroupWeight());
            world.addChestItems(weight, chestItems);
        }
    }

    private void mapChestQuantities(YamlNode quantitiesNode, SkyGridWorld world) {
        for (YamlNode quantityNode : quantitiesNode) {
            final ReaderSplitter splitter = new ReaderSplitter(quantityNode.textValue(), ":");
            final int quantity = splitter.nextAsInt(-1);
            final int weight = splitter.nextAsInt(-1);

            if (quantity > 0 && weight > 0) {
                world.addChestQuantity(weight, new ChestQuantity(quantity));
            }
        }
    }

    private void mapCreatureSpawners(YamlNode creatureSpawnersNode, SkyGridWorld world, Config config) {
        CreatureGroupMapper.INSTANCE.map(creatureSpawnersNode, world, config);
    }
}
