package me.seetch.basicspawnprotection.utils;

import cn.nukkit.Server;
import cn.nukkit.utils.Config;
import lombok.Getter;

import java.util.List;

@Getter
public class Settings {

    private final List<String> worlds;

    private final boolean voidTpEnabled;
    private final int voidTpLevel;

    private final boolean alwaysDayEnabled;
    private final boolean alwaysNightEnabled;

    public Settings(Config config) {
        this.worlds = config.getStringList("worlds");

        this.voidTpEnabled = config.getBoolean("void-tp.enable");
        this.voidTpLevel = config.getInt("void-tp.level");

        this.alwaysDayEnabled = config.getBoolean("always-day");
        this.alwaysNightEnabled = config.getBoolean("always-night");
    }
}