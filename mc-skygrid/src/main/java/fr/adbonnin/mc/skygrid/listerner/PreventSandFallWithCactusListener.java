package fr.adbonnin.mc.skygrid.listerner;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class PreventSandFallWithCactusListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    private void onSandFall(EntityChangeBlockEvent event) {
        final Block block = event.getBlock();

        final boolean isFallingSand = event.getEntityType() == EntityType.FALLING_BLOCK &&
                event.getTo() == Material.AIR &&
                block.getType() == Material.SAND &&
                Material.CACTUS == block.getRelative(BlockFace.UP).getType();

        if (!isFallingSand) {
            return;
        }

        event.setCancelled(true);
        block.getState().update(false, false); // Update the block to fix a visual client bug, but don't apply physics
    }
}
