package fr.adbonnin.mc.skygrid;

import fr.adbonnin.mc.skygrid.listerner.PreventSandFallWithCactusListener;
import fr.adbonnin.mc.skygrid.mapper.ConfigMapper;
import fr.adbonnin.mc.skygrid.model.Config;
import fr.adbonnin.mc.skygrid.model.SkyGridWorld;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class SkyGrid extends JavaPlugin {

    private Config config = null;

    public Config getSkyGridConfig() {
        return config;
    }

    @Override
    public void onEnable() {
        onEnable(ConfigMapper.INSTANCE.map(getConfig()));
    }

    public void onEnable(Config config) {
        this.config = config;

        if (config.isPreventSandFallWithCactus()) {
            getServer().getPluginManager().registerEvents(new PreventSandFallWithCactusListener(), this);
        }
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        final SkyGridWorld skyGridWorld = config.findWorld(worldName);
        return new SkyGridChunkGenerator(skyGridWorld, this);
    }
}
