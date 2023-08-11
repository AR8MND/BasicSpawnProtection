package me.seetch.basicspawnprotection;

import cn.nukkit.plugin.PluginBase;

public class BasicSpawnProtectionPlugin extends PluginBase {

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents(new EventListener(this), this);
    }
}
