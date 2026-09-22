package se.nixonmc.duels.modules.duel;

import se.nixonmc.duels.core.DuelsCore;
import se.nixonmc.duels.core.module.DuelsModule;

public class DuelModule implements DuelsModule {
    private final DuelSystem duelSystem;
    private final DuelsCore core;

    public DuelModule(DuelsCore core) {
        this.core = core;

        DuelManager duelManager = new DuelManager();
        this.duelSystem = new DuelSystem(duelManager);
    }

    @Override
    public String getName() {
        return "duel";
    }

    @Override
    public void enable() {
        core.getPlugin().getLogger().info(
                "Duel module enabled!"
        );
    }

    @Override
    public void disable() {
        core.getPlugin().getLogger().info(
                "Duel module disabled!"
        );
    }
}