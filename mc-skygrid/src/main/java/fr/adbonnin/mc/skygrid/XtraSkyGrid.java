package fr.adbonnin.mc.skygrid;

import static fr.adbonnin.xtra.bukkit.XtraMinecraft.CHUNK_SIZE;

public final class XtraSkyGrid {

    public static final int BLOCK_SPACE = 4;

    public static void chunkIteration(int height, LocationFunction locationFunction) {
        for (int y = 0; y < height; y += BLOCK_SPACE) {
            for (int x = 0; x < CHUNK_SIZE; x += BLOCK_SPACE) {
                for (int z = 0; z < CHUNK_SIZE; z += BLOCK_SPACE) {
                    locationFunction.apply(x, y, z);
                }
            }
        }
    }

    private XtraSkyGrid() { /* Cannot be instantiated */}
}
