package se.nixonmc.duels;

import org.bukkit.plugin.java.JavaPlugin;
import se.nixonmc.duels.core.DuelsCore;

public final class Main extends JavaPlugin {

    private DuelsCore core;

    @Override
    public void onEnable() {
        core = new DuelsCore(this);
        core.enable();
    }

    @Override
    public void onDisable() {
        if (core != null) {
            core.disable();
        }
    }

    public DuelsCore getCore() {
        return core;
    }
}