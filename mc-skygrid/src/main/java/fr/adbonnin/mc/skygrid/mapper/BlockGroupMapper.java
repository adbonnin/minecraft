package fr.adbonnin.mc.skygrid.mapper;

import fr.adbonnin.mc.skygrid.model.Config;
import fr.adbonnin.mc.skygrid.model.block.BlockGroup;
import fr.adbonnin.mc.skygrid.model.block.BlockGroupContainer;
import fr.adbonnin.mc.skygrid.model.block.RandomBlockGroupContainer;
import fr.adbonnin.xtra.bukkit.yaml.YamlNode;
import fr.adbonnin.xtra.io.ReaderSplitter;

public enum BlockGroupMapper {
    INSTANCE;

    public BlockGroup map(YamlNode blockGroupNode, Config config) {
        final BlockGroupContainer container = new RandomBlockGroupContainer();
        mapBlockGroups(blockGroupNode, container, config);
        return container;
    }

    @SuppressWarnings("Duplicates")
    public void mapBlockGroups(YamlNode blockGroupNode, BlockGroupContainer container, Config config) {
        for (YamlNode blockNode : blockGroupNode) {
            final ReaderSplitter splitter = new ReaderSplitter(blockNode.textValue(), ":");

            final String name = splitter.next(null);
            if (name == null) {
                continue;
            }

            final BlockGroup blockGroup = config.findBlockGroup(name);
            if (blockGroup == null) {
                throw new IllegalArgumentException("block group can't be found; " +
                        "name: " + name);
            }

            final double weight = splitter.nextAsDouble(config.getDefaultBlockGroupWeight());
            container.addBlockGroup(weight, blockGroup);
        }
    }
}
