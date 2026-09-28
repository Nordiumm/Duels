package se.nixonmc.duels.modules.duel;

import se.nixonmc.duels.core.DuelsCore;
import se.nixonmc.duels.core.module.DuelsModule;
import se.nixonmc.duels.modules.ArenaSystem.ArenaCreatorCommands;
import se.nixonmc.duels.modules.ArenaSystem.ArenaManager;
import se.nixonmc.duels.modules.kit.KitSystem;
import se.nixonmc.duels.modules.kit.KitTestCommand;

public class DuelModule implements DuelsModule {

    private DuelSystem duelSystem;
    private final DuelsCore core;
    private final KitSystem kitSystem;
    private ArenaManager arenaManager;

    public DuelModule(DuelsCore core) {
        this.core = core;
        this.kitSystem = new KitSystem();
    }

    @Override
    public String getName() {
        return "duel";
    }

    @Override
    public void enable() {
        arenaManager = new ArenaManager(core);

        DuelManager duelManager = new DuelManager();
        DuelRequestManager requestManager = new DuelRequestManager();

        this.duelSystem = new DuelSystem(
                duelManager,
                requestManager,
                kitSystem,
                arenaManager
        );

        DuelKitMenu kitMenu = new DuelKitMenu(kitSystem);
        DuelMapMenu mapMenu = new DuelMapMenu(arenaManager);
        DuelConfirmMenu confirmMenu = new DuelConfirmMenu(
                kitMenu,
                duelSystem
        );

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
                new DuelKitMenuListener(core, kitMenu, mapMenu, confirmMenu, duelSystem),
                core.getPlugin()
        );

        core.getPlugin().getServer().getPluginManager().registerEvents(
                new DuelEndListener(core, duelSystem, arenaManager),
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