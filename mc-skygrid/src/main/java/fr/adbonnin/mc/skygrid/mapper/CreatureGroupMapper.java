package fr.adbonnin.mc.skygrid.mapper;

import com.google.common.base.Function;
import fr.adbonnin.mc.skygrid.model.Config;
import fr.adbonnin.mc.skygrid.model.creature.CreatureGroup;
import fr.adbonnin.mc.skygrid.model.creature.RandomCreatureGroup;
import fr.adbonnin.xtra.base.Pair;
import fr.adbonnin.xtra.bukkit.yaml.YamlNode;
import fr.adbonnin.xtra.io.ReaderSplitter;

import java.util.Iterator;
import java.util.Map;

import static com.google.common.collect.Iterators.transform;
import static java.util.Objects.requireNonNull;

public class CreatureGroupMapper {

    private final Config config;

    public CreatureGroupMapper(Config config) {
        this.config = requireNonNull(config);
    }

    public Iterator<Map.Entry<String, CreatureGroup>> mapByName(YamlNode objectNode) {
        return transform(objectNode.fields(), new Function<Map.Entry<String, YamlNode>, Map.Entry<String, CreatureGroup>>() {
            @Override
            public Map.Entry<String, CreatureGroup> apply(Map.Entry<String, YamlNode> input) {
                final RandomCreatureGroup creatureGroup = new RandomCreatureGroup();
                creatureGroup.addCreatureGroups(map(input.getValue()));
                return new Pair<>(input.getKey(), (CreatureGroup) creatureGroup);
            }
        });
    }

    public Iterator<Map.Entry<CreatureGroup, Double>> map(YamlNode arrayNode) {
        return transform(arrayNode.iterator(), new Function<YamlNode, Map.Entry<CreatureGroup, Double>>() {
            @Override
            public Map.Entry<CreatureGroup, Double> apply(YamlNode input) {
                final String text = input.textValue();
                final ReaderSplitter splitter = new ReaderSplitter(text, ":");

                final CreatureGroup creatureGroup = config.findCreatureGroup(splitter.next());
                if (creatureGroup == null) {
                    throw new IllegalArgumentException("creature group can't be found; " +
                            "text: " + text);
                }

                final double weight = splitter.nextAsDouble(config.getDefaultCreatureGroupWeight());
                return new Pair<>(creatureGroup, weight);
            }
        });
    }
}
