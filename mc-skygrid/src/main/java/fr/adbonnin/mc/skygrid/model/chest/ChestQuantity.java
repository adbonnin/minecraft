package fr.adbonnin.mc.skygrid.model.chest;

import java.util.Random;

public class ChestQuantity {

    private final int quantity;

    public ChestQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getRandomCount(Random random) {
        return random.nextInt(quantity) + 1;
    }
}
