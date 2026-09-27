package se.nixonmc.duels.modules.duel;

import se.nixonmc.duels.core.DuelsCore;
import se.nixonmc.duels.core.module.DuelsModule;
import se.nixonmc.duels.modules.ArenaSystem.ArenaCreatorCommands;
import se.nixonmc.duels.modules.ArenaSystem.ArenaManager;
import se.nixonmc.duels.modules.kit.KitSystem;
import se.nixonmc.duels.modules.kit.KitTestCommand;

public class DuelModule implements DuelsModule {

    private final DuelSystem duelSystem;
    private final DuelsCore core;
    private final KitSystem kitSystem;
    private ArenaManager arenaManager;

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
        DuelKitMenu kitMenu = new DuelKitMenu(kitSystem);
        arenaManager = new ArenaManager(core);

        core.getPlugin().getLogger().info(
                "Duel module enabled!"
        );

        core.getPlugin().getCommand("duel").setExecutor(
                new DuelCommand(core, duelSystem, kitMenu)
        );

        core.getPlugin().getCommand("testkit").setExecutor(
                new KitTestCommand(kitSystem)
        );

        core.getPlugin().getCommand("arena").setExecutor(
                new ArenaCreatorCommands(arenaManager, core)
        );

        core.getPlugin().getServer().getPluginManager().registerEvents(
                new DuelKitMenuListener(kitMenu),
                core.getPlugin()
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