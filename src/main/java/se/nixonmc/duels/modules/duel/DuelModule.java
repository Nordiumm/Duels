package se.nixonmc.duels.modules.duel;

import se.nixonmc.duels.core.DuelsCore;
import se.nixonmc.duels.core.module.DuelsModule;
import se.nixonmc.duels.modules.kit.KitSystem;
import se.nixonmc.duels.modules.kit.KitTestCommand;

public class DuelModule implements DuelsModule {
    private final DuelSystem duelSystem;
    private final DuelsCore core;
    private final KitSystem kitSystem;

    public DuelModule(DuelsCore core) {
        this.core = core;

        DuelManager duelManager = new DuelManager();
        DuelRequestManager requestManager = new DuelRequestManager();
        kitSystem = new KitSystem();

        this.duelSystem = new DuelSystem(
                duelManager,
                requestManager,
                kitSystem
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
        core.getPlugin().getCommand("testkit").setExecutor(
                new KitTestCommand(kitSystem)
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