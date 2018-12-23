package fr.adbonnin.mc.skygrid;

import fr.adbonnin.mc.skygrid.model.SkyGridWorld;
import fr.adbonnin.xtra.bukkit.logging.BukkitLogger;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import static fr.adbonnin.mc.skygrid.XtraSkyGrid.locationToString;
import static java.util.Objects.requireNonNull;

public class SkyGridChunkGenerator extends ChunkGenerator {

    private final SkyGridWorld skyGridWorld;

    private final List<BlockPopulator> populators;

    private final BukkitLogger log;

    public SkyGridChunkGenerator(SkyGridWorld skyGridWorld, SkyGrid plugin) {
        this.skyGridWorld = requireNonNull(skyGridWorld);
        this.populators = Collections.<BlockPopulator>singletonList(new SkyGridBlockPopulator(skyGridWorld, plugin));
        this.log = new BukkitLogger(plugin, SkyGridChunkGenerator.class);
    }

    @Override
    public ChunkData generateChunkData(World world, final Random random, int chunkX, int chunkZ, BiomeGrid biomes) {
        final ChunkData chunk = createChunkData(world);

        skyGridWorld.chunkIteration(new LocationFunction() {
            @Override
            public void apply(int x, int y, int z) {
                final Material material = skyGridWorld.getRandomBlock(random);
                chunk.setBlock(x, y, z, material);
            }
        });

        return chunk;
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        final Location location = new Location(world, 0.5D, skyGridWorld.getHighestBlockY() + 1, 0.5D);
        log.debug("Spawn location has been fixed; " + locationToString(location));
        return location;
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return populators;
    }
}
