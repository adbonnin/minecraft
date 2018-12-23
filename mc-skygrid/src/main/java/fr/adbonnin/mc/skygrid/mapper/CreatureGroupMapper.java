package fr.adbonnin.mc.skygrid.mapper;

import fr.adbonnin.mc.skygrid.model.Config;
import fr.adbonnin.mc.skygrid.model.block.BlockGroup;
import fr.adbonnin.mc.skygrid.model.creature.CreatureGroup;
import fr.adbonnin.mc.skygrid.model.creature.RandomCreatureGroup;
import fr.adbonnin.xtra.bukkit.yaml.YamlNode;
import fr.adbonnin.xtra.io.ReaderSplitter;

public enum CreatureGroupMapper {
    INSTANCE;

    public CreatureGroup map(YamlNode creatureGroupsNode, Config config) {
        final RandomCreatureGroup creatureGroup = new RandomCreatureGroup();
        map(creatureGroupsNode, creatureGroup, config);
        return creatureGroup;
    }

    @SuppressWarnings("Duplicates")
    public void map(YamlNode creaturesGroupsNode, CreatureGroup container, Config config) {
        for (YamlNode creatureGroupNode : creaturesGroupsNode) {
            final ReaderSplitter splitter = new ReaderSplitter(creatureGroupNode.textValue(), ":");

            final String name = splitter.next(null);
            if (name == null) {
                continue;
            }

            final CreatureGroup found = config.findCreatureGroup(name);
            if (found == null) {
                throw new IllegalArgumentException("creature group can't be found; " +
                        "name: " + name);
            }

            final double weight = splitter.nextAsDouble(config.getDefaultCreatureGroupWeight());
            container.addCreatureGroup(weight, found);
        }
    }
}
