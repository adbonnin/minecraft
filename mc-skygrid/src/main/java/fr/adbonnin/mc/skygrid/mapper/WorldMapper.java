package fr.adbonnin.mc.skygrid.mapper;

import fr.adbonnin.mc.skygrid.model.Config;
import fr.adbonnin.mc.skygrid.model.SkyGridWorld;
import fr.adbonnin.xtra.bukkit.yaml.YamlNode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WorldMapper {

    private final BlockGroupMapper blockGroupMapper;

    private final CreatureGroupMapper creatureGroupMapper;

    private final ChestItemsMapper chestItemsMapper;

    public WorldMapper(Config config) {
        this.blockGroupMapper = new BlockGroupMapper(config);
        this.creatureGroupMapper = new CreatureGroupMapper(config);
        this.chestItemsMapper = new ChestItemsMapper(config);
    }

    public Map<String, SkyGridWorld> mapByName(YamlNode objectNode) {
        final Map<String, SkyGridWorld> worlds = new HashMap<>();

        final Iterator<Map.Entry<String, YamlNode>> itr = objectNode.fields();
        while (itr.hasNext()) {
            final Map.Entry<String, YamlNode> next = itr.next();

            final String name = next.getKey();
            final SkyGridWorld world = map(next.getValue());
            worlds.put(name, world);
        }

        return worlds;
    }

    public SkyGridWorld map(YamlNode worldNode) {
        final SkyGridWorld world = new SkyGridWorld();
        world.setHeight(worldNode.asInt("height", SkyGridWorld.DEFAULT_HEIGHT));
        world.addBlockGroups(blockGroupMapper.map(worldNode.path("block-groups")));
        world.addChestItems(chestItemsMapper.map(worldNode.path("chest-items")));
        world.addChestQuantities(chestItemsMapper.mapQuantities(worldNode.path("chest-quantities")));
        world.addCreatureGroups(creatureGroupMapper.map(worldNode.path("creature-spawners")));
        return world;
    }
}
