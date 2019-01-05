package fr.adbonnin.mc.skygrid.model.chest;

import fr.adbonnin.xtra.collect.RandomCollection;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class ChestItems {

    private final RandomCollection<ChestItem> chestItems = new RandomCollection<>();

    public void addChestItem(ChestItem chestItem, double weight) {
        this.chestItems.add(chestItem, weight);
    }

    public ChestItem getRandomChestItem(Random random) {
        return chestItems.get(random);
    }
}
