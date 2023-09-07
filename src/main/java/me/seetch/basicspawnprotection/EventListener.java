package me.seetch.basicspawnprotection;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.*;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityExplodeEvent;
import cn.nukkit.event.player.PlayerDropItemEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;

import java.util.List;

public class EventListener implements Listener {

    private final List<String> worlds;

    public EventListener(BasicSpawnProtectionPlugin plugin) {
        this.worlds = plugin.getConfig().getStringList("worlds");
    }

    private boolean checkSpawnProtection(Player player) {
        if (worlds.contains(player.getLevel().getFolderName())) {
            return !player.hasPermission("basicspawnprotect.bypass");
        }

        return false;
    }

    private boolean checkSpawnProtection(Entity entity) {
        if (worlds.contains(entity.getLevel().getFolderName())) {
            if (entity instanceof Player) {
                return !((Player) entity).hasPermission("basicspawnprotect.bypass");
            }
        }

        return false;
    }

    private boolean checkSpawnProtection(Position position) {
        return worlds.contains(position.getLevel().getFolderName());
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        if (this.checkSpawnProtection(event.getPlayer())) {
            event.setCancelled();
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (this.checkSpawnProtection(event.getPlayer())) {
            event.setCancelled();
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onBlockUpdate(BlockUpdateEvent event) {
        if (this.checkSpawnProtection(event.getBlock())) {
            event.setCancelled();
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent event) {
        if (this.checkSpawnProtection(event.getPlayer())) {
            event.setCancelled();
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onEntityExplode(EntityExplodeEvent event) {
        if (this.checkSpawnProtection(event.getPosition())) {
            event.setCancelled();
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (this.checkSpawnProtection(event.getPlayer())) {
            event.setCancelled();
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onSignChange(SignChangeEvent event) {
        if (this.checkSpawnProtection(event.getPlayer())) {
            event.setCancelled();
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onEntityDamage(EntityDamageEvent event) {
        if (this.checkSpawnProtection(event.getEntity())) {
            event.setCancelled();
        }

        if (event instanceof EntityDamageByEntityEvent) {
            Entity damager = ((EntityDamageByEntityEvent) event).getDamager();

            if (this.checkSpawnProtection(damager)) {
                event.setCancelled();
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onItemFrameUse(ItemFrameUseEvent event) {
        if (this.checkSpawnProtection(event.getPlayer())) {
            event.setCancelled();
        }
    }
}
