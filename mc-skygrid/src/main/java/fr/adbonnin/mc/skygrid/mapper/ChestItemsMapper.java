package fr.adbonnin.mc.skygrid.mapper;

import fr.adbonnin.mc.skygrid.model.Config;
import fr.adbonnin.mc.skygrid.model.block.BlockGroup;
import fr.adbonnin.mc.skygrid.model.chest.ChestItem;
import fr.adbonnin.mc.skygrid.model.chest.ChestItems;
import fr.adbonnin.xtra.bukkit.yaml.YamlNode;
import fr.adbonnin.xtra.io.ReaderSplitter;

public enum ChestItemsMapper {
    INSTANCE;

    public ChestItems map(YamlNode chestItemsNode, Config config) {
        final ChestItems chestItems = new ChestItems();
        mapChestItems(chestItems, chestItemsNode, config);
        return chestItems;
    }

    @SuppressWarnings("Duplicates")
    private void mapChestItems(ChestItems chestItems, YamlNode chestItemsNode, Config config) {
        for (YamlNode chestItemNode : chestItemsNode) {
            final ReaderSplitter splitter = new ReaderSplitter(chestItemNode.textValue(), ":");

            final String name = splitter.next(null);
            if (name == null) {
                continue;
            }

            final BlockGroup blockGroup = config.findBlockGroup(name);
            if (blockGroup == null) {
                throw new IllegalArgumentException("block group can't be found; " +
                        "name: " + name);
            }

            final int count = splitter.nextAsInt(config.getDefaultChestItemCount());
            final double weight = splitter.nextAsDouble(config.getDefaultBlockGroupWeight());

            final ChestItem chestItem = new ChestItem(blockGroup, count);
            chestItems.addChestItem(weight, chestItem);
        }
    }
}
