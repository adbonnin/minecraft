package fr.adbonnin.mc.skygrid.model.block;

import org.bukkit.Material;

import java.util.Random;

public interface BlockGroup {

    Material getRandomBlock(Random random);
}
