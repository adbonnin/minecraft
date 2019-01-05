package fr.adbonnin.mc.skygrid.mapper;

import fr.adbonnin.mc.skygrid.model.Config;
import fr.adbonnin.mc.skygrid.model.block.BlockGroup;
import fr.adbonnin.mc.skygrid.model.chest.ChestItem;
import fr.adbonnin.mc.skygrid.model.chest.ChestItems;
import fr.adbonnin.mc.skygrid.model.chest.ChestQuantity;
import fr.adbonnin.xtra.base.Function;
import fr.adbonnin.xtra.base.Pair;
import fr.adbonnin.xtra.bukkit.yaml.YamlNode;
import fr.adbonnin.xtra.collect.XtraIterators;
import fr.adbonnin.xtra.io.ReaderSplitter;

import java.util.Iterator;
import java.util.Map;

import static fr.adbonnin.xtra.collect.XtraIterators.transform;
import static java.util.Objects.requireNonNull;

public class ChestItemsMapper {

    private final Config config;

    public ChestItemsMapper(Config config) {
        this.config = requireNonNull(config);
    }

    public Iterator<Map.Entry<ChestItems, Double>> map(YamlNode arrayNode) {
        return XtraIterators.transform(arrayNode.iterator(), new Function<YamlNode, Map.Entry<ChestItems, Double>>() {
            @Override
            public Map.Entry<ChestItems, Double> apply(YamlNode value) {
                final String text = value.textValue();
                final ReaderSplitter splitter = new ReaderSplitter(text, ":");

                final ChestItems chestItems = config.findChestItems(splitter.next(null));
                if (chestItems == null) {
                    throw new IllegalArgumentException("chest item can't be found; " +
                            "text: " + text);
                }

                final double weight = splitter.nextAsDouble(config.getDefaultBlockGroupWeight());
                return new Pair<>(chestItems, weight);
            }
        });
    }

    public Iterator<Map.Entry<String, ChestItems>> mapByName(YamlNode objectNode) {
        return transform(objectNode.fields(), new Function<Map.Entry<String, YamlNode>, Map.Entry<String, ChestItems>>() {
            @Override
            public Map.Entry<String, ChestItems> apply(Map.Entry<String, YamlNode> value) {
                final ChestItems chestItems = new ChestItems();

                for (YamlNode yamlNode : value.getValue()) {
                    final String text = yamlNode.textValue();
                    final ReaderSplitter splitter = new ReaderSplitter(text, ":");

                    final BlockGroup blockGroup = config.findBlockGroup(splitter.next(null));
                    if (blockGroup == null) {
                        throw new IllegalArgumentException("block group can't be found; " +
                                "text: " + text);
                    }

                    final int count = splitter.nextAsInt(config.getDefaultChestItemCount());
                    final double weight = splitter.nextAsDouble(config.getDefaultBlockGroupWeight());

                    final ChestItem chestItem = new ChestItem(blockGroup, count);
                    chestItems.addChestItem(chestItem, weight);
                }

                return new Pair<>(value.getKey(), chestItems);
            }
        });
    }

    public Iterator<Map.Entry<ChestQuantity, Double>> mapQuantities(YamlNode arrayNode) {
        return transform(arrayNode.iterator(), new Function<YamlNode, Map.Entry<ChestQuantity, Double>>() {
            @Override
            public Map.Entry<ChestQuantity, Double> apply(YamlNode value) {
                final ReaderSplitter splitter = new ReaderSplitter(value.textValue(), ":");
                final int quantity = splitter.nextAsInt(-1);
                final double weight = splitter.nextAsDouble(-1);
                return new Pair<>(new ChestQuantity(quantity), weight);
            }
        });
    }
}
