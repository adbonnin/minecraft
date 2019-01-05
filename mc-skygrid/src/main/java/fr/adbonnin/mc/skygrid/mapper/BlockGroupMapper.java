package fr.adbonnin.mc.skygrid.mapper;

import fr.adbonnin.mc.skygrid.model.Config;
import fr.adbonnin.mc.skygrid.model.block.BlockGroup;
import fr.adbonnin.mc.skygrid.model.block.RandomBlockGroup;
import fr.adbonnin.xtra.base.Function;
import fr.adbonnin.xtra.base.Pair;
import fr.adbonnin.xtra.bukkit.yaml.YamlNode;
import fr.adbonnin.xtra.io.ReaderSplitter;
import org.bukkit.Material;

import java.util.Iterator;
import java.util.Map;

import static fr.adbonnin.xtra.collect.XtraIterators.transform;
import static java.util.Objects.requireNonNull;

public class BlockGroupMapper {

    private final Config config;

    public BlockGroupMapper(Config config) {
        this.config = requireNonNull(config);
    }

    public Iterator<Map.Entry<String, BlockGroup>> mapByName(YamlNode objectNode) {
        return transform(objectNode.fields(), new Function<Map.Entry<String, YamlNode>, Map.Entry<String, BlockGroup>>() {
            @Override
            public Map.Entry<String, BlockGroup> apply(Map.Entry<String, YamlNode> value) {
                final RandomBlockGroup blockGroup = new RandomBlockGroup();
                blockGroup.addBlockGroups(map(value.getValue()));
                return new Pair<>(value.getKey(), (BlockGroup) blockGroup);
            }
        });
    }

    public Iterator<Map.Entry<Material, BlockGroup>> mapByMaterial(YamlNode objectNode) {
        return transform(mapByName(objectNode), new Function<Map.Entry<String, BlockGroup>, Map.Entry<Material, BlockGroup>>() {
            @Override
            public Map.Entry<Material, BlockGroup> apply(Map.Entry<String, BlockGroup> value) {

                final String name = value.getKey();
                final Material material = config.findMaterial(name);
                if (material == null) {
                    throw new IllegalArgumentException("material can't be found; " +
                            "name: " + name);
                }

                return new Pair<>(material, value.getValue());
            }
        });
    }

    public Iterator<Map.Entry<BlockGroup, Double>> map(YamlNode arrayNode) {
        return transform(arrayNode.iterator(), new Function<YamlNode, Map.Entry<BlockGroup, Double>>() {
            @Override
            public Map.Entry<BlockGroup, Double> apply(YamlNode textNode) {
                final String text = textNode.textValue();
                final ReaderSplitter splitter = new ReaderSplitter(text, ":");

                final BlockGroup blockGroup = config.findBlockGroup(splitter.next());
                if (blockGroup == null) {
                    throw new IllegalArgumentException("block group can't be found; " +
                            "text: " + text);
                }

                final double weight = splitter.nextAsDouble(config.getDefaultBlockGroupWeight());
                return new Pair<>(blockGroup, weight);
            }
        });
    }
}
