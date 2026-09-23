package se.nixonmc.duels.modules.duel;

import se.nixonmc.duels.core.DuelsCore;
import se.nixonmc.duels.core.module.DuelsModule;

public class DuelModule implements DuelsModule {
    private final DuelSystem duelSystem;
    private final DuelsCore core;

    public DuelModule(DuelsCore core) {
        this.core = core;

        DuelManager duelManager = new DuelManager();
        DuelRequestManager requestManager = new DuelRequestManager();
        this.duelSystem = new DuelSystem(
                duelManager,
                requestManager
        );
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

        core.getPlugin().getCommand("duel").setExecutor(
                new DuelCommand(core, duelSystem)
        );
    }

    @Override
    public void disable() {
        core.getPlugin().getLogger().info(
                "Duel module disabled!"
        );
    }

    public DuelSystem getDuelSystem() {
        return duelSystem;
    }
}