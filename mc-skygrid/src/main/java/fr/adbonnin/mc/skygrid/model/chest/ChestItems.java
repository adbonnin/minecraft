package fr.adbonnin.mc.skygrid.model.chest;

import fr.adbonnin.xtra.collect.RandomCollection;

import java.util.Random;

import static java.util.Objects.requireNonNull;

public class ChestItems {

    private final RandomCollection<ChestItem> chestItems = new RandomCollection<>();

    public void addChestItem(double weight, ChestItem chestItem) {
        chestItems.add(weight, chestItem);
    }

    public ChestItem getRandomChestItem(Random random) {
        return chestItems.get(random);
    }
}
