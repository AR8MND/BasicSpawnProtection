package me.seetch.basicspawnprotection;

import cn.nukkit.Server;
import cn.nukkit.level.Level;
import cn.nukkit.plugin.PluginBase;
import me.seetch.basicspawnprotection.utils.Settings;

public class BasicSpawnProtectionPlugin extends PluginBase {

    protected Settings settings;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        settings = new Settings(this.getConfig());
        this.getServer().getPluginManager().registerEvents(new EventListener(this), this);

        this.getServer().getScheduler().scheduleDelayedRepeatingTask(this, () -> {
            if (settings.isAlwaysDayEnabled()) {
                for (Level w : Server.getInstance().getLevels().values()) {
                    if (settings.getWorlds().contains(w.getFolderName())) {
                        w.setTime(6000);
                    }
                }
            }
            if (settings.isAlwaysNightEnabled()) {
                for (Level w : Server.getInstance().getLevels().values()) {
                    if (settings.getWorlds().contains(w.getFolderName())) {
                        w.setTime(18000);
                    }
                }
            }
        }, 20, 200);
    }
}
