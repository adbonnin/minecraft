package fr.adbonnin.mc.skygrid.model.block;

import org.bukkit.Material;

import java.util.Random;

import static java.util.Objects.requireNonNull;

public class MaterialBlockGroup implements BlockGroup {

    private final Material material;

    public MaterialBlockGroup(Material material) {
        this.material = requireNonNull(material);
    }

    @Override
    public Material getRandomBlock(Random random) {
        return material;
    }
}
