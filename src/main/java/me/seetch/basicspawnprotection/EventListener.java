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
import cn.nukkit.event.level.WeatherChangeEvent;
import cn.nukkit.event.player.*;
import cn.nukkit.level.Position;

import java.awt.*;

public class EventListener implements Listener {

    private BasicSpawnProtectionPlugin plugin;

    public EventListener(BasicSpawnProtectionPlugin plugin) {
        this.plugin = plugin;
    }

    private boolean checkSpawnProtection(Player player) {
        if (plugin.settings.getWorlds().contains(player.getLevel().getFolderName())) {
            return !player.hasPermission("basicspawnprotect.bypass");
        }

        return false;
    }

    private boolean checkSpawnProtection(Entity entity) {
        if (plugin.settings.getWorlds().contains(entity.getLevel().getFolderName())) {
            if (entity instanceof Player) {
                return !((Player) entity).hasPermission("basicspawnprotect.bypass");
            }
        }

        return false;
    }

    private boolean checkSpawnProtection(Position position) {
        return plugin.settings.getWorlds().contains(position.getLevel().getFolderName());
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
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
    public void onWeatherChange(WeatherChangeEvent event) {
        if (plugin.settings.getWorlds().contains(event.getLevel().getFolderName())) {
            event.setCancelled();
        }
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
    public void onBlockBurn(BlockBurnEvent event) {
        if (this.checkSpawnProtection(event.getBlock())) {
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
    public void onInteract(PlayerInteractEvent event) {
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
    public void onEntityExplode(EntityExplodeEvent event) {
        if (this.checkSpawnProtection(event.getPosition())) {
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
    public void onItemFrameUse(ItemFrameUseEvent event) {
        if (this.checkSpawnProtection(event.getPlayer())) {
            event.setCancelled();
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlayerBucketFill(PlayerBucketFillEvent event) {
        if (this.checkSpawnProtection(event.getPlayer())) {
            event.setCancelled();
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        if (this.checkSpawnProtection(event.getPlayer())) {
            event.setCancelled();
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onBlockIgnite(BlockIgniteEvent event) {
        if (this.checkSpawnProtection(event.getEntity())) {
            event.setCancelled();
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onSignColorChange(SignColorChangeEvent event) {
        if (this.checkSpawnProtection(event.getPlayer())) {
            event.setCancelled();
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onSignGlow(SignGlowEvent event) {
        if (this.checkSpawnProtection(event.getPlayer())) {
            event.setCancelled();
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (plugin.settings.isVoidTpEnabled()) {
            if (player.getLocation().getY() < plugin.settings.getVoidTpLevel()) {
                player.teleport(getSpawn(player));
            }
        }
    }

    private Position getSpawn(Player player) {
        for (String w : plugin.settings.getWorlds()) {
            if (w.contains(player.getLevel().getFolderName())) {
                return Server.getInstance().getLevelByName(w).getSpawnLocation();
            }
        }
        return Server.getInstance().getDefaultLevel().getSpawnLocation();
    }
}
