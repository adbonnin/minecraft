package fr.adbonnin.mc.skygrid;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;

import static fr.adbonnin.xtra.base.XtraObjects.NULL_DEFAULT;
import static fr.adbonnin.xtra.base.XtraStrings.leftPad;
import static fr.adbonnin.xtra.bukkit.XtraMinecraft.CHUNK_SIZE;
import static java.lang.String.valueOf;

public final class XtraSkyGrid {

    public static final int BLOCK_SPACE = 4;

    public static final int DEFAULT_PAD_COORDINATE = 4;

    public static void chunkIteration(int height, LocationFunction locationFunction) {
        for (int y = 0; y < height; y += BLOCK_SPACE) {
            for (int x = 0; x < CHUNK_SIZE; x += BLOCK_SPACE) {
                for (int z = 0; z < CHUNK_SIZE; z += BLOCK_SPACE) {
                    locationFunction.apply(x, y, z);
                }
            }
        }
    }

    public static String chunkToString(Chunk chunk) {
        return chunk == null ? NULL_DEFAULT : "{" +
                "x: " + leftPad(valueOf(chunk.getX()), DEFAULT_PAD_COORDINATE) + ", " +
                "z: " + leftPad(valueOf(chunk.getZ()), DEFAULT_PAD_COORDINATE) + "}";
    }

    public static String blockToString(Block block) {
        return block == null ? NULL_DEFAULT : "{" +
                "x: " + leftPad(valueOf(block.getX()), DEFAULT_PAD_COORDINATE) + ", " +
                "y: " + leftPad(valueOf(block.getY()), DEFAULT_PAD_COORDINATE) + ", " +
                "z: " + leftPad(valueOf(block.getZ()), DEFAULT_PAD_COORDINATE) + ", " +
                "type: " + block.getType().name() + "}";
    }

    public static String locationToString(Location location) {
        return location == null ? NULL_DEFAULT : "{" +
                "x: " + leftPad(valueOf(location.getX()), DEFAULT_PAD_COORDINATE) + ", " +
                "y: " + leftPad(valueOf(location.getY()), DEFAULT_PAD_COORDINATE) + ", " +
                "z: " + leftPad(valueOf(location.getZ()), DEFAULT_PAD_COORDINATE) + "}";
    }

    private XtraSkyGrid() { /* Cannot be instantiated */}
}
